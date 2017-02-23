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

import java.io.IOException;
import java.net.ServerSocket;

/**
 * 
 * @author lenny
 */
public class Server extends Thread {

    private String driver = null;
    private int port = 4444;
    
    private ServerSocket serverSocket = null;
    private boolean listening = true;
    
    /**
     * Creates a new instance of Server
     */
    public Server(String driver, int port) {

        this.driver = driver;
	this.port = port;

        init();
    }

    public Server(String driver, String port) {

        this.driver = driver;
        this.port = Integer.parseInt(port);

        init();
    }


    public void init() {
        
        try {
            
            serverSocket = new ServerSocket(port);
            Utils.log("notice", "listening on " + port);
            
        } catch (IOException e) {
            
            Utils.log("error", "could not listen on " + port);
            return;
        }
        
        try {
            
	    Class.forName(driver);
	    Utils.log("notice", "loaded " + driver);
            
        } catch (ClassNotFoundException ex) {
            
            Utils.log("error", "could not load JDBC driver");
            return;
        }
    }
    
    public void run() {
        
        while (listening) {
            
            try {
                
                new ServerThread(serverSocket.accept()).start();
                
            } catch (IOException ex) {
                
                Utils.log("error", "could not create thread");
                return;
            }
        }
    }
    
    public void shutdown() {
        
        listening = false;
        interrupt();
    }
    
    public static void main(String[] args) {
        
        Server server = new Server(args[0], args[1]);
        
        try {
            
            server.start();
            server.join();
            
        } catch (InterruptedException ex) {
            
            Utils.log("error", "could not join thread");
        }
    }
}
