/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.vacation.setting.compensatoryleave.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.TimeDigestiveUnit;
import nts.uk.ctx.at.shared.dom.vacation.setting.TimeVacationDigestUnit;

/**
 * The Class CompensatoryDigestiveTimeUnitDto.
 */
@Getter
@Setter
@AllArgsConstructor
public class CompensatoryDigestiveTimeUnitDto {

	/** The is manage by time. */
	private Integer isManageByTime;

	/** The digestive unit. */
	private Integer digestiveUnit;

	public TimeVacationDigestUnit toDomainNew() {
		return new TimeVacationDigestUnit(
				ManageDistinct.valueOf(this.isManageByTime),
				TimeDigestiveUnit.valueOf(this.digestiveUnit));
	}
	
	public static CompensatoryDigestiveTimeUnitDto toDto(TimeVacationDigestUnit domain) {
		return new CompensatoryDigestiveTimeUnitDto(domain.getManage().value, domain.getDigestUnit().value);
	}
	/**
	 * To domain.
	 *
	 * @return the compensatory digestive time unit
	 */
	public TimeVacationDigestUnit toDomain() {
		return new TimeVacationDigestUnit(
				ManageDistinct.valueOf(this.isManageByTime),
				TimeDigestiveUnit.valueOf(this.digestiveUnit));
	}

}
