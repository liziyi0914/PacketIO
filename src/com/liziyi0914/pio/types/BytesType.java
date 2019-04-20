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
package com.liziyi0914.pio.types;

import com.liziyi0914.pio.Type;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 *
 * @author liziyi0914
 */
public class BytesType extends Type<byte[]> {

    byte[] data = new byte[0];
    int length = 0;

    public BytesType out(byte[] data) {
        this.data = data;
        length = data.length;
        return this;
    }

    public BytesType in(int length) {
        this.length = length;
        data = new byte[length];
        return this;
    }
    
    public int length(){
        return length;
    }

    @Override
    public void write(ByteArrayOutputStream out) throws IOException {
        out.write(data);
    }

    @Override
    public byte[] load(ByteArrayInputStream in) throws IOException {
        in.read(data);
        return data;
    }

}
