package countries;



import java.io.IOException;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.time.ZoneId;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

public class Homework2 {

    private List<Country> countries;

    public Homework2() {
        countries = new CountryRepository().getAll();
    }

    public static int vowelCount(String str) {
        int count = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == 'a' || str.charAt(i) == 'e' || str.charAt(i) == 'i'
                    || str.charAt(i) == 'o' || str.charAt(i) == 'u') {
                count++;
            }
        }
        return count;
    }

    /**
     * Returns the longest country name translation.
     */
    public Optional<String> streamPipeline1() {
        return countries.stream().flatMap(x -> x.getTranslations().values().stream()).max((x, y) -> ((Integer) x.length()).compareTo((Integer) y.length()));
    }

    /**
     * Returns the longest Italian (i.e., {@code "it"}) country name translation.
     */
    public Optional<String> streamPipeline2() {
        return countries.stream().filter(x -> x.getTranslations().containsKey("it")).map(x -> x.getTranslations().get("it")).max((x, y) -> ((Integer) x.length()).compareTo((Integer) y.length()));
    }

    /**
     * Prints the longest country name translation together with its language code in the form language=translation.
     */
    public void streamPipeline3() {
        countries.stream().flatMap(x -> x.getTranslations().entrySet().stream()).sorted((y, x) -> ((Integer) x.getValue().length()).compareTo((Integer) y.getValue().length())).limit(1).forEach(x -> System.out.println(x.getKey() + "=" + x.getValue()));

    }

    /**
     * Prints single word country names (i.e., country names that do not contain any space characters).
     */
    public void streamPipeline4() {
        countries.stream().map(x -> x.getName()).filter(x -> !x.contains(" ")).forEach(System.out::println);
    }

    /**
     * Returns the country name with the most number of words.
     */
    public Optional<String> streamPipeline5() {
        return countries.stream().map(x -> x.getName()).max((x, y) -> ((Integer) (x.length() - x.replace(" ", "").length())).compareTo(((Integer) (y.length() - y.replace(" ", "").length()))));
    }

    /**
     * Returns whether there exists at least one capital that is a palindrome.
     */
    public boolean streamPipeline6() {
        return !countries.stream().map(x -> x.getCapital()).noneMatch(x -> x.toLowerCase() == new StringBuilder(x.toLowerCase()).reverse().toString());
    }

    /**
     * Returns the country name with the most number of {@code 'e'} characters ignoring case.
     */
    public Optional<String> streamPipeline7() {
        return countries.stream().map(x -> x.getName()).max((x, y) -> ((Integer) (x.length() - x.toLowerCase().replace("e", "").length())).compareTo(((Integer) (y.length() - y.toLowerCase().replace("e", "").length()))));
    }

    /**
     * Returns the capital with the most number of English vowels (i.e., {@code 'a'}, {@code 'e'}, {@code 'i'}, {@code 'o'}, {@code 'u'}).
     */
    public Optional<String> streamPipeline8() {
        return countries.stream().map(x -> x.getName()).max((x, y) -> ((Integer) (vowelCount(x))).compareTo(((Integer) (vowelCount(y)))));
    }

    /**
     * Returns a map that contains for each character the number of occurrences in country names ignoring case.
     */
    public Map<Character, Long> streamPipeline9() {
        return countries.stream().map(x -> x.getName().toLowerCase()).flatMap(x -> x.chars().mapToObj(c -> (char) c)).collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    }

    /**
     * Returns a map that contains the number of countries for each possible timezone.
     */
    public Map<ZoneId, Long> streamPipeline10() {
        return countries.stream().flatMap(x -> x.getTimezones().stream()).collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    }

    /**
     * Returns the number of country names by region that starts with their two-letter country code ignoring case.
     */
    public Map<Region, Long> streamPipeline11() {
        return countries.stream().filter(x -> (x.getName().substring(0, 2).toUpperCase()).equals(x.getCode())).map(x -> x.getRegion()).collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    }

    /**
     * Returns a map that contains the number of countries whose population is greater or equal than the population average versus the the number of number of countries with population below the average.
     */
    public Map<Boolean, Long> streamPipeline12() {
        return countries.stream().filter(x -> x.getArea() != null).map(x -> (float) x.getPopulation() / (x.getArea().floatValue())).map(x ->
                {

                    return x >= (countries.stream().filter(y -> y.getArea() != null).mapToDouble(z -> (float) z.getPopulation() / (z.getArea().floatValue())).average().orElse(0));
                }
        )
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    }


    /**
     * Returns a map that contains for each country code the name of the corresponding country in Portuguese ({@code "pt"}).
     */
    public Map<String, String> streamPipeline13() {
        return countries.stream().collect(Collectors.toMap(x -> x.getCode(), x -> x.getTranslations().get("pt")));
    }

    /**
     * Returns the list of capitals by region whose name is the same is the same as the name of their country.
     */
    public Map<Region, List<String>> streamPipeline14() {
        return countries.stream().filter(x -> x.getName().equals(x.getCapital())).
                collect(Collectors.groupingBy(Country::getRegion, Collectors.mapping(Country::getCapital, Collectors.toList())));
    }


    /**
     * Returns a map of country name-population density pairs.
     */
    public Map<String, Double> streamPipeline15() {
        return countries.stream().collect(Collectors.toMap(x -> x.getName(), x -> {
            if (x.getArea() != null)
                return ((Double) (x.getPopulation() / (x.getArea().doubleValue())));
            else {
                return Double.NaN;

            }
        }));
    }


}
