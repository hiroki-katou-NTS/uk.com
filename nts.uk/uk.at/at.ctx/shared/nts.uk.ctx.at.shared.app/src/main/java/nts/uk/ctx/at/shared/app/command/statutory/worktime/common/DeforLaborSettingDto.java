/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.statutory.worktime.common;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.Month;
import nts.uk.ctx.at.shared.dom.common.MonthlyEstimateTime;
import nts.uk.ctx.at.shared.dom.common.Year;
import nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComDeforLaborSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComDeforLaborSettingGetMemento;
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.MonthlyUnit;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class DeforLaborSettingDto.
 */
@Getter
@Setter
public class DeforLaborSettingDto {
	
	/** The year. */
	protected Integer year;

	/** The statutory setting. */
	protected List<MonthlyUnitDto> statutorySetting;
	
	public ComDeforLaborSetting toDomain(int year) {
		return new ComDeforLaborSetting(new ComDeforLaborSettingMemento(year, this.statutorySetting));
	}
	
	private class ComDeforLaborSettingMemento implements ComDeforLaborSettingGetMemento {
		
		private Integer year;
		private List<MonthlyUnitDto> statutorySetting;
		
		public ComDeforLaborSettingMemento(Integer year, List<MonthlyUnitDto> statutorySetting) {
			this.year = year;
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
	}
}
