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
import de.flashpixx.examrepository.hash.CHash;
import de.flashpixx.examrepository.metadata.IMetaData;

import javax.annotation.Nonnull;
import java.text.MessageFormat;
import java.util.LinkedHashSet;
import java.util.Set;


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
     * binary data
     */
    private final IBinary m_binary;
    /**
     * updated meta-data
     */
    private final Set<IMetaData> m_updatedmetadata = new LinkedHashSet<>();

    /**
     * ctor
     *
     * @param p_initmetadata initialized meta-data
     * @param p_binary binary data
     * @todo binary data hashing is missing
     */
    private CExam(
            @Nonnull final IMetaData p_initmetadata,
            @Nonnull final IBinary p_binary
    )
    {
        m_binary = p_binary;
        m_initialmetadata = p_initmetadata;

        m_hash = CHash.INSTANCE
                      .get()
                      .put( m_initialmetadata.institution() )
                      .put( m_initialmetadata.institutionemail() )
                      .put( m_initialmetadata.author() )
                      .put( m_initialmetadata.authoremail() )
                      .put( m_initialmetadata.comment() )
                      .hash();

    }

    @Override
    public final IMetaData initialmetadata()
    {
        return m_initialmetadata;
    }

    @Override
    public final Set<IMetaData> updatedmetadata()
    {
        return m_updatedmetadata;
    }

    /**
     * update meta-data
     *
     * @todo missing
     * @return self reference
     */
    @Override
    public final IExam updatemetadata()
    {
        return this;
    }

    @Override
    public final String hash()
    {
        return m_hash;
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
        return MessageFormat.format( "[ {0} | {1} | {2} ]", m_hash, m_initialmetadata, m_updatedmetadata );
    }

    /**
     * factory
     *
     * @param p_initmetadata initial meta-data
     * @param p_binary binary data
     * @return exam
     */
    public static IExam from(
            @Nonnull final IMetaData p_initmetadata,
            @Nonnull final IBinary p_binary
    )
    {
        return new CExam( p_initmetadata, p_binary );
    }
}
