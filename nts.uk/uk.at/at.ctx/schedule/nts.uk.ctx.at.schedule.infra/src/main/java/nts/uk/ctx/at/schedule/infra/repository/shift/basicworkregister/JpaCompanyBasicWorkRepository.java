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
import nts.uk.ctx.at.schedule.infra.entity.shift.basicworkregister.KscmtBasicWorkCom;
import nts.uk.ctx.at.schedule.infra.entity.shift.basicworkregister.KscmtBasicWorkComPK_;
import nts.uk.ctx.at.schedule.infra.entity.shift.basicworkregister.KscmtBasicWorkCom_;

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
		List<KscmtBasicWorkCom> entities = this.toEntity(companyBasicWork);
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
		List<KscmtBasicWorkCom> entities = this.toEntity(companyBasicWork);
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
		CriteriaQuery<KscmtBasicWorkCom> cq = bd.createQuery(KscmtBasicWorkCom.class);
		
		// Root
		Root<KscmtBasicWorkCom> root = cq.from(KscmtBasicWorkCom.class);
		cq.select(root);

		// Predicate where clause
		List<Predicate> predicateList = new ArrayList<>();
		predicateList.add(bd.equal(root.get(KscmtBasicWorkCom_.kscmtBasicWorkComPK).get(KscmtBasicWorkComPK_.cid),
				companyId));
		
		// Set Where clause to SQL Query
		cq.where(predicateList.toArray(new Predicate[] {}));

		// Create Query
		TypedQuery<KscmtBasicWorkCom> query = em.createQuery(cq);

		List<KscmtBasicWorkCom> entities = query.getResultList();

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
	private CompanyBasicWork toDomain(List<KscmtBasicWorkCom> entity) {
		return new CompanyBasicWork(new JpaCompanyBasicWorkGetMemento(entity));
	}

	/**
	 * To entity.
	 *
	 * @param domain
	 *            the domain
	 * @return the list
	 */
	private List<KscmtBasicWorkCom> toEntity(CompanyBasicWork domain) {
		return domain.getBasicWorkSetting().stream().map(basic -> {
			KscmtBasicWorkCom entity = new KscmtBasicWorkCom();
			basic.saveToMemento(new JpaBWSettingComSetMemento(entity));
			entity.getKscmtBasicWorkComPK().setCid(domain.getCompanyId());
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
	private KscmtBasicWorkCom updateEntity(KscmtBasicWorkCom entity) {
		KscmtBasicWorkCom entityToUpdate = this.queryProxy()
				.find(entity.getKscmtBasicWorkComPK(), KscmtBasicWorkCom.class).get();
		entityToUpdate.setWorktypeCode(entity.getWorktypeCode());
		entityToUpdate.setWorkingCode(StringUtils.isEmpty(entity.getWorkingCode()) ? null : entity.getWorkingCode());
		entityToUpdate.getKscmtBasicWorkComPK()
				.setWorkdayDivision(entity.getKscmtBasicWorkComPK().getWorkdayDivision());
		entityToUpdate.getKscmtBasicWorkComPK().setCid(entity.getKscmtBasicWorkComPK().getCid());
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
		CriteriaQuery<KscmtBasicWorkCom> cq = bd.createQuery(KscmtBasicWorkCom.class);

		// Root
		Root<KscmtBasicWorkCom> root = cq.from(KscmtBasicWorkCom.class);
		cq.select(root);

		// Predicate where clause
		List<Predicate> predicateList = new ArrayList<>();
		
		// equal companyId
		predicateList.add(bd.equal(root.get(KscmtBasicWorkCom_.kscmtBasicWorkComPK).get(KscmtBasicWorkComPK_.cid),
				companyId));
		
		// equal workdayDivision
		predicateList.add(bd.equal(root.get(KscmtBasicWorkCom_.kscmtBasicWorkComPK).get(KscmtBasicWorkComPK_.workdayDivision),
				workdayAtr));

		// Set Where clause to SQL Query
		cq.where(predicateList.toArray(new Predicate[] {}));

		// Create Query
		TypedQuery<KscmtBasicWorkCom> query = em.createQuery(cq);

		List<KscmtBasicWorkCom> entities = query.getResultList();

		if (CollectionUtil.isEmpty(entities)) {
			return Optional.empty();
		}

		return Optional.of(this.toDomain(entities));
	}

}
