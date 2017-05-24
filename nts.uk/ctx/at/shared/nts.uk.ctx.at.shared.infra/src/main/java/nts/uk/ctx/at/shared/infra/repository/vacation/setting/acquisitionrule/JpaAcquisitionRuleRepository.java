/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.vacation.setting.acquisitionrule;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.acquisitionrule.AcquisitionRule;
import nts.uk.ctx.at.shared.dom.vacation.setting.acquisitionrule.AcquisitionRuleRepository;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.acquisitionrule.KmfstAcquisitionRule;

/**
 * The Class JpaAcquisitionRuleRepository.
 */
@Stateless
public class JpaAcquisitionRuleRepository extends JpaRepository
		implements AcquisitionRuleRepository {

	/**
	 * Creates the.
	 *
	 * @param acquisitionRule
	 *            the acquisition rule
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.vacation.setting.acquisitionrule.
	 * VaAcRuleRepository#create(nts.uk.ctx.pr.core.dom.vacation.setting.
	 * acquisitionrule.VacationAcquisitionRule)
	 */
	@Override
	public void create(AcquisitionRule acquisitionRule) {
		EntityManager em = this.getEntityManager();
		KmfstAcquisitionRule entity = new KmfstAcquisitionRule();
		acquisitionRule.saveToMemento(new JpaAcquisitionRuleSetMemento(entity));
		em.persist(entity);
	}

	/**
	 * Update.
	 *
	 * @param acquisitionRule
	 *            the acquisition rule
	 */
	@Override
	public void update(AcquisitionRule acquisitionRule) {
		EntityManager em = this.getEntityManager();
		KmfstAcquisitionRule entity = new KmfstAcquisitionRule();
		acquisitionRule.saveToMemento(new JpaAcquisitionRuleSetMemento(entity));
		em.merge(entity);
	}

	/**
	 * Removes the.
	 *
	 * @param companyId
	 *            the company id
	 */
	@Override
	public void remove(String companyId) {
		// TODO Auto-generated method stub

	}

	/**
	 * Find by id.
	 *
	 * @param companyId
	 *            the company id
	 * @return the optional
	 */
	@Override
	public Optional<AcquisitionRule> findById(String companyId) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Find all.
	 *
	 * @return the list
	 */
	@Override
	public List<AcquisitionRule> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
