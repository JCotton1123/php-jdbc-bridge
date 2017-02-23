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

/**
 * This class is the Java Service Wrapper interface.
 * @author lenny
 */
public class Service {
    
    Server server = null;
    
    public void init(String[] args){

        server = new Server(args[0], args[1]);
    }
    
    public void start() {
        
        server.start();
    }
    
    public void stop() {
        
        server.shutdown();
    }

    public void destroy() {
        return;
    }

    public static void main(String[] args){

        Service service = new Service();
        service.init(args);
        service.start();
    }
}

