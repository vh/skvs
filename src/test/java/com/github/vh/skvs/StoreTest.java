package com.github.vh.skvs;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junitpioneer.jupiter.TempDirectory;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.file.Path;
import java.util.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(TempDirectory.class)
public class StoreTest {

    private Store store;

    public StoreTest(@TempDirectory.TempDir Path tempDir) {
        this.store = StoreFactory.createStore(tempDir, "test");
    }

    @Test
    void putGet() {
        // String
        store.put("foo", "bar");
        Assertions.assertEquals(store.getString("foo"), "bar");
        Assertions.assertNull(store.getString("baz"));

        store.put("foo", "baz");
        Assertions.assertEquals(store.getString("foo"), "baz");

        // Int
        store.put("int", 123);
        Assertions.assertEquals((int)store.getInt("int"), 123);

        // Long
        store.put("long", 123L);
        Assertions.assertEquals((long)store.getLong("long"), 123L);

        // Float
        store.put("float", 1.23f);
        Assertions.assertEquals((float)store.getFloat("float"), 1.23f);

        // Double
        store.put("double", 1.23);
        Assertions.assertEquals((double)store.getDouble("double"), 1.23);

        // Boolean
        store.put("boolean", true);
        Assertions.assertEquals(store.getBoolean("boolean"), true);
        store.put("boolean", false);
        Assertions.assertEquals(store.getBoolean("boolean"), false);

        // Bytes
        store.put("bytes", "123".getBytes());
        Assertions.assertArrayEquals(store.getBytes("bytes"), "123".getBytes());

        // ByteBuffer
        ByteBuffer buf = ByteBufferUtils.fromBytes("123".getBytes());
        store.put("buf", buf);
        Assertions.assertEquals(store.get("buf"), buf);

        // Map
        HashMap<String, Object> map = new HashMap<>();
        map.put("string", "foo");
        map.put("int", 123);
        map.put("double", 1.23);
        map.put("list", Arrays.asList(1, 2, 3));
        map.put("map", Collections.singletonMap("foo", "bar"));

        store.put("map", map);
        Assertions.assertEquals(store.get("map", HashMap.class), map);

        // BigInteger
        store.put("bigint", BigInteger.valueOf(123));
        Assertions.assertEquals(store.get("bigint", BigInteger.class), BigInteger.valueOf(123));

        // Exception
        Assertions.assertThrows(ClassCastException.class, () -> {
           store.get("bigint", Integer.class);
        });
    }

    @Test
    void contains() {
        Assertions.assertFalse(store.contains("key"));

        store.put("key", "value");
        Assertions.assertTrue(store.contains("key"));

        store.remove("key");
        Assertions.assertFalse(store.contains("key"));
    }

    // TODO: @Test
    void enumerate() {

    }
}
