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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Worker thread for the Server class.
 * Contains the dispatch table for the commands received
 * from the PHP backend.
 * @author lenny
 */
public class ServerThread extends Thread {
    
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private ServerCommands serverCommands;
    
    public ServerThread(Socket socket) throws IOException {
        
        this.socket = socket;
        this.socket.setSoLinger(false, 0);
        
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.out = new PrintWriter(socket.getOutputStream(), false);
        this.serverCommands = new ServerCommands(this);
    }
    
    public void run() {
        
        try {
            
            String line;
            
            while ((line = in.readLine()) != null) {
                
                String[] cmd = Utils.parseString(line, true);
                
                if (cmd[0].equals("connect")) {
                    
                    serverCommands.connect(cmd);
                    
                } else if (cmd[0].equals("exec")) {
                    
                    serverCommands.exec(cmd);
                    
                } else if (cmd[0].equals("fetch_array")) {
                    
                    serverCommands.fetch_array(cmd);
                    
                } else if (cmd[0].equals("free_result")) {
                    
                    serverCommands.free_result(cmd);
                    
                } else {
                    
                    break;
                }
                out.flush();
            }
            
            serverCommands.close();
            socket.close();
            
        } catch (IOException e) {
            
            Utils.log("error", "socket lost");
        }
    }
    
    public void write(String s) {
        
        out.println(Base64.encodeString(s));
    }
    
    public void write(String k, String v) {
        
        if (v == null)
            v = "";
        
        out.println(
                Base64.encodeString(k) + " " +
                Base64.encodeString(v)
                );
    }
    
    void write(String k, int v) {
        
        out.println(
                Base64.encodeString(k) + " " +
                Base64.encodeString(new Integer(v).toString())
                );
    }

    void write(String k, int v1, int v2) {
        
        out.println(
                Base64.encodeString(k) + " " +
                Base64.encodeString(new Integer(v1).toString()) + " " +
                Base64.encodeString(new Integer(v2).toString())
                );
    }
}
