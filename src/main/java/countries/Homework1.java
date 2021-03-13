package countries;

import java.io.IOException;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Optional;

public class Homework1 {

    private List<Country> countries;

    public Homework1() {
        countries = new CountryRepository().getAll();
    }

    /**
     * Returns whether there is at least one country with the word "island" in its name ignoring case.
     */
    public boolean streamPipeline1() {
	return countries.stream().map(country->country.getName()).map(String::toLowerCase).anyMatch(name->name.contains("island"));
  
    }

    /**
     *  Returns the first country name that contains the word "island" ignoring case.
     */
    public Optional<String> streamPipeline2() {
	return countries.stream().map(country->country.getName()).map(String::toLowerCase).filter(name->name.contains("island")).findFirst();
    }

    /**
     * Prints each country name in which the first and the last letters are the same ignoring case.
     */
    public void streamPipeline3() {
	countries.stream().map(country->country.getName()).map(String::toLowerCase).filter(x->x.charAt(0)==x.charAt(x.length()-1)).forEach(System.out::println);

    }

    /**
     * Prints the populations of the first ten least populous countries.
     */
    public void streamPipeline4() {
	countries.stream().mapToLong(x->x.getPopulation()).sorted().limit(10).forEach(System.out::println);

    }

    /**
     * Prints the names of the first ten least populous countries.
     */
    public void streamPipeline5() {
	countries.stream().sorted((x,y)->((Long)x.getPopulation()).compareTo(((Long)y.getPopulation()))).limit(10).map(x->x.getName()).forEach(x->System.out.println(x));

    }

    /**
     * Returns summary statistics about the number of country name translations associated with each country.
     */
    public IntSummaryStatistics streamPipeline6() {
	return countries.stream().mapToInt(x->x.getTranslations().size()).summaryStatistics();

    }

    /**
     * Prints the names of countries in the ascending order of the number of timezones.
     */
    public void streamPipeline7() {
	countries.stream().sorted((x,y)->((Integer) x.getTimezones().size()).compareTo((Integer) y.getTimezones().size())).map(x->x.getName()).forEach(System.out::println);


    }

    /**
     * Prints the number of timezones for each country in the form {@code name:timezones}, in the ascending order of the number of timezones.
     */
    public void streamPipeline8() {

	countries.stream().sorted((x,y)->((Integer) x.getTimezones().size()).compareTo((Integer) y.getTimezones().size())).forEach(x->System.out.println(x.getName()+": "+x.getTimezones().size()));

    }

    /**
     * Returns the number of countries with no Spanish country name translation (the Spanish language is identifi
ed by the language code "es").
     */
    public long streamPipeline9() {
	return countries.stream().filter(x->!(x.getTranslations().containsKey("es"))).count();
 
    }

    /**
     * Prints the names of countries with null area.
     */
    public void streamPipeline10() {
	countries.stream().filter(x->x.getArea()==null).forEach(x->System.out.println(x.getName()));

    }

    /**
     * Prints all distinct language tags of country name translations sorted in alphabetical order.
     */
    public void streamPipeline11() {
	countries.stream().flatMap(x->x.getTranslations().keySet().stream()).distinct().sorted().forEach(System.out::println);

    }

    /**
     * Returns the average length of country names.
     */
    public double streamPipeline12() {
	return countries.stream().mapToInt(x->x.getName().length()).average().orElse(-1);

    }

    /**
     * Prints all distinct regions of the countries with null area.
     */
    public void streamPipeline13() {
	countries.stream().filter(x->x.getArea()==null).map(x->x.getRegion()).distinct().forEach(System.out::println);

    }

    /**
     * Returns the largest country with non-null area.
     */
    public Optional<Country> streamPipeline14() {
	return countries.stream().filter(x->x.getArea()!=null).max((x,y)->x.getArea().compareTo(y.getArea()));
    
    }

    /**
     * Prints the names of countries with a non-null area below 1.
     */
    public void streamPipeline15() {
	countries.stream().filter(x->x.getArea()!=null && (((x.getArea())).compareTo(BigDecimal.valueOf(1))==-1)).forEach(x->System.out.println(x.getName()));
 
    }

    /**
     * Prints all distinct timezones of European and Asian countries.
     */
    public void streamPipeline16() {
	countries.stream().filter(x->x.getRegion()==Region.ASIA || x.getRegion()==Region.EUROPE).flatMap(x->x.getTimezones().stream()).distinct().forEach(System.out::println);
     
    }

}
