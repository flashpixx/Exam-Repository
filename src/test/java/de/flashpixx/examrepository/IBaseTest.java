/*
 * @cond LICENSE
 * ######################################################################################
 * # LGPL License                                                                       #
 * #                                                                                    #
 * # This file is part of the Exam-Repository                                           #
 * # Copyright (c) 2017, Exam-Repository <philipp.kraus@flashpixx.de>                   #
 * # This program is free software: you can redistribute it and/or modify               #
 * # it under the terms of the GNU Lesser General Public License as                     #
 * # published by the Free Software Foundation, either version 3 of the                 #
 * # License, or (at your option) any later version.                                    #
 * #                                                                                    #
 * # This program is distributed in the hope that it will be useful,                    #
 * # but WITHOUT ANY WARRANTY; without even the implied warranty of                     #
 * # MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the                      #
 * # GNU Lesser General Public License for more details.                                #
 * #                                                                                    #
 * # You should have received a copy of the GNU Lesser General Public License           #
 * # along with this program. If not, see http://www.gnu.org/licenses/                  #
 * ######################################################################################
 * @endcond
 */

package de.flashpixx.examrepository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.AssumptionViolatedException;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;


/**
 * base test class with helpers
 */
public abstract class IBaseTest
{

    /**
     * invoke all test manually
     */
    protected final void invoketest()
    {
        final Set<Method> l_before = this.before();

        Arrays.stream( this.getClass().getMethods() )
              .filter( i -> i.getAnnotation( Test.class ) != null )
              .filter( i -> i.getAnnotation( Ignore.class ) == null )
              .forEach( i -> this.invoke( i, l_before ) );
    }

    /**
     * invoke method and read if possible the data-provider
     *
     * @param p_method method
     * @param p_before before method
     */
    private void invoke( final Method p_method, final Set<Method> p_before )
    {
        // method uses a data-provider
        if ( p_method.getAnnotation( UseDataProvider.class ) == null )
            this.execute( p_method, p_before );
        else
        {
            final Object[] l_arguments;

            try
            {
                l_arguments = (Object[]) this.getClass().getDeclaredMethod( p_method.getAnnotation( UseDataProvider.class ).value() ).invoke( null );

            }
            catch ( final InvocationTargetException l_exception )
            {
                Assert.assertTrue( l_exception.getTargetException().toString(), false );
                return;
            }
            catch ( final IllegalAccessException | NoSuchMethodException l_exception )
            {
                Assert.assertTrue( l_exception.toString(), false );
                return;
            }

            Arrays.stream( l_arguments ).forEach( i -> this.execute( p_method, p_before, i ) );
        }
    }

    /**
     * invokes the method within the current object context
     *
     * @param p_method method
     * @param p_before before method
     * @param p_arguments optional arguments
     */
    private void execute( final Method p_method, final Set<Method> p_before, final Object... p_arguments )
    {
        try
        {
            if ( !p_before.isEmpty() )
                p_before.forEach( i ->
                {
                    try
                    {
                        i.invoke( this );
                    }
                    catch ( final IllegalAccessException | InvocationTargetException l_exception )
                    {
                        l_exception.printStackTrace();
                        Assert.assertTrue( false );
                    }
                } );

            p_method.invoke( this, p_arguments );
        }
        catch ( final AssumptionViolatedException l_exception )
        {
        }
        catch ( final InvocationTargetException l_exception )
        {
            if ( l_exception.getTargetException() instanceof AssumptionViolatedException )
                return;

            if ( !p_method.getAnnotation( Test.class ).expected().isInstance( l_exception.getTargetException() ) )
            {
                l_exception.getTargetException().printStackTrace();
                Assert.assertTrue( false );
            }
        }
        catch ( final IllegalAccessException l_exception )
        {
            Assert.assertTrue( l_exception.toString(), false );
        }
    }

    /**
     * reads the before annotated methods
     *
     * @return optional before method
     */
    private Set<Method> before()
    {
        return Arrays.stream( this.getClass().getMethods() )
                     .filter( i -> i.getAnnotation( Before.class ) != null )
                     .filter( i -> i.getAnnotation( Ignore.class ) == null )
                     .collect( Collectors.toSet() );
    }


    /**
     * writes data to a json file
     *
     * @param p_filename filename
     * @param p_data data as any object
     */
    protected final void tojson( final String p_filename, final Object p_data )
    {
        try
        {
            new ObjectMapper().writeValue( new File( p_filename ), p_data );
        }
        catch ( final IOException l_exception )
        {
            throw new RuntimeException( l_exception );
        }
    }

    /**
     * reads data from a json file which contains a json object
     *
     * @param p_filename filename
     * @return data-map
     */
    @SuppressWarnings( "unchecked" )
    protected final CDataMap fromjsonobject( final String p_filename )
    {
        try
        {
            return new CDataMap( new ObjectMapper().readValue( FileUtils.readFileToString( new File( p_filename ), "utf-8" ), Map.class ) );
        }
        catch ( final IOException l_exception )
        {
            throw new RuntimeException( l_exception );
        }
    }

    /**
     * reads data from a json file which contains a list of json objects
     *
     * @param p_filename filename
     * @return list with data-maps
     */
    @SuppressWarnings( "unchecked" )
    protected final List<CDataMap> fromjsonlist( final String p_filename  )
    {
        try
        {
            return Arrays.stream(
                new ObjectMapper().readValue(
                    FileUtils.readFileToString( new File( p_filename ), "utf-8" ),
                    Map[].class
                )
            )
                         .map( i -> new CDataMap( (Map<String, Object>) i ) )
                         .collect( Collectors.toList() );
        }
        catch ( final IOException l_exception )
        {
            throw new RuntimeException( l_exception );
        }
    }

    /**
     * writes data to a yaml file
     *
     * @param p_filename filename
     * @param p_data data as any object
     */
    protected final void toyaml( final String p_filename, final Object p_data )
    {
        try
        {
            new Yaml().dump( p_data, new FileWriter( new File( p_filename ) ) );
        }
        catch ( final IOException l_exception )
        {
            throw new RuntimeException( l_exception );
        }
    }

    /**
     * reads data from a yaml file
     *
     * @param p_filename filename
     * @return data-map
     */
    @SuppressWarnings( "unchecked" )
    protected final CDataMap fromyaml( final String p_filename )
    {
        try
            (
                final InputStream l_stream = new FileInputStream( p_filename );
            )
        {

            return new CDataMap( (Map<String, Object>) new Yaml().load( l_stream ) );

        }
        catch ( final IOException l_exception )
        {
            throw new RuntimeException( l_exception );
        }
    }

    /**
     * writes data to a json file
     *
     * @param p_filename filename
     * @param p_data data as map
     */
    protected final void tocsv( final String p_filename, final Stream<Collection<?>> p_data )
    {
        try
            (
                final FileWriter l_write = new FileWriter( p_filename );
            )
        {
            final CSVPrinter l_output = new CSVPrinter( l_write, CSVFormat.DEFAULT );
            p_data.forEach( i ->
            {
                try
                {
                    l_output.printRecords( i );
                }
                catch ( final IOException l_exception )
                {
                    throw new UncheckedIOException( l_exception );
                }
            } );
        }
        catch ( final IOException l_exception )
        {
            throw new RuntimeException( l_exception );
        }
    }

    /**
     * reads data from a csv file
     *
     * @param p_filename filename
     * @return list with string data
     */
    protected final List<List<String>> fromcsv( final String p_filename )
    {
        try
            (
                final FileReader l_reader = new FileReader( new File( p_filename ) );
            )
        {
            return CSVFormat.DEFAULT.parse( l_reader )
                             .getRecords()
                             .stream()
                             .map( i -> IntStream.range( 0, i.size() ).mapToObj( i::get ).collect( Collectors.toList() ) )
                             .collect( Collectors.toList() );
        }
        catch ( final IOException l_exception )
        {
            throw new RuntimeException( l_exception );
        }
    }

    /**
     * class to browse yaml and json data
     */
    protected static final class CDataMap
    {
        /**
         * map data
         */
        private final Map<String, Object> m_data;

        /**
         * ctor
         *
         * @param p_data object map
         */
        public CDataMap( final Map<String, Object> p_data )
        {
            m_data = Collections.unmodifiableMap( p_data );
        }

        /**
         * returns a configuration value
         *
         * @param p_path path of the element
         * @tparam T returning type
         * @return value or null
         */
        public final <T> T get( final String... p_path )
        {
            return recursivedescent( m_data, p_path );
        }

        /**
         * returns a configuration value or on not
         * existing the default value
         *
         * @param p_default default value
         * @param p_path path of the element
         * @tparam T returning type
         * @return value / default vaue
         */
        public final <T> T getOrDefault( final T p_default, final String... p_path )
        {
            final T l_result = recursivedescent( m_data, p_path );
            return l_result == null
                   ? p_default
                   : l_result;
        }

        /**
         * recursive descent
         *
         * @param p_map configuration map
         * @param p_path path
         * @tparam T returning type parameter
         * @return value
         */
        @SuppressWarnings( "unchecked" )
        private static <T> T recursivedescent( final Map<String, ?> p_map, final String... p_path )
        {
            if ( ( p_path == null ) || ( p_path.length == 0 ) )
                throw new RuntimeException( "path need not to be empty" );

            final Object l_data = p_map.get( p_path[0].toLowerCase( Locale.ROOT ) );
            return ( p_path.length == 1 ) || ( l_data == null )
                   ? (T) l_data
                   : (T) recursivedescent( (Map<String, ?>) l_data, Arrays.copyOfRange( p_path, 1, p_path.length ) );
        }
    }

}
