package com.viktarx.coherence;

import com.tangosol.net.CacheFactory;
import com.tangosol.net.NamedCache;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.*;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertNotNull;

public class CoherenceIntegrationTest {

    private static NamedCache cache;
    private static final String CACHE_NAME = "order";
    private static final int MAX_ALLOWED_PROFILES = 1_000_000;
    private static final String DEFAULT_PROFILE = "4902320009020423992";

    private static final Random rand = new Random();

    private static final List<Long> profiles = new ArrayList<>();
    private static final Map<String, Long> items = new HashMap<>();

    static {
        items.put("testSku1", 1L);
        items.put("testSku2", 2L);
        items.put("testSku3", 3L);
        items.put("testSku4", 4L);
    }

    @BeforeClass
    public static void init() {
        initCache();
        assertNotNull(String.format("Cache %s is not initialized properly.", CACHE_NAME), cache);
        assertEquals(String.format("Cache name is %s, expected %s", cache.getCacheName(), CACHE_NAME), cache.getCacheName(), CACHE_NAME);
    }

    @Test
    public void put() throws Exception {
        boolean success = true;
        Order order = order();
        try {
            cache.put(order.profile(), order);
        } catch (Exception ex) {
            success = false;
            ex.printStackTrace();
        }
        assertTrue(String.format("Failed to put an order by profile=%s", order.profile()), success);
    }

    @Test
    public void get() throws Exception {
        String profile = DEFAULT_PROFILE;
        if (!profiles.isEmpty()) {
            profile = String.valueOf(profiles.get(rand.nextInt(profiles.size())));
        }
        Order result = (Order) cache.get(profile);
        assertNotNull(String.format("Nothing is returned by %s key.", profile), result);
        assertEquals(String.format("Returned order has profile=%s, expected profile=%s",
                result.profile(), profile), result.profile(), profile);
    }

    private static void initCache() {
        cache = CacheFactory.getCache(CACHE_NAME);
    }

    private static Order order() {
        return new Order(String.valueOf(Math.abs(rand.nextLong())), items, "new", String.valueOf(profile()), new Date(), new Date());
    }

    private static long profile() {
        long profileId;
        if (profiles.size() < MAX_ALLOWED_PROFILES) {
            profileId = Math.abs(rand.nextLong()) + 1;
            profiles.add(profileId);
        } else {
            profileId = profiles.get(rand.nextInt(profiles.size()));
        }
        return profileId;
    }
}
