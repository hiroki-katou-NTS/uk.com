/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.infra.repository.login;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.sys.gateway.dom.login.User;
import nts.uk.ctx.sys.gateway.dom.login.UserRepository;
import nts.uk.ctx.sys.gateway.infra.entity.login.SgwmtUser;
import nts.uk.ctx.sys.gateway.infra.entity.login.SgwmtUser_;

/**
 * The Class JpaUserRepository.
 */
@Stateless
public class JpaUserRepository extends JpaRepository implements UserRepository {

	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.gateway.dom.login.UserRepository#getByLoginId(java.lang.String)
	 */
	@Override
	public Optional<User> getByLoginId(String loginId) {
		EntityManager em = this.getEntityManager();

		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<SgwmtUser> query = builder.createQuery(SgwmtUser.class);
		Root<SgwmtUser> root = query.from(SgwmtUser.class);

		List<Predicate> predicateList = new ArrayList<>();

		predicateList.add(builder.equal(root.get(SgwmtUser_.loginId), loginId));

		query.where(predicateList.toArray(new Predicate[] {}));

		List<SgwmtUser> result = em.createQuery(query).getResultList();
		//get single user 
		if (result.isEmpty()) {
			return Optional.empty();
		} else {
			return Optional.of(new User(new JpaUserGetMemento(result.get(0))));
		}
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.gateway.dom.login.UserRepository#getByAssociatedPersonId(java.lang.String)
	 */
	@Override
	public Optional<User> getByAssociatedPersonId(String associatedPersonId) {
		EntityManager em = this.getEntityManager();

		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<SgwmtUser> query = builder.createQuery(SgwmtUser.class);
		Root<SgwmtUser> root = query.from(SgwmtUser.class);

		List<Predicate> predicateList = new ArrayList<>();

		predicateList.add(builder.equal(root.get(SgwmtUser_.assoSid), associatedPersonId));

		query.where(predicateList.toArray(new Predicate[] {}));

		List<SgwmtUser> result = em.createQuery(query).getResultList();
		//get single user 
		if (result.isEmpty()) {
			return Optional.empty();
		} else {
			return Optional.of(new User(new JpaUserGetMemento(result.get(0))));
		}
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.gateway.dom.login.UserRepository#addNewUser(nts.uk.ctx.sys.gateway.dom.login.User)
	 */
	@Override
	public void addNewUser(User user) {
		
		this.commandProxy().insert(toEntity(user));
		
	}
	
	private SgwmtUser toEntity(User user){
		
		short isSpecialUser =  (short) (user.isSpecialUser()?1:0);
		short isMultiCompanyConcurrent =  (short) (user.isMultiCompanyConcurrent()?1:0);
		return new SgwmtUser(user.getUserId(), user.getPassword().v(), user.getLoginId().v(), user.getContractCode().v(), 
				Date.valueOf(user.getExpirationDate().toString("yyyy-MM-dd")), isSpecialUser, isMultiCompanyConcurrent, user.getMailAddress().v(), user.getUserName().v(), user.getAssociatedPersonId());
	}

}
