/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.auth.infra.repository.password.changelog;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.sys.auth.dom.password.changelog.PasswordChangeLog;
import nts.uk.ctx.sys.auth.dom.password.changelog.PasswordChangeLogRepository;
import nts.uk.ctx.sys.auth.infra.entity.password.changelog.SacdtPasswordChangeLog;
import nts.uk.ctx.sys.auth.infra.entity.password.changelog.SacdtPasswordChangeLogPK;
import nts.uk.ctx.sys.auth.infra.entity.password.changelog.SacdtPasswordChangeLogPK_;
import nts.uk.ctx.sys.auth.infra.entity.password.changelog.SacdtPasswordChangeLog_;

/**
 * The Class JpaPasswordChangeLogRepository.
 */
@Stateless
public class JpaPasswordChangeLogRepository extends JpaRepository implements PasswordChangeLogRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.sys.auth.dom.password.changelog.PasswordChangeLogRepository#
	 * register(nts.uk.ctx.sys.auth.dom.password.changelog.PasswordChangeLog)
	 */
	@Override
	public void add(PasswordChangeLog passwordChangeLog) {
		SacdtPasswordChangeLog entity = new SacdtPasswordChangeLog(new SacdtPasswordChangeLogPK());
		passwordChangeLog.saveToMemento(new JpaPasswordChangeLogSetMemento(entity));
		this.commandProxy().insert(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.sys.auth.dom.password.changelog.PasswordChangeLogRepository#
	 * findByUserId(java.lang.String, int)
	 */
	@Override
	public List<PasswordChangeLog> findByUserId(String userID, int limitNumber) {
		List<PasswordChangeLog> listResult = new ArrayList<>();
		// Get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<SacdtPasswordChangeLog> cq = criteriaBuilder.createQuery(SacdtPasswordChangeLog.class);
		Root<SacdtPasswordChangeLog> root = cq.from(SacdtPasswordChangeLog.class);
		
		// Build query
		cq.select(root);
		
		// Add where conditions
		List<Predicate> lstpredicateWhere = new ArrayList<>();
		lstpredicateWhere.add(criteriaBuilder.equal(root.get(SacdtPasswordChangeLog_.sacdtPasswordChangeLogPK).get(SacdtPasswordChangeLogPK_.userId), userID));
		cq.where(lstpredicateWhere.toArray(new Predicate[] {}));
		cq.orderBy(criteriaBuilder.desc(root.get(SacdtPasswordChangeLog_.sacdtPasswordChangeLogPK).get(SacdtPasswordChangeLogPK_.modifiedDatetime)));

		List<SacdtPasswordChangeLog> listEntity = em.createQuery(cq)
				.setMaxResults(limitNumber)
				.getResultList();

		// Check exist
		if (!CollectionUtil.isEmpty(listEntity)) {
			listResult = listEntity.stream()
					.map(entity -> new PasswordChangeLog(new JpaPasswordChangeLogGetMemento(entity)))
					.collect(Collectors.toList());
		}
		// Return
		return listResult;
	}

}
