package ru.netology.i18n;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import ru.netology.entity.Country;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class LocalizationServiceImplTests {

    @ParameterizedTest
    @EnumSource(Country.class)
    public void testLocale(Country country) {
        System.out.println("test locale");
        LocalizationService localizationService = new LocalizationServiceImpl();
        
        String expected = (country == Country.RUSSIA) ? "Добро пожаловать" :  "Welcome";

        assertThat(expected, equalTo(localizationService.locale(country)));
    }
}
