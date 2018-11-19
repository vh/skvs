package com.github.vh.skvs;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junitpioneer.jupiter.TempDirectory;

import java.nio.file.Path;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(TempDirectory.class)
public class StoreTest {

    private Store store;

    public StoreTest(@TempDirectory.TempDir Path tempDir) {
        this.store = StoreFactory.createStore(tempDir, "test");
    }

    @Test
    void putGet() {
        store.put("foo", "bar");
        Assertions.assertEquals(store.getString("foo"), "bar");
        Assertions.assertNull(store.getString("baz"));

        store.put("foo", "baz");
        Assertions.assertEquals(store.getString("foo"), "baz");

        store.put("int", 123);
        Assertions.assertEquals((int)store.getInt("int"), 123);

        store.put("long", 123L);
        Assertions.assertEquals((long)store.getLong("long"), 123L);

        store.put("float", 1.23f);
        Assertions.assertEquals((float)store.getFloat("float"), 1.23f);

        store.put("double", 1.23);
        Assertions.assertEquals((double)store.getDouble("double"), 1.23);

        store.put("boolean", true);
        Assertions.assertEquals(store.getBoolean("boolean"), true);
        store.put("boolean", false);
        Assertions.assertEquals(store.getBoolean("boolean"), false);

        // TODO: More tests
    }
}
