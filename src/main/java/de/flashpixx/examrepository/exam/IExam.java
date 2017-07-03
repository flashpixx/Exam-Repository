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

import de.flashpixx.examrepository.metadata.IMetaData;

import java.util.Set;


/**
 * interface of exam
 */
public interface IExam
{

    /**
     * initial meta-data
     *
     * @return meta-data
     */
    IMetaData initialmetadata();

    /**
     * updated meta-data
     *
     * @return updated meta-data
     */
    Set<IMetaData> updatedmetadata();

    /**
     * updates meta-data
     *
     * @return self reference
     */
    IExam updatemetadata();

    /**
     * hash of the exam
     *
     * @return hash
     */
    String hash();

}
