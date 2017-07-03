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
import org.apache.commons.io.output.NullOutputStream;

import java.io.OutputStream;

/**
 * interface of binary element
 */
public interface IBinary
{
    /**
     * empty object
     */
    IBinary EMPTY = new IBinary()
    {
        /**
         * hash
         */
        private final String m_hash = CHash.INSTANCE.get().put( "" ).hash();
        /**
         * output stream
         */
        private final OutputStream m_stream = new NullOutputStream();

        @Override
        public final String hash()
        {
            return m_hash;
        }

        @Override
        public final OutputStream stream()
        {
            return m_stream;
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
    };

    /**
     * hash of the binary data
     *
     * @return hash
     */
    String hash();

    /**
     * output stream
     *
     * @return stream
     */
    OutputStream stream();
}
