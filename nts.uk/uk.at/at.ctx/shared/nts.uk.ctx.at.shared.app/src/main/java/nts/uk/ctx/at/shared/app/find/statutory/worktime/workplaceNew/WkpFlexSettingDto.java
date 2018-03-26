/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.statutory.worktime.workplaceNew;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import lombok.Data;
import nts.uk.ctx.at.shared.app.command.statutory.worktime.common.MonthlyUnitDto;
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.MonthlyUnit;
import nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.WkpFlexSetting;
import nts.uk.shr.com.context.AppContexts;


/**
 * The Class WkpFlexSettingDto.
 */

@Data
public class WkpFlexSettingDto{
	
	/** The wkp id. */
	/** 社員ID. */
	private String wkpId;
	
	/** The year. */
	/** 年. */
	private int year;
	
	/** The company id. */
	private String companyId;
	
	/** The statutory setting. */
	/** 法定時間. */
	private List<MonthlyUnitDto> statutorySetting;
	
	/** The specified setting. */
	/** 所定時間. */
	private List<MonthlyUnitDto> specifiedSetting;

	/**
	 * From domain.
	 *
	 * @param domain the domain
	 * @return the wkp flex setting dto
	 */
	public static WkpFlexSettingDto fromDomain(WkpFlexSetting domain) {
		WkpFlexSettingDto dto = new WkpFlexSettingDto();
		dto.setYear(domain.getYear().v());
		dto.setCompanyId(AppContexts.user().companyId());
		dto.setWkpId(domain.getWorkplaceId().v());
		
		Function<MonthlyUnit, MonthlyUnitDto> funMap = monthly -> {
			return new MonthlyUnitDto(monthly.getMonth().v(), monthly.getMonthlyTime().v());
		};
		
		List<MonthlyUnitDto> statutoryMonthlys = domain.getStatutorySetting().stream().map(funMap).collect(Collectors.toList());
		dto.setStatutorySetting(statutoryMonthlys);
		
		List<MonthlyUnitDto> specMonthlys = domain.getSpecifiedSetting().stream().map(funMap).collect(Collectors.toList());
		dto.setSpecifiedSetting(specMonthlys);
		return dto;
	}
}
