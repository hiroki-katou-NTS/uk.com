/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.statutory.worktime.common;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.TimeOfDay;
import nts.uk.ctx.at.shared.dom.common.WeeklyTime;
import nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComRegularLaborTime;
import nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComRegularLaborTimeGetMemento;
import nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComTransLaborTime;
import nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComTransLaborTimeGetMemento;
import nts.uk.ctx.at.shared.dom.statutory.worktime.shared.WeekStart;
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.DailyUnit;
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.WeeklyUnit;
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.WorkingTimeSetting;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class WorkingTimeSettingDto.
 */
@Getter
@Setter
@Builder
public class WorkingTimeSettingDto {

	/** The weekly time. */
	private WeeklyUnitDto weeklyTime;

	/** The daily time. */
	private DailyUnitDto dailyTime;

	public ComRegularLaborTime toComRegularLaborTimeDomain() {
		return new ComRegularLaborTime(new ComRegularLaborTimeDtoMemento(weeklyTime, dailyTime));
	}
	
	public ComTransLaborTime toComTransLaborTimeDomain() {
		return new ComTransLaborTime(new ComTransLaborTimeDtoMemento(weeklyTime, dailyTime));
	}
	
	private class ComRegularLaborTimeDtoMemento implements ComRegularLaborTimeGetMemento{
		
		private WeeklyUnitDto weeklyTime;
		private DailyUnitDto dailyTime;
		
		public ComRegularLaborTimeDtoMemento(WeeklyUnitDto weeklyTime, DailyUnitDto dailyTime) {
			this.weeklyTime = weeklyTime;
			this.dailyTime = dailyTime;
		}

		@Override
		public CompanyId getCompanyId() {
			return new CompanyId(AppContexts.user().companyId());
		}

		@Override
		public WorkingTimeSetting getWorkingTimeSet() {
			WeeklyUnit weeklyUnit = new WeeklyUnit(new WeeklyTime(this.weeklyTime.getTime()), WeekStart.valueOf(this.weeklyTime.getStart()));
			DailyUnit dailyUnit = new DailyUnit(new TimeOfDay(this.dailyTime.getDailyTime()));
			return new WorkingTimeSetting(weeklyUnit, dailyUnit);
		}
		
	}
	
	private class ComTransLaborTimeDtoMemento implements ComTransLaborTimeGetMemento {

		private WeeklyUnitDto weeklyTime;
		private DailyUnitDto dailyTime;

		public ComTransLaborTimeDtoMemento(WeeklyUnitDto weeklyTime, DailyUnitDto dailyTime) {
			super();
			this.weeklyTime = weeklyTime;
			this.dailyTime = dailyTime;
		}

		@Override
		public CompanyId getCompanyId() {
			return new CompanyId(AppContexts.user().companyId());
		}

		@Override
		public WorkingTimeSetting getWorkingTimeSet() {
			WeeklyUnit weeklyUnit = new WeeklyUnit(new WeeklyTime(this.weeklyTime.getTime()), WeekStart.valueOf(this.weeklyTime.getStart()));
			DailyUnit dailyUnit = new DailyUnit(new TimeOfDay(this.dailyTime.getDailyTime()));
			return new WorkingTimeSetting(weeklyUnit, dailyUnit);
		}
		
	}
	
}
