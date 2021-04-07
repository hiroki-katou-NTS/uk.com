package nts.uk.ctx.at.shared.dom.worktype;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import java.util.Map;

import org.junit.Test;

import nts.uk.ctx.at.shared.dom.worktime.common.AmPmAtr;

public class HalfDayWorkTypeClassificationTest {
	
	@Test
	public void testCreateByWholeDay() {
		WorkTypeClassification input = WorkTypeClassification.Attendance; // any
		
		HalfDayWorkTypeClassification result = HalfDayWorkTypeClassification.createByWholeDay(input);
		
		assertThat( result.getMorningClass() ).isEqualTo( input );
		assertThat( result.getAfternoonClass() ).isEqualTo( input );
	}
	
	@Test
	public void testCreateByAmAndPm() {
		WorkTypeClassification morningInput = WorkTypeClassification.Attendance; // any
		WorkTypeClassification afternoonInput = WorkTypeClassification.AnnualHoliday; // any
		
		HalfDayWorkTypeClassification result = HalfDayWorkTypeClassification.createByAmAndPm(morningInput, afternoonInput);
		
		assertThat( result.getMorningClass() ).isEqualTo( morningInput );
		assertThat( result.getAfternoonClass() ).isEqualTo( afternoonInput );
	}
	
	@Test
	public void testIsSameWorkTypeClassificationWholeDay_true() {
		
		HalfDayWorkTypeClassification target = HalfDayWorkTypeClassification.createByAmAndPm(
						WorkTypeClassification.Attendance
					,	WorkTypeClassification.Attendance);
			
		boolean result = target.isSameWorkTypeClassificationWholeDay();
		
		assertThat( result ).isTrue();
	}
	
	@Test
	public void testIsSameWorkTypeClassificationWholeDay_false() {
		
		HalfDayWorkTypeClassification target = HalfDayWorkTypeClassification.createByAmAndPm(
					WorkTypeClassification.Attendance
				,	WorkTypeClassification.AnnualHoliday);
		
		boolean result = target.isSameWorkTypeClassificationWholeDay();
		
		assertThat( result ).isFalse();
	}
	
	@Test
	public void testGetAsMap() {
		
		HalfDayWorkTypeClassification target = HalfDayWorkTypeClassification.createByAmAndPm(
				WorkTypeClassification.Attendance, 
				WorkTypeClassification.AnnualHoliday);
		
		Map<AmPmAtr, WorkTypeClassification> result = target.getAsMap();
		
		assertThat( result.entrySet() )
		.extracting( d -> d.getKey(), d -> d.getValue() )
		.containsOnly( 
				tuple( AmPmAtr.AM, WorkTypeClassification.Attendance),
				tuple( AmPmAtr.PM, WorkTypeClassification.AnnualHoliday) );
	}
}
