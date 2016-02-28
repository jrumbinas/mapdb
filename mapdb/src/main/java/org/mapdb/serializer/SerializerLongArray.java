package org.mapdb.serializer;

import org.mapdb.DataInput2;
import org.mapdb.DataOutput2;
import org.mapdb.Serializer;

import java.io.IOException;
import java.util.Arrays;

/**
 * Created by jan on 2/28/16.
 */
public class SerializerLongArray implements Serializer<long[]> {

    @Override
    public void serialize(DataOutput2 out, long[] value) throws IOException {
        out.packInt(value.length);
        for (long c : value) {
            out.writeLong(c);
        }
    }

    @Override
    public long[] deserialize(DataInput2 in, int available) throws IOException {
        final int size = in.unpackInt();
        long[] ret = new long[size];
        for (int i = 0; i < size; i++) {
            ret[i] = in.readLong();
        }
        return ret;
    }


    @Override
    public boolean isTrusted() {
        return true;
    }

    @Override
    public boolean equals(long[] a1, long[] a2) {
        return Arrays.equals(a1, a2);
    }

    @Override
    public int hashCode(long[] bytes, int seed) {
        for (long element : bytes) {
            int elementHash = (int) (element ^ (element >>> 32));
            seed = (-1640531527) * seed + elementHash;
        }
        return seed;
    }

    @Override
    public int compare(long[] o1, long[] o2) {
        if (o1 == o2) return 0;
        final int len = Math.min(o1.length, o2.length);
        for (int i = 0; i < len; i++) {
            if (o1[i] == o2[i])
                continue;
            if (o1[i] > o2[i])
                return 1;
            return -1;
        }
        return SerializerUtils.compareInt(o1.length, o2.length);
    }

}