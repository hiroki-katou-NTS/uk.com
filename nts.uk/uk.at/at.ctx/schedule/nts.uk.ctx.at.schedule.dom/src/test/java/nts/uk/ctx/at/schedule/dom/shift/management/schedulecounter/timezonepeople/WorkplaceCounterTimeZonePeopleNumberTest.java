package nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter.timezonepeople;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;

public class WorkplaceCounterTimeZonePeopleNumberTest {
	
	@Test
	public void create_with_emptyList() {
		
		NtsAssert.businessException("Msg_1819", () -> {
			WorkplaceCounterTimeZonePeopleNumber.create(Collections.emptyList());
		});
	}
	
	@Test
	public void create_with_over_size_list() {
		
		// timeZoneList.size == 25
		List<WorkplaceCounterStartTime> timeZoneList = IntStream.range(0, 25)
			    .mapToObj(i -> new WorkplaceCounterStartTime(i))
			    .collect(Collectors.toList());
		
		NtsAssert.businessException("Msg_1819", () -> {
			WorkplaceCounterTimeZonePeopleNumber.create(timeZoneList);
		});
	}
	
	@Test
	public void create_with_max_size_list() {
		
		// timeZoneList.size == 24
		List<WorkplaceCounterStartTime> timeZoneList = IntStream.range(0, 24)
			    .mapToObj(i -> new WorkplaceCounterStartTime(i))
			    .collect(Collectors.toList());
		
		WorkplaceCounterTimeZonePeopleNumber result = WorkplaceCounterTimeZonePeopleNumber.create(timeZoneList);
		
		assertThat(result.getTimeZoneList()).hasSize(24);
	}
	
	@Test
	public void create_with_duplicated_list() {
		
		List<WorkplaceCounterStartTime> timeZoneList = Arrays.asList(
				new WorkplaceCounterStartTime(0),
				new WorkplaceCounterStartTime(1),
				new WorkplaceCounterStartTime(1)
				);
		
		NtsAssert.businessException("Msg_1820", () -> {
			WorkplaceCounterTimeZonePeopleNumber.create(timeZoneList);
		});
	}
	
	@Test
	public void create_sucessfully() {
		
		List<WorkplaceCounterStartTime> timeZoneList = Arrays.asList(
				new WorkplaceCounterStartTime(0),
				new WorkplaceCounterStartTime(1),
				new WorkplaceCounterStartTime(2)
				);
		
		WorkplaceCounterTimeZonePeopleNumber result = WorkplaceCounterTimeZonePeopleNumber.create(timeZoneList);
		
		assertThat(result.getTimeZoneList()).extracting( element -> element.v() )
											.containsOnly( 0, 1, 2);
	}
	
	@Test
	public void getters() {
		
		List<WorkplaceCounterStartTime> timeZoneList = Arrays.asList(
				new WorkplaceCounterStartTime(0),
				new WorkplaceCounterStartTime(1),
				new WorkplaceCounterStartTime(2)
				);
		
		WorkplaceCounterTimeZonePeopleNumber target = WorkplaceCounterTimeZonePeopleNumber.create(timeZoneList);
		NtsAssert.invokeGetters(target);  
	}

}
