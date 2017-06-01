/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.infra.repository.company.organization.employee;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.basic.dom.company.organization.employee.Employee;
import nts.uk.ctx.basic.dom.company.organization.employee.EmployeeRepository;
import nts.uk.ctx.basic.infra.entity.company.organization.employee.CemptEmployee;
import nts.uk.ctx.basic.infra.entity.company.organization.employee.CemptEmployeePK_;
import nts.uk.ctx.basic.infra.entity.company.organization.employee.CemptEmployee_;

/**
 * The Class JpaEmployeeRepository.
 */
@Stateless
public class JpaEmployeeRepository extends JpaRepository implements EmployeeRepository{

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.basic.dom.company.organization.employee.EmployeeRepository#add
	 * (nts.uk.ctx.basic.dom.company.organization.employee.Employee)
	 */
	@Override
	public void add(Employee employee) {
		this.commandProxy().insert(this.toEntity(employee));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.basic.dom.company.organization.employee.EmployeeRepository#
	 * update(nts.uk.ctx.basic.dom.company.organization.employee.Employee)
	 */
	@Override
	public void update(Employee employee) {
		this.commandProxy().update(this.toEntity(employee));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.basic.dom.company.organization.employee.EmployeeRepository#
	 * findAll()
	 */
	@Override
	public List<Employee> findAll(String companyId) {
		
		// get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		// call CEMPT_EMPLOYEE (CemptEmployee SQL)
		CriteriaQuery<CemptEmployee> cq = criteriaBuilder.createQuery(CemptEmployee.class);

		// root data
		Root<CemptEmployee> root = cq.from(CemptEmployee.class);

		// select root
		cq.select(root);

		// add where
		List<Predicate> lstpredicateWhere = new ArrayList<>();

		// equal company id
		lstpredicateWhere
			.add(criteriaBuilder.equal(root.get(CemptEmployee_.cemptEmployeePK)
				.get(CemptEmployeePK_.ccid), companyId));
		// set where to SQL
		cq.where(lstpredicateWhere.toArray(new Predicate[] {}));

		// create query
		TypedQuery<CemptEmployee> query = em.createQuery(cq);

		// exclude select
		return query.getResultList().stream().map(category -> toDomain(category))
			.collect(Collectors.toList());
	}
	
	/**
	 * To domain.
	 *
	 * @param entity the entity
	 * @return the employee
	 */
	private Employee toDomain(CemptEmployee entity){
		return new Employee(new JpaEmployeeGetMemento(entity));
	}
	
	
	/**
	 * To entity.
	 *
	 * @param domain the domain
	 * @return the cempt employee
	 */
	private CemptEmployee toEntity(Employee domain){
		CemptEmployee entity = new CemptEmployee();
		domain.saveToMemento(new JpaEmployeeSetMemento(entity));
		return entity;
	}

}
