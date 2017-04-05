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
import nts.uk.ctx.pr.core.dom.insurance.social.pensionavgearn.PensionAvgearnGetMemento;
import nts.uk.ctx.pr.core.dom.insurance.social.pensionavgearn.PensionAvgearnValue;

/**
 * The Class PensionAvgearnCommandDto.
 */
@Setter
@Getter
public class PensionAvgearnCommandDto implements PensionAvgearnGetMemento {

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

	/* (non-Javadoc)
	 * @see nts.uk.ctx.pr.core.dom.insurance.social.pensionavgearn.PensionAvgearnGetMemento#getPersonalPension()
	 */
	@Override
	public PensionAvgearnValue getPersonalPension() {
		return PensionAvgearnValueDto.toDomain(this.personalPension);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.pr.core.dom.insurance.social.pensionavgearn.PensionAvgearnGetMemento#getPersonalFundExemption()
	 */
	@Override
	public PensionAvgearnValue getPersonalFundExemption() {
		return PensionAvgearnValueDto.toDomain(this.personalFundExemption);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.pr.core.dom.insurance.social.pensionavgearn.PensionAvgearnGetMemento#getPersonalFund()
	 */
	@Override
	public PensionAvgearnValue getPersonalFund() {
		return PensionAvgearnValueDto.toDomain(this.personalFund);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.pr.core.dom.insurance.social.pensionavgearn.PensionAvgearnGetMemento#getLevelCode()
	 */
	@Override
	public Integer getLevelCode() {
		return this.levelCode;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.pr.core.dom.insurance.social.pensionavgearn.PensionAvgearnGetMemento#getHistoryId()
	 */
	@Override
	public String getHistoryId() {
		// Set latter in commandHandler.
		return "";
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.pr.core.dom.insurance.social.pensionavgearn.PensionAvgearnGetMemento#getCompanyPension()
	 */
	@Override
	public PensionAvgearnValue getCompanyPension() {
		return PensionAvgearnValueDto.toDomain(this.companyPension);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.pr.core.dom.insurance.social.pensionavgearn.PensionAvgearnGetMemento#getCompanyFundExemption()
	 */
	@Override
	public PensionAvgearnValue getCompanyFundExemption() {
		return PensionAvgearnValueDto.toDomain(this.companyFundExemption);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.pr.core.dom.insurance.social.pensionavgearn.PensionAvgearnGetMemento#getCompanyFund()
	 */
	@Override
	public PensionAvgearnValue getCompanyFund() {
		return PensionAvgearnValueDto.toDomain(this.companyFund);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.pr.core.dom.insurance.social.pensionavgearn.PensionAvgearnGetMemento#getChildContributionAmount()
	 */
	@Override
	public CommonAmount getChildContributionAmount() {
		return new CommonAmount(this.childContributionAmount);
	}
}
