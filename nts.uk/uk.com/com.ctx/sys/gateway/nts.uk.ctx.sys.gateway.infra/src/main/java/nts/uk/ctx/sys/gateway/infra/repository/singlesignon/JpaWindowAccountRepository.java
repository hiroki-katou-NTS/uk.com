/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.infra.repository.singlesignon;

import java.util.ArrayList;
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
import javax.transaction.Transactional;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.sys.gateway.dom.singlesignon.WindowAccount;
import nts.uk.ctx.sys.gateway.dom.singlesignon.WindowAccountRepository;
import nts.uk.ctx.sys.gateway.infra.entity.singlesignon.SgwmtWindowAcc;
import nts.uk.ctx.sys.gateway.infra.entity.singlesignon.SgwmtWindowAccPK;
import nts.uk.ctx.sys.gateway.infra.entity.singlesignon.SgwmtWindowAccPK_;
import nts.uk.ctx.sys.gateway.infra.entity.singlesignon.SgwmtWindowAcc_;

/**
 * The Class JpaWindowAccountRepository.
 */
@Stateless
@Transactional
public class JpaWindowAccountRepository extends JpaRepository implements WindowAccountRepository {

	private final String DELETE_WIN_ACC_BY_USER_ID = "DELETE FROM SgwmtWindowAcc wc WHERE wc.sgwmtWindowAccPK.userId = :userId ";

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.sys.gateway.dom.singlesignon.WindowAccountRepository#
	 * findByUserIdAndUseAtr(java.lang.String, java.lang.Integer)
	 */
	@Override
	public List<WindowAccount> findByUserIdAndUseAtr(String userId, Integer useAtr) {

		// Get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder bd = em.getCriteriaBuilder();
		CriteriaQuery<SgwmtWindowAcc> cq = bd.createQuery(SgwmtWindowAcc.class);

		// Root
		Root<SgwmtWindowAcc> root = cq.from(SgwmtWindowAcc.class);
		cq.select(root);

		// Predicate where clause
		List<Predicate> predicateList = new ArrayList<>();
		predicateList.add(bd.equal(root.get(SgwmtWindowAcc_.sgwmtWindowAccPK).get(SgwmtWindowAccPK_.userId), userId));

		predicateList.add(bd.equal(root.get(SgwmtWindowAcc_.useAtr), useAtr));

		// Set Where clause to SQL Query
		cq.where(predicateList.toArray(new Predicate[] {}));

		// Create Query
		TypedQuery<SgwmtWindowAcc> query = em.createQuery(cq);

		return query.getResultList().stream().map(item -> this.toDomain(item)).collect(Collectors.toList());

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.sys.gateway.dom.singlesignon.WindowAccountRepository#remove(
	 * java.util.List)
	 */
	@Override
	public void remove(String userId, String userName, String hostName) {

		SgwmtWindowAccPK pk = new SgwmtWindowAccPK(userId, userName, hostName);

		if (pk != null) {
			this.commandProxy().remove(SgwmtWindowAcc.class, pk);

		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.sys.gateway.dom.singlesignon.WindowAccountRepository#add(nts.
	 * uk.ctx.sys.gateway.dom.singlesignon.WindowAccount)
	 */
	@Override
	public void add(WindowAccount windowAccount) {
		SgwmtWindowAcc entity = new SgwmtWindowAcc();
		windowAccount.saveToMemento(new JpaWindowAccountSetMemento(entity));
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
	public Optional<WindowAccount> findbyUserNameAndHostName(String userName, String hostName) {

		// Get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder bd = em.getCriteriaBuilder();
		CriteriaQuery<SgwmtWindowAcc> cq = bd.createQuery(SgwmtWindowAcc.class);

		// Root
		Root<SgwmtWindowAcc> root = cq.from(SgwmtWindowAcc.class);
		cq.select(root);

		// Predicate where clause
		List<Predicate> predicateList = new ArrayList<>();
		predicateList
				.add(bd.equal(root.get(SgwmtWindowAcc_.sgwmtWindowAccPK).get(SgwmtWindowAccPK_.userName), userName));
		predicateList
				.add(bd.equal(root.get(SgwmtWindowAcc_.sgwmtWindowAccPK).get(SgwmtWindowAccPK_.hostName), hostName));

		// Set Where clause to SQL Query
		cq.where(predicateList.toArray(new Predicate[] {}));

		// Create Query
		TypedQuery<SgwmtWindowAcc> query = em.createQuery(cq);

		try {
			// exclude select
			return Optional.of(this.toDomain(query.getSingleResult()));
		} catch (NoResultException e) {
			return Optional.empty();
		}

	}

	@Override
	public List<WindowAccount> findByUserId(String userId) {
		// Get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder bd = em.getCriteriaBuilder();
		CriteriaQuery<SgwmtWindowAcc> cq = bd.createQuery(SgwmtWindowAcc.class);

		// Root
		Root<SgwmtWindowAcc> root = cq.from(SgwmtWindowAcc.class);
		cq.select(root);

		// Predicate where clause
		List<Predicate> predicateList = new ArrayList<>();
		predicateList.add(bd.equal(root.get(SgwmtWindowAcc_.sgwmtWindowAccPK).get(SgwmtWindowAccPK_.userId), userId));

		// Set Where clause to SQL Query
		cq.where(predicateList.toArray(new Predicate[] {}));

		// Create Query
		TypedQuery<SgwmtWindowAcc> query = em.createQuery(cq);

		return query.getResultList().stream().map(item -> this.toDomain(item)).collect(Collectors.toList());
	}

	/**
	 * To domain.
	 *
	 * @param entity
	 *            the entity
	 * @return the window account
	 */
	private WindowAccount toDomain(SgwmtWindowAcc entity) {
		return new WindowAccount(new JpaWindowAccountGetMemento(entity));

	}	

	@Override
	public void update(WindowAccount winAccCommand, WindowAccount winAccDb) {
		SgwmtWindowAcc entity = this.queryProxy()
				.find(new SgwmtWindowAccPK(winAccDb.getUserId(), winAccDb.getUserName(), winAccDb.getHostName()),
						SgwmtWindowAcc.class)
				.get();

		// set data
		entity.getSgwmtWindowAccPK().setHostName(winAccCommand.getHostName());
		entity.getSgwmtWindowAccPK().setUserName(winAccCommand.getUserName());

		// update
		this.commandProxy().update(entity);
	}

}
