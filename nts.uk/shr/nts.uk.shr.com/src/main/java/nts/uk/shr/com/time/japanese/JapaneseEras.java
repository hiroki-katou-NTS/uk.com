package nts.uk.shr.com.time.japanese;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.Getter;
import nts.arc.time.GeneralDate;

/**
 * JapaneseEras
 */
public class JapaneseEras {

	/** names */
	@Getter
	private final List<JapaneseEraName> names;
	
	/**
	 * Constructs.
	 * 
	 * @param names names
	 */
	public JapaneseEras(List<JapaneseEraName> names) {
		this.names = names.stream()
				.sorted(Comparator.comparing(JapaneseEraName::startDate).reversed())
				.collect(Collectors.toList());
	}
	
	/**
	 * Returns era of given date.
	 * 
	 * @param date date
	 * @return era
	 */
	public Optional<JapaneseEraName> eraOf(GeneralDate date) {
		return this.names.stream()
				.filter(e -> e.contains(date))
				.findFirst();
	}
}
