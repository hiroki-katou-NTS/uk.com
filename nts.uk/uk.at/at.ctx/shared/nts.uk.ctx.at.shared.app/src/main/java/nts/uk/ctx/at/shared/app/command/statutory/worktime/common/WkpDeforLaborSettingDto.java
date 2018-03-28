package nts.uk.ctx.at.shared.app.command.statutory.worktime.common;

import java.util.List;
import java.util.stream.Collectors;

import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.Month;
import nts.uk.ctx.at.shared.dom.common.MonthlyEstimateTime;
import nts.uk.ctx.at.shared.dom.common.WorkplaceId;
import nts.uk.ctx.at.shared.dom.common.Year;
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.MonthlyUnit;
import nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.WkpDeforLaborSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.WkpDeforLaborSettingGetMemento;
import nts.uk.shr.com.context.AppContexts;

public class WkpDeforLaborSettingDto extends DeforLaborSettingDto {
	
	public WkpDeforLaborSetting toWkpDomain(int year, String wkpId) {
		return new WkpDeforLaborSetting(new WkpDeforLaborSettingMemento(year, wkpId, this.statutorySetting));
	}
	
	private class WkpDeforLaborSettingMemento implements WkpDeforLaborSettingGetMemento {
		
		private Integer year;
		private String wkpId;
		private List<MonthlyUnitDto> statutorySetting;
		
		public WkpDeforLaborSettingMemento(Integer year, String wkpId, List<MonthlyUnitDto> statutorySetting) {
			this.year = year;
			this.wkpId = wkpId;
			this.statutorySetting = statutorySetting;
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
