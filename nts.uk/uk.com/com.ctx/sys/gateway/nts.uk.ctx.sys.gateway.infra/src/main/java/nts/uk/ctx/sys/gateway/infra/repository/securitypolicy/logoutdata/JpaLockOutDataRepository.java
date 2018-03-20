/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.infra.repository.securitypolicy.logoutdata;

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
import nts.uk.ctx.sys.gateway.dom.securitypolicy.lockoutdata.LockOutData;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.lockoutdata.LockOutDataRepository;
import nts.uk.ctx.sys.gateway.infra.entity.securitypolicy.logoutdata.SgwmtLogoutData;
import nts.uk.ctx.sys.gateway.infra.entity.securitypolicy.logoutdata.SgwmtLogoutDataPK_;
import nts.uk.ctx.sys.gateway.infra.entity.securitypolicy.logoutdata.SgwmtLogoutData_;

/**
 * The Class JpaLogoutDataRepository.
 */
@Stateless
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
		CriteriaQuery<SgwmtLogoutData> query = builder.createQuery(SgwmtLogoutData.class);
		Root<SgwmtLogoutData> root = query.from(SgwmtLogoutData.class);

		List<Predicate> predicateList = new ArrayList<>();

		predicateList.add(
				builder.equal(root.get(SgwmtLogoutData_.sgwmtLogoutDataPK).get(SgwmtLogoutDataPK_.userId), userId));

		query.where(predicateList.toArray(new Predicate[] {}));

		List<SgwmtLogoutData> result = em.createQuery(query).getResultList();

		if (result.isEmpty()) {
			return Optional.empty();
		} else {
			return Optional.of(new LockOutData(new JpaLockOutDataGetMemento(result.get(0))));
		}
	}

	@Override
	public void add(LockOutData lockOutData) {
		SgwmtLogoutData entity = new SgwmtLogoutData();
		lockOutData.saveToMemento(new JpaLockOutDataSetMemento(entity));
		this.commandProxy().insert(entity);
	}
}
