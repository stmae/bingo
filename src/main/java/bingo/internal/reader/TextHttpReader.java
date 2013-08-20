/*
  File: TextHttpReader.java

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

// TextHttpReader.java
package bingo.internal.reader;

import java.io.*;

import java.net.*;


/**
 *
 */
public class TextHttpReader {
	InputStreamReader reader;
	StringBuffer sb;
	String uri;

	/**
	 * Creates a new TextHttpReader object.
	 *
	 * @param URI  DOCUMENT ME!
	 *
	 * @throws Exception  DOCUMENT ME!
	 */
	public TextHttpReader(String URI) throws Exception {
		uri = URI;
		sb = new StringBuffer();
	} // ctor

	/**
	 *  DOCUMENT ME!
	 *
	 * @return  DOCUMENT ME!
	 *
	 * @throws Exception DOCUMENT ME!
	 */
	public int read() throws Exception {
		// Start with an empty StringBuffer in case this method is called more than once.
		sb = new StringBuffer(getPage(uri));

		return sb.length();
	} // read

	/**
	 *  DOCUMENT ME!
	 *
	 * @return  DOCUMENT ME!
	 *
	 * @throws Exception DOCUMENT ME!
	 */
	public String getText() throws Exception {
		if (sb.length() <= 0)
			read();

		return sb.toString();
	} // read

	/**
	 *  DOCUMENT ME!
	 *
	 * @param urlString DOCUMENT ME!
	 *
	 * @return  DOCUMENT ME!
	 *
	 * @throws Exception DOCUMENT ME!
	 */
	static public String getPage(String urlString) throws Exception {
		return getPage(new URL(urlString));
	}

	/**
	 *  DOCUMENT ME!
	 *
	 * @param url DOCUMENT ME!
	 *
	 * @return  DOCUMENT ME!
	 *
	 * @throws Exception DOCUMENT ME!
	 * @throws IOException DOCUMENT ME!
	 */
	static public String getPage(URL url) throws Exception {
		int characterCount = 0;
		StringBuffer result = new StringBuffer();

		HttpURLConnection urlConnection = null;

        try {
            urlConnection = (HttpURLConnection) url.openConnection();

            // Ensure we are reading the real content from url,
            // and not some out-of-date cached content:
            urlConnection.setUseCaches(false);
            int responseCode = urlConnection.getResponseCode();
            String contentType = urlConnection.getContentType();

            int contentLength = urlConnection.getContentLength();

            String contentEncoding = urlConnection.getContentEncoding();

            if (responseCode != HttpURLConnection.HTTP_OK)
                throw new IOException("\nHTTP response code: " + responseCode);

            String thisLine;
            InputStream connStrm = null;

            try {
				BufferedReader theHTML = null;

                connStrm = urlConnection.getInputStream();
				try {
					theHTML = new BufferedReader(new InputStreamReader(connStrm));
					while ((thisLine = theHTML.readLine()) != null) {
						result.append(thisLine);
						result.append("\n");
					}
				}
				finally {
					if (theHTML != null) {
						theHTML.close();
					}
				}
            }
            finally {
                if (connStrm != null) {
                    connStrm.close();
                }
            }
        }
        finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        
		return result.toString();
	} // getPage
} // TextReader
