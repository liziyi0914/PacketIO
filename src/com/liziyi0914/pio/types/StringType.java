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
import java.nio.charset.Charset;

/**
 *
 * @author liziyi0914
 */
public class StringType extends Type<String> {

    String str = "";
    byte[] bs = str.getBytes();
    int length = 0;
    VarintType varint = new VarintType();
    BytesType bytes = new BytesType();
    Charset charset = Charset.forName("utf-8");

    public StringType(Charset charset) {
        this.charset = charset;
    }

    public StringType() {
        this(Charset.forName("utf-8"));
    }

    public StringType out(String str) {
        this.str = str;
        this.bs = str.getBytes(charset);
        this.length = bs.length;
        return this;
    }

    public StringType in() {
        return this;
    }

    public int length() {
        return length;
    }

    @Override
    public void write(ByteArrayOutputStream out) throws IOException {
        varint.out(new BigInteger("" + this.length)).write(out);
        bytes.out(this.bs).write(out);
    }

    @Override
    public String load(ByteArrayInputStream in) throws IOException {
        length = varint.load(in).intValue();
        bs = bytes.in(length).load(in);
        return new String(bs, charset);
    }

}
