/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.insurance.social.pensionavgearn.command;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.pr.core.app.insurance.social.pensionavgearn.find.PensionAvgearnValueDto;
import nts.uk.ctx.pr.core.dom.insurance.CommonAmount;
import nts.uk.ctx.pr.core.dom.insurance.InsuranceAmount;
import nts.uk.ctx.pr.core.dom.insurance.social.pensionavgearn.PensionAvgearn;
import nts.uk.ctx.pr.core.dom.insurance.social.pensionavgearn.PensionAvgearnGetMemento;
import nts.uk.ctx.pr.core.dom.insurance.social.pensionavgearn.PensionAvgearnValue;

/**
 * The Class PensionAvgearnBaseCommand.
 */
@Getter
@Setter
public class PensionAvgearnBaseCommand {

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
		PensionAvgearnBaseCommand command = this;

		// Transfer data
		PensionAvgearn updatedPensionAvgearn = new PensionAvgearn(new PensionAvgearnGetMemento() {

			@Override
			public PensionAvgearnValue getPersonalPension() {
				return new PensionAvgearnValue(new CommonAmount(command.getPersonalPension().getMaleAmount()),
						new CommonAmount(command.getPersonalPension().getFemaleAmount()),
						new CommonAmount(command.getPersonalPension().getUnknownAmount()));
			}

			@Override
			public PensionAvgearnValue getPersonalFundExemption() {
				return new PensionAvgearnValue(new CommonAmount(command.getPersonalFundExemption().getMaleAmount()),
						new CommonAmount(command.getPersonalFundExemption().getFemaleAmount()),
						new CommonAmount(command.getPersonalFundExemption().getUnknownAmount()));
			}

			@Override
			public PensionAvgearnValue getPersonalFund() {
				return new PensionAvgearnValue(new CommonAmount(command.getPersonalFund().getMaleAmount()),
						new CommonAmount(command.getPersonalFund().getFemaleAmount()),
						new CommonAmount(command.getPersonalFund().getUnknownAmount()));
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
				return new PensionAvgearnValue(new CommonAmount(command.getCompanyPension().getMaleAmount()),
						new CommonAmount(command.getCompanyPension().getFemaleAmount()),
						new CommonAmount(command.getCompanyPension().getUnknownAmount()));
			}

			@Override
			public PensionAvgearnValue getCompanyFundExemption() {
				return new PensionAvgearnValue(new CommonAmount(command.getCompanyFundExemption().getMaleAmount()),
						new CommonAmount(command.getCompanyFundExemption().getFemaleAmount()),
						new CommonAmount(command.getCompanyFundExemption().getUnknownAmount()));
			}

			@Override
			public PensionAvgearnValue getCompanyFund() {
				return new PensionAvgearnValue(new CommonAmount(command.getCompanyFund().getMaleAmount()),
						new CommonAmount(command.getCompanyFund().getFemaleAmount()),
						new CommonAmount(command.getCompanyFund().getUnknownAmount()));
			}

			@Override
			public InsuranceAmount getChildContributionAmount() {
				return new InsuranceAmount(command.getChildContributionAmount());
			}
		});

		return updatedPensionAvgearn;
	}
}
