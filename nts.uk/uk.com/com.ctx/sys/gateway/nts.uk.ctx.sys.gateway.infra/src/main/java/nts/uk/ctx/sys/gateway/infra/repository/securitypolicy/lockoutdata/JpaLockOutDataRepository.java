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
import nts.uk.ctx.sys.gateway.infra.entity.securitypolicy.lockoutdata.SgwdtLockout;
import nts.uk.ctx.sys.gateway.infra.entity.securitypolicy.lockoutdata.SgwdtLockoutPK_;
import nts.uk.ctx.sys.gateway.infra.entity.securitypolicy.lockoutdata.SgwdtLockout_;

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
		CriteriaQuery<SgwdtLockout> query = builder.createQuery(SgwdtLockout.class);
		Root<SgwdtLockout> root = query.from(SgwdtLockout.class);

		List<Predicate> predicateList = new ArrayList<>();

		//Check UserId
		predicateList.add(
				builder.equal(root.get(SgwdtLockout_.sgwdtLockoutPK).get(SgwdtLockoutPK_.userId), userId));

		query.where(predicateList.toArray(new Predicate[] {}));

		//Get Result
		List<SgwdtLockout> result = em.createQuery(query).getResultList();

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
		SgwdtLockout entity = new SgwdtLockout();
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
		CriteriaDelete<SgwdtLockout> cq = criteriaBuilder.createCriteriaDelete(SgwdtLockout.class);
		Root<SgwdtLockout> root = cq.from(SgwdtLockout.class);
		
		CollectionUtil.split(usersID, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, splitData -> {
			List<Predicate> lstpredicateWhere = new ArrayList<>();
			lstpredicateWhere.add(root.get(SgwdtLockout_.sgwdtLockoutPK).get(SgwdtLockoutPK_.userId).in(splitData));
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
		CriteriaQuery<SgwdtLockout> query = builder.createQuery(SgwdtLockout.class);
		Root<SgwdtLockout> root = query.from(SgwdtLockout.class);

		List<Predicate> predicateList = new ArrayList<>();

		//Check UserId
		predicateList.add(
				builder.equal(root.get(SgwdtLockout_.sgwdtLockoutPK).get(SgwdtLockoutPK_.contractCd), contractCode));

		query.where(predicateList.toArray(new Predicate[] {}));

		//Get Result
		List<SgwdtLockout> result = em.createQuery(query).getResultList();
		return result.stream().map(this::toDomain).collect(Collectors.toList());
	}
	
	/**
	 * To domain.
	 *
	 * @param entity the entity
	 * @return the lock out data
	 */
	private LockOutData toDomain(SgwdtLockout entity) {
		return new LockOutData(new JpaLockOutDataGetMemento(entity));
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.gateway.dom.securitypolicy.lockoutdata.LockOutDataRepository#findByUserIdAndContractCode(java.lang.String, java.lang.String)
	 */
	@Override
	public Optional<LockOutData> findByUserIdAndContractCode(String userId, String contractCd) {
		EntityManager em = this.getEntityManager();

		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<SgwdtLockout> query = builder.createQuery(SgwdtLockout.class);
		Root<SgwdtLockout> root = query.from(SgwdtLockout.class);

		List<Predicate> predicateList = new ArrayList<>();

		//Check UserId
		predicateList.add(
				builder.equal(root.get(SgwdtLockout_.sgwdtLockoutPK).get(SgwdtLockoutPK_.userId), userId));
		predicateList.add(
				builder.equal(root.get(SgwdtLockout_.sgwdtLockoutPK).get(SgwdtLockoutPK_.contractCd), contractCd));

		query.where(predicateList.toArray(new Predicate[] {}));

		//Get Result
		List<SgwdtLockout> result = em.createQuery(query).getResultList();

		if (result.isEmpty()) {
			return Optional.empty();
		} else {
			return Optional.of(new LockOutData(new JpaLockOutDataGetMemento(result.get(0))));
		}
	}
}
