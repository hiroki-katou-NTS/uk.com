/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.statutory.worktime_new.workplace;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.WkpNormalSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.WkpNormalSettingRepository;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.workingplace.KshstWkpNormalSet;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.workingplace.KshstWkpNormalSetPK;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.workingplace.KshstWkpNormalSetPK_;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.workingplace.KshstWkpNormalSet_;

/**
 * The Class JpaWkpNormalSettingRepository.
 */
@Stateless
public class JpaWkpNormalSettingRepository extends JpaRepository
		implements WkpNormalSettingRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.
	 * WkpNormalSettingRepository#update(nts.uk.ctx.at.shared.dom.statutory.
	 * worktime.companyNew.WkpNormalSetting)
	 */
	@Override
	public void update(WkpNormalSetting domain) {
		KshstWkpNormalSet entity = this.queryProxy()
				.find(new KshstWkpNormalSetPK(domain.getCompanyId().v(),
						domain.getWorkplaceId().v(), domain.getYear().v()), KshstWkpNormalSet.class)
				.get();
		domain.saveToMemento(new JpaWkpNormalSettingSetMemento(entity));
		this.commandProxy().update(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.
	 * WkpNormalSettingRepository#find(java.lang.String, java.lang.String, int)
	 */
	@Override
	public Optional<WkpNormalSetting> find(String cid, String wkpId, int year) {
		// Get info
		Optional<KshstWkpNormalSet> optEntity = this.queryProxy()
				.find(new KshstWkpNormalSetPK(cid, wkpId, year), KshstWkpNormalSet.class);

		// Check exist
		if (!optEntity.isPresent()) {
			return Optional.empty();
		}

		// Return
		return Optional
				.of(new WkpNormalSetting(new JpaWkpNormalSettingGetMemento(optEntity.get())));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.
	 * WkpNormalSettingRepository#add(nts.uk.ctx.at.shared.dom.statutory.
	 * worktime.workplaceNew.WkpNormalSetting)
	 */
	@Override
	public void add(WkpNormalSetting domain) {
		KshstWkpNormalSet entity = new KshstWkpNormalSet();
		domain.saveToMemento(new JpaWkpNormalSettingSetMemento(entity));
		this.commandProxy().insert(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.
	 * WkpNormalSettingRepository#remove(java.lang.String, java.lang.String,
	 * int)
	 */
	@Override
	public void remove(String cid, String wkpId, int year) {
		this.commandProxy().remove(KshstWkpNormalSet.class,
				new KshstWkpNormalSetPK(cid, wkpId, year));
	}
	
	/**
	 * To domain.
	 *
	 * @param entities the entities
	 * @return the list
	 */
	private List<WkpNormalSetting> toDomain(List<KshstWkpNormalSet> entities) {
		if (entities == null ||entities.isEmpty()) {
			return Collections.emptyList();
		}
		return entities.stream().map(entity -> new WkpNormalSetting(new JpaWkpNormalSettingGetMemento(entity))).collect(Collectors.toList());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpNormalSettingRepository#findList(java.lang.String, java.lang.String)
	 */
	@Override
	public List<WkpNormalSetting> findList(String cid, String wkpId) {
		EntityManager em = this.getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<KshstWkpNormalSet> cq = cb.createQuery(KshstWkpNormalSet.class);
		Root<KshstWkpNormalSet> root = cq.from(KshstWkpNormalSet.class);

		List<Predicate> predicateList = new ArrayList<Predicate>();
		predicateList.add(cb.equal(root.get(KshstWkpNormalSet_.kshstWkpNormalSetPK).get(KshstWkpNormalSetPK_.cid), cid));
		predicateList.add(cb.equal(root.get(KshstWkpNormalSet_.kshstWkpNormalSetPK).get(KshstWkpNormalSetPK_.wkpId), wkpId));
		
		cq.where(predicateList.toArray(new Predicate[] {}));
		
		return this.toDomain(em.createQuery(cq).getResultList());	
	}
	
}
