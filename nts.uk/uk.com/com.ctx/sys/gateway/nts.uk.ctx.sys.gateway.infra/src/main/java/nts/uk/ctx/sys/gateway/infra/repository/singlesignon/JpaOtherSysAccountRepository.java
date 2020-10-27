/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.infra.repository.singlesignon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.sys.gateway.dom.singlesignon.OtherSysAccount;
import nts.uk.ctx.sys.gateway.dom.singlesignon.OtherSysAccountRepository;
import nts.uk.ctx.sys.gateway.infra.entity.singlesignon.SgwmtSsoOtherSysAcc;
import nts.uk.ctx.sys.gateway.infra.entity.singlesignon.SgwmtSsoOtherSysAccPK;
import nts.uk.ctx.sys.gateway.infra.entity.singlesignon.SgwmtSsoOtherSysAccPK_;
import nts.uk.ctx.sys.gateway.infra.entity.singlesignon.SgwmtSsoOtherSysAcc_;

/**
 * The Class JpaOtherSysAccountRepository.
 */
@Stateless
public class JpaOtherSysAccountRepository extends JpaRepository implements OtherSysAccountRepository {

	/** The get by list userids. */
	private static final String GET_BY_LIST_SID = "SELECT o FROM SgwmtSsoOtherSysAcc o "
			+ " where o.sgwmtSsoOtherSysAccPK.employeeId IN :lstEmployeeId";

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.sys.gateway.dom.singlesignon.OtherSysAccountRepository#remove(
	 * java.lang.String)
	 */
	@Override
	public void remove(String cid, String employeeId) {
		SgwmtSsoOtherSysAccPK pk = new SgwmtSsoOtherSysAccPK(cid,employeeId);

		if (pk != null) {
			this.commandProxy().remove(SgwmtSsoOtherSysAcc.class, pk);

		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.sys.gateway.dom.singlesignon.OtherSysAccountRepository#add(nts
	 * .uk.ctx.sys.gateway.dom.singlesignon.OtherSysAccount)
	 */
	@Override
	public void add(OtherSysAccount otherSysAccount) {
		SgwmtSsoOtherSysAcc entity = new SgwmtSsoOtherSysAcc();
		otherSysAccount.saveToMemento(new JpaOtherSysAccountSetMemento(entity));
		this.commandProxy().insert(entity);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.sys.gateway.dom.singlesignon.OtherSysAccountRepository#
	 * findByCompanyCodeAndUserName(java.lang.String, java.lang.String)
	 */
	@Override
	public List<OtherSysAccount> findByCompanyCodeAndUserName(String companyCode, String userName) {
		// Get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder bd = em.getCriteriaBuilder();
		CriteriaQuery<SgwmtSsoOtherSysAcc> cq = bd.createQuery(SgwmtSsoOtherSysAcc.class);

		// Root
		Root<SgwmtSsoOtherSysAcc> root = cq.from(SgwmtSsoOtherSysAcc.class);
		cq.select(root);

		// Predicate where clause
		List<Predicate> predicateList = new ArrayList<>();
		predicateList.add(bd.equal(root.get(SgwmtSsoOtherSysAcc_.ccd), companyCode));
		predicateList.add(bd.equal(root.get(SgwmtSsoOtherSysAcc_.userName), userName));

		// Set Where clause to SQL Query
		cq.where(predicateList.toArray(new Predicate[] {}));

		// Create Query
		List<SgwmtSsoOtherSysAcc> result = em.createQuery(cq).getResultList();

		if (result.isEmpty()) {
			return new ArrayList<>();
		} else {
			return result.stream().map(item -> {
				return this.toDomain(item);
			}).collect(Collectors.toList());
		}

	}

	/**
	 * To domain.
	 *
	 * @param entity
	 *            the entity
	 * @return the other sys account
	 */
	private OtherSysAccount toDomain(SgwmtSsoOtherSysAcc entity) {
		return new OtherSysAccount(new JpaOtherSysAccountGetMemento(entity));

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.sys.gateway.dom.singlesignon.OtherSysAccountRepository#
	 * findByUserId(java.lang.String)
	 */
	@Override
	public Optional<OtherSysAccount> findByEmployeeId(String companyId, String employeeId) {

		// Get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder bd = em.getCriteriaBuilder();
		CriteriaQuery<SgwmtSsoOtherSysAcc> cq = bd.createQuery(SgwmtSsoOtherSysAcc.class);

		// Root
		Root<SgwmtSsoOtherSysAcc> root = cq.from(SgwmtSsoOtherSysAcc.class);
		cq.select(root);

		// Predicate where clause
		List<Predicate> predicateList = new ArrayList<>();
		predicateList.add(bd.equal(root.get(SgwmtSsoOtherSysAcc_.sgwmtSsoOtherSysAccPK).get(SgwmtSsoOtherSysAccPK_.employeeId),
				employeeId));
		predicateList
				.add(bd.equal(root.get(SgwmtSsoOtherSysAcc_.sgwmtSsoOtherSysAccPK).get(SgwmtSsoOtherSysAccPK_.cid), companyId));

		// Set Where clause to SQL Query
		cq.where(predicateList.toArray(new Predicate[] {}));

		// Create Query
		TypedQuery<SgwmtSsoOtherSysAcc> query = em.createQuery(cq);

		try {
			// exclude select
			return Optional.of(this.toDomain(query.getSingleResult()));
		} catch (NoResultException e) {
			throw new RuntimeException(e);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.sys.gateway.dom.singlesignon.OtherSysAccountRepository#update(
	 * nts.uk.ctx.sys.gateway.dom.singlesignon.OtherSysAccount,
	 * nts.uk.ctx.sys.gateway.dom.singlesignon.OtherSysAccount)
	 */
	@Override
	public void update(OtherSysAccount otherSysAccCommand, OtherSysAccount otherSysAccDB) {
		SgwmtSsoOtherSysAcc entity = this.queryProxy()
				.find(new SgwmtSsoOtherSysAccPK(otherSysAccDB.getCompanyId(),otherSysAccDB.getEmployeeId()), SgwmtSsoOtherSysAcc.class).get();

		// set data
		entity.setCcd(otherSysAccCommand.getAccountInfo().getCompanyCode().v());
		entity.setUserName(otherSysAccCommand.getAccountInfo().getUserName().v());
		entity.setUseAtr(otherSysAccCommand.getAccountInfo().getUseAtr().value);

		// update
		this.commandProxy().update(entity);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.sys.gateway.dom.singlesignon.OtherSysAccountRepository#
	 * findAllOtherSysAccount(java.util.List)
	 */
	@Override
	public List<OtherSysAccount> findAllOtherSysAccount(List<String> listEmployeeId) {
		// Check conditions
		if (CollectionUtil.isEmpty(listEmployeeId)) {
			return Collections.emptyList();
		}

		// Split user id list.
		List<SgwmtSsoOtherSysAcc> resultList = new ArrayList<>();

		CollectionUtil.split(listEmployeeId, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {
			resultList.addAll(this.queryProxy().query(GET_BY_LIST_SID, SgwmtSsoOtherSysAcc.class)
					.setParameter("lstEmployeeId", subList).getList());
		});

		// Return
		return resultList.stream().map(entity -> this.toDomain(entity)).collect(Collectors.toList());

	}

}
