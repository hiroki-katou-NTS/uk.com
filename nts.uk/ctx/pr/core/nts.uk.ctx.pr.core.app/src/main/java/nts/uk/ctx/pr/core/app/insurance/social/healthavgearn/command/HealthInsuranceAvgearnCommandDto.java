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
 * The Class HealthInsuranceAvgearnBaseCommand.
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
	 * @param historyId
	 *            the history id
	 * @param levelCode
	 *            the level code
	 * @return the health insurance avgearn
	 */
	public HealthInsuranceAvgearn toDomain(String historyId, Integer levelCode) {
		HealthInsuranceAvgearnCommandDto dto = this;

		// Transfer data
		HealthInsuranceAvgearn updatedHealthInsuranceAvgearn = new HealthInsuranceAvgearn(
				new HealthInsuranceAvgearnGetMemento() {

					@Override
					public HealthInsuranceAvgearnValue getPersonalAvg() {
						return HealthInsuranceAvgearnValueDto.toDomain(dto.getPersonalAvg());
					}

					@Override
					public Integer getLevelCode() {
						return levelCode;
					}

					@Override
					public String getHistoryId() {
						return historyId;
					}

					@Override
					public HealthInsuranceAvgearnValue getCompanyAvg() {
						return HealthInsuranceAvgearnValueDto.toDomain(dto.getCompanyAvg());
					}
				});

		return updatedHealthInsuranceAvgearn;
	}

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
