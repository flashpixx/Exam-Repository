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

package de.flashpixx.examrepository.hash;

import com.google.common.base.Charsets;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hasher;
import com.google.common.hash.Hashing;

import javax.annotation.Nonnull;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * hash factory
 */
public final class CHash implements Supplier<IHashFunction>
{
    /**
     * singletone instance
     */
    public static final CHash INSTANCE = new CHash();

    /**
     * ctor
     */
    private CHash()
    {}

    @Override
    public final IHashFunction get()
    {
        return new CBaseHashFunction( Hashing.sha256() );
    }


    /**
     * base hash function based on Google Guava
     *
     * @see https://github.com/google/guava/wiki/HashingExplained
     */
    private static final class CBaseHashFunction implements IHashFunction
    {
        /**
         * hasher
         */
        private final Hasher m_hasher;

        /**
         * ctor
         *
         * @param p_function hash function
         */
        CBaseHashFunction( @Nonnull final HashFunction p_function )
        {
            m_hasher = p_function.newHasher();
        }

        @Override
        public final IHashFunction putstring( @Nonnull final String p_value )
        {
            m_hasher.putString( p_value, Charsets.UTF_8 );
            return this;
        }

        @Override
        public final IHashFunction putbyte( final byte p_value )
        {
            m_hasher.putByte( p_value );
            return this;
        }

        @Override
        public final IHashFunction putstring( @Nonnull final Stream<String> p_stream )
        {
            p_stream.forEach( i -> m_hasher.putString( i, Charsets.UTF_8 ) );
            return this;
        }

        @Override
        public final IHashFunction putbyte( @Nonnull final Stream<Byte> p_stream )
        {
            p_stream.forEach( m_hasher::putByte );
            return this;
        }

        @Override
        public final String hash()
        {
            return m_hasher.hash().toString();
        }
    }
}
