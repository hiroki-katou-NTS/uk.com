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
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;

import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.ClassifiBasicWorkRepository;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.ClassificationBasicWork;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.ClassificationCode;
import nts.uk.ctx.at.schedule.infra.entity.shift.basicworkregister.KscmtBasicWorkCls;
import nts.uk.ctx.at.schedule.infra.entity.shift.basicworkregister.KscmtBasicWorkClsPK_;
import nts.uk.ctx.at.schedule.infra.entity.shift.basicworkregister.KscmtBasicWorkCls_;

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
		List<KscmtBasicWorkCls> entities = this.toEntity(classificationBasicWork);
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
		List<KscmtBasicWorkCls> entities = this.toEntity(classificationBasicWork);
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

		CriteriaDelete<KscmtBasicWorkCls> cd = bd.createCriteriaDelete(KscmtBasicWorkCls.class);

		// Root
		Root<KscmtBasicWorkCls> root = cd.from(KscmtBasicWorkCls.class);

		// Predicate where clause
		List<Predicate> predicateList = new ArrayList<>();
		predicateList.add(bd.equal(
				root.get(KscmtBasicWorkCls_.kscmtBasicWorkClsPK).get(KscmtBasicWorkClsPK_.cid), companyId));
		predicateList.add(bd.equal(
				root.get(KscmtBasicWorkCls_.kscmtBasicWorkClsPK).get(KscmtBasicWorkClsPK_.classifyCode),
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
		CriteriaQuery<KscmtBasicWorkCls> cq = bd.createQuery(KscmtBasicWorkCls.class);
		
		// Root
		Root<KscmtBasicWorkCls> root = cq.from(KscmtBasicWorkCls.class);
		cq.select(root);
		
		// Predicate where clause
		List<Predicate> predicateList = new ArrayList<>();
		predicateList.add(bd.equal(
				root.get(KscmtBasicWorkCls_.kscmtBasicWorkClsPK).get(KscmtBasicWorkClsPK_.cid), companyId));
		predicateList.add(bd.equal(
				root.get(KscmtBasicWorkCls_.kscmtBasicWorkClsPK).get(KscmtBasicWorkClsPK_.classifyCode),
				classificationCode));
		
		// Set Where clause to SQL Query
		cq.where(predicateList.toArray(new Predicate[] {}));
		
		// Create Query
		TypedQuery<KscmtBasicWorkCls> query = em.createQuery(cq);

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
		Root<KscmtBasicWorkCls> root = cq.from(KscmtBasicWorkCls.class);
		
		// Select Classification Code
		cq.select(root.get(KscmtBasicWorkCls_.kscmtBasicWorkClsPK).get(KscmtBasicWorkClsPK_.classifyCode)).distinct(true);
		
		// Predicate where clause
		List<Predicate> predicateList = new ArrayList<>();
		predicateList.add(bd.equal(
				root.get(KscmtBasicWorkCls_.kscmtBasicWorkClsPK).get(KscmtBasicWorkClsPK_.cid), companyId));
		
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
	private ClassificationBasicWork toDomain(List<KscmtBasicWorkCls> entity) {
		return new ClassificationBasicWork(new JpaClassifiBasicWorkGetMemento(entity));
	}

	/**
	 * To entity.
	 *
	 * @param domain
	 *            the domain
	 * @return the list
	 */
	private List<KscmtBasicWorkCls> toEntity(ClassificationBasicWork domain) {
		return domain.getBasicWorkSetting().stream().map(basic -> {
			KscmtBasicWorkCls entity = new KscmtBasicWorkCls();
			basic.saveToMemento(new JpaBWSettingClassifySetMemento(entity));
			entity.getKscmtBasicWorkClsPK().setCid(domain.getCompanyId());
			entity.getKscmtBasicWorkClsPK().setClassifyCode(domain.getClassificationCode().v());
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
	private KscmtBasicWorkCls updateEntity(KscmtBasicWorkCls entity) {
		KscmtBasicWorkCls entityToUpdate = this.queryProxy()
				.find(entity.getKscmtBasicWorkClsPK(), KscmtBasicWorkCls.class).get();
		entityToUpdate.setWorktypeCode(entity.getWorktypeCode());
		entityToUpdate.setWorkingCode(StringUtils.isEmpty(entity.getWorkingCode()) ? null : entity.getWorkingCode());
		entityToUpdate.getKscmtBasicWorkClsPK()
				.setWorkdayDivision(entity.getKscmtBasicWorkClsPK().getWorkdayDivision());
		entityToUpdate.getKscmtBasicWorkClsPK().setCid(entity.getKscmtBasicWorkClsPK().getCid());
		entityToUpdate.getKscmtBasicWorkClsPK()
				.setClassifyCode(entity.getKscmtBasicWorkClsPK().getClassifyCode());

		return entityToUpdate;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.basicworkregister.
	 * ClassifiBasicWorkRepository#findById(java.lang.String, java.lang.String,
	 * int)
	 */
	@Override
	public Optional<ClassificationBasicWork> findById(String companyId, String classificationCode, int workdayAtr) {
		// Get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder bd = em.getCriteriaBuilder();
		CriteriaQuery<KscmtBasicWorkCls> cq = bd.createQuery(KscmtBasicWorkCls.class);

		// Root
		Root<KscmtBasicWorkCls> root = cq.from(KscmtBasicWorkCls.class);
		cq.select(root);

		// Predicate where clause
		List<Predicate> predicateList = new ArrayList<>();
		predicateList.add(bd.equal(
				root.get(KscmtBasicWorkCls_.kscmtBasicWorkClsPK).get(KscmtBasicWorkClsPK_.cid), companyId));
		predicateList.add(bd.equal(
				root.get(KscmtBasicWorkCls_.kscmtBasicWorkClsPK).get(KscmtBasicWorkClsPK_.classifyCode),
				classificationCode));
		predicateList.add(bd.equal(
				root.get(KscmtBasicWorkCls_.kscmtBasicWorkClsPK).get(KscmtBasicWorkClsPK_.workdayDivision),
				workdayAtr));

		// Set Where clause to SQL Query
		cq.where(predicateList.toArray(new Predicate[] {}));

		// Create Query
		TypedQuery<KscmtBasicWorkCls> query = em.createQuery(cq);

		if (CollectionUtil.isEmpty(query.getResultList())) {
			return Optional.empty();
		}

		return Optional.of(this.toDomain(query.getResultList()));
	}



}
