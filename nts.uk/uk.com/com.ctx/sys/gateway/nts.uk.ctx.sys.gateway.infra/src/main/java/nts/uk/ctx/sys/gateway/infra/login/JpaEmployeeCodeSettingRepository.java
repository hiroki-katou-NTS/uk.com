/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.infra.login;

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
import nts.uk.ctx.sys.gateway.dom.login.EmployeeCodeSetting;
import nts.uk.ctx.sys.gateway.dom.login.EmployeeCodeSettingRepository;
import nts.uk.ctx.sys.gateway.entity.login.SgwstEmployeeCodeSet;
import nts.uk.ctx.sys.gateway.entity.login.SgwstEmployeeCodeSet_;
@Stateless
public class JpaEmployeeCodeSettingRepository extends JpaRepository implements EmployeeCodeSettingRepository{

	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.gateway.dom.login.EmployeeCodeSettingRepository#getbyCompanyId(java.lang.String)
	 */
	@Override
	public Optional<EmployeeCodeSetting> getbyCompanyId(String companyId) {

		EntityManager em = this.getEntityManager();

		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<SgwstEmployeeCodeSet> query = builder.createQuery(SgwstEmployeeCodeSet.class);
		Root<SgwstEmployeeCodeSet> root = query.from(SgwstEmployeeCodeSet.class);

		List<Predicate> predicateList = new ArrayList<>();

		predicateList.add(builder.equal(root.get(SgwstEmployeeCodeSet_.cid), companyId));

		query.where(predicateList.toArray(new Predicate[] {}));

		List<SgwstEmployeeCodeSet> result = em.createQuery(query).getResultList();
		if (result.isEmpty()) {
			return Optional.empty();
		} else {
			return Optional.of(new EmployeeCodeSetting(new JpaEmployeeCodeSettingGetMemento(result.get(0))));
		}
	}

}
