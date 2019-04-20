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
import java.math.BigInteger;

/**
 *
 * @author liziyi0914
 */
public class FileType extends Type {

    byte[] data = new byte[0];
    int size = 0;
    String name;
    StringType string = new StringType();
    BytesType bytes = new BytesType();
    VarintType varint = new VarintType();

    public FileType out(String name,byte[] data ) throws IOException {
        this.name = name;
        this.data = data;
        this.size = data.length;
        return this;
    }

    public FileType in() {
        return this;
    }

    public int size() {
        return size;
    }

    @Override
    public void write(ByteArrayOutputStream out) throws IOException {
        string.out(name).write(out);
        varint.out(BigInteger.valueOf(size)).write(out);
        bytes.out(data).write(out);
    }

    @Override
    public Object load(ByteArrayInputStream in) throws IOException {
        name = string.in().load(in);
        size = varint.in().load(in).intValue();
        data = bytes.in(size).load(in);
        return new SimpleFile(name, size, data);
    }

    public class SimpleFile{
        private String name;
        private int size;
        private byte[] data;

        /**
         * @return the name
         */
        public String getName() {
            return name;
        }

        /**
         * @param name the name to set
         */
        public void setName(String name) {
            this.name = name;
        }

        /**
         * @return the size
         */
        public int getSize() {
            return size;
        }

        /**
         * @param size the size to set
         */
        public void setSize(int size) {
            this.size = size;
        }

        /**
         * @return the data
         */
        public byte[] getData() {
            return data;
        }

        /**
         * @param data the data to set
         */
        public void setData(byte[] data) {
            this.data = data;
        }

        public SimpleFile(String name, int size, byte[] data) {
            this.name = name;
            this.size = size;
            this.data = data;
        }
        
    }
    
}
