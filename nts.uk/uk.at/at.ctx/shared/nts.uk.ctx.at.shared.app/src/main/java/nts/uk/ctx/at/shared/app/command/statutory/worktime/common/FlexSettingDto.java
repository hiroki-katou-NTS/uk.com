/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.statutory.worktime.common;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.Month;
import nts.uk.ctx.at.shared.dom.common.MonthlyEstimateTime;
import nts.uk.ctx.at.shared.dom.common.Year;
import nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComFlexSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComFlexSettingGetMemento;
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.MonthlyUnit;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class FlexSettingDto.
 */
@Getter
@Setter
@Builder
public class FlexSettingDto {

	/** The statutory setting. */
	protected List<MonthlyUnitDto> statutorySetting;

	/** The specified setting. */
	protected List<MonthlyUnitDto> specifiedSetting;
	
	public ComFlexSetting toDomain(int year) {
		return new ComFlexSetting(new ComFlexSettingMemento(year, this.statutorySetting, this.specifiedSetting));
	}
	
	private class ComFlexSettingMemento implements ComFlexSettingGetMemento {

		private int year;
		private List<MonthlyUnitDto> statutorySetting;
		private List<MonthlyUnitDto> specifiedSetting;

		public ComFlexSettingMemento(int year, List<MonthlyUnitDto> statutorySetting, List<MonthlyUnitDto> specifiedSetting) {
			this.year = year;
			this.statutorySetting = statutorySetting;
			this.specifiedSetting = specifiedSetting;
		}

		@Override
		public Year getYear() {
			return new Year(this.year);
		}

		@Override
		public List<MonthlyUnit> getStatutorySetting() {
			return this.statutorySetting.stream().map(funcMap).collect(Collectors.toList());
		}

		@Override
		public List<MonthlyUnit> getSpecifiedSetting() {
			return this.specifiedSetting.stream().map(funcMap).collect(Collectors.toList());
		}

		@Override
		public CompanyId getCompanyId() {
			return new CompanyId(AppContexts.user().companyId());
		}
		
		private Function<MonthlyUnitDto, MonthlyUnit> funcMap = dto -> {
			return new MonthlyUnit(new Month(dto.getMonth()), new MonthlyEstimateTime(dto.getMonthlyTime()));
		};
		
	}
}
