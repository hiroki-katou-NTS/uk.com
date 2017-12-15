/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.fixedset;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSettingRepository;
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtFlexWorkSet;
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtFlexWorkSetPK_;
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtFlexWorkSet_;

/**
 * The Class JpaFlexWorkSettingRepository.
 */
@Stateless
public class JpaFlexWorkSettingRepository extends JpaRepository
		implements FlexWorkSettingRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSettingRepository#
	 * findById(java.lang.String, java.lang.String)
	 */
	@Override
	public Optional<FlexWorkSetting> find(String companyId, String worktimeCode) {
		return this.findWorkSetting(companyId, worktimeCode).map(entity -> this.toDomain(entity));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSettingRepository#
	 * saveFlexWorkSetting(nts.uk.ctx.at.shared.dom.worktime.flexset.
	 * FlexWorkSetting)
	 */
	@Override
	public void saveFlexWorkSetting(FlexWorkSetting domain) {

	}

	/**
	 * To domain.
	 *
	 * @param entity
	 *            the entity
	 * @return the flex work setting
	 */
	private FlexWorkSetting toDomain(KshmtFlexWorkSet entity) {
		return new FlexWorkSetting(new JpaFlexWorkSettingGetMemento(entity, null, null));
	}

	/**
	 * Find work setting.
	 *
	 * @param companyId
	 *            the company id
	 * @param worktimeCode
	 *            the worktime code
	 * @return the optional
	 */
	private Optional<KshmtFlexWorkSet> findWorkSetting(String companyId, String worktimeCode) {

		// get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		// call KSHMT_FLEX_WORK_SET (KshmtFlexWorkSet SQL)
		CriteriaQuery<KshmtFlexWorkSet> cq = criteriaBuilder.createQuery(KshmtFlexWorkSet.class);

		// root data
		Root<KshmtFlexWorkSet> root = cq.from(KshmtFlexWorkSet.class);

		// select root
		cq.select(root);

		// add where
		List<Predicate> lstpredicateWhere = new ArrayList<>();

		// equal company id
		lstpredicateWhere.add(criteriaBuilder.equal(
				root.get(KshmtFlexWorkSet_.kshmtFlexWorkSetPK).get(KshmtFlexWorkSetPK_.cid),
				companyId));

		// equal work time code
		lstpredicateWhere.add(criteriaBuilder.equal(
				root.get(KshmtFlexWorkSet_.kshmtFlexWorkSetPK).get(KshmtFlexWorkSetPK_.worktimeCd),
				worktimeCode));

		// set where to SQL
		cq.where(lstpredicateWhere.toArray(new Predicate[] {}));

		// create query
		TypedQuery<KshmtFlexWorkSet> query = em.createQuery(cq);

		// exclude select
		return query.getResultList().stream().findFirst();
	}

}
