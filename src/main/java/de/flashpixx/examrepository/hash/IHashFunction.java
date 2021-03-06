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

import javax.annotation.Nonnull;
import java.util.stream.Stream;

/**
 * hash value
 */
public interface IHashFunction
{
    /**
     * adds a new value
     *
     * @param p_value stringvalue
     * @return self reference
     */
    IHashFunction putstring( @Nonnull final String p_value );

    /**
     * add a new byte value
     *
     * @param p_value byte value
     * @return self reference
     */
    IHashFunction putbyte( final byte p_value );

    /**
     * add all stream elements
     *
     * @param p_stream string stream
     * @return self reference
     */
    IHashFunction putstring( @Nonnull final Stream<String> p_stream );

    /**
     * adds all stream elements
     *
     * @param p_stream byte stream
     * @return self reference
     */
    IHashFunction putbyte( @Nonnull final Stream<Byte> p_stream );

    /**
     * returns the hash value of the data
     *
     * @return  hash
     */
    String hash();

}
