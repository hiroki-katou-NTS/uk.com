package nts.uk.ctx.at.shared.app.command.statutory.worktime.common;

import java.util.List;
import java.util.stream.Collectors;

import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.Month;
import nts.uk.ctx.at.shared.dom.common.MonthlyEstimateTime;
import nts.uk.ctx.at.shared.dom.common.WorkplaceId;
import nts.uk.ctx.at.shared.dom.common.Year;
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.MonthlyUnit;
import nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.WkpFlexSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.WkpFlexSettingGetMemento;
import nts.uk.shr.com.context.AppContexts;

public class WkpFlexSettingDto extends FlexSettingDto{
	
	public WkpFlexSetting toWkpDomain(int year, String wkpId) {
		return new WkpFlexSetting(new WkpFlexSettingMemento(year, wkpId,
				this.statutorySetting, this.specifiedSetting, this.weekAveSetting));
	}
	
	private class WkpFlexSettingMemento implements WkpFlexSettingGetMemento {

		private int year;
		private String wkpId;
		private List<MonthlyUnitDto> statutorySetting;
		private List<MonthlyUnitDto> specifiedSetting;
		private List<MonthlyUnitDto> weekAveSetting;

		public WkpFlexSettingMemento(int year, String wkpId, List<MonthlyUnitDto> statutorySetting,
				List<MonthlyUnitDto> specifiedSetting, List<MonthlyUnitDto> weekAveSetting) {
			this.year = year;
			this.wkpId = wkpId;
			this.statutorySetting = statutorySetting;
			this.specifiedSetting = specifiedSetting;
			this.weekAveSetting = weekAveSetting;
		}

		@Override
		public Year getYear() {
			return new Year(this.year);
		}

		@Override
		public List<MonthlyUnit> getStatutorySetting() {
			return this.statutorySetting.stream().map(dto -> {
				return new MonthlyUnit(new Month(dto.getMonth()), new MonthlyEstimateTime(dto.getMonthlyTime()));
			}).collect(Collectors.toList());
		}

		@Override
		public List<MonthlyUnit> getSpecifiedSetting() {
			return this.specifiedSetting.stream().map(dto -> {
				return new MonthlyUnit(new Month(dto.getMonth()), new MonthlyEstimateTime(dto.getMonthlyTime()));
			}).collect(Collectors.toList());
		}

		@Override
		public List<MonthlyUnit> getWeekAveSetting() {
			return this.weekAveSetting.stream().map(dto -> {
				return new MonthlyUnit(new Month(dto.getMonth()), new MonthlyEstimateTime(dto.getMonthlyTime()));
			}).collect(Collectors.toList());
		}
		
		@Override
		public CompanyId getCompanyId() {
			return new CompanyId(AppContexts.user().companyId());
		}

		@Override
		public WorkplaceId getWorkplaceId() {
			return new WorkplaceId(this.wkpId);
		}
		
	}
	

}
