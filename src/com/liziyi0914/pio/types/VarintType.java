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
public class VarintType extends Type<BigInteger> {

    BigInteger bi;
    ByteType byteType = new ByteType();
    BytesType bytesType = new BytesType();

    public VarintType out(BigInteger bi) {
        this.bi = bi;
        return this;
    }

    public VarintType in() {
        return this;
    }

    @Override
    public void write(ByteArrayOutputStream out) throws IOException {
        BigInteger tmp = bi;
        byte current = (byte) 0x00;
        ByteArrayOutputStream o = new ByteArrayOutputStream();
        while (!tmp.equals(BigInteger.ZERO)) {
            current = (byte) (current | tmp.mod(BigInteger.valueOf(128)).byteValue());
            tmp = tmp.divide(BigInteger.valueOf(128));
            o.write(new byte[]{current});
            current = (byte) 0x80;
        }
        byte[] t = o.toByteArray();
        byte[] bs = new byte[t.length];
        for (int i = 0; i < bs.length; i++) {
            bs[bs.length - i - 1] = t[i];
        }
//        System.out.println(Arrays.toString(bs));
        out.write(bs);
    }

    @Override
    public BigInteger load(ByteArrayInputStream in) throws IOException {
        BigInteger bi = BigInteger.ZERO;
        byte b = 0x00;
        int r;
        boolean doing = true;
//        System.out.println("Varint[");
        while (doing) {
            r=in.read();
            if (r == -1) {
                break;
            }
            b = (byte) (r & 0xff);
//            System.out.println(b);
            if ((b & 0x80) != 0x00) {
                b = (byte) (b & 0x7f);
            } else {
                doing = false;
            }
            bi = bi.multiply(BigInteger.valueOf(128)).add(BigInteger.valueOf(b));
        }
//        System.out.println("]");
        return bi;
    }

}
