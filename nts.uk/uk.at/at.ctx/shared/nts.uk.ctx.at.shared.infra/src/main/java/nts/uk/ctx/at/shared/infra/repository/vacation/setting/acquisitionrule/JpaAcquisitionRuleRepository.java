/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.vacation.setting.acquisitionrule;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.acquisitionrule.AcquisitionRule;
import nts.uk.ctx.at.shared.dom.vacation.setting.acquisitionrule.AcquisitionRuleRepository;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.acquisitionrule.KarstAcquisitionRule;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.acquisitionrule.KarstAcquisitionRule_;

/**
 * The Class JpaAcquisitionRuleRepository.
 */
@Stateless
public class JpaAcquisitionRuleRepository extends JpaRepository implements AcquisitionRuleRepository {

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
		KarstAcquisitionRule entity = new KarstAcquisitionRule();
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
		Optional<KarstAcquisitionRule> optional = this.queryProxy().find(acquisitionRule.getCompanyId(),
				KarstAcquisitionRule.class);
		if (optional.isPresent()) {
			KarstAcquisitionRule entity = optional.get();
			acquisitionRule.saveToMemento(new JpaAcquisitionRuleSetMemento(entity));
			em.merge(entity);
		}
		
	}

	/**
	 * Removes the.
	 *
	 * @param companyId
	 *            the company id
	 */
	@Override
	public void remove(String companyId) {
		Optional<KarstAcquisitionRule> entity = this.queryProxy().find(new KarstAcquisitionRule(companyId),
				KarstAcquisitionRule.class);
		if (entity.isPresent()) {
			this.commandProxy().remove(entity.get());
		}
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
		EntityManager em = this.getEntityManager();
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<KarstAcquisitionRule> cq = builder.createQuery(KarstAcquisitionRule.class);
        Root<KarstAcquisitionRule> root = cq.from(KarstAcquisitionRule.class);
        List<Predicate> predicateList = new ArrayList<Predicate>();

        predicateList.add(builder.equal(root.get(KarstAcquisitionRule_.cid), companyId));

        cq.where(predicateList.toArray(new Predicate[]{}));

        List<KarstAcquisitionRule> list = em.createQuery(cq).getResultList();
        return list.stream()
                .map(item -> new AcquisitionRule(new JpaAcquisitionRuleGetMemento(item)))
                .findFirst();
	}

}
