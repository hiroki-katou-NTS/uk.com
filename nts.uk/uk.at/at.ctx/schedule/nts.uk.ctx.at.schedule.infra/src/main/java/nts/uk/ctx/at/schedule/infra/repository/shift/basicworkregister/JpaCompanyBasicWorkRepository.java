/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
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

import org.apache.commons.lang3.StringUtils;

import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.CompanyBasicWork;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.CompanyBasicWorkRepository;
import nts.uk.ctx.at.schedule.infra.entity.shift.basicworkregister.KscmtCompanyWorkSet;
import nts.uk.ctx.at.schedule.infra.entity.shift.basicworkregister.KscmtCompanyWorkSetPK_;
import nts.uk.ctx.at.schedule.infra.entity.shift.basicworkregister.KscmtCompanyWorkSet_;

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
		List<KscmtCompanyWorkSet> entities = this.toEntity(companyBasicWork);
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
		List<KscmtCompanyWorkSet> entities = this.toEntity(companyBasicWork);
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
		CriteriaQuery<KscmtCompanyWorkSet> cq = bd.createQuery(KscmtCompanyWorkSet.class);
		
		// Root
		Root<KscmtCompanyWorkSet> root = cq.from(KscmtCompanyWorkSet.class);
		cq.select(root);

		// Predicate where clause
		List<Predicate> predicateList = new ArrayList<>();
		predicateList.add(bd.equal(root.get(KscmtCompanyWorkSet_.kscmtCompanyWorkSetPK).get(KscmtCompanyWorkSetPK_.cid),
				companyId));
		
		// Set Where clause to SQL Query
		cq.where(predicateList.toArray(new Predicate[] {}));

		// Create Query
		TypedQuery<KscmtCompanyWorkSet> query = em.createQuery(cq);

		List<KscmtCompanyWorkSet> entities = query.getResultList();

		if (CollectionUtil.isEmpty(entities)) {
			return Optional.empty();
		}

		return Optional.of(this.toDomain(entities));
	}


	/**
	 * To domain.
	 *
	 * @param entity
	 *            the entity
	 * @return the company basic work
	 */
	private CompanyBasicWork toDomain(List<KscmtCompanyWorkSet> entity) {
		return new CompanyBasicWork(new JpaCompanyBasicWorkGetMemento(entity));
	}

	/**
	 * To entity.
	 *
	 * @param domain
	 *            the domain
	 * @return the list
	 */
	private List<KscmtCompanyWorkSet> toEntity(CompanyBasicWork domain) {
		return domain.getBasicWorkSetting().stream().map(basic -> {
			KscmtCompanyWorkSet entity = new KscmtCompanyWorkSet();
			basic.saveToMemento(new JpaBWSettingComSetMemento(entity));
			entity.getKscmtCompanyWorkSetPK().setCid(domain.getCompanyId());
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
	private KscmtCompanyWorkSet updateEntity(KscmtCompanyWorkSet entity) {
		KscmtCompanyWorkSet entityToUpdate = this.queryProxy()
				.find(entity.getKscmtCompanyWorkSetPK(), KscmtCompanyWorkSet.class).get();
		entityToUpdate.setWorktypeCode(entity.getWorktypeCode());
		entityToUpdate.setWorkingCode(StringUtils.isEmpty(entity.getWorkingCode()) ? null : entity.getWorkingCode());
		entityToUpdate.getKscmtCompanyWorkSetPK()
				.setWorkdayDivision(entity.getKscmtCompanyWorkSetPK().getWorkdayDivision());
		entityToUpdate.getKscmtCompanyWorkSetPK().setCid(entity.getKscmtCompanyWorkSetPK().getCid());
		return entityToUpdate;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.schedule.dom.shift.basicworkregister.CompanyBasicWorkRepository#findById(java.lang.String, int)
	 */
	@Override
	public Optional<CompanyBasicWork> findById(String companyId, int workdayAtr) {
		// Get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder bd = em.getCriteriaBuilder();
		CriteriaQuery<KscmtCompanyWorkSet> cq = bd.createQuery(KscmtCompanyWorkSet.class);

		// Root
		Root<KscmtCompanyWorkSet> root = cq.from(KscmtCompanyWorkSet.class);
		cq.select(root);

		// Predicate where clause
		List<Predicate> predicateList = new ArrayList<>();
		
		// equal companyId
		predicateList.add(bd.equal(root.get(KscmtCompanyWorkSet_.kscmtCompanyWorkSetPK).get(KscmtCompanyWorkSetPK_.cid),
				companyId));
		
		// equal workdayDivision
		predicateList.add(bd.equal(root.get(KscmtCompanyWorkSet_.kscmtCompanyWorkSetPK).get(KscmtCompanyWorkSetPK_.workdayDivision),
				workdayAtr));

		// Set Where clause to SQL Query
		cq.where(predicateList.toArray(new Predicate[] {}));

		// Create Query
		TypedQuery<KscmtCompanyWorkSet> query = em.createQuery(cq);

		List<KscmtCompanyWorkSet> entities = query.getResultList();

		if (CollectionUtil.isEmpty(entities)) {
			return Optional.empty();
		}

		return Optional.of(this.toDomain(entities));
	}

}
