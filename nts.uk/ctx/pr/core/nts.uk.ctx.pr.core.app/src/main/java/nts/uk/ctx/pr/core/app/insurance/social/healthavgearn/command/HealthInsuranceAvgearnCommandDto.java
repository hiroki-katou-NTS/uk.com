/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.insurance.social.healthavgearn.command;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.pr.core.app.insurance.social.healthavgearn.find.HealthInsuranceAvgearnValueDto;
import nts.uk.ctx.pr.core.dom.insurance.social.healthavgearn.HealthInsuranceAvgearn;
import nts.uk.ctx.pr.core.dom.insurance.social.healthavgearn.HealthInsuranceAvgearnGetMemento;
import nts.uk.ctx.pr.core.dom.insurance.social.healthavgearn.HealthInsuranceAvgearnValue;

/**
 * The Class HealthInsuranceAvgearnCommandDto.
 */

@Getter
@Setter
public class HealthInsuranceAvgearnCommandDto {

	/** The history id. */
	private String historyId;

	/** The level code. */
	private Integer levelCode;

	/** The company avg. */
	private HealthInsuranceAvgearnValueDto companyAvg;

	/** The personal avg. */
	private HealthInsuranceAvgearnValueDto personalAvg;

	/**
	 * To domain.
	 *
	 * @return the health insurance avgearn
	 */
	public HealthInsuranceAvgearn toDomain() {
		HealthInsuranceAvgearnCommandDto dto = this;

		// Transfer data
		HealthInsuranceAvgearn healthInsuranceAvgearn = new HealthInsuranceAvgearn(
				new HealthInsuranceAvgearnGetMemento() {

					@Override
					public HealthInsuranceAvgearnValue getPersonalAvg() {
						return HealthInsuranceAvgearnValueDto.toDomain(dto.getPersonalAvg());
					}

					@Override
					public Integer getLevelCode() {
						return dto.getLevelCode();
					}

					@Override
					public String getHistoryId() {
						return dto.getHistoryId();
					}

					@Override
					public HealthInsuranceAvgearnValue getCompanyAvg() {
						return HealthInsuranceAvgearnValueDto.toDomain(dto.getCompanyAvg());
					}
				});

		return healthInsuranceAvgearn;
	}
}
