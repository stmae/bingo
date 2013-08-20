/*
 File: AnnotationDescription.java

 Copyright (c) 2006, The Cytoscape Consortium (www.cytoscape.org)

 The Cytoscape Consortium is:
 - Institute for Systems Biology
 - University of California San Diego
 - Memorial Sloan-Kettering Cancer Center
 - Institut Pasteur
 - Agilent Technologies

 This library is free software; you can redistribute it and/or modify it
 under the terms of the GNU Lesser General Public License as published
 by the Free Software Foundation; either version 2.1 of the License, or
 any later version.

 This library is distributed in the hope that it will be useful, but
 WITHOUT ANY WARRANTY, WITHOUT EVEN THE IMPLIED WARRANTY OF
 MERCHANTABILITY OR FITNESS FOR A PARTICULAR PURPOSE.  The software and
 documentation provided hereunder is on an "as is" basis, and the
 Institute for Systems Biology and the Whitehead Institute
 have no obligations to provide maintenance, support,
 updates, enhancements or modifications.  In no event shall the
 Institute for Systems Biology and the Whitehead Institute
 be liable to any party for direct, indirect, special,
 incidental or consequential damages, including lost profits, arising
 out of the use of this software and its documentation, even if the
 Institute for Systems Biology and the Whitehead Institute
 have been advised of the possibility of such damage.  See
 the GNU Lesser General Public License for more details.

 You should have received a copy of the GNU Lesser General Public License
 along with this library; if not, write to the Free Software Foundation,
 Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.
 */
package bingo.internal.ontology;

import java.io.Serializable;

/**
 * Distinguish among different ontologies, by curator and type. For example,
 * KEGG maintains two ontologies: metabolic pathways, and regulatory pathways.
 * GO has three: biological process, molecular function, cellular component.
 * This simple class captures these distinctions It will perhaps prove most
 * useful when some data source (a biodata server, for example) contains a
 * number of ontologies, and needs to communicate a summary of these to a
 * client.
 * 
 */
public class AnnotationDescription implements Serializable {
	protected String species; // halopbacterium sp, saccharomyces cerevisiae,
								// ...
	protected String curator; // KEGG, GO, ...
	protected String annotationType; // biological process, metabolic pathway,
										// ...

	/**
	 * @param species
	 *            The formal Linnaean name (genus and species) is preferred
	 * @param curator
	 *            The institute or group which maintains this annotation
	 * @param annotationType
	 *            The nature of this annotation, eg, "metabolic pathway",
	 *            "molecular function", or "cellular component"
	 */
	public AnnotationDescription(String species, String curator, String annotationType) {
		this.species = species;
		this.curator = curator;
		this.annotationType = annotationType;
	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 */
	public String getSpecies() {
		return species;
	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 */
	public String getCurator() {
		return curator;
	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 */
	public String getType() {
		return annotationType;
	}

	/**
	 * redefine equals so that instances with the same contents are equal. this
	 * emulates the way that String equality is judged.
	 * 
	 * @param obj
	 *            DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 */
	public boolean equals(Object obj) {
		if (!obj.getClass().equals(this.getClass()))
			return false;

		AnnotationDescription other = (AnnotationDescription) obj;

		if (species.equals(other.getSpecies()) && curator.equals(other.getCurator())
				&& annotationType.equals(other.getType()))
			return true;

		return false;
	}

	/**
	 * redefine hashCode so that instances with the same contents have the same
	 * hashCode. this will make them identical when used as the keys in
	 * HashMaps, the purpose for which they were invented.
	 * 
	 * @return DOCUMENT ME!
	 */
	public int hashCode() {
		return species.hashCode() + curator.hashCode() + annotationType.hashCode();
	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 */
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Data Source = " + curator + ", Type = " + annotationType + ", Species = " + species);

		return sb.toString();
	}
}
