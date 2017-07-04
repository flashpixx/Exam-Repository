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

import de.flashpixx.examrepository.binary.IBinary;
import de.flashpixx.examrepository.binary.access.EDataWriter;
import de.flashpixx.examrepository.hash.CHash;
import de.flashpixx.examrepository.metadata.IMetaData;

import javax.annotation.Nonnull;
import java.text.MessageFormat;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Stream;


/**
 * interface of exam
 */
public interface IExam
{
    /**
     * empty object
     */
    IExam EMPTY = new IExam()
    {
        /**
         * hash
         */
        private final String m_hash = CHash.INSTANCE.get().putstring( "" ).hash();

        @Override
        public final IMetaData initialmetadata()
        {
            return IMetaData.EMPTY;
        }

        @Override
        public final IExam addmetadata( @Nonnull final IMetaData... p_data )
        {
            return this;
        }

        @Override
        public final Stream<IMetaData> additionalmetadata()
        {
            return Stream.empty();
        }

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
            return Collections.emptySet();
        }

        @Override
        public final Stream<IBinary> binary()
        {
            return Stream.empty();
        }

        @Override
        public final IExam store( final EDataWriter p_writer, final String p_prefix )
        {
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
            return MessageFormat.format( "[ {0} | {1} | {2} ]", m_hash, IMetaData.EMPTY, Collections.emptySet() );
        }
    };

    /**
     * initial meta-data
     *
     * @return meta-data
     */
    IMetaData initialmetadata();

    /**
     * adds a new meta-data set
     *
     * @param p_data meta-data (chronologically)
     * @return self reference
     */
    IExam addmetadata( @Nonnull final IMetaData... p_data );

    /**
     * returns additional meta-data
     *
     * @return stream of meta-data (chronologically)
     */
    Stream<IMetaData> additionalmetadata();

    /**
     * updates meta-data
     *
     * @return self reference
     */
    IExam lookup();

    /**
     * hash of the exam
     *
     * @return hash
     */
    String hash();

    /**
     * predecessor nodes
     *
     * @return set of predecessor
     */
    Set<IExam> predecessor();

    /**
     * binary data
     *
     * @return binary data
     */
    Stream<IBinary> binary();

    /**
     * writer
     *
     * @param p_writer writer
     * @param p_prefix prefix
     * @return self reference
     */
    IExam store( final EDataWriter p_writer, final String p_prefix );

}
