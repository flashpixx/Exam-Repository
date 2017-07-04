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

/**
 * defines the meta-data of the element
 */
public interface IMetaData
{
    /**
     * empty object
     */
    IMetaData EMPTY = new IMetaData()
    {
        @Override
        public final String author()
        {
            return "";
        }

        @Override
        public final String authoremail()
        {
            return "";
        }

        @Override
        public final String institution()
        {
            return "";
        }

        @Override
        public final String institutionemail()
        {
            return "";
        }

        @Override
        public final String comment()
        {
            return "";
        }

        @Override
        public final int hashCode()
        {
            return 0;
        }

        @Override
        public final boolean equals( final Object p_object )
        {
            return ( p_object != null ) && ( p_object instanceof IMetaData ) && ( this.hashCode() == p_object.hashCode() );
        }

        @Override
        public final String toString()
        {
            return "";
        }
    };

    /**
     * returns the author
     *
     * @return author
     */
    String author();

    /**
     * author email
     *
     * @return email
     */
    String authoremail();

    /**
     * institution
     *
     * @return intitution
     */
    String institution();

    /**
     * institution email
     *
     * @return email
     */
    String institutionemail();

    /**
     * any comment
     *
     * @return comment
     */
    String comment();

}
