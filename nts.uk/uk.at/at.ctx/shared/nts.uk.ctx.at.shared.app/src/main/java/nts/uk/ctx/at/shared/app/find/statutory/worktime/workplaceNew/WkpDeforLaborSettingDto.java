/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.statutory.worktime.workplaceNew;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.shared.app.command.statutory.worktime.common.MonthlyUnitDto;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSet;


/**
 * The Class WkpDeforLaborSettingDto.
 */
@Data
@AllArgsConstructor
public class WkpDeforLaborSettingDto {

	/** The company id. */
	/** 会社ID. */
	private String companyId;

	/** The wkp id. */
	/** 社員ID. */
	private String wkpId;

	/** The year. */
	/** 年. */
	private int year;

	/** The statutory setting. */
	/** 法定時間. */
	private List<MonthlyUnitDto> statutorySetting;

	public static <T extends MonthlyWorkTimeSet> WkpDeforLaborSettingDto with (
			int year, String companyId, String wkpid, List<T> workTime) {
		
		return new WkpDeforLaborSettingDto(companyId, wkpid, year, workTime.stream()
				.map(c -> new MonthlyUnitDto(c.getYm().month(), c.getLaborTime().getLegalLaborTime().v()))
				.collect(Collectors.toList()));
	}
}
