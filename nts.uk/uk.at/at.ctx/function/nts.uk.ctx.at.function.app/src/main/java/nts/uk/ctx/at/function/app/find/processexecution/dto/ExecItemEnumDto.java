/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.function.app.find.processexecution.dto;

import java.util.List;

import lombok.Data;
import nts.arc.enums.EnumAdaptor;
import nts.arc.enums.EnumConstant;
import nts.uk.ctx.at.function.dom.processexecution.dailyperformance.DailyPerformanceItem;
import nts.uk.ctx.at.function.dom.processexecution.personalschedule.CreateScheduleYear;
import nts.uk.ctx.at.function.dom.processexecution.personalschedule.TargetMonth;
import nts.uk.ctx.at.function.dom.processexecution.tasksetting.detail.RepeatMonthDaysSelect;
import nts.uk.ctx.at.function.dom.processexecution.tasksetting.enums.RepeatContentItem;
import nts.uk.shr.infra.i18n.resource.I18NResourcesForUK;

@Data
public class ExecItemEnumDto {

	private List<EnumConstant> targetMonthList;

	private List<EnumConstant> dailyPerfItemList;
	
	private List<EnumConstant> repeatContentItemList;
	
	private List<EnumConstant> monthDayList;
	
	private List<EnumConstant> designatedYearList;
	
	public static ExecItemEnumDto init(I18NResourcesForUK i18n) {
		ExecItemEnumDto dto = new ExecItemEnumDto();
		dto.setTargetMonthList(EnumAdaptor.convertToValueNameList(TargetMonth.class, i18n));
		dto.setDailyPerfItemList(EnumAdaptor.convertToValueNameList(DailyPerformanceItem.class, i18n));
		dto.setRepeatContentItemList(EnumAdaptor.convertToValueNameList(RepeatContentItem.class, i18n));
		dto.setMonthDayList(EnumAdaptor.convertToValueNameList(RepeatMonthDaysSelect.class, i18n));
		dto.setDesignatedYearList(EnumAdaptor.convertToValueNameList(CreateScheduleYear.class, i18n));
		return dto;
	}
}
