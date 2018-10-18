/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.infra.repository.singlesignon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.sys.gateway.dom.singlesignon.WindowsAccount;
import nts.uk.ctx.sys.gateway.dom.singlesignon.WindowsAccountGetMemento;
import nts.uk.ctx.sys.gateway.dom.singlesignon.WindowsAccountInfo;
import nts.uk.ctx.sys.gateway.dom.singlesignon.WindowsAccountRepository;
import nts.uk.ctx.sys.gateway.infra.entity.singlesignon.SgwmtWindowAcc;
import nts.uk.ctx.sys.gateway.infra.entity.singlesignon.SgwmtWindowAccPK;
import nts.uk.ctx.sys.gateway.infra.entity.singlesignon.SgwmtWindowAccPK_;
import nts.uk.ctx.sys.gateway.infra.entity.singlesignon.SgwmtWindowAcc_;

/**
 * The Class JpaWindowAccountRepository.
 */
@Stateless
@Transactional
public class JpaWindowAccountRepository extends JpaRepository implements WindowsAccountRepository {

	/** The get by list userids. */
	private static final String GET_BY_LIST_USERIDS = "SELECT w FROM SgwmtWindowAcc w "
			+ " where w.sgwmtWindowAccPK.userId IN :lstUserId";

	/** The Constant USED. */
	private static final int USED = 1;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.sys.gateway.dom.singlesignon.WindowAccountRepository#
	 * findByUserIdAndUseAtr(java.lang.String, java.lang.Integer)
	 */
	@Override
	public Optional<WindowsAccount> findListWindowAccountByUserId(String userId) {

		// Get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder bd = em.getCriteriaBuilder();
		CriteriaQuery<SgwmtWindowAcc> cq = bd.createQuery(SgwmtWindowAcc.class);

		// Root
		Root<SgwmtWindowAcc> root = cq.from(SgwmtWindowAcc.class);
		cq.select(root);

		// Predicate where clause
		List<Predicate> predicateList = new ArrayList<>();
		predicateList.add(bd.equal(
				root.get(SgwmtWindowAcc_.sgwmtWindowAccPK).get(SgwmtWindowAccPK_.userId), userId));

		// Set Where clause to SQL Query
		cq.where(predicateList.toArray(new Predicate[] {}));

		// Create Query
		List<SgwmtWindowAcc> result = em.createQuery(cq).getResultList();

		if (result.isEmpty()) {
			return Optional.empty();
		} else {
			return Optional.of(this.toWindowsAccountDomain(
					result.stream().findFirst().get().getSgwmtWindowAccPK().getUserId(),
					result.stream().map(item -> this.toAccInfoDomain(item))
							.collect(Collectors.toList())));
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.sys.gateway.dom.singlesignon.WindowAccountRepository#remove(
	 * java.util.List)
	 */
	@Override
	public void remove(String userId, Integer no) {

		SgwmtWindowAccPK pk = new SgwmtWindowAccPK(userId, no);

		if (pk != null) {
			this.commandProxy().remove(SgwmtWindowAcc.class, pk);

		}
	}

	/**
	 * Adds the.
	 *
	 * @param windowAccount
	 *            the window account
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.sys.gateway.dom.singlesignon.WindowAccountRepository#add(nts.
	 * uk.ctx.sys.gateway.dom.singlesignon.WindowAccount)
	 */
	@Override
	public void add(String userId, WindowsAccountInfo windowAccount) {
		SgwmtWindowAcc entity = new SgwmtWindowAcc();
		windowAccount.saveToMemento(new JpaWindowAccountInfoSetMemento(userId, entity));
		this.commandProxy().insert(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.sys.gateway.dom.singlesignon.WindowAccountRepository#
	 * findbyUserNameAndHostName(java.lang.String, java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public Optional<WindowsAccount> findbyUserNameAndHostName(String userName, String hostName) {

		// Get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder bd = em.getCriteriaBuilder();
		CriteriaQuery<SgwmtWindowAcc> cq = bd.createQuery(SgwmtWindowAcc.class);

		// Root
		Root<SgwmtWindowAcc> root = cq.from(SgwmtWindowAcc.class);
		cq.select(root);

		// Predicate where clause
		List<Predicate> predicateList = new ArrayList<>();
		predicateList.add(bd.equal(root.get(SgwmtWindowAcc_.userName), userName));
		predicateList.add(bd.equal(root.get(SgwmtWindowAcc_.hostName), hostName));

		// Set Where clause to SQL Query
		cq.where(predicateList.toArray(new Predicate[] {}));

		// Create Query
		List<SgwmtWindowAcc> result = em.createQuery(cq).getResultList();

		if (result.isEmpty()) {
			return Optional.empty();
		} else {
			return Optional.of(this.toWindowsAccountDomain(
					result.stream().findFirst().get().getSgwmtWindowAccPK().getUserId(),
					result.stream().map(item -> this.toAccInfoDomain(item))
							.collect(Collectors.toList())));
		}

	}

	/**
	 * Findby user name and host name and is used.
	 *
	 * @param userName the user name
	 * @param hostName the host name
	 * @return the optional
	 */
	@Override
	public Optional<WindowsAccount> findbyUserNameAndHostNameAndIsUsed(String userName, String hostName) {
		
		// Get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder bd = em.getCriteriaBuilder();
		CriteriaQuery<SgwmtWindowAcc> cq = bd.createQuery(SgwmtWindowAcc.class);
		
		// Root
		Root<SgwmtWindowAcc> root = cq.from(SgwmtWindowAcc.class);
		cq.select(root);
		
		// Predicate where clause
		List<Predicate> predicateList = new ArrayList<>();
		predicateList.add(bd.equal(root.get(SgwmtWindowAcc_.userName), userName));
		predicateList.add(bd.equal(root.get(SgwmtWindowAcc_.hostName), hostName));
		predicateList.add(bd.equal(root.get(SgwmtWindowAcc_.useAtr), USED));
		
		// Set Where clause to SQL Query
		cq.where(predicateList.toArray(new Predicate[] {}));
		
		// Create Query
		List<SgwmtWindowAcc> result = em.createQuery(cq).getResultList();
		
		if (result.isEmpty()) {
			return Optional.empty();
		} else {
			return Optional.of(this.toWindowsAccountDomain(
					result.stream().findFirst().get().getSgwmtWindowAccPK().getUserId(),
					result.stream().map(item -> this.toAccInfoDomain(item))
					.collect(Collectors.toList())));
		}
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.sys.gateway.dom.singlesignon.WindowsAccountRepository#
	 * findByUserId(java.lang.String)
	 */
	@Override
	public Optional<WindowsAccount> findByUserId(String userId) {
		// Get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder bd = em.getCriteriaBuilder();
		CriteriaQuery<SgwmtWindowAcc> cq = bd.createQuery(SgwmtWindowAcc.class);

		// Root
		Root<SgwmtWindowAcc> root = cq.from(SgwmtWindowAcc.class);
		cq.select(root);

		// Predicate where clause
		List<Predicate> predicateList = new ArrayList<>();
		predicateList.add(bd.equal(
				root.get(SgwmtWindowAcc_.sgwmtWindowAccPK).get(SgwmtWindowAccPK_.userId), userId));

		// Set Where clause to SQL Query
		cq.where(predicateList.toArray(new Predicate[] {}));

		// Create Query
		List<SgwmtWindowAcc> result = em.createQuery(cq).getResultList();

		if (result.isEmpty()) {
			return Optional.empty();
		} else {
			return Optional.of(this.toWindowsAccountDomain(
					result.stream().findFirst().get().getSgwmtWindowAccPK().getUserId(),
					result.stream().map(item -> this.toAccInfoDomain(item))
							.collect(Collectors.toList())));
		}
	}

	/**
	 * To windows account domain.
	 *
	 * @param entity
	 *            the entity
	 * @return the windows account
	 */
	private WindowsAccount toWindowsAccountDomain(String userId,
			List<WindowsAccountInfo> windowsAccountInfos) {
		return new WindowsAccount(new JpaWindowsAccountGetMemento(userId, windowsAccountInfos));
	}

	/**
	 * The Class JpaWindowsAccountGetMemento.
	 */
	private class JpaWindowsAccountGetMemento implements WindowsAccountGetMemento {

		/** The user id. */
		private String userId;

		/** The windows account infos. */
		private List<WindowsAccountInfo> windowsAccountInfos;

		/**
		 * Instantiates a new jpa windows account get memento.
		 *
		 * @param userId
		 *            the user id
		 * @param windowsAccountInfos
		 *            the windows account infos
		 */
		public JpaWindowsAccountGetMemento(String userId,
				List<WindowsAccountInfo> windowsAccountInfos) {
			this.userId = userId;
			this.windowsAccountInfos = windowsAccountInfos;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * nts.uk.ctx.sys.gateway.dom.singlesignon.WindowsAccountGetMemento#
		 * getUserId()
		 */
		@Override
		public String getUserId() {
			return this.userId;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * nts.uk.ctx.sys.gateway.dom.singlesignon.WindowsAccountGetMemento#
		 * getAccountInfos()
		 */
		@Override
		public List<WindowsAccountInfo> getAccountInfos() {
			return this.windowsAccountInfos;
		}
	}

	/**
	 * To acc info domain.
	 *
	 * @param entity
	 *            the entity
	 * @return the windows account info
	 */
	private WindowsAccountInfo toAccInfoDomain(SgwmtWindowAcc entity) {
		return new WindowsAccountInfo(new JpaWindowAccountInfoGetMemento(entity));
	}

	/**
	 * Update.
	 *
	 * @param winAccCommand
	 *            the win acc command
	 * @param winAccDb
	 *            the win acc db
	 */
	@Override
	public void update(String userId, WindowsAccountInfo winAccCommand,
			WindowsAccountInfo winAccDb) {
		SgwmtWindowAcc entity = this.queryProxy()
				.find(new SgwmtWindowAccPK(userId, winAccDb.getNo()), SgwmtWindowAcc.class).get();

		// set data
		entity.setHostName(winAccCommand.getHostName().v());
		entity.setUserName(winAccCommand.getUserName().v());
		entity.setUseAtr(winAccCommand.getUseAtr().value);

		// update
		this.commandProxy().update(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.sys.gateway.dom.singlesignon.WindowsAccountRepository#
	 * findByListUserId(java.util.List)
	 */
	@Override
	public List<WindowsAccount> findByListUserId(List<String> ltsUserId) {
		// Check conditions
		if (CollectionUtil.isEmpty(ltsUserId)) {
			return Collections.emptyList();
		}

		// Split user id list.
		List<SgwmtWindowAcc> resultList = new ArrayList<>();

		CollectionUtil.split(ltsUserId, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {
			resultList.addAll(this.queryProxy().query(GET_BY_LIST_USERIDS, SgwmtWindowAcc.class)
					.setParameter("lstUserId", subList).getList());
		});

		Map<String, List<SgwmtWindowAcc>> mapUsrAcc = resultList.stream()
				.collect(Collectors.groupingBy(item -> item.getSgwmtWindowAccPK().getUserId()));

		// Return
		return mapUsrAcc.keySet().stream().map(userId -> {
			List<SgwmtWindowAcc> result = mapUsrAcc.get(userId);

			return this.toWindowsAccountDomain(
					result.stream().findFirst().get().getSgwmtWindowAccPK().getUserId(),
					result.stream().map(item -> this.toAccInfoDomain(item))
							.collect(Collectors.toList()));

		}).collect(Collectors.toList());
	}

}
