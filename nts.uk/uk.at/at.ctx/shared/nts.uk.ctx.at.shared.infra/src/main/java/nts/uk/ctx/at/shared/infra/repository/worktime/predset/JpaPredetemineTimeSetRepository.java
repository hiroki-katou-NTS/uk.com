/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.predset;

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
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSettingRepository;
import nts.uk.ctx.at.shared.infra.entity.worktime.predset.KshmtPredTimeSet;
import nts.uk.ctx.at.shared.infra.entity.worktime.predset.KshmtPredTimeSetPK_;
import nts.uk.ctx.at.shared.infra.entity.worktime.predset.KshmtPredTimeSet_;
import nts.uk.ctx.at.shared.infra.entity.worktime.predset.KshmtWorkTimeSheetSet;
import nts.uk.ctx.at.shared.infra.entity.worktime.predset.KshmtWorkTimeSheetSetPK_;
import nts.uk.ctx.at.shared.infra.entity.worktime.predset.KshmtWorkTimeSheetSet_;

/**
 * The Class JpaPredetemineTimeSetRepository.
 */
@Stateless
public class JpaPredetemineTimeSetRepository extends JpaRepository implements PredetemineTimeSettingRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.predset.
	 * PredetemineTimeSettingRepository#findByWorkTimeCode(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public Optional<PredetemineTimeSetting> findByWorkTimeCode(String companyId, String workTimeCode) {

		Optional<KshmtPredTimeSet> optionalEntityTimeSet = this.findById(companyId, workTimeCode);

		if (optionalEntityTimeSet.isPresent()) {
			return Optional.ofNullable(new PredetemineTimeSetting(new JpaPredetemineTimeSettingGetMemento(
					optionalEntityTimeSet.get(), this.findWorktimeSheet(companyId, workTimeCode))));
		}

		return Optional.empty();
	}

	/**
	 * Find worktime sheet.
	 *
	 * @param companyId the company id
	 * @param worktimeCode the worktime code
	 * @return the list
	 */
	private List<KshmtWorkTimeSheetSet> findWorktimeSheet(String companyId, String worktimeCode) {

		// get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		CriteriaQuery<KshmtWorkTimeSheetSet> cq = criteriaBuilder.createQuery(KshmtWorkTimeSheetSet.class);
		Root<KshmtWorkTimeSheetSet> root = cq.from(KshmtWorkTimeSheetSet.class);

		cq.select(root);
		// +++++++++++++++++++++++++++++++++++
		List<Predicate> lstpredicateWhere = new ArrayList<>();
		lstpredicateWhere.add(criteriaBuilder.equal(
				root.get(KshmtWorkTimeSheetSet_.kshmtWorkTimeSheetSetPK).get(KshmtWorkTimeSheetSetPK_.cid), companyId));

		lstpredicateWhere.add(criteriaBuilder.equal(
				root.get(KshmtWorkTimeSheetSet_.kshmtWorkTimeSheetSetPK).get(KshmtWorkTimeSheetSetPK_.worktimeCd),
				worktimeCode));

		cq.where(lstpredicateWhere.toArray(new Predicate[] {}));

		return em.createQuery(cq).getResultList();
	}
	
	/**
	 * Find by id.
	 *
	 * @param companyId the company id
	 * @param worktimeCode the worktime code
	 * @return the optional
	 */
	private Optional<KshmtPredTimeSet> findById(String companyId, String worktimeCode) {
		// get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		CriteriaQuery<KshmtPredTimeSet> cq = criteriaBuilder.createQuery(KshmtPredTimeSet.class);
		Root<KshmtPredTimeSet> root = cq.from(KshmtPredTimeSet.class);

		cq.select(root);

		// add where
		List<Predicate> lstpredicateWhere = new ArrayList<>();
		lstpredicateWhere.add(criteriaBuilder
				.equal(root.get(KshmtPredTimeSet_.kshmtPredTimeSetPK).get(KshmtPredTimeSetPK_.cid), companyId));

		lstpredicateWhere.add(criteriaBuilder.equal(
				root.get(KshmtPredTimeSet_.kshmtPredTimeSetPK).get(KshmtPredTimeSetPK_.worktimeCd), worktimeCode));

		cq.where(lstpredicateWhere.toArray(new Predicate[] {}));

		try {
			KshmtPredTimeSet entity = em.createQuery(cq).getSingleResult();
			return Optional.ofNullable(entity);
		} catch (Exception e) {
			return Optional.empty();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.predset.
	 * PredetemineTimeSettingRepository#save(nts.uk.ctx.at.shared.dom.worktime.
	 * predset.PredetemineTimeSetting)
	 */
	@Override
	public void save(PredetemineTimeSetting domain) {

		Optional<KshmtPredTimeSet> optionalEntityTimeSet = this.findById(domain.getCompanyId(),
				domain.getWorkTimeCode().v());
		if (optionalEntityTimeSet.isPresent()) {
			KshmtPredTimeSet entity = optionalEntityTimeSet.get();
			List<KshmtWorkTimeSheetSet> lstEntityTime = this.findWorktimeSheet(domain.getCompanyId(),
					domain.getWorkTimeCode().v());
			domain.saveToMemento(new JpaPredetemineTimeSettingSetMemento(entity, lstEntityTime));
			this.commandProxy().update(entity);
			this.commandProxy().updateAll(lstEntityTime);
		} else {
			KshmtPredTimeSet entity = new KshmtPredTimeSet();
			List<KshmtWorkTimeSheetSet> lstEntityTime = new ArrayList<>();
			domain.saveToMemento(new JpaPredetemineTimeSettingSetMemento(entity, lstEntityTime));
			this.commandProxy().insert(entity);
			this.commandProxy().insertAll(lstEntityTime);
		}

	}

	@Override
	public void remove(String companyId, String workTimeCode) {
		// TODO Auto-generated method stub
		
	}

}
