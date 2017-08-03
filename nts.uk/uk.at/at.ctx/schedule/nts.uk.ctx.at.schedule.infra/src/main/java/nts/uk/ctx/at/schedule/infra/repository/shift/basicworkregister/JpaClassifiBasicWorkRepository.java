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
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.ClassifiBasicWorkRepository;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.ClassificationBasicWork;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.ClassificationCode;
import nts.uk.ctx.at.schedule.infra.entity.shift.basicworkregister.KcbmtClassifyWorkSet;
import nts.uk.ctx.at.schedule.infra.entity.shift.basicworkregister.KcbmtClassifyWorkSetPK_;
import nts.uk.ctx.at.schedule.infra.entity.shift.basicworkregister.KcbmtClassifyWorkSet_;

/**
 * The Class JpaClassifiBasicWorkRepository.
 */
@Stateless
public class JpaClassifiBasicWorkRepository extends JpaRepository implements ClassifiBasicWorkRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.basicworkregister.
	 * ClassifiBasicWorkRepository#insert(nts.uk.ctx.at.schedule.dom.shift.
	 * basicworkregister.ClassificationBasicWork)
	 */
	@Override
	public void insert(ClassificationBasicWork classificationBasicWork) {
		List<KcbmtClassifyWorkSet> entities = this.toEntity(classificationBasicWork);
		commandProxy().insertAll(entities);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.basicworkregister.
	 * ClassifiBasicWorkRepository#update(nts.uk.ctx.at.schedule.dom.shift.
	 * basicworkregister.ClassificationBasicWork)
	 */
	@Override
	public void update(ClassificationBasicWork classificationBasicWork) {
		List<KcbmtClassifyWorkSet> entities = this.toEntity(classificationBasicWork);
		commandProxy()
				.updateAll(entities.stream().map(entity -> this.updateEntity(entity)).collect(Collectors.toList()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.basicworkregister.
	 * ClassifiBasicWorkRepository#remove(java.lang.String, java.lang.String)
	 */
	@Override
	public void remove(String companyId, String classificationCode) {
		// Get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder bd = em.getCriteriaBuilder();

		CriteriaDelete<KcbmtClassifyWorkSet> cd = bd.createCriteriaDelete(KcbmtClassifyWorkSet.class);

		// Root
		Root<KcbmtClassifyWorkSet> root = cd.from(KcbmtClassifyWorkSet.class);

		// Predicate where clause
		List<Predicate> predicateList = new ArrayList<>();
		predicateList.add(bd.equal(
				root.get(KcbmtClassifyWorkSet_.kcbmtClassifyWorkSetPK).get(KcbmtClassifyWorkSetPK_.cid), companyId));
		predicateList.add(bd.equal(
				root.get(KcbmtClassifyWorkSet_.kcbmtClassifyWorkSetPK).get(KcbmtClassifyWorkSetPK_.classifyCode),
				classificationCode));
		
		// Set Where clause to SQL Query
		cd.where(predicateList.toArray(new Predicate[] {}));
		em.createQuery(cd).executeUpdate();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.basicworkregister.
	 * ClassifiBasicWorkRepository#findAll(java.lang.String, java.lang.String)
	 */
	@Override
	public Optional<ClassificationBasicWork> findAll(String companyId, String classificationCode) {
		
		// Get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder bd = em.getCriteriaBuilder();
		CriteriaQuery<KcbmtClassifyWorkSet> cq = bd.createQuery(KcbmtClassifyWorkSet.class);
		
		// Root
		Root<KcbmtClassifyWorkSet> root = cq.from(KcbmtClassifyWorkSet.class);
		cq.select(root);
		
		// Predicate where clause
		List<Predicate> predicateList = new ArrayList<>();
		predicateList.add(bd.equal(
				root.get(KcbmtClassifyWorkSet_.kcbmtClassifyWorkSetPK).get(KcbmtClassifyWorkSetPK_.cid), companyId));
		predicateList.add(bd.equal(
				root.get(KcbmtClassifyWorkSet_.kcbmtClassifyWorkSetPK).get(KcbmtClassifyWorkSetPK_.classifyCode),
				classificationCode));
		
		// Set Where clause to SQL Query
		cq.where(predicateList.toArray(new Predicate[] {}));
		
		// Create Query
		TypedQuery<KcbmtClassifyWorkSet> query = em.createQuery(cq);

		if (CollectionUtil.isEmpty(query.getResultList())) {
			return Optional.empty();
		}

		return Optional.of(this.toDomain(query.getResultList()));
	}
	

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.schedule.dom.shift.basicworkregister.ClassifiBasicWorkRepository#findSetting(java.lang.String)
	 */
	@Override
	public List<ClassificationCode> findSetting(String companyId) {
		
		// Get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder bd = em.getCriteriaBuilder();
		CriteriaQuery<String> cq = bd.createQuery(String.class);
		
		// Root
		Root<KcbmtClassifyWorkSet> root = cq.from(KcbmtClassifyWorkSet.class);
		
		// Select Classification Code
		cq.select(root.get(KcbmtClassifyWorkSet_.kcbmtClassifyWorkSetPK).get(KcbmtClassifyWorkSetPK_.classifyCode)).distinct(true);
		
		// Predicate where clause
		List<Predicate> predicateList = new ArrayList<>();
		predicateList.add(bd.equal(
				root.get(KcbmtClassifyWorkSet_.kcbmtClassifyWorkSetPK).get(KcbmtClassifyWorkSetPK_.cid), companyId));
		
		// Set Where clause to SQL Query
		cq.where(predicateList.toArray(new Predicate[] {}));
		
		// Create Query
		TypedQuery<String> query = em.createQuery(cq);
		
		return query.getResultList().stream().map(item -> {
			return new ClassificationCode(item);
		}).collect(Collectors.toList());
	}

	/**
	 * To domain.
	 *
	 * @param entity
	 *            the entity
	 * @return the classification basic work
	 */
	private ClassificationBasicWork toDomain(List<KcbmtClassifyWorkSet> entity) {
		return new ClassificationBasicWork(new JpaClassifiBasicWorkGetMemento(entity));
	}

	/**
	 * To entity.
	 *
	 * @param domain
	 *            the domain
	 * @return the list
	 */
	private List<KcbmtClassifyWorkSet> toEntity(ClassificationBasicWork domain) {
		return domain.getBasicWorkSetting().stream().map(basic -> {
			KcbmtClassifyWorkSet entity = new KcbmtClassifyWorkSet();
			basic.saveToMemento(new JpaBWSettingClassifySetMemento(entity));
			entity.getKcbmtClassifyWorkSetPK().setCid(domain.getCompanyId());
			entity.getKcbmtClassifyWorkSetPK().setClassifyCode(domain.getClassificationCode().v());
			return entity;
		}).collect(Collectors.toList());
	}

	/**
	 * Update entity.
	 *
	 * @param entity
	 *            the entity
	 * @return the kcbmt classify work set
	 */
	private KcbmtClassifyWorkSet updateEntity(KcbmtClassifyWorkSet entity) {
		KcbmtClassifyWorkSet entityToUpdate = this.queryProxy()
				.find(entity.getKcbmtClassifyWorkSetPK(), KcbmtClassifyWorkSet.class).get();
		entityToUpdate.setWorktypeCode(entity.getWorktypeCode());
		entityToUpdate.setWorkingCode(entity.getWorkingCode());
		entityToUpdate.getKcbmtClassifyWorkSetPK()
				.setWorkdayDivision(entity.getKcbmtClassifyWorkSetPK().getWorkdayDivision());
		entityToUpdate.getKcbmtClassifyWorkSetPK().setCid(entity.getKcbmtClassifyWorkSetPK().getCid());
		entityToUpdate.getKcbmtClassifyWorkSetPK()
				.setClassifyCode(entity.getKcbmtClassifyWorkSetPK().getClassifyCode());

		return entityToUpdate;
	}



}
