package nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter.timezonepeople;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;

public class WplCounterTimeZonePeopleNumberTest {
	
	@Test
	public void create_with_emptyList() {
		
		NtsAssert.businessException("Msg_1819", () -> {
			WplCounterTimeZonePeopleNumber.create(Collections.emptyList());
		});
	}
	
	@Test
	public void create_with_over_size_list() {
		
		// timeZoneList.size == 25
		List<WplCounterStartTime> timeZoneList = IntStream.range(0, 25)
			    .mapToObj(i -> new WplCounterStartTime(i))
			    .collect(Collectors.toList());
		
		NtsAssert.businessException("Msg_1819", () -> {
			WplCounterTimeZonePeopleNumber.create(timeZoneList);
		});
	}
	
	@Test
	public void create_with_max_size_list() {
		
		// timeZoneList.size == 24
		List<WplCounterStartTime> timeZoneList = IntStream.range(0, 24)
			    .mapToObj(i -> new WplCounterStartTime(i))
			    .collect(Collectors.toList());
		
		WplCounterTimeZonePeopleNumber result = WplCounterTimeZonePeopleNumber.create(timeZoneList);
		
		assertThat(result.getTimeZoneList()).hasSize(24);
	}
	
	@Test
	public void create_with_duplicated_list() {
		
		List<WplCounterStartTime> timeZoneList = Arrays.asList(
				new WplCounterStartTime(0),
				new WplCounterStartTime(1),
				new WplCounterStartTime(1)
				);
		
		NtsAssert.businessException("Msg_1820", () -> {
			WplCounterTimeZonePeopleNumber.create(timeZoneList);
		});
	}
	
	@Test
	public void create_sucessfully() {
		
		List<WplCounterStartTime> timeZoneList = Arrays.asList(
				new WplCounterStartTime(0),
				new WplCounterStartTime(1),
				new WplCounterStartTime(2)
				);
		
		WplCounterTimeZonePeopleNumber result = WplCounterTimeZonePeopleNumber.create(timeZoneList);
		
		assertThat(result.getTimeZoneList()).hasSize(3)
											.extracting( element -> element.v() )
											.contains( 0, 1, 2);
	}
	
	@Test
	public void getters() {
		
		List<WplCounterStartTime> timeZoneList = Arrays.asList(
				new WplCounterStartTime(0),
				new WplCounterStartTime(1),
				new WplCounterStartTime(2)
				);
		
		WplCounterTimeZonePeopleNumber target = WplCounterTimeZonePeopleNumber.create(timeZoneList);
		NtsAssert.invokeGetters(target);  
	}

}
