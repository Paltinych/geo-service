package ru.netology.geo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.netology.entity.Country;
import ru.netology.entity.Location;

import java.util.stream.Stream;

import static org.unitils.reflectionassert.ReflectionAssert.assertReflectionEquals;

public class GeoServiceTests {
    GeoService geoService = new GeoServiceImpl();

    @ParameterizedTest
    @MethodSource("addSource")
    public void testByIp(String ip, Location expected) {
        System.out.println("test byIp");

        // when:
        Location result = geoService.byIp(ip);

        // then:
        assertReflectionEquals(expected, result);
    }

    public static Stream<Arguments> addSource() {
        return Stream.of(
                Arguments.of("127.0.0.1", new Location(null, null, null, 0)),
                Arguments.of("172.0.32.11", new Location("Moscow", Country.RUSSIA, "Lenina", 15)),
                Arguments.of("96.44.183.149", new Location("New York", Country.USA, " 10th Avenue", 32)),
                Arguments.of("172.0.48.52", new Location("Moscow", Country.RUSSIA, null, 0)),
                Arguments.of("96.44.145.25", new Location("New York", Country.USA, null,  0)),
                Arguments.of("192.168.1.1", null)
        );
    }

    @Test
    public void testByCoordinates() {
        System.out.println("test byCoordinates");
        Assertions.assertThrows(RuntimeException.class, () -> {
            geoService.byCoordinates(55.753472, 37.621806);
        });
    }
}
