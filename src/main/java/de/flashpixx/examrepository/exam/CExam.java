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

package de.flashpixx.examrepository.exam;

import de.flashpixx.examrepository.binary.access.EDataReader;
import de.flashpixx.examrepository.binary.IBinary;
import de.flashpixx.examrepository.binary.access.EDataWriter;
import de.flashpixx.examrepository.binary.access.IDataWriter;
import de.flashpixx.examrepository.hash.CHash;
import de.flashpixx.examrepository.metadata.IMetaData;

import javax.annotation.Nonnull;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * exam structure
 */
public final class CExam implements IExam
{
    /**
     * hash value
     */
    private final String m_hash;
    /**
     * initialized meta-data
     */
    private final IMetaData m_initialmetadata;
    /**
     * updated meta-data
     */
    private final Set<IMetaData> m_additionalmetadata = new LinkedHashSet<>();
    /**
     * binary data
     */
    private final Set<IBinary> m_binary;
    /**
     * predecessor nodes
     */
    private final Set<IExam> m_predecessor;

    /**
     * ctor
     *
     * @param p_initmetadata initialized meta-data
     * @param p_binary binary data
     */
    private CExam(
            @Nonnull final IMetaData p_initmetadata,
            @Nonnull final Set<IBinary> p_binary,
            @Nonnull final Set<IExam> p_predecessor
    )
    {
        m_binary = p_binary;
        m_initialmetadata = p_initmetadata;
        m_predecessor = Collections.unmodifiableSet( p_predecessor );

        m_hash = CHash.INSTANCE
                      .get()
                      .putstring( m_initialmetadata.stream() )
                      .putstring( p_predecessor.stream().map( IExam::hash ) )
                      .putstring( p_binary.stream().map( IBinary::hash ) )
                      .hash();

    }

    @Override
    public final IMetaData initialmetadata()
    {
        return m_initialmetadata;
    }

    @Override
    public final IExam addmetadata( @Nonnull final IMetaData... p_data )
    {
        m_additionalmetadata.addAll( Arrays.asList( p_data ) );
        return this;
    }

    @Override
    public final Stream<IMetaData> additionalmetadata()
    {
        return m_additionalmetadata.stream();
    }

    /**
     * update meta-data
     *
     * @todo missing
     * @return self reference
     */
    @Override
    public final IExam lookup()
    {
        return this;
    }

    @Override
    public final String hash()
    {
        return m_hash;
    }

    @Override
    public final Set<IExam> predecessor()
    {
        return m_predecessor;
    }

    @Override
    public final Stream<IBinary> binary()
    {
        return m_binary.stream();
    }

    @Override
    public final IExam store( final EDataWriter p_writer, final String p_prefix )
    {
        final IDataWriter l_writer = p_writer.get();
        m_binary.forEach( i -> l_writer.accept( l_writer.concat( p_prefix, i.name() ), i.stream() ) );
        return this;
    }

    @Override
    public final int hashCode()
    {
        return m_hash.hashCode();
    }

    @Override
    public final boolean equals( final Object p_object )
    {
        return ( p_object != null ) && ( p_object instanceof IExam ) && ( this.hashCode() == p_object.hashCode() );
    }

    @Override
    public final String toString()
    {
        return MessageFormat.format(
                "[ {0} | {1} | {2} | {3} ]",
                m_hash,
                m_predecessor,
                m_initialmetadata,
                m_additionalmetadata
        );
    }

    /**
     * factory
     *
     * @param p_datareader data reader
     * @param p_initmetadata initial meta-data
     * @param p_binary binary data
     * @return exam
     */
    public static IExam from(
        @Nonnull final EDataReader p_datareader,
        @Nonnull final IMetaData p_initmetadata,
        @Nonnull final String p_binary
    )
    {
        return new CExam( p_initmetadata, p_datareader.get().apply( p_binary ), Collections.emptySet() );
    }

    /**
     * factory
     *
     * @param p_datareader data reader
     * @param p_initmetadata initial meta-data
     * @param p_binary binary data
     * @param p_predecessor predecessor nodes
     * @return exam
     */
    public static IExam from(
        @Nonnull final EDataReader p_datareader,
        @Nonnull final IMetaData p_initmetadata,
        @Nonnull final String p_binary,
        @Nonnull final IExam... p_predecessor
    )
    {
        return new CExam( p_initmetadata, p_datareader.get().apply( p_binary ), Arrays.stream( p_predecessor ).collect( Collectors.toSet() ) );
    }

}
