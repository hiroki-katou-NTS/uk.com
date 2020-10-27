/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.infra.repository.login;

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
import nts.uk.ctx.sys.gateway.dom.login.EmployeeLoginSetting;
import nts.uk.ctx.sys.gateway.dom.login.EmployeeLoginSettingRepository;
import nts.uk.ctx.sys.gateway.infra.entity.login.SgwmtEmployeeLogin;
import nts.uk.ctx.sys.gateway.infra.entity.login.SgwmtEmployeeLogin_;

/**
 * The Class JpaEmployeeLoginSettingRepository.
 */
@Stateless
public class JpaEmployeeLoginSettingRepository extends JpaRepository implements EmployeeLoginSettingRepository {

	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.gateway.dom.login.EmployeeLoginSettingRepository#getByContractCode(java.lang.String)
	 */
	@Override
	public Optional<EmployeeLoginSetting> getByContractCode(String contractCode) {
		EntityManager em = this.getEntityManager();

		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<SgwmtEmployeeLogin> query = builder.createQuery(SgwmtEmployeeLogin.class);
		Root<SgwmtEmployeeLogin> root = query.from(SgwmtEmployeeLogin.class);

		List<Predicate> predicateList = new ArrayList<>();

		predicateList.add(builder.equal(root.get(SgwmtEmployeeLogin_.contractCd), contractCode));

		query.where(predicateList.toArray(new Predicate[] {}));

		List<SgwmtEmployeeLogin> result = em.createQuery(query).getResultList();
		//get single Employee login setting
		if (result.isEmpty()) {
			return Optional.empty();
		} else {
			return Optional.of(new EmployeeLoginSetting(new JpaEmployeeLoginSettingGetMemento(result.get(0))));
		}
	}
}
