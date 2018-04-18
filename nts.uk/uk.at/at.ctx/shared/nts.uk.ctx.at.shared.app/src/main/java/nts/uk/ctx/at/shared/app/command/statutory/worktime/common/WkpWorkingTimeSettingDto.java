package nts.uk.ctx.at.shared.app.command.statutory.worktime.common;

import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.TimeOfDay;
import nts.uk.ctx.at.shared.dom.common.WeeklyTime;
import nts.uk.ctx.at.shared.dom.common.WorkplaceId;
import nts.uk.ctx.at.shared.dom.statutory.worktime.shared.WeekStart;
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.DailyUnit;
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.WeeklyUnit;
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.WorkingTimeSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.WkpRegularLaborTime;
import nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.WkpRegularLaborTimeGetMemento;
import nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.WkpTransLaborTime;
import nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.WkpTransLaborTimeGetMemento;
import nts.uk.shr.com.context.AppContexts;

public class WkpWorkingTimeSettingDto extends WorkingTimeSettingDto {
	
	public WkpRegularLaborTime toWkpRegularLaborTimeDomain(String wkpId) {
		return new WkpRegularLaborTime(new WkpRegularLaborTimeDtoMemento(wkpId, this.weeklyTime, this.dailyTime));
	}
	
	public WkpTransLaborTime toWkpTransTimeDomain(String wkpId) {
		return new WkpTransLaborTime(new WkpTransTimeDtoMemento(wkpId, this.weeklyTime, this.dailyTime));
	}
	
	private class WkpTransTimeDtoMemento implements WkpTransLaborTimeGetMemento {
		
		private String wkpId;
		private WeeklyUnitDto weeklyTime;
		private DailyUnitDto dailyTime;
		
		public WkpTransTimeDtoMemento(String wkpId, WeeklyUnitDto weeklyTime, DailyUnitDto dailyTime) {
			this.wkpId = wkpId;
			this.weeklyTime = weeklyTime;
			this.dailyTime = dailyTime;
		}

		@Override
		public CompanyId getCompanyId() {
			return new CompanyId(AppContexts.user().companyId());
		}

		@Override
		public WorkplaceId getWorkplaceId() {
			return new WorkplaceId(this.wkpId);
		}

		@Override
		public WorkingTimeSetting getWorkingTimeSet() {
			WeeklyUnit weeklyUnit = new WeeklyUnit(new WeeklyTime(this.weeklyTime.getTime()), WeekStart.valueOf(this.weeklyTime.getStart()));
			DailyUnit dailyUnit = new DailyUnit(new TimeOfDay(this.dailyTime.getDailyTime()));
			return new WorkingTimeSetting(weeklyUnit, dailyUnit);
		}
		
	}
	
	private class WkpRegularLaborTimeDtoMemento implements WkpRegularLaborTimeGetMemento {
		
		private String wkpId;
		private WeeklyUnitDto weeklyTime;
		private DailyUnitDto dailyTime;
		
		public WkpRegularLaborTimeDtoMemento(String wkpId, WeeklyUnitDto weeklyTime, DailyUnitDto dailyTime) {
			super();
			this.wkpId = wkpId;
			this.weeklyTime = weeklyTime;
			this.dailyTime = dailyTime;
		}

		@Override
		public CompanyId getCompanyId() {
			return new CompanyId(AppContexts.user().companyId());
		}

		@Override
		public WorkplaceId getWorkplaceId() {
			return new WorkplaceId(this.wkpId);
		}

		@Override
		public WorkingTimeSetting getWorkingTimeSet() {
			WeeklyUnit weeklyUnit = new WeeklyUnit(new WeeklyTime(this.weeklyTime.getTime()), WeekStart.valueOf(this.weeklyTime.getStart()));
			DailyUnit dailyUnit = new DailyUnit(new TimeOfDay(this.dailyTime.getDailyTime()));
			return new WorkingTimeSetting(weeklyUnit, dailyUnit);
		}
		
	}

}
