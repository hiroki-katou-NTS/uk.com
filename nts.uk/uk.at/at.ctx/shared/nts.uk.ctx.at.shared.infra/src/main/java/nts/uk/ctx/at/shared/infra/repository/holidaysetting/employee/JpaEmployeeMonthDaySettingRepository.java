/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.holidaysetting.employee;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.common.Year;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.employee.EmployeeMonthDaySetting;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.employee.EmployeeMonthDaySettingRepository;
import nts.uk.ctx.at.shared.infra.entity.holidaysetting.employee.KshmtEmployeeMonthDaySet;
import nts.uk.ctx.at.shared.infra.entity.holidaysetting.employee.KshmtEmployeeMonthDaySetPK_;
import nts.uk.ctx.at.shared.infra.entity.holidaysetting.employee.KshmtEmployeeMonthDaySet_;

/**
 * The Class JpaEmployeeMonthDaySettingRepository.
 */
@Stateless
public class JpaEmployeeMonthDaySettingRepository extends JpaRepository implements EmployeeMonthDaySettingRepository {

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.holidaysetting.employee.EmployeeMonthDaySettingRepository#findByYear(nts.uk.ctx.bs.employee.dom.common.CompanyId, java.lang.String, nts.uk.ctx.bs.employee.dom.holidaysetting.common.Year)
	 */
	@Override
	public Optional<EmployeeMonthDaySetting> findByYear(CompanyId companyId, String employee, Year year) {
		List<KshmtEmployeeMonthDaySet> result = this.findBy(companyId, employee, year, null);
		
		// Check exist
		if (result.isEmpty()) {
			return Optional.empty();
		}
		
		EmployeeMonthDaySetting domain = new EmployeeMonthDaySetting(new JpaEmployeeMonthDaySettingGetMemento(result));
			
		return Optional.of(domain);
	}
	
	/**
	 * Find all employee register.
	 *
	 * @return the list
	 */
	public List<String> findAllEmployeeRegister(CompanyId companyId, Year year) {
		List<KshmtEmployeeMonthDaySet> result = this.findBy(companyId, null, year, null);
		// Check exist
		if (result != null && !result.isEmpty()) {
			return result.stream()
								.map(obj -> obj.getKshmtEmployeeMonthDaySetPK().getSid())
								.distinct()
								.collect(Collectors.toList());
		}
		
		return new ArrayList<>();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.holidaysetting.employee.EmployeeMonthDaySettingRepository#add(nts.uk.ctx.bs.employee.dom.holidaysetting.employee.EmployeeMonthDaySetting)
	 */
	@Override
	public void add(EmployeeMonthDaySetting domain) {
		List<KshmtEmployeeMonthDaySet> entities = new ArrayList<>();
		domain.saveToMemento(new JpaEmployeeMonthDaySettingSetMemento(entities));
		this.commandProxy().insertAll(entities);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.holidaysetting.employee.EmployeeMonthDaySettingRepository#update(nts.uk.ctx.bs.employee.dom.holidaysetting.employee.EmployeeMonthDaySetting)
	 */
	@Override
	public void update(EmployeeMonthDaySetting domain) {
		List<KshmtEmployeeMonthDaySet> entities = this.findBy(domain.getCompanyId(), domain.getEmployeeId(),
				domain.getManagementYear(), null);
		domain.saveToMemento(new JpaEmployeeMonthDaySettingSetMemento(entities));
		this.commandProxy().updateAll(entities);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.holidaysetting.employee.EmployeeMonthDaySettingRepository#remove(nts.uk.ctx.bs.employee.dom.common.CompanyId, java.lang.String, nts.uk.ctx.bs.employee.dom.holidaysetting.common.Year)
	 */
	@Override
	public void remove(CompanyId companyId, String employee, Year year) {
		List<KshmtEmployeeMonthDaySet> result = this.findBy(companyId, employee, year, null);
		this.commandProxy().removeAll(result);
	}
	
	/**
	 * Find by.
	 *
	 * @param companyId the company id
	 * @param employee the employee
	 * @param year the year
	 * @param month the month
	 * @return the list
	 */
	private List<KshmtEmployeeMonthDaySet> findBy(CompanyId companyId, String employee, Year year, Integer month) {
		// get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		CriteriaQuery<KshmtEmployeeMonthDaySet> cq = criteriaBuilder.createQuery(KshmtEmployeeMonthDaySet.class);

		// root data
		Root<KshmtEmployeeMonthDaySet> root = cq.from(KshmtEmployeeMonthDaySet.class);

		// select root
		cq.select(root);

		// add where
		List<Predicate> lstpredicateWhere = new ArrayList<>();

		// eq company id
		if (companyId != null) {
			lstpredicateWhere
					.add(criteriaBuilder.equal(root.get(KshmtEmployeeMonthDaySet_.kshmtEmployeeMonthDaySetPK).get(KshmtEmployeeMonthDaySetPK_.cid), companyId.v()));
		}
		
		if (employee != null) {
			lstpredicateWhere.add(criteriaBuilder.equal(
					root.get(KshmtEmployeeMonthDaySet_.kshmtEmployeeMonthDaySetPK).get(KshmtEmployeeMonthDaySetPK_.sid),
					employee));
		}

		if (year != null) {
			lstpredicateWhere.add(criteriaBuilder.equal(
					root.get(KshmtEmployeeMonthDaySet_.kshmtEmployeeMonthDaySetPK).get(KshmtEmployeeMonthDaySetPK_.manageYear),
					year.v()));
		}

		if (month != null) {
			lstpredicateWhere
					.add(criteriaBuilder.equal(root.get(KshmtEmployeeMonthDaySet_.kshmtEmployeeMonthDaySetPK)
							.get(KshmtEmployeeMonthDaySetPK_.month), month.intValue()));
		}

		cq.orderBy(criteriaBuilder.asc(root.get(KshmtEmployeeMonthDaySet_.kshmtEmployeeMonthDaySetPK).get(KshmtEmployeeMonthDaySetPK_.month)));
		// set where to SQL
		cq.where(lstpredicateWhere.toArray(new Predicate[] {}));

		// creat query
		TypedQuery<KshmtEmployeeMonthDaySet> query = em.createQuery(cq);

		return query.getResultList();
	}
}
