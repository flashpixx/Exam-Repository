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

package de.flashpixx.examrepository.binary.access;

import de.flashpixx.examrepository.binary.CBinary;
import de.flashpixx.examrepository.binary.IBinary;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * data reader of files and directories
 */
public final class CFileReader implements IDataReader
{
    @Override
    public final Set<IBinary> apply( final String p_path )
    {
        if ( p_path.isEmpty() )
            return Collections.emptySet();

        final File l_path = new File( p_path );
        if ( l_path.isFile() )
            return Stream.of( l_path ).map( i -> read( i.getName(), i ) ).filter( i -> !i.equals( IBinary.EMPTY ) ).collect( Collectors.toSet() );

        try
        {
            return Files.walk( Paths.get( p_path ) )
                        .filter( Files::isRegularFile )
                        .map( i -> read( i.toFile().getName(), i.toFile() ) )
                        .filter( i -> !i.equals( IBinary.EMPTY ) )
                        .collect( Collectors.toSet() );
        }
        catch ( final IOException l_exception )
        {
            return Collections.emptySet();
        }
    }

    /**
     * data reading
     *
     * @param p_name name
     * @param p_file input file
     * @return binary
     */
    private static IBinary read( @Nonnull final String p_name, @Nonnull final File p_file )
    {
        try
        (
            final InputStream l_stream = new FileInputStream( p_file );
        )
        {
            return CBinary.from( p_name, l_stream );
        }
        catch ( final IOException l_exception )
        {
            return IBinary.EMPTY;
        }
    }

}
