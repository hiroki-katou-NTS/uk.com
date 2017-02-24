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
 * The Class PensionAvgearnBaseCommand.
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
	 * @param historyId
	 *            the history id
	 * @param levelCode
	 *            the level code
	 * @return the pension avgearn
	 */
	public PensionAvgearn toDomain(String historyId, Integer levelCode) {
		PensionAvgearnCommandDto command = this;

		// Transfer data
		PensionAvgearn updatedPensionAvgearn = new PensionAvgearn(new PensionAvgearnGetMemento() {

			@Override
			public PensionAvgearnValue getPersonalPension() {
				return PensionAvgearnValueDto.toDomain(command.getPersonalPension());
			}

			@Override
			public PensionAvgearnValue getPersonalFundExemption() {
				return PensionAvgearnValueDto.toDomain(command.getPersonalFundExemption());
			}

			@Override
			public PensionAvgearnValue getPersonalFund() {
				//return PensionAvgearnValueDto.toDomain(command.getPersonalFund());
				// thua truong nay?
				return new PensionAvgearnValue();
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
			public PensionAvgearnValue getCompanyPension() {
				return PensionAvgearnValueDto.toDomain(command.getCompanyPension());
			}

			@Override
			public PensionAvgearnValue getCompanyFundExemption() {
				return PensionAvgearnValueDto.toDomain(command.getCompanyFundExemption());
			}

			@Override
			public PensionAvgearnValue getCompanyFund() {
				//return PensionAvgearnValueDto.toDomain(command.getCompanyFund());
				// Thua truong nay?
				return new PensionAvgearnValue();
			}

			@Override
			public InsuranceAmount getChildContributionAmount() {
				return new InsuranceAmount(command.getChildContributionAmount());
			}
		});

		return updatedPensionAvgearn;
	}
}
