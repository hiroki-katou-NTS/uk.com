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

import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.acquisitionrule.AcquisitionRule;
import nts.uk.ctx.at.shared.dom.vacation.setting.acquisitionrule.AcquisitionRuleRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.AnnualPaidLeaveSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.ManageAnnualSetting;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.acquisitionrule.KmfstAcquisitionRule;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.acquisitionrule.KmfstAcquisitionRule_;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.annualpaidleave.KmfmtMngAnnualSet;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.annualpaidleave.KmfmtMngAnnualSet_;
import nts.uk.ctx.at.shared.infra.repository.vacation.setting.annualpaidleave.JpaAnnualPaidLeaveSettingGetMemento;
import nts.uk.ctx.at.shared.infra.repository.vacation.setting.annualpaidleave.JpaManageAnnualSettingGetMemento;

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
		Optional<KmfstAcquisitionRule> entity = this.queryProxy().find(new KmfstAcquisitionRule(companyId),
				KmfstAcquisitionRule.class);
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
        CriteriaQuery<KmfstAcquisitionRule> cq = builder.createQuery(KmfstAcquisitionRule.class);
        Root<KmfstAcquisitionRule> root = cq.from(KmfstAcquisitionRule.class);
        List<Predicate> predicateList = new ArrayList<Predicate>();

        predicateList.add(builder.equal(root.get(KmfstAcquisitionRule_.cid), companyId));

        cq.where(predicateList.toArray(new Predicate[]{}));

        List<KmfstAcquisitionRule> list = em.createQuery(cq).getResultList();
        return list.stream()
                .map(item -> new AcquisitionRule(new JpaAcquisitionRuleGetMemento(item)))
                .findFirst();
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
