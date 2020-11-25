package nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter.timescounting;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;

public class TimesNumberCounterSelectionTest {
	
	@Test
	public void create_with_emptyList() {
		
		NtsAssert.businessException("Msg_1817", () -> {
			TimesNumberCounterSelection.create(TimesNumberCounterType.WORKPLACE, Collections.emptyList());
		});
		
	}
	
	@Test
	public void create_with_over_size_list() {
		
		// selectedNoList.size == 11
		List<Integer> selectedNoList = IntStream.rangeClosed(1, 11).boxed().collect(Collectors.toList());
		
		NtsAssert.businessException("Msg_1837", () -> {
			TimesNumberCounterSelection.create(TimesNumberCounterType.PERSON_1, selectedNoList);
		});
	}
	
	@Test
	public void create_with_max_size_list() {
		
		// selectedNoList.size == 10
		List<Integer> selectedNoList = IntStream.rangeClosed(1, 10).boxed().collect(Collectors.toList());
		
		
		TimesNumberCounterSelection result = TimesNumberCounterSelection.create(TimesNumberCounterType.PERSON_1, selectedNoList);
		
		assertThat(result.getSelectedNoList()).hasSize(10);
	}
	
	@Test
	public void create_with_type_workplace_size_over10() {
		
		// selectedNoList.size == 11
		List<Integer> selectedNoList = IntStream.rangeClosed(1, 11).boxed().collect(Collectors.toList());
		
		TimesNumberCounterSelection result = TimesNumberCounterSelection.create(TimesNumberCounterType.WORKPLACE, selectedNoList);
		
		assertThat(result.getSelectedNoList()).hasSize(11);
	}
	
	@Test
	public void create_successfully() {
		
		TimesNumberCounterSelection result = TimesNumberCounterSelection.create(TimesNumberCounterType.PERSON_1, Arrays.asList(1, 3, 2));
		
		assertThat(result.getType()).isEqualTo(TimesNumberCounterType.PERSON_1);
		assertThat(result.getSelectedNoList()).containsExactly(1, 3, 2);
	}
	
	@Test
	public void getters() {
		
		TimesNumberCounterSelection target = TimesNumberCounterSelection.create(TimesNumberCounterType.PERSON_1, Arrays.asList(1, 3, 2));
		NtsAssert.invokeGetters(target);  
	}

}
