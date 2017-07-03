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

package de.flashpixx.examrepository.metadata;

import javax.annotation.Nonnull;
import java.util.Map;
import java.util.TreeMap;

/**
 * meta-data implementation
 */
public final class CMetaData implements IMetaData
{
    /**
     * map with data
     */
    private final Map<String, String> m_data = new TreeMap<>( String.CASE_INSENSITIVE_ORDER );


    /**
     * ctor
     *
     * @param p_institute institute
     * @param p_institutemail institute email
     * @param p_author author
     * @param p_authormail author email
     */
    private CMetaData(
            @Nonnull final String p_institute, @Nonnull final String p_institutemail,
            @Nonnull final String p_author, @Nonnull final String p_authormail,
            @Nonnull final String p_comment
    )
    {
        m_data.put( "institute", p_institute );
        m_data.put( "institutemail", p_institutemail );

        m_data.put( "author", p_author );
        m_data.put( "authormail", p_authormail );

        m_data.put( "comment", p_comment );
    }


    @Override
    public final String author()
    {
        return m_data.get( "author" );
    }

    @Override
    public final String authoremail()
    {
        return m_data.get( "authormail" );
    }

    @Override
    public final String institution()
    {
        return m_data.get( "institute" );
    }

    @Override
    public final String institutionemail()
    {
        return m_data.get( "institutemail" );
    }

    @Override
    public final String comment()
    {
        return m_data.get( "comment" );
    }

    /**
     * factory
     *
     * @param p_institute institute
     * @param p_institutemail institute email
     * @param p_author author
     * @param p_authormail author email
     * @return meta-data object
     */
    public static IMetaData from(
            @Nonnull final String p_institute, @Nonnull final String p_institutemail,
            @Nonnull final String p_author, @Nonnull final String p_authormail
    )
    {
        return from(
                p_institute, p_institutemail,
                p_author, p_authormail,
                ""
        );
    }

    @Override
    public final int hashCode()
    {
        return m_data.values().stream().map( String::hashCode ).reduce( 0, ( i, j ) -> i ^ j );
    }

    @Override
    public final boolean equals( final Object p_object )
    {
        return ( p_object != null ) && ( p_object instanceof IMetaData ) && ( this.hashCode() == p_object.hashCode() );
    }

    @Override
    public final String toString()
    {
        return m_data.toString();
    }

    /**
     * factory
     *
     * @param p_institute institute
     * @param p_institutemail institute email
     * @param p_author author
     * @param p_authormail author email
     * @return meta-data object
     */
    public static IMetaData from(
            @Nonnull final String p_institute, @Nonnull final String p_institutemail,
            @Nonnull final String p_author, @Nonnull final String p_authormail,
            @Nonnull final String p_comment
    )
    {
        return new CMetaData(
                p_institute, p_institutemail,
                p_author, p_authormail,
                p_comment
        );
    }
}
