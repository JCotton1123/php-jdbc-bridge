/*
 * Copyright (C) 2007 lenny@mondogrigio.cjb.net
 *
 * This file is part of PJBS (http://sourceforge.net/projects/pjbs)
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Miscellaneous helper functions.
 * @author lenny
 */
public class Utils {
    
    private static final int BUFFER_SIZE = 1024;
    private static int makeUID_i = 1;
    
    /**
     * Log something to the console.
     * @param l 
     * @param s 
     */
    public static synchronized void log(String l, String s) {
        
        System.out.println(l + ": " + s);
    }
    
    /**
     * Reads a whole file into a string.
     * @param fn Path and name of the file.
     * @return A string on success, null on failure.
     */
    public static String readFile(String fn) {
        
        try {
            
            FileReader fr = new FileReader(fn);
            StringBuffer sb = new StringBuffer();
            
            try {
                
                while (true) {
                    
                    char[] b = new char[BUFFER_SIZE];
                    int l = fr.read(b, 0, BUFFER_SIZE);
                    
                    if (l > 0)
                        sb.append(b, 0, l);
                    else if (l < 0)
                        break;
                }
                
                return sb.toString();
                
            } catch (IOException ex) {
                
                log("error", "could not read file " + fn);
            }
            
        } catch (FileNotFoundException ex) {
            
            log("error", "file not found " + fn);
        }
        
        return null;
    }
    
    /**
     * Split a string into words.
     * Needed to parse the commands from the PHP backend and
     * the main config file.
     * @param s String to split into words.
     * @param base64 The words are base64-encoded?
     * @return An array of words.
     */
    public static String[] parseString(String s, boolean base64) {

        if (base64) {
        
            String r[] = s.split(" ", -1);
        
            for (int i = 0; i < r.length; i ++)
                r[i] = Base64.decodeString(r[i]);
            
            return r;
        
        } else {
            
            return s.trim().split("[ \t\r\n]+", -1);
        }
    }
    
    /**
     * Same as parseString(s, false);
     * @param s String to split into words.
     * @return An array of words.
     */
    public static String[] parseString(String s) {
        
        return parseString(s, false);
    }
    
    /**
     * Split a file into words.
     * Needed to read the main config file.
     * @param fn Path and name of the file to read
     * @return An array of words on success, null on failure.
     */
    public static String[] parseFile(String fn) {
        
        String s = readFile(fn);
        
        if (s != null)
            return parseString(s);
        else
            return null;
    }
    
    /**
     * Create an UID.
     * Needed to keep track of the JDBC ResultSets with the PHP
     * backend.
     * @return The generated UID.
     */
    public static synchronized String makeUID() {
        
        if((makeUID_i + 1) == Integer.MAX_VALUE)
            makeUID_i = 0;
        
        return new String("PJBS_id_" + Integer.toString(makeUID_i ++));
    }
    
    /**
     * Make a filename safe.
     * @param fn Unsafe file name
     * @return Safe file name
     */
    public static String safeFn(String fn) {
    
        return fn.replaceAll("[^a-zA-Z0-9_.\\-]", "");
    }
}
