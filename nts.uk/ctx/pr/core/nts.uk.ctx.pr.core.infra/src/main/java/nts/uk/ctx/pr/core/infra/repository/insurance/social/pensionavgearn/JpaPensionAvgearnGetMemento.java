/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.infra.repository.insurance.social.pensionavgearn;

import nts.uk.ctx.pr.core.dom.insurance.InsuranceAmount;
import nts.uk.ctx.pr.core.dom.insurance.social.pensionavgearn.PensionAvgearnGetMemento;
import nts.uk.ctx.pr.core.dom.insurance.social.pensionavgearn.PensionAvgearnValue;
import nts.uk.ctx.pr.core.infra.entity.insurance.social.pensionavgearn.QismtPensionAvgearn;

/**
 * The Class JpaPensionAvgearnGetMemento.
 */
public class JpaPensionAvgearnGetMemento implements PensionAvgearnGetMemento {

	/** The type value. */
	protected QismtPensionAvgearn typeValue;

	/**
	 * Instantiates a new jpa pension avgearn get memento.
	 *
	 * @param typeValue
	 *            the type value
	 */
	public JpaPensionAvgearnGetMemento(QismtPensionAvgearn typeValue) {
		this.typeValue = typeValue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.insurance.social.pensionavgearn.
	 * PensionAvgearnGetMemento#getHistoryId()
	 */
	@Override
	public String getHistoryId() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.insurance.social.pensionavgearn.
	 * PensionAvgearnGetMemento#getLevelCode()
	 */
	@Override
	public Integer getLevelCode() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.insurance.social.pensionavgearn.
	 * PensionAvgearnGetMemento#getChildContributionAmount()
	 */
	@Override
	public InsuranceAmount getChildContributionAmount() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.insurance.social.pensionavgearn.
	 * PensionAvgearnGetMemento#getCompanyFund()
	 */
	@Override
	public PensionAvgearnValue getCompanyFund() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.insurance.social.pensionavgearn.
	 * PensionAvgearnGetMemento#getCompanyFundExemption()
	 */
	@Override
	public PensionAvgearnValue getCompanyFundExemption() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.insurance.social.pensionavgearn.
	 * PensionAvgearnGetMemento#getCompanyPension()
	 */
	@Override
	public PensionAvgearnValue getCompanyPension() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.insurance.social.pensionavgearn.
	 * PensionAvgearnGetMemento#getPersonalFund()
	 */
	@Override
	public PensionAvgearnValue getPersonalFund() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.insurance.social.pensionavgearn.
	 * PensionAvgearnGetMemento#getPersonalFundExemption()
	 */
	@Override
	public PensionAvgearnValue getPersonalFundExemption() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.insurance.social.pensionavgearn.
	 * PensionAvgearnGetMemento#getPersonalPension()
	 */
	@Override
	public PensionAvgearnValue getPersonalPension() {
		// TODO Auto-generated method stub
		return null;
	}

}
