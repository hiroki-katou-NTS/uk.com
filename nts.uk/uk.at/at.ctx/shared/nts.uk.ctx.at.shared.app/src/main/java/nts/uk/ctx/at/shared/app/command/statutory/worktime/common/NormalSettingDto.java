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
import nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComNormalSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComNormalSettingGetMemento;
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.MonthlyUnit;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class NormalSettingDto.
 */
@Getter
@Setter
public class NormalSettingDto {

	/** The statutory setting. */
	protected List<MonthlyUnitDto> statutorySetting;
	
	public ComNormalSetting toDomain(int year) {
		return new ComNormalSetting(new ComNormalSettingMemento(year, this.statutorySetting));
	}
	
	private class ComNormalSettingMemento implements ComNormalSettingGetMemento {

		private List<MonthlyUnitDto> statutorySetting;
		private int year;
		
		public ComNormalSettingMemento(int year, List<MonthlyUnitDto> statutorySetting) {
			this.statutorySetting = statutorySetting;
			this.year = year;
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