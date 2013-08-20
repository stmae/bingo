package bingo.internal;

/* * Copyright (c) 2005 Flanders Interuniversitary Institute for Biotechnology (VIB)
 * *
 * * Authors : Steven Maere, Karel Heymans
 * *
 * * This program is free software; you can redistribute it and/or modify
 * * it under the terms of the GNU General Public License as published by
 * * the Free Software Foundation; either version 2 of the License, or
 * * (at your option) any later version.
 * *
 * * This program is distributed in the hope that it will be useful,
 * * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. 
 * * The software and documentation provided hereunder is on an "as is" basis,
 * * and the Flanders Interuniversitary Institute for Biotechnology
 * * has no obligations to provide maintenance, support,
 * * updates, enhancements or modifications.  In no event shall the
 * * Flanders Interuniversitary Institute for Biotechnology
 * * be liable to any party for direct, indirect, special,
 * * incidental or consequential damages, including lost profits, arising
 * * out of the use of this software and its documentation, even if
 * * the Flanders Interuniversitary Institute for Biotechnology
 * * has been advised of the possibility of such damage. See the
 * * GNU General Public License for more details.
 * *
 * * You should have received a copy of the GNU General Public License
 * * along with this program; if not, write to the Free Software
 * * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 * *
 * * Authors: Steven Maere, Karel Heymans
 * * Date: Mar.25.2005
 * * Description: Class that calculates the Hypergeometric probability P(x or more |X,N,n) for given x, X, n, N.    
 **/


import java.math.*;
import cern.jet.stat.*;


/**
 * *****************************************************************
 * HypergeometricDistribution.java    Steven Maere & Karel Heymans (c) March 2005
 * -------------------------------
 * <p/>
 * Class that calculates the Hypergeometric probability P(x or more |X,N,n) for given x, X, n, N.
 * ******************************************************************
 */


public class HypergeometricDistributionUnder {

    /*--------------------------------------------------------------
    FIELDS.
    --------------------------------------------------------------*/

    // x out of X genes in cluster A belong to GO category B which
    // is shared by n out of N genes in the reference set.
    /**
     * number of successes in sample.
     */
    private static int x;
    /**
     * sample size.
     */
    private static int bigX;
    /**
     * number of successes in population.
     */
    private static int n;
    /**
     * population size.
     */
    private static int bigN;
    /**
     * scale of result.
     */
    private static final int SCALE_RESULT = 100;

    /*--------------------------------------------------------------
    CONSTRUCTOR.
    --------------------------------------------------------------*/

    /**
     * constructor with as arguments strings containing numbers.
     *
     * @param x    number of genes with GO category B in cluster A.
     * @param bigX number of genes in cluster A.
     * @param n    number of genes with GO category B in the whole genome.
     * @param bigN number of genes in whole genome.
     */

    public HypergeometricDistributionUnder(int x, int bigX, int n, int bigN) {
        this.x = x;
        this.bigX = bigX;
        this.n = n;
        this.bigN = bigN;
    }

    /*--------------------------------------------------------------
    METHODS.
    --------------------------------------------------------------*/

    /**
     * method that conducts the calculations.
     * P(x or more |X,N,n) = 1 - sum{[C(n,i)*C(N-n, X-i)] / C(N,X)}
     * for i=0 ... x-1
     *
     * @return String with result of calculations.
     */
public String calculateHypergDistr() {
      if(bigN >= 2){
        double sum = 0;
		//mode of distribution, integer division (returns integer <= double result)!
		int mode = (bigX+1)*(n+1)/(bigN+2) ;
		if(x+1 >= mode){
                    int i = x+1 ;
                    while ((bigN - n >= bigX - i) && (i <= Math.min(bigX, n))) {
			double pdfi = Math.exp(Gamma.logGamma(n+1)-Gamma.logGamma(i+1)-Gamma.logGamma(n-i+1) + Gamma.logGamma(bigN-n+1)-Gamma.logGamma(bigX-i+1)-Gamma.logGamma(bigN-n-bigX+i+1)- Gamma.logGamma(bigN+1)+Gamma.logGamma(bigX+1)+Gamma.logGamma(bigN-bigX+1)) ;
			sum = sum+pdfi;
                        i++;
                    }
                    sum = 1-sum;
		}	
		else{
                    int i = x;
                    while ((bigN - n >= bigX - i) && (i >= 0)) {
                        double pdfi = Math.exp(Gamma.logGamma(n+1)-Gamma.logGamma(i+1)-Gamma.logGamma(n-i+1) + Gamma.logGamma(bigN-n+1)-Gamma.logGamma(bigX-i+1)-Gamma.logGamma(bigN-n-bigX+i+1)- Gamma.logGamma(bigN+1)+Gamma.logGamma(bigX+1)+Gamma.logGamma(bigN-bigX+1)) ;
                        sum = sum+pdfi;
                        i--;
                    }	
                }
                return (new Double(sum)).toString();
        }
	else{return (new Double(1)).toString();}
    }	
}
		
	    
	
	   
