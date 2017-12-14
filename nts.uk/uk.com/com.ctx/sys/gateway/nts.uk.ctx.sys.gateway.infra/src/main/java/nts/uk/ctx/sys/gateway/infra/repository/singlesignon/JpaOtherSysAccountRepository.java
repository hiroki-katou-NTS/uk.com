/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.infra.repository.singlesignon;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.sys.gateway.dom.singlesignon.OtherSysAccount;
import nts.uk.ctx.sys.gateway.dom.singlesignon.OtherSysAccountRepository;
import nts.uk.ctx.sys.gateway.infra.entity.singlesignon.SgwmtOtherSysAcc;
import nts.uk.ctx.sys.gateway.infra.entity.singlesignon.SgwmtOtherSysAccPK;
import nts.uk.ctx.sys.gateway.infra.entity.singlesignon.SgwmtOtherSysAccPK_;
import nts.uk.ctx.sys.gateway.infra.entity.singlesignon.SgwmtOtherSysAcc_;

/**
 * The Class JpaOtherSysAccountRepository.
 */
@Stateless
public class JpaOtherSysAccountRepository extends JpaRepository implements OtherSysAccountRepository {

	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.gateway.dom.singlesignon.OtherSysAccountRepository#remove(java.lang.String)
	 */
	@Override
	public void remove(String userId, String companyCode, String userName) {
		SgwmtOtherSysAccPK pk = new SgwmtOtherSysAccPK(userId ,companyCode,  userName);

		if (pk != null) {
			this.commandProxy().remove(SgwmtOtherSysAcc.class, pk);

		}
		
		

	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.gateway.dom.singlesignon.OtherSysAccountRepository#add(nts.uk.ctx.sys.gateway.dom.singlesignon.OtherSysAccount)
	 */
	@Override
	public void add(OtherSysAccount otherSysAccount) {
		SgwmtOtherSysAcc entity = new SgwmtOtherSysAcc();
		otherSysAccount.saveToMemento(new JpaOtherSysAccountSetMemento(entity));
		this.commandProxy().insert(entity);

	}

	/**
	 * Find by company code and user name.
	 *
	 * @param companyCode the company code
	 * @param userName the user name
	 * @return the optional
	 */
	@Override
	public Optional<OtherSysAccount> findByCompanyCodeAndUserName(String companyCode, String userName) {
		// Get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder bd = em.getCriteriaBuilder();
		CriteriaQuery<SgwmtOtherSysAcc> cq = bd.createQuery(SgwmtOtherSysAcc.class);

		// Root
		Root<SgwmtOtherSysAcc> root = cq.from(SgwmtOtherSysAcc.class);
		cq.select(root);

		// Predicate where clause
		List<Predicate> predicateList = new ArrayList<>();
		predicateList.add(
				bd.equal(root.get(SgwmtOtherSysAcc_.sgwmtOtherSysAccPK).get(SgwmtOtherSysAccPK_.ccd), companyCode));
		predicateList.add(
				bd.equal(root.get(SgwmtOtherSysAcc_.sgwmtOtherSysAccPK).get(SgwmtOtherSysAccPK_.userName), userName));

		// Set Where clause to SQL Query
		cq.where(predicateList.toArray(new Predicate[] {}));

		// Create Query
		TypedQuery<SgwmtOtherSysAcc> query = em.createQuery(cq);

		// if (query.getSingleResult() != null) {
		// return Optional.of(this.toDomain(query.getSingleResult()));
		// }
		//
		// return Optional.empty();

		try {
			// exclude select
			return Optional.of(this.toDomain(query.getSingleResult()));
		} catch (NoResultException e) {
			return Optional.empty();
		}

	}

	/**
	 * To domain.
	 *
	 * @param entity the entity
	 * @return the other sys account
	 */
	private OtherSysAccount toDomain(SgwmtOtherSysAcc entity) {
		return new OtherSysAccount(new JpaOtherSysAccountGetMemento(entity));

	}

	@Override
	public Optional<OtherSysAccount> findByUserId(String userId) {
		
		// Get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder bd = em.getCriteriaBuilder();
		CriteriaQuery<SgwmtOtherSysAcc> cq = bd.createQuery(SgwmtOtherSysAcc.class);

		// Root
		Root<SgwmtOtherSysAcc> root = cq.from(SgwmtOtherSysAcc.class);
		cq.select(root);

		// Predicate where clause
		List<Predicate> predicateList = new ArrayList<>();
		predicateList
				.add(bd.equal(root.get(SgwmtOtherSysAcc_.sgwmtOtherSysAccPK).get(SgwmtOtherSysAccPK_.userId), userId));

		// Set Where clause to SQL Query
		cq.where(predicateList.toArray(new Predicate[] {}));

		// Create Query
		TypedQuery<SgwmtOtherSysAcc> query = em.createQuery(cq);

		try {
			// exclude select
			return Optional.of(this.toDomain(query.getSingleResult()));
		} catch (NoResultException e) {
			return Optional.empty();
		}

	}

}
