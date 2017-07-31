/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.shift.basicworkregister;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.CompanyBasicWork;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.CompanyBasicWorkRepository;
import nts.uk.ctx.at.schedule.infra.entity.shift.basicworkregister.KcbmtCompanyWorkSet;
import nts.uk.ctx.at.schedule.infra.entity.shift.basicworkregister.KcbmtCompanyWorkSetPK_;
import nts.uk.ctx.at.schedule.infra.entity.shift.basicworkregister.KcbmtCompanyWorkSet_;

/**
 * The Class JpaCompanyBasicWorkRepository.
 */
@Stateless
public class JpaCompanyBasicWorkRepository extends JpaRepository implements CompanyBasicWorkRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.basicworkregister.
	 * CompanyBasicWorkRepository#insert(nts.uk.ctx.at.schedule.dom.shift.
	 * basicworkregister.CompanyBasicWork)
	 */
	@Override
	public void insert(CompanyBasicWork companyBasicWork) {
		List<KcbmtCompanyWorkSet> entities = this.toEntity(companyBasicWork);
		commandProxy().insertAll(entities);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.basicworkregister.
	 * CompanyBasicWorkRepository#update(nts.uk.ctx.at.schedule.dom.shift.
	 * basicworkregister.CompanyBasicWork)
	 */
	@Override
	public void update(CompanyBasicWork companyBasicWork) {
		List<KcbmtCompanyWorkSet> entities = this.toEntity(companyBasicWork);
		commandProxy()
				.updateAll(entities.stream().map(entity -> this.updateEntity(entity)).collect(Collectors.toList()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.basicworkregister.
	 * CompanyBasicWorkRepository#findAll(java.lang.String)
	 */
	@Override
	public Optional<CompanyBasicWork> findAll(String companyId) {
		// Get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder bd = em.getCriteriaBuilder();
		CriteriaQuery<KcbmtCompanyWorkSet> cq = bd.createQuery(KcbmtCompanyWorkSet.class);
		// Root
		Root<KcbmtCompanyWorkSet> root = cq.from(KcbmtCompanyWorkSet.class);
		cq.select(root);

		// Predicate where clause
		List<Predicate> predicateList = new ArrayList<>();
		predicateList.add(bd.equal(root.get(KcbmtCompanyWorkSet_.kcbmtCompanyWorkSetPK).get(KcbmtCompanyWorkSetPK_.cid),
				companyId));
		// Set Where clause to SQL Query
		cq.where(predicateList.toArray(new Predicate[] {}));

		// Create Query
		TypedQuery<KcbmtCompanyWorkSet> query = em.createQuery(cq);

		if (CollectionUtil.isEmpty(query.getResultList())) {
			return Optional.empty();
		}

		// return query.getResultList().stream().map(item ->
		// this.toDomain(item)).collect(Collectors.toList());
		List<KcbmtCompanyWorkSet> entities = query.getResultList();
		return Optional.of(this.toDomain(entities));
	}


	/**
	 * To domain.
	 *
	 * @param entity
	 *            the entity
	 * @return the company basic work
	 */
	private CompanyBasicWork toDomain(List<KcbmtCompanyWorkSet> entity) {
		return new CompanyBasicWork(new JpaCompanyBasicWorkGetMemento(entity));
	}

	/**
	 * To entity.
	 *
	 * @param domain
	 *            the domain
	 * @return the list
	 */
	private List<KcbmtCompanyWorkSet> toEntity(CompanyBasicWork domain) {
		return domain.getBasicWorkSetting().stream().map(basic -> {
			KcbmtCompanyWorkSet entity = new KcbmtCompanyWorkSet();
			basic.saveToMemento(new JpaBWSettingComSetMemento(entity));
			entity.getKcbmtCompanyWorkSetPK().setCid(domain.getCompanyId());
//			entity.getKcbmtCompanyWorkSetPK().setWorkdayDivision(basic.getWorkdayDivision().value);
			return entity;
		}).collect(Collectors.toList());
	}

	/**
	 * Update entity.
	 *
	 * @param entity
	 *            the entity
	 * @return the kcbmt company work set
	 */
	private KcbmtCompanyWorkSet updateEntity(KcbmtCompanyWorkSet entity) {
		KcbmtCompanyWorkSet entityToUpdate = this.queryProxy()
				.find(entity.getKcbmtCompanyWorkSetPK(), KcbmtCompanyWorkSet.class).get();
		entityToUpdate.setWorktypeCode(entity.getWorktypeCode());
		entityToUpdate.setWorkingCode(entity.getWorkingCode());
		entityToUpdate.getKcbmtCompanyWorkSetPK()
				.setWorkdayDivision(entity.getKcbmtCompanyWorkSetPK().getWorkdayDivision());
		entityToUpdate.getKcbmtCompanyWorkSetPK().setCid(entity.getKcbmtCompanyWorkSetPK().getCid());
		return entityToUpdate;
	}

}
