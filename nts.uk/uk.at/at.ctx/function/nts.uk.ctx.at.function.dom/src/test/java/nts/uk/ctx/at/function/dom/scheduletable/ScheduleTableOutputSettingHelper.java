package nts.uk.ctx.at.function.dom.scheduletable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import nts.uk.shr.com.enumcommon.NotUseAtr;

public class ScheduleTableOutputSettingHelper {

	public static ScheduleTableOutputSetting defaultCreate() {
		
		return new ScheduleTableOutputSetting(
				new OutputSettingCode("code"), 
				new OutputSettingName("name"),
				OutputItem.create( NotUseAtr.USE, NotUseAtr.USE, NotUseAtr.USE, 
						Arrays.asList(OneRowOutputItem.create(
								Optional.of(ScheduleTablePersonalInfoItem.EMPLOYEE_NAME), 
								Optional.empty(), 
								Optional.of(ScheduleTableAttendanceItem.SHIFT)))),
				Arrays.asList(WorkplaceCounterCategory.LABOR_COSTS_AND_TIME), 
				Arrays.asList(PersonalCounterCategory.ATTENDANCE_HOLIDAY_DAYS));
				
	}
	
	public static ScheduleTableOutputSetting createWithCounters(
		List<WorkplaceCounterCategory> workplaceCounterCategories,
		List<PersonalCounterCategory> personalCounterCategories) {
		
		return ScheduleTableOutputSetting.create(
				new OutputSettingCode("code"), 
				new OutputSettingName("name"),
				OutputItem.create( NotUseAtr.USE, NotUseAtr.USE, NotUseAtr.USE, 
						Arrays.asList(OneRowOutputItem.create(
								Optional.of(ScheduleTablePersonalInfoItem.EMPLOYEE_NAME), 
								Optional.empty(), 
								Optional.of(ScheduleTableAttendanceItem.SHIFT)))),
				workplaceCounterCategories, 
				personalCounterCategories );
				
	}
	
	public static ScheduleTableOutputSetting createWithCodeName( String code, String name) {
			
			return ScheduleTableOutputSetting.create(
					new OutputSettingCode(code), 
					new OutputSettingName(name),
					OutputItem.create( NotUseAtr.USE, NotUseAtr.USE, NotUseAtr.USE, 
							Arrays.asList(OneRowOutputItem.create(
									Optional.of(ScheduleTablePersonalInfoItem.EMPLOYEE_NAME), 
									Optional.empty(), 
									Optional.of(ScheduleTableAttendanceItem.SHIFT)))),
					Arrays.asList(WorkplaceCounterCategory.LABOR_COSTS_AND_TIME), 
					Arrays.asList(PersonalCounterCategory.ATTENDANCE_HOLIDAY_DAYS));
					
		}
}
