/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.insurance.social.pensionavgearn.command;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.pr.core.app.insurance.social.pensionavgearn.find.PensionAvgearnValueDto;
import nts.uk.ctx.pr.core.dom.insurance.InsuranceAmount;
import nts.uk.ctx.pr.core.dom.insurance.social.pensionavgearn.PensionAvgearn;
import nts.uk.ctx.pr.core.dom.insurance.social.pensionavgearn.PensionAvgearnGetMemento;
import nts.uk.ctx.pr.core.dom.insurance.social.pensionavgearn.PensionAvgearnValue;

/**
 * The Class PensionAvgearnCommandDto.
 */

@Getter
@Setter
public class PensionAvgearnCommandDto {

	/** The history id. */
	private String historyId;

	/** The level code. */
	private Integer levelCode;

	/** The child contribution amount. */
	private BigDecimal childContributionAmount;

	/** The company fund. */
	private PensionAvgearnValueDto companyFund;

	/** The company fund exemption. */
	private PensionAvgearnValueDto companyFundExemption;

	/** The company pension. */
	private PensionAvgearnValueDto companyPension;

	/** The personal fund. */
	private PensionAvgearnValueDto personalFund;

	/** The personal fund exemption. */
	private PensionAvgearnValueDto personalFundExemption;

	/** The personal pension. */
	private PensionAvgearnValueDto personalPension;

	/**
	 * To domain.
	 *
	 * @return the pension avgearn
	 */
	public PensionAvgearn toDomain() {
		PensionAvgearnCommandDto dto = this;

		// Transfer data
		PensionAvgearn updatedPensionAvgearn = new PensionAvgearn(new PensionAvgearnGetMemento() {

			@Override
			public PensionAvgearnValue getPersonalPension() {
				return PensionAvgearnValueDto.toDomain(dto.getPersonalPension());
			}

			@Override
			public PensionAvgearnValue getPersonalFundExemption() {
				return PensionAvgearnValueDto.toDomain(dto.getPersonalFundExemption());
			}

			@Override
			public PensionAvgearnValue getPersonalFund() {
				return PensionAvgearnValueDto.toDomain(dto.getPersonalFund());
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
			public PensionAvgearnValue getCompanyPension() {
				return PensionAvgearnValueDto.toDomain(dto.getCompanyPension());
			}

			@Override
			public PensionAvgearnValue getCompanyFundExemption() {
				return PensionAvgearnValueDto.toDomain(dto.getCompanyFundExemption());
			}

			@Override
			public PensionAvgearnValue getCompanyFund() {
				return PensionAvgearnValueDto.toDomain(dto.getCompanyFund());
			}

			@Override
			public InsuranceAmount getChildContributionAmount() {
				return new InsuranceAmount(dto.getChildContributionAmount());
			}
		});

		return updatedPensionAvgearn;
	}
}
