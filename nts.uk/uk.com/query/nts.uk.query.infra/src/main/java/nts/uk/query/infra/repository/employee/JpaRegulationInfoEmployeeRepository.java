/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.query.infra.repository.employee;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.infra.entity.employee.order.BsymtEmpOrderCond;
import nts.uk.ctx.bs.employee.infra.entity.employee.order.BsymtEmployeeOrder;
import nts.uk.ctx.bs.employee.infra.entity.employee.order.BsymtEmployeeOrderPK;
import nts.uk.query.infra.entity.employee.EmployeeDataView;
import nts.uk.query.infra.entity.employee.EmployeeDataView_;
import nts.uk.query.model.employee.EmployeeSearchQuery;
import nts.uk.query.model.employee.RegulationInfoEmployee;
import nts.uk.query.model.employee.RegulationInfoEmployeeRepository;

/**
 * The Class JpaRegulationInfoEmployeeRepository.
 */
@Stateless
public class JpaRegulationInfoEmployeeRepository extends JpaRepository implements RegulationInfoEmployeeRepository {

	/** The Constant LIST_MAX_QUERY_IN. */
	private static final int LIST_MAX_QUERY_IN = 1000;

	/** The Constant LEAVE_ABSENCE_QUOTA_NO. */
	private static final int LEAVE_ABSENCE_QUOTA_NO = 1;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.query.model.EmployeeQueryModelRepository#find(nts.uk.query.model.
	 * EmployeeSearchQuery)
	 */
	@Override
	public List<RegulationInfoEmployee> find(String comId, EmployeeSearchQuery paramQuery) {
		EntityManager em = this.getEntityManager();

		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<EmployeeDataView> cq = cb.createQuery(EmployeeDataView.class);
		Root<EmployeeDataView> root = cq.from(EmployeeDataView.class);
		List<EmployeeDataView> resultList = new ArrayList<>();

		// Constructing condition.
		List<Predicate> conditions = new ArrayList<Predicate>();
		List<String> employmentCodes = paramQuery.getEmploymentCodes();
		List<String> workplaceCodes = paramQuery.getWorkplaceCodes();
		List<String> classificationCodes = paramQuery.getClassificationCodes();
		List<String> jobTitleCodes = paramQuery.getJobTitleCodes();
		List<String> worktypeCodes = paramQuery.getWorktypeCodes();

		// employment condition
		if (paramQuery.getFilterByEmployment()) {
			if (!employmentCodes.isEmpty()) {
				conditions.add(root.get(EmployeeDataView_.empCd).in(employmentCodes));
			}
			conditions
					.add(cb.lessThanOrEqualTo(root.get(EmployeeDataView_.employmentStrDate), paramQuery.getBaseDate()));
			conditions.add(
					cb.greaterThanOrEqualTo(root.get(EmployeeDataView_.employmentEndDate), paramQuery.getBaseDate()));
		}
		// workplace condition
		if (paramQuery.getFilterByWorkplace()) {
			if (!workplaceCodes.isEmpty()) {
				conditions.add(root.get(EmployeeDataView_.workplaceId).in(workplaceCodes));
			}
			conditions.add(cb.lessThanOrEqualTo(root.get(EmployeeDataView_.wplStrDate), paramQuery.getBaseDate()));
			conditions.add(cb.greaterThanOrEqualTo(root.get(EmployeeDataView_.wplEndDate), paramQuery.getBaseDate()));
			conditions.add(cb.lessThanOrEqualTo(root.get(EmployeeDataView_.wplInfoStrDate), paramQuery.getBaseDate()));
			conditions
					.add(cb.greaterThanOrEqualTo(root.get(EmployeeDataView_.wplInfoEndDate), paramQuery.getBaseDate()));
		}
		// classification condition
		if (paramQuery.getFilterByClassification()) {
			if (!classificationCodes.isEmpty()) {
				conditions.add(root.get(EmployeeDataView_.classificationCode).in(classificationCodes));
			}
			conditions.add(cb.lessThanOrEqualTo(root.get(EmployeeDataView_.classStrDate), paramQuery.getBaseDate()));
			conditions.add(cb.greaterThanOrEqualTo(root.get(EmployeeDataView_.classEndDate), paramQuery.getBaseDate()));
		}
		// jobtitle condition
		if (paramQuery.getFilterByJobTitle()) {
			if (!jobTitleCodes.isEmpty()) {
				conditions.add(root.get(EmployeeDataView_.jobTitleId).in(jobTitleCodes));
			}
			conditions.add(cb.lessThanOrEqualTo(root.get(EmployeeDataView_.jobStrDate), paramQuery.getBaseDate()));
			conditions.add(cb.greaterThanOrEqualTo(root.get(EmployeeDataView_.jobEndDate), paramQuery.getBaseDate()));
			conditions.add(cb.lessThanOrEqualTo(root.get(EmployeeDataView_.jobInfoStrDate), paramQuery.getBaseDate()));
			conditions
					.add(cb.greaterThanOrEqualTo(root.get(EmployeeDataView_.jobInfoEndDate), paramQuery.getBaseDate()));
		}
		if (paramQuery.getSystemType() == SystemType.EMPLOYMENT.value && paramQuery.getFilterByWorktype()) {
			if (!worktypeCodes.isEmpty()) {
				conditions.add(root.get(EmployeeDataView_.workTypeCd).in(worktypeCodes));
			}
			conditions.add(cb.lessThanOrEqualTo(root.get(EmployeeDataView_.workTypeStrDate),
					GeneralDate.localDate(paramQuery.getBaseDate().toLocalDate())));
			conditions.add(cb.greaterThanOrEqualTo(root.get(EmployeeDataView_.workTypeEndDate),
					GeneralDate.localDate(paramQuery.getBaseDate().toLocalDate())));
		}

		// status of employee conddition
		Predicate statusOfEmployeeCondition = cb.disjunction();
		Predicate incumbentCondition = cb.conjunction();
		Predicate workerOnLeaveCondition = cb.conjunction();
		Predicate occupancyCondition = cb.conjunction();
		Predicate retireCondition = cb.conjunction();

		// include incumbents condition
		if (paramQuery.getIncludeIncumbents()) {
			Predicate comDateCondition = cb.or(
					cb.lessThanOrEqualTo(root.get(EmployeeDataView_.comStrDate), paramQuery.getPeriodStart()),
					cb.greaterThanOrEqualTo(root.get(EmployeeDataView_.comEndDate), paramQuery.getPeriodStart()),
					cb.lessThanOrEqualTo(root.get(EmployeeDataView_.comStrDate), paramQuery.getPeriodEnd()),
					cb.greaterThanOrEqualTo(root.get(EmployeeDataView_.comEndDate), paramQuery.getPeriodEnd()));
			Predicate absDateCondition = cb.or(
					cb.lessThan(root.get(EmployeeDataView_.absStrDate), paramQuery.getPeriodEnd()),
					cb.greaterThan(root.get(EmployeeDataView_.absEndDate), paramQuery.getPeriodStart()));
			incumbentCondition = cb.and(comDateCondition, absDateCondition);
		}
		// include workers on leave condition
		if (paramQuery.getIncludeWorkersOnLeave()) {
			Predicate comDateCondition = cb.or(
					cb.lessThanOrEqualTo(root.get(EmployeeDataView_.comStrDate), paramQuery.getPeriodStart()),
					cb.greaterThanOrEqualTo(root.get(EmployeeDataView_.comEndDate), paramQuery.getPeriodStart()),
					cb.lessThanOrEqualTo(root.get(EmployeeDataView_.comStrDate), paramQuery.getPeriodEnd()),
					cb.greaterThanOrEqualTo(root.get(EmployeeDataView_.comEndDate), paramQuery.getPeriodEnd()));
			Predicate absDateCondition = cb.or(
					cb.lessThanOrEqualTo(root.get(EmployeeDataView_.absStrDate), paramQuery.getPeriodStart()),
					cb.greaterThanOrEqualTo(root.get(EmployeeDataView_.comEndDate), paramQuery.getPeriodStart()),
					cb.lessThanOrEqualTo(root.get(EmployeeDataView_.absStrDate), paramQuery.getPeriodEnd()),
					cb.greaterThanOrEqualTo(root.get(EmployeeDataView_.comEndDate), paramQuery.getPeriodEnd()));
			Predicate frameNoCondition = cb.equal(root.get(EmployeeDataView_.tempAbsFrameNo), LEAVE_ABSENCE_QUOTA_NO);
			workerOnLeaveCondition = cb.and(comDateCondition, absDateCondition, frameNoCondition);
		}
		// include occupancy condition
		if (paramQuery.getIncludeOccupancy()) {
			Predicate comDateCondition = cb.or(
					cb.lessThanOrEqualTo(root.get(EmployeeDataView_.comStrDate), paramQuery.getPeriodStart()),
					cb.greaterThanOrEqualTo(root.get(EmployeeDataView_.comEndDate), paramQuery.getPeriodStart()),
					cb.lessThanOrEqualTo(root.get(EmployeeDataView_.comStrDate), paramQuery.getPeriodEnd()),
					cb.greaterThanOrEqualTo(root.get(EmployeeDataView_.comEndDate), paramQuery.getPeriodEnd()));
			Predicate absDateCondition = cb.or(
					cb.lessThanOrEqualTo(root.get(EmployeeDataView_.absStrDate), paramQuery.getPeriodStart()),
					cb.greaterThanOrEqualTo(root.get(EmployeeDataView_.comEndDate), paramQuery.getPeriodStart()),
					cb.lessThanOrEqualTo(root.get(EmployeeDataView_.absStrDate), paramQuery.getPeriodEnd()),
					cb.greaterThanOrEqualTo(root.get(EmployeeDataView_.comEndDate), paramQuery.getPeriodEnd()));
			Predicate frameNoCondition = cb.notEqual(root.get(EmployeeDataView_.tempAbsFrameNo),
					LEAVE_ABSENCE_QUOTA_NO);
			occupancyCondition = cb.and(comDateCondition, absDateCondition, frameNoCondition);

		}
		// include retire condition
		if (paramQuery.getIncludeRetirees()) {
			retireCondition = cb.greaterThan(root.get(EmployeeDataView_.comEndDate), paramQuery.getRetireStart());
		}

		// set condition
		statusOfEmployeeCondition = cb.or(incumbentCondition, workerOnLeaveCondition, occupancyCondition,
				retireCondition);
		conditions.add(statusOfEmployeeCondition);
		cq.where(conditions.toArray(new Predicate[] {}));

		// getSortConditions
		List<Order> orders = new ArrayList<>();
		List<BsymtEmpOrderCond> sortConditions = this.getSortConditions(comId, paramQuery.getSystemType(),
				paramQuery.getSortOrderNo());

		sortConditions.forEach(cond -> {
			switch (cond.getId().getSearchType()) {
			case 0: // EMPLOYMENT
				orders.add(cb.asc(root.get(EmployeeDataView_.empCd)));
				break;
			case 1: // DEPARTMENT
				break;
			case 2: // WORKPLACE
				orders.add(cb.asc(root.get(EmployeeDataView_.wplCd)));
				break;
			case 3: // CLASSIFICATION
				orders.add(cb.asc(root.get(EmployeeDataView_.classificationCode)));
				break;
			case 4: // POSITION
				// TODO sort by posistion order?
				orders.add(cb.asc(root.get(EmployeeDataView_.jobCd)));
				break;
			case 5: // HIRE_DATE
				orders.add(cb.asc(root.get(EmployeeDataView_.comStrDate)));
				break;
			case 6: // NAME
				orders.add(cb.asc(root.get(EmployeeDataView_.personNameKana)));
				break;
			}
		});

		// sort by worktype code
		if (paramQuery.getSystemType() == SystemType.EMPLOYMENT.value) {
			orders.add(cb.asc(root.get(EmployeeDataView_.workTypeCd)));
		}

		// sort by employee code
		orders.add(cb.asc(root.get(EmployeeDataView_.scd)));
		cq.orderBy(orders);

		// execute query & add to resultList
		resultList.addAll(em.createQuery(cq).getResultList());

		return resultList.stream().map(entity -> RegulationInfoEmployee.builder()
				.classificationCode(Optional.ofNullable(entity.getClassificationCode())).employeeCode(entity.getScd())
				.employeeID(entity.getSid()).employmentCode(Optional.ofNullable(entity.getEmpCd()))
				.hireDate(Optional.of(entity.getComStrDate())).jobTitleCode(Optional.ofNullable(entity.getJobCd()))
				.name(Optional.of(entity.getBusinessName())).workplaceCode(Optional.ofNullable(entity.getWplCd()))
				.build()).collect(Collectors.toList());
	}

	/**
	 * Gets the sort conditions.
	 *
	 * @param comId the com id
	 * @param systemType the system type
	 * @param sortOrderNo the sort order no
	 * @return the sort conditions
	 */
	private List<BsymtEmpOrderCond> getSortConditions(String comId, int systemType, int sortOrderNo) {
		BsymtEmployeeOrderPK pk = new BsymtEmployeeOrderPK(comId, sortOrderNo, systemType);
		Optional<BsymtEmployeeOrder> empOrder = this.queryProxy().find(pk, BsymtEmployeeOrder.class);
		return empOrder.isPresent() ? empOrder.get().getLstBsymtEmpOrderCond() : Collections.emptyList();
	}

	/**
	 * The Enum SystemType.
	 */
	public enum SystemType {

		/** The personal information. */
		// システム管理者
		PERSONAL_INFORMATION(1),

		/** The employment. */
		// 就業
		EMPLOYMENT(2),

		/** The salary. */
		// 給与
		SALARY(3),

		/** The human resources. */
		// 人事
		HUMAN_RESOURCES(4),

		/** The administrator. */
		// 管理者
		ADMINISTRATOR(5);

		/** The value. */
		public final int value;

		/** The Constant values. */
		private final static SystemType[] values = SystemType.values();

		/**
		 * Instantiates a new system type.
		 *
		 * @param value the value
		 */
		private SystemType(int value) {
			this.value = value;
		}

		/**
		 * Value of.
		 *
		 * @param value the value
		 * @return the system type
		 */
		public static SystemType valueOf(Integer value) {
			// Invalid object.
			if (value == null) {
				return null;
			}

			// Find value.
			for (SystemType val : SystemType.values) {
				if (val.value == value) {
					return val;
				}
			}
			// Not found.
			return null;
		}
	}

}
