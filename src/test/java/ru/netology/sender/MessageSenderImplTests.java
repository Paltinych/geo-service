package ru.netology.sender;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.geo.GeoServiceImpl;
import ru.netology.i18n.LocalizationService;
import ru.netology.i18n.LocalizationServiceImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class MessageSenderImplTests {

    @ParameterizedTest
    @MethodSource("addSource")
    public void testSend(String ip, Location location, String expected) {
        System.out.println("\ntest send");
        GeoService geoService = Mockito.mock(GeoServiceImpl.class);
        Mockito.when(geoService.byIp(ip))
                .thenReturn(location);

        LocalizationService localizationService = Mockito.mock(LocalizationServiceImpl.class);
        Mockito.when(localizationService.locale((location).getCountry()))
                .thenReturn((location.getCountry() == Country.RUSSIA) ? "Добро пожаловать" :  "Welcome");

        MessageSender messageSender = new MessageSenderImpl(geoService, localizationService);
        Map<String, String> headers = new HashMap<String, String>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, ip);
        String result = messageSender.send(headers);
        Assertions.assertEquals(expected, result);

    }

    public static Stream<Arguments> addSource() {
        return Stream.of(
                Arguments.of("172.0.32.11", new Location("Moscow", Country.RUSSIA, "Lenina", 15), "Добро пожаловать"),
                Arguments.of("96.44.183.149", new Location("New York", Country.USA, " 10th Avenue", 32), "Welcome"),
                Arguments.of("96.44.183.149", new Location("New York", Country.BRAZIL, " 10th Avenue", 32), "Welcome"),
                Arguments.of("96.44.183.149", new Location("New York", Country.GERMANY, " 10th Avenue", 32), "Welcome"),
                Arguments.of("", new Location("New York", Country.USA, " 10th Avenue", 32), "Welcome")
        );
    }
}
