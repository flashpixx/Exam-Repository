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

package de.flashpixx.examrepository.binary;

import de.flashpixx.examrepository.hash.CHash;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;

import javax.annotation.Nonnull;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;


/**
 * binary storage
 */
public final class CBinary implements IBinary
{
    /**
     * stored data
     */
    private final byte[] m_data;
    /**
     * data hash
     */
    private final String m_hash;
    /**
     * name
     */
    private final String m_name;

    /**
     * ctor
     *
     * @param p_name name
     * @param p_stream input stream
     */
    private CBinary( @Nonnull final String p_name, @Nonnull final InputStream p_stream ) throws IOException
    {
        m_name = p_name;
        m_data = IOUtils.toByteArray( p_stream );
        m_hash = CHash.INSTANCE.get().putstring( p_name ).putbyte( Arrays.stream( ArrayUtils.toObject( m_data ) ) ).hash();
    }

    @Override
    public final String hash()
    {
        return m_hash;
    }

    @Override
    public final InputStream stream()
    {
        return new ByteArrayInputStream( m_data );
    }

    @Override
    public final String name()
    {
        return m_name;
    }

    @Override
    public final int hashCode()
    {
        return m_hash.hashCode();
    }

    @Override
    public final boolean equals( final Object p_object )
    {
        return ( p_object != null ) && ( p_object instanceof IBinary ) && ( this.hashCode() == p_object.hashCode() );
    }

    /**
     * factory to create a binary object from stream
     *
     * @param p_name name
     * @param p_stream input stream
     * @return binary
     */
    public static IBinary from( @Nonnull final String p_name, @Nonnull final InputStream p_stream )
    {
        try
        {
            return new CBinary( p_name, p_stream );
        }
        catch ( final IOException l_exception )
        {
            return IBinary.EMPTY;
        }
    }
}
