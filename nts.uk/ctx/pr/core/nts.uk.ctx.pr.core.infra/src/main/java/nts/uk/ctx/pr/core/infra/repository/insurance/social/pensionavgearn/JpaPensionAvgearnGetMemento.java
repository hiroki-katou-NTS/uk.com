/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.infra.repository.insurance.social.pensionavgearn;

import nts.uk.ctx.pr.core.dom.insurance.CommonAmount;
import nts.uk.ctx.pr.core.dom.insurance.social.pensionavgearn.PensionAvgearnGetMemento;
import nts.uk.ctx.pr.core.dom.insurance.social.pensionavgearn.PensionAvgearnValue;
import nts.uk.ctx.pr.core.infra.entity.insurance.social.pensionavgearn.QismtPensionAvgearn;

/**
 * The Class JpaPensionAvgearnGetMemento.
 */
public class JpaPensionAvgearnGetMemento implements PensionAvgearnGetMemento {

	/** The entity. */
	protected QismtPensionAvgearn entity;

	/**
	 * Instantiates a new jpa pension avgearn get memento.
	 *
	 * @param entity the entity
	 */
	public JpaPensionAvgearnGetMemento(QismtPensionAvgearn entity) {
		this.entity = entity;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.pr.core.dom.insurance.social.pensionavgearn.PensionAvgearnGetMemento#getPersonalPension()
	 */
	@Override
	public PensionAvgearnValue getPersonalPension() {
		return new PensionAvgearnValue(new CommonAmount(this.entity.getPPensionMaleMny()),
				new CommonAmount(this.entity.getPPensionFemMny()), new CommonAmount(this.entity.getPPensionMinerMny()));
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.pr.core.dom.insurance.social.pensionavgearn.PensionAvgearnGetMemento#getPersonalFundExemption()
	 */
	@Override
	public PensionAvgearnValue getPersonalFundExemption() {
		return new PensionAvgearnValue(new CommonAmount(this.entity.getPFundExemptMaleMny()),
				new CommonAmount(this.entity.getPFundExemptFemMny()),
				new CommonAmount(this.entity.getPFundExemptMinerMny()));
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.pr.core.dom.insurance.social.pensionavgearn.PensionAvgearnGetMemento#getPersonalFund()
	 */
	@Override
	public PensionAvgearnValue getPersonalFund() {
		return new PensionAvgearnValue(new CommonAmount(this.entity.getPFundMaleMny()),
				new CommonAmount(this.entity.getPFundFemMny()), new CommonAmount(this.entity.getPFundMinerMny()));
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.pr.core.dom.insurance.social.pensionavgearn.PensionAvgearnGetMemento#getLevelCode()
	 */
	@Override
	public Integer getLevelCode() {
		return this.entity.getQismtPensionAvgearnPK().getPensionGrade().intValue();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.pr.core.dom.insurance.social.pensionavgearn.PensionAvgearnGetMemento#getHistoryId()
	 */
	@Override
	public String getHistoryId() {
		return this.entity.getQismtPensionAvgearnPK().getHistId();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.pr.core.dom.insurance.social.pensionavgearn.PensionAvgearnGetMemento#getCompanyPension()
	 */
	@Override
	public PensionAvgearnValue getCompanyPension() {
		return new PensionAvgearnValue(new CommonAmount(this.entity.getCPensionMaleMny()),
				new CommonAmount(this.entity.getCPensionFemMny()), new CommonAmount(this.entity.getCPensionMinerMny()));
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.pr.core.dom.insurance.social.pensionavgearn.PensionAvgearnGetMemento#getCompanyFundExemption()
	 */
	@Override
	public PensionAvgearnValue getCompanyFundExemption() {
		return new PensionAvgearnValue(new CommonAmount(this.entity.getCFundExemptMaleMny()),
				new CommonAmount(this.entity.getCFundExemptFemMny()),
				new CommonAmount(this.entity.getCFundExemptMinerMny()));
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.pr.core.dom.insurance.social.pensionavgearn.PensionAvgearnGetMemento#getCompanyFund()
	 */
	@Override
	public PensionAvgearnValue getCompanyFund() {
		return new PensionAvgearnValue(new CommonAmount(this.entity.getCFundMaleMny()),
				new CommonAmount(this.entity.getCFundFemMny()), new CommonAmount(this.entity.getCFundMinerMny()));
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.pr.core.dom.insurance.social.pensionavgearn.PensionAvgearnGetMemento#getChildContributionAmount()
	 */
	@Override
	public CommonAmount getChildContributionAmount() {
		return new CommonAmount(this.entity.getChildContributionMny());
	}

}
