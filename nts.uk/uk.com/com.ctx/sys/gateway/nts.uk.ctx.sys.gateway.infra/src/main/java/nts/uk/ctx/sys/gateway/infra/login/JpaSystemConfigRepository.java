/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.infra.login;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.sys.gateway.dom.login.SystemConfig;
import nts.uk.ctx.sys.gateway.dom.login.SystemConfigRepository;
import nts.uk.ctx.sys.gateway.entity.login.SgwstSystemConfig;

/**
 * The Class JpaSystemConfigRepository.
 */
@Stateless
public class JpaSystemConfigRepository extends JpaRepository implements SystemConfigRepository {

	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.gateway.dom.login.SystemConfigRepository#getSystemConfig()
	 */
	@Override
	public Optional<SystemConfig> getSystemConfig() {

		// SgwstSystemConfig result = this.queryProxy().find(null,
		// SgwstSystemConfig.class).get();
		// get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		CriteriaQuery<SgwstSystemConfig> cq = criteriaBuilder.createQuery(SgwstSystemConfig.class);

		// root data
		Root<SgwstSystemConfig> root = cq.from(SgwstSystemConfig.class);

		// select root
		cq.select(root);

		SgwstSystemConfig result = em.createQuery(cq).getSingleResult();
		if (result == null) {
			return Optional.empty();
		} else {
			return Optional.of(new SystemConfig(new JpaSystemConfigGetMemento(result)));
		}
	}

}
