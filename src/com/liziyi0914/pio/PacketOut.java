/*
 * Copyright (C) 2019 liziyi0914.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, see <http://www.gnu.org/licenses/lgpl.txt>
 */
package com.liziyi0914.pio;

import java.io.*;

/**
 *
 * @author liziyi0914
 */
public class PacketOut {

    ByteArrayOutputStream out = new ByteArrayOutputStream();
    
    public PacketOut() {
    }
    
    public byte[] finish(){
        return out.toByteArray();
    }
    
    public void write(Type type) throws IOException{
        type.write(out);
    }
    
}
