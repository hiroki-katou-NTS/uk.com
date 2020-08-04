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
import nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.WkpRegularLaborTime;
import nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.WkpRegularLaborTimeRepository;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.workingplace.KshstWkpRegLaborTime;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.workingplace.KshstWkpRegLaborTimePK;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.workingplace.KshstWkpRegLaborTimePK_;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.workingplace.KshstWkpRegLaborTime_;

/**
 * The Class JpaWkpRegularLaborTimeRepository.
 */
@Stateless
public class JpaWkpRegularLaborTimeRepository extends JpaRepository implements WkpRegularLaborTimeRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.
	 * WkpRegularLaborTimeRepository#update(nts.uk.ctx.at.shared.dom.statutory.
	 * worktime.workplaceNew.WkpRegularLaborTime)
	 */
	/*
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.
	 * WkpRegularLaborTimeRepository#update(nts.uk.ctx.at.shared.dom.statutory.
	 * worktime.companyNew.WkpRegularLaborTime)
	 */
	@Override
	public void update(WkpRegularLaborTime setting) {
		KshstWkpRegLaborTime entity = this.queryProxy()
				.find(new KshstWkpRegLaborTimePK(setting.getCompanyId().v(), setting.getWorkplaceId().v()),
						KshstWkpRegLaborTime.class)
				.get();
		setting.saveToMemento(new JpaWkpRegularLaborTimeSetMemento(entity));
		this.commandProxy().update(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.
	 * WkpRegularLaborTimeRepository#find(java.lang.String, java.lang.String)
	 */
	@Override
	public Optional<WkpRegularLaborTime> find(String cid, String wkpId) {
		// Get info
		Optional<KshstWkpRegLaborTime> optEntity = this.queryProxy().find(new KshstWkpRegLaborTimePK(cid, wkpId),
				KshstWkpRegLaborTime.class);

		// Check exist
		if (!optEntity.isPresent()) {
			return Optional.empty();
		}

		// Return
		return Optional.ofNullable(new WkpRegularLaborTime(new JpaWkpRegularLaborTimeGetMemento(optEntity.get())));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.
	 * WkpRegularLaborTimeRepository#add(nts.uk.ctx.at.shared.dom.statutory.
	 * worktime.workplaceNew.WkpRegularLaborTime)
	 */
	@Override
	public void add(WkpRegularLaborTime domain) {
		KshstWkpRegLaborTime entity = new KshstWkpRegLaborTime();
		domain.saveToMemento(new JpaWkpRegularLaborTimeSetMemento(entity));
		this.commandProxy().insert(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.
	 * WkpRegularLaborTimeRepository#remove(java.lang.String, java.lang.String)
	 */
	@Override
	public void remove(String cid, String wkpId) {
		this.commandProxy().remove(KshstWkpRegLaborTime.class, new KshstWkpRegLaborTimePK(cid, wkpId));
	}
	
	/**
	 * To domain.
	 *
	 * @param entities the entities
	 * @return the list
	 */
	private List<WkpRegularLaborTime> toDomain(List<KshstWkpRegLaborTime> entities) {
		if (entities.isEmpty()) {
			return Collections.emptyList();
		}
		return entities.stream().map(entity -> new WkpRegularLaborTime(new JpaWkpRegularLaborTimeGetMemento(entity))).collect(Collectors.toList());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.WkpRegularLaborTimeRepository#findAll(java.lang.String)
	 */
	@Override
	public List<WkpRegularLaborTime> findAll(String cid) {
		// Get entity manager
		EntityManager em = this.getEntityManager();

		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<KshstWkpRegLaborTime> cq = cb.createQuery(KshstWkpRegLaborTime.class);
		Root<KshstWkpRegLaborTime> root = cq.from(KshstWkpRegLaborTime.class);

		// Constructing condition.
		List<Predicate> predicateList = new ArrayList<Predicate>();
		predicateList.add(
				cb.equal(root.get(KshstWkpRegLaborTime_.kshstWkpRegLaborTimePK).get(KshstWkpRegLaborTimePK_.cid), cid));
		cq.where(predicateList.toArray(new Predicate[] {}));

		List<KshstWkpRegLaborTime> resultList = em.createQuery(cq).getResultList();

		return this.toDomain(resultList); 
	}
}
