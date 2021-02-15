/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.infra.repository.securitypolicy.lockoutdata;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.lockoutdata.LockOutData;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.lockoutdata.LockOutDataRepository;
import nts.uk.ctx.sys.gateway.infra.entity.securitypolicy.lockoutdata.SgwmtLockoutData;
import nts.uk.ctx.sys.gateway.infra.entity.securitypolicy.lockoutdata.SgwmtLockoutDataPK_;
import nts.uk.ctx.sys.gateway.infra.entity.securitypolicy.lockoutdata.SgwmtLockoutData_;

/**
 * The Class JpaLogoutDataRepository.
 */
@Stateless
@Transactional
public class JpaLockOutDataRepository extends JpaRepository implements LockOutDataRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.sys.gateway.dom.securitypolicy.logoutdata.LogoutDataRepository
	 * #findByUserId(java.lang.String)
	 */
	@Override
	public Optional<LockOutData> findByUserId(String userId) {
		EntityManager em = this.getEntityManager();

		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<SgwmtLockoutData> query = builder.createQuery(SgwmtLockoutData.class);
		Root<SgwmtLockoutData> root = query.from(SgwmtLockoutData.class);

		List<Predicate> predicateList = new ArrayList<>();

		//Check UserId
		predicateList.add(
				builder.equal(root.get(SgwmtLockoutData_.sgwmtLockoutDataPK).get(SgwmtLockoutDataPK_.userId), userId));

		query.where(predicateList.toArray(new Predicate[] {}));

		//Get Result
		List<SgwmtLockoutData> result = em.createQuery(query).getResultList();

		if (result.isEmpty()) {
			return Optional.empty();
		} else {
			return Optional.of(new LockOutData(new JpaLockOutDataGetMemento(result.get(0))));
		}
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.gateway.dom.securitypolicy.lockoutdata.LockOutDataRepository#add(nts.uk.ctx.sys.gateway.dom.securitypolicy.lockoutdata.LockOutData)
	 */
	@Override
	public void add(LockOutData lockOutData) {
		SgwmtLockoutData entity = new SgwmtLockoutData();
		lockOutData.saveToMemento(new JpaLockOutDataSetMemento(entity));
		this.commandProxy().insert(entity);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.gateway.dom.securitypolicy.lockoutdata.LockOutDataRepository#remove(java.util.List)
	 */
	@Override
	public void remove(List<String> usersID) {
		
		if(CollectionUtil.isEmpty(usersID)) {
			return;
		}
		
		// Get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaDelete<SgwmtLockoutData> cq = criteriaBuilder.createCriteriaDelete(SgwmtLockoutData.class);
		Root<SgwmtLockoutData> root = cq.from(SgwmtLockoutData.class);
		
		CollectionUtil.split(usersID, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, splitData -> {
			List<Predicate> lstpredicateWhere = new ArrayList<>();
			lstpredicateWhere.add(root.get(SgwmtLockoutData_.sgwmtLockoutDataPK).get(SgwmtLockoutDataPK_.userId).in(splitData));
			cq.where(lstpredicateWhere.toArray(new Predicate[] {}));
			em.createQuery(cq).executeUpdate();
		});
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.gateway.dom.securitypolicy.lockoutdata.LockOutDataRepository#findByContractCode(java.lang.String)
	 */
	@Override
	public List<LockOutData> findByContractCode(String contractCode) {
		EntityManager em = this.getEntityManager();

		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<SgwmtLockoutData> query = builder.createQuery(SgwmtLockoutData.class);
		Root<SgwmtLockoutData> root = query.from(SgwmtLockoutData.class);

		List<Predicate> predicateList = new ArrayList<>();

		//Check UserId
		predicateList.add(
				builder.equal(root.get(SgwmtLockoutData_.sgwmtLockoutDataPK).get(SgwmtLockoutDataPK_.contractCd), contractCode));

		query.where(predicateList.toArray(new Predicate[] {}));

		//Get Result
		List<SgwmtLockoutData> result = em.createQuery(query).getResultList();
		return result.stream().map(this::toDomain).collect(Collectors.toList());
	}
	
	/**
	 * To domain.
	 *
	 * @param entity the entity
	 * @return the lock out data
	 */
	private LockOutData toDomain(SgwmtLockoutData entity) {
		return new LockOutData(new JpaLockOutDataGetMemento(entity));
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.gateway.dom.securitypolicy.lockoutdata.LockOutDataRepository#findByUserIdAndContractCode(java.lang.String, java.lang.String)
	 */
	@Override
	public Optional<LockOutData> findByUserIdAndContractCode(String userId, String contractCd) {
		EntityManager em = this.getEntityManager();

		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<SgwmtLockoutData> query = builder.createQuery(SgwmtLockoutData.class);
		Root<SgwmtLockoutData> root = query.from(SgwmtLockoutData.class);

		List<Predicate> predicateList = new ArrayList<>();

		//Check UserId
		predicateList.add(
				builder.equal(root.get(SgwmtLockoutData_.sgwmtLockoutDataPK).get(SgwmtLockoutDataPK_.userId), userId));
		predicateList.add(
				builder.equal(root.get(SgwmtLockoutData_.sgwmtLockoutDataPK).get(SgwmtLockoutDataPK_.contractCd), contractCd));

		query.where(predicateList.toArray(new Predicate[] {}));

		//Get Result
		List<SgwmtLockoutData> result = em.createQuery(query).getResultList();

		if (result.isEmpty()) {
			return Optional.empty();
		} else {
			return Optional.of(new LockOutData(new JpaLockOutDataGetMemento(result.get(0))));
		}
	}
}
