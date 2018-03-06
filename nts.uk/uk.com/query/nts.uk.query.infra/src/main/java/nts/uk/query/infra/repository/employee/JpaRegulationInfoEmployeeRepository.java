/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.query.infra.repository.employee;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
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

	/** The Constant LEAVE_ABSENCE_QUOTA_NO. */
	private static final int LEAVE_ABSENCE_QUOTA_NO = 1;

	/** The Constant NOT_DELETED. */
	private static final int NOT_DELETED = 0;

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

		// Add company condition 
		conditions.add(cb.equal(root.get(EmployeeDataView_.cid), comId));

		// Add NOT_DELETED condition
		conditions.add(cb.equal(root.get(EmployeeDataView_.delStatusAtr), NOT_DELETED));

		// employment condition
		if (paramQuery.getFilterByEmployment()) {
			// return empty list if condition code list is empty
			if (employmentCodes.isEmpty()) {
				return Collections.emptyList();
			}

			// update query conditions
			conditions.add(root.get(EmployeeDataView_.empCd).in(employmentCodes));
			conditions
					.add(cb.lessThanOrEqualTo(root.get(EmployeeDataView_.employmentStrDate), paramQuery.getBaseDate()));
			conditions.add(
					cb.greaterThanOrEqualTo(root.get(EmployeeDataView_.employmentEndDate), paramQuery.getBaseDate()));
		}
		// workplace condition
		if (paramQuery.getFilterByWorkplace()) {
			// return empty list if condition code list is empty
			if (workplaceCodes.isEmpty()) {
				return Collections.emptyList();
			}

			// update query conditions
			conditions.add(root.get(EmployeeDataView_.workplaceId).in(workplaceCodes));
			conditions.add(cb.lessThanOrEqualTo(root.get(EmployeeDataView_.wplStrDate), paramQuery.getBaseDate()));
			conditions.add(cb.greaterThanOrEqualTo(root.get(EmployeeDataView_.wplEndDate), paramQuery.getBaseDate()));
			conditions.add(cb.lessThanOrEqualTo(root.get(EmployeeDataView_.wplInfoStrDate), paramQuery.getBaseDate()));
			conditions
					.add(cb.greaterThanOrEqualTo(root.get(EmployeeDataView_.wplInfoEndDate), paramQuery.getBaseDate()));
		}
		// classification condition
		if (paramQuery.getFilterByClassification()) {
			// return empty list if condition code list is empty
			if (classificationCodes.isEmpty()) {
				return Collections.emptyList();
			}

			// update query conditions
			conditions.add(root.get(EmployeeDataView_.classificationCode).in(classificationCodes));
			conditions.add(cb.lessThanOrEqualTo(root.get(EmployeeDataView_.classStrDate), paramQuery.getBaseDate()));
			conditions.add(cb.greaterThanOrEqualTo(root.get(EmployeeDataView_.classEndDate), paramQuery.getBaseDate()));
		}
		// jobtitle condition
		if (paramQuery.getFilterByJobTitle()) {
			// return empty list if condition code list is empty
			if (jobTitleCodes.isEmpty()) {
				return Collections.emptyList();
			}

			// update query conditions
			conditions.add(root.get(EmployeeDataView_.jobTitleId).in(jobTitleCodes));
			conditions.add(cb.lessThanOrEqualTo(root.get(EmployeeDataView_.jobStrDate), paramQuery.getBaseDate()));
			conditions.add(cb.greaterThanOrEqualTo(root.get(EmployeeDataView_.jobEndDate), paramQuery.getBaseDate()));
			conditions.add(cb.lessThanOrEqualTo(root.get(EmployeeDataView_.jobInfoStrDate), paramQuery.getBaseDate()));
			conditions
					.add(cb.greaterThanOrEqualTo(root.get(EmployeeDataView_.jobInfoEndDate), paramQuery.getBaseDate()));
		}
		if (paramQuery.getSystemType() == SystemType.EMPLOYMENT.value && paramQuery.getFilterByWorktype()) {
			// return empty list if condition code list is empty
			if (worktypeCodes.isEmpty()) {
				return Collections.emptyList();
			}

			// update query conditions
			conditions.add(root.get(EmployeeDataView_.workTypeCd).in(worktypeCodes));
			conditions.add(cb.lessThanOrEqualTo(root.get(EmployeeDataView_.workTypeStrDate),
					GeneralDate.localDate(paramQuery.getBaseDate().toLocalDate())));
			conditions.add(cb.greaterThanOrEqualTo(root.get(EmployeeDataView_.workTypeEndDate),
					GeneralDate.localDate(paramQuery.getBaseDate().toLocalDate())));
		}

		// status of employee conddition
		Predicate incumbentCondition = null;
		Predicate workerOnLeaveCondition = null;
		Predicate occupancyCondition = null;
		Predicate retireCondition = null;

		// include incumbents condition
		if (paramQuery.getIncludeIncumbents()) {
			// Out of absence range
			incumbentCondition = cb.or(
					cb.greaterThan(root.get(EmployeeDataView_.absStrDate), paramQuery.getPeriodEnd()),
					cb.lessThan(root.get(EmployeeDataView_.absEndDate), paramQuery.getPeriodStart()),
					cb.isNull(root.get(EmployeeDataView_.tempAbsFrameNo)));
		} else {
			// In absence range
			incumbentCondition = cb.or(
					cb.and(cb.lessThanOrEqualTo(root.get(EmployeeDataView_.absStrDate), paramQuery.getPeriodStart()),
							cb.greaterThanOrEqualTo(root.get(EmployeeDataView_.absEndDate),
									paramQuery.getPeriodStart())),
					cb.and(cb.lessThanOrEqualTo(root.get(EmployeeDataView_.absStrDate), paramQuery.getPeriodEnd()), cb
							.greaterThanOrEqualTo(root.get(EmployeeDataView_.absEndDate), paramQuery.getPeriodEnd())));
		}

		// include workers on leave condition
		if (paramQuery.getIncludeWorkersOnLeave()) {
			// In absence range and tempAbsFrameNo = LEAVE_ABSENCE_QUOTA_NO
			workerOnLeaveCondition = cb.and(cb.or(
					cb.and(cb.lessThanOrEqualTo(root.get(EmployeeDataView_.absStrDate), paramQuery.getPeriodStart()),
							cb.greaterThanOrEqualTo(root.get(EmployeeDataView_.absEndDate),
									paramQuery.getPeriodStart())),
					cb.and(cb.lessThanOrEqualTo(root.get(EmployeeDataView_.absStrDate), paramQuery.getPeriodEnd()),
							cb.greaterThanOrEqualTo(root.get(EmployeeDataView_.absEndDate), paramQuery.getPeriodEnd())),
					cb.equal(root.get(EmployeeDataView_.tempAbsFrameNo), LEAVE_ABSENCE_QUOTA_NO)));
		} else {
			// Out of absence range and tempAbsFrameNo = LEAVE_ABSENCE_QUOTA_NO
			workerOnLeaveCondition = cb.and(
					cb.or(cb.greaterThan(root.get(EmployeeDataView_.absStrDate), paramQuery.getPeriodEnd()),
							cb.lessThan(root.get(EmployeeDataView_.absEndDate), paramQuery.getPeriodStart())),
					cb.equal(root.get(EmployeeDataView_.tempAbsFrameNo), LEAVE_ABSENCE_QUOTA_NO));
		}

		// include occupancy condition
		if (paramQuery.getIncludeOccupancy()) {
			// In absence range and tempAbsFrameNo <> LEAVE_ABSENCE_QUOTA_NO
			occupancyCondition = cb.and(cb.or(
					cb.and(cb.lessThanOrEqualTo(root.get(EmployeeDataView_.absStrDate), paramQuery.getPeriodStart()),
							cb.greaterThanOrEqualTo(root.get(EmployeeDataView_.absEndDate),
									paramQuery.getPeriodStart())),
					cb.and(cb.lessThanOrEqualTo(root.get(EmployeeDataView_.absStrDate), paramQuery.getPeriodEnd()),
							cb.greaterThanOrEqualTo(root.get(EmployeeDataView_.absEndDate), paramQuery.getPeriodEnd())),
					cb.notEqual(root.get(EmployeeDataView_.tempAbsFrameNo), LEAVE_ABSENCE_QUOTA_NO)));
		} else {
			// Out of absence range and tempAbsFrameNo <> LEAVE_ABSENCE_QUOTA_NO
			occupancyCondition = cb.and(
					cb.or(cb.greaterThan(root.get(EmployeeDataView_.absStrDate), paramQuery.getPeriodEnd()),
							cb.lessThan(root.get(EmployeeDataView_.absEndDate), paramQuery.getPeriodStart())),
					cb.notEqual(root.get(EmployeeDataView_.tempAbsFrameNo), LEAVE_ABSENCE_QUOTA_NO));
		}

		// include retire condition
		if (paramQuery.getIncludeRetirees()) {
			// Retire start date < company end date
			retireCondition = cb.lessThan(root.get(EmployeeDataView_.comEndDate), paramQuery.getRetireStart());
		} else {
			// Retire start date in Company range or Retire end date in Company range
			retireCondition = cb.or(
					cb.and(cb.greaterThanOrEqualTo(root.get(EmployeeDataView_.comEndDate), paramQuery.getRetireStart()),
							cb.lessThanOrEqualTo(root.get(EmployeeDataView_.comStrDate), paramQuery.getRetireStart())),
					cb.and(cb.greaterThanOrEqualTo(root.get(EmployeeDataView_.comEndDate), paramQuery.getRetireEnd()),
							cb.lessThanOrEqualTo(root.get(EmployeeDataView_.comStrDate), paramQuery.getRetireEnd())));
		}

		// set condition
		Predicate statusOfEmployeeCondition = cb.or(incumbentCondition, workerOnLeaveCondition, occupancyCondition,
				retireCondition);
		conditions.add(statusOfEmployeeCondition);
		cq.where(conditions.toArray(new Predicate[] {}));

		// getSortConditions
		List<Order> orders = new ArrayList<>();
		List<BsymtEmpOrderCond> sortConditions = this.getSortConditions(comId, paramQuery.getSystemType(),
				paramQuery.getSortOrderNo());

		// sort by employee code
		orders.add(cb.asc(root.get(EmployeeDataView_.scd)));

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

		cq.orderBy(orders);

		// execute query & add to resultList
		resultList.addAll(em.createQuery(cq).getResultList());
		
		// Distinct employee in result list.
		resultList = resultList.stream().filter(this.distinctByKey(EmployeeDataView::getSid)).collect(Collectors.toList());

		return resultList.stream().map(entity -> RegulationInfoEmployee.builder()
				.classificationCode(Optional.ofNullable(entity.getClassificationCode()))
				.employeeCode(entity.getScd())
				.employeeID(entity.getSid())
				.employmentCode(Optional.ofNullable(entity.getEmpCd()))
				.hireDate(Optional.ofNullable(entity.getComStrDate()))
				.jobTitleCode(Optional.ofNullable(entity.getJobCd()))
				.name(Optional.ofNullable(entity.getBusinessName()))
				.workplaceId(Optional.ofNullable(entity.getWorkplaceId()))
				.workplaceCode(Optional.ofNullable(entity.getWplCd()))
				.workplaceName(Optional.ofNullable(entity.getWplName()))
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
	
	private <T> java.util.function.Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
	    Set<Object> seen = ConcurrentHashMap.newKeySet();
	    return t -> seen.add(keyExtractor.apply(t));
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
