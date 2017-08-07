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
import nts.uk.ctx.sys.gateway.dom.login.Employee;
import nts.uk.ctx.sys.gateway.dom.login.GateWayEmployeeRepository;
import nts.uk.ctx.sys.gateway.entity.login.SgwdtEmployee;

@Stateless
public class JpaGateWayEmployeeRepository extends JpaRepository implements GateWayEmployeeRepository {

	@Override
	public Optional<Employee> getByEmployeeCode(String companyId, String employeeCode) {
		EntityManager em = this.getEntityManager();

		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<SgwdtEmployee> query = builder.createQuery(SgwdtEmployee.class);
		Root<SgwdtEmployee> root = query.from(SgwdtEmployee.class);

		List<Predicate> predicateList = new ArrayList<>();

//		predicateList.add(builder.equal(root.get(SgwdtEmployee_.sgwdtEmployeePK).get(SgwdtEmployeePK_.cid), companyId));
//		predicateList.add(builder.equal(root.get(SgwdtEmployee_.scd), employeeCode));
		query.where(predicateList.toArray(new Predicate[] {}));

		List<SgwdtEmployee> result = em.createQuery(query).getResultList();
		if (result.isEmpty()) {
			return Optional.empty();
		} else {
			return Optional.of(new Employee(new JpaEmployeeGetMemento(result.get(0))));
		}
	}

}
