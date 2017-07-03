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

package de.flashpixx.examrepository;

import de.flashpixx.examrepository.binary.IBinary;
import de.flashpixx.examrepository.exam.CExam;
import de.flashpixx.examrepository.metadata.CMetaData;
import org.junit.Test;

/**
 * exam test
 */
public final class TestCExam extends IBaseTest
{
    /**
     * test exam
     */
    @Test
    public final void exam()
    {
        System.out.println(
            CExam.from(
                CMetaData.from(
                    "TU-Clausthal", "info@tu-clausthal.de",
                    "Any Author", "any.author@tu-clausthal.de",
                    "a long description of the exam"
                ),

                IBinary.EMPTY
            )
        );
    }


    /**
     * main
     *
     * @param p_args command-line arguments
     */
    public static void main( final String[] p_args )
    {
        new TestCExam().invoketest();
    }
}
