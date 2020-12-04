package nts.uk.ctx.at.function.dom.scheduletable;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;
import nts.uk.shr.com.enumcommon.NotUseAtr;

public class ScheduleTableOutputSettingTest {
	
	@Test
	public void testGetter() {
		
		ScheduleTableOutputSetting target = ScheduleTableOutputSettingHelper.defaultCreate();
		
		NtsAssert.invokeGetters(target);
	}
	
	@Test
	public void testCreate_exception_workplaceCounterCategories_duplicate(){
		
		NtsAssert.systemError( () ->
				ScheduleTableOutputSettingHelper.createWithCounters(
					Arrays.asList(
							WorkplaceCounterCategory.LABOR_COSTS_AND_TIME, 
							WorkplaceCounterCategory.LABOR_COSTS_AND_TIME), 
					Collections.emptyList())
		);
	}
	
	@Test
	public void testCreate_exception_personalCounterCategories_duplicate(){
		
		NtsAssert.systemError( () ->
				ScheduleTableOutputSettingHelper.createWithCounters(
					Collections.emptyList(),
					Arrays.asList(
							PersonalCounterCategory.MONTHLY_EXPECTED_SALARY,
							PersonalCounterCategory.MONTHLY_EXPECTED_SALARY ))
		);
	}
	
	
	@Test
	public void testCreate_exception_counterCategories_duplicate(){
		
		NtsAssert.systemError( () ->
				ScheduleTableOutputSettingHelper.createWithCounters(
					Arrays.asList(
							WorkplaceCounterCategory.LABOR_COSTS_AND_TIME,
							WorkplaceCounterCategory.LABOR_COSTS_AND_TIME), 
					Arrays.asList(
							PersonalCounterCategory.MONTHLY_EXPECTED_SALARY,
							PersonalCounterCategory.MONTHLY_EXPECTED_SALARY ))
		);
	}
	
	public void testClone_ok() {

		OutputItem outputItem = OutputItem.create( NotUseAtr.USE, NotUseAtr.USE, NotUseAtr.USE, 
				Arrays.asList(OneRowOutputItem.create(
						Optional.of(ScheduleTablePersonalInfoItem.EMPLOYEE_NAME), 
						Optional.empty(), 
						Optional.of(ScheduleTableAttendanceItem.SHIFT))));
		
		ScheduleTableOutputSetting target = ScheduleTableOutputSetting.create(
				new OutputSettingCode("code1"), 
				new OutputSettingName("name1"),
				outputItem,
				Arrays.asList(WorkplaceCounterCategory.LABOR_COSTS_AND_TIME), 
				Arrays.asList(PersonalCounterCategory.ATTENDANCE_HOLIDAY_DAYS));
		
		ScheduleTableOutputSetting result = target.clone(new OutputSettingCode("code2"), new OutputSettingName("name2"));
		
		assertThat(result.getCode().v()).isEqualTo("code2");
		assertThat(result.getName().v()).isEqualTo("name2");
		assertThat(result.getOutputItem()).isEqualTo(outputItem);
		assertThat(result.getWorkplaceCounterCategories()).containsExactly(WorkplaceCounterCategory.LABOR_COSTS_AND_TIME);
		assertThat(result.getPersonalCounterCategories()).containsExactly(PersonalCounterCategory.ATTENDANCE_HOLIDAY_DAYS);
		
		/**
		 * test
		 * target.workplaceCounterCategories != result.workplaceCounterCategories
		 * target.personalCounterCategories != result.personalCounterCategories
		 * 
		 * 上の条件が妥当かどうかチェックする。 
		 */
		target.getWorkplaceCounterCategories().add(WorkplaceCounterCategory.EXTERNAL_BUDGET);
		target.getPersonalCounterCategories().clear();
		
		assertThat(result.getWorkplaceCounterCategories()).containsExactly(WorkplaceCounterCategory.LABOR_COSTS_AND_TIME);
		assertThat(result.getPersonalCounterCategories()).containsExactly(PersonalCounterCategory.ATTENDANCE_HOLIDAY_DAYS);
	}

}
