package nts.uk.ctx.at.shared.app.command.statutory.worktime.common;

import java.util.List;
import java.util.stream.Collectors;

import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.Month;
import nts.uk.ctx.at.shared.dom.common.MonthlyEstimateTime;
import nts.uk.ctx.at.shared.dom.common.WorkplaceId;
import nts.uk.ctx.at.shared.dom.common.Year;
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.MonthlyUnit;
import nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.WkpNormalSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.WkpNormalSettingGetMemento;
import nts.uk.shr.com.context.AppContexts;

public class WkpNormalSettingDto extends NormalSettingDto {
	
	public WkpNormalSetting toWkpDomain(int year, String wkpId) {
		return new WkpNormalSetting(new WkpNormalSettingMemento(year, wkpId, this.statutorySetting));
	}
	
	private class WkpNormalSettingMemento implements WkpNormalSettingGetMemento {

		private List<MonthlyUnitDto> statutorySetting;
		private int year;
		private String wkpId;
		
		public WkpNormalSettingMemento(int year, String wkpId, List<MonthlyUnitDto> statutorySetting) {
			this.statutorySetting = statutorySetting;
			this.year = year;
			this.wkpId = wkpId;
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
		public CompanyId getCompanyId() {
			return new CompanyId(AppContexts.user().companyId());
		}

		@Override
		public WorkplaceId getWorkplaceId() {
			return new WorkplaceId(this.wkpId);
		}
	}

}
