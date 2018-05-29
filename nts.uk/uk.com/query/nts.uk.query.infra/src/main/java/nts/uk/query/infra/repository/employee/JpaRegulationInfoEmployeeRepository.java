/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.query.infra.repository.employee;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
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
import nts.arc.time.GeneralDateTime;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.bs.employee.infra.entity.employee.mngdata.BsymtEmployeeDataMngInfo;
import nts.uk.ctx.bs.employee.infra.entity.employee.order.BsymtEmpOrderCond;
import nts.uk.ctx.bs.employee.infra.entity.employee.order.BsymtEmployeeOrder;
import nts.uk.ctx.bs.employee.infra.entity.employee.order.BsymtEmployeeOrderPK;
import nts.uk.ctx.bs.person.infra.entity.person.info.BpsmtPerson;
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
	public static final int LEAVE_ABSENCE_QUOTA_NO = 1;

	/** The Constant NOT_DELETED. */
	private static final int NOT_DELETED = 0;

	/** The Constant MAX_WHERE_IN. */
	private static final int MAX_WHERE_IN = 1000;

	private static final String FIND_EMPLOYEE = "SELECT e, p"
			+ " FROM BsymtEmployeeDataMngInfo e"
			+ " LEFT JOIN BpsmtPerson p ON p.bpsmtPersonPk.pId = e.bsymtEmployeeDataMngInfoPk.pId"
			+ " WHERE e.bsymtEmployeeDataMngInfoPk.sId IN :listSid";

	private static final String FIND_WORKPLACE = "SELECT awh.sid, wi.wkpcd, wi.bsymtWorkplaceInfoPK.wkpid, wi.wkpName"
			+ " FROM BsymtAffiWorkplaceHist awh"
			+ " LEFT JOIN BsymtAffiWorkplaceHistItem awhi ON awhi.hisId = awh.hisId"
			+ " LEFT JOIN BsymtWorkplaceHist wh ON wh.bsymtWorkplaceHistPK.wkpid = awhi.workPlaceId"
			+ " LEFT JOIN BsymtWorkplaceInfo wi ON wi.bsymtWorkplaceInfoPK.historyId = wh.bsymtWorkplaceHistPK.historyId"
			+ " AND wi.bsymtWorkplaceInfoPK.wkpid = wh.bsymtWorkplaceHistPK.wkpid"
			+ " WHERE awh.sid IN :listSid"
			+ " AND awh.strDate <= :refDate"
			+ " AND awh.endDate >= :refDate"
			+ " AND wh.strD <= :refDate"
			+ " AND wh.endD >= :refDate";

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.query.model.EmployeeQueryModelRepository#find(nts.uk.query.model.
	 * EmployeeSearchQuery)
	 */
	@Override
	public List<RegulationInfoEmployee> find(String comId, EmployeeSearchQuery paramQuery) {
		// Return empty list if all status of employee = 対象外
		if (!paramQuery.getIncludeIncumbents() && !paramQuery.getIncludeOccupancy() && !paramQuery.getIncludeRetirees()
				&& !paramQuery.getIncludeWorkersOnLeave()) {
			return Collections.emptyList();
		}
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
		List<Integer> closureIds = paramQuery.getClosureIds();

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
			conditions.add(cb.lessThanOrEqualTo(root.get(EmployeeDataView_.wkpConfStrDate), paramQuery.getBaseDate()));
			conditions
					.add(cb.greaterThanOrEqualTo(root.get(EmployeeDataView_.wkpConfEndDate), paramQuery.getBaseDate()));
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
		if (paramQuery.getSystemType() == SystemType.EMPLOYMENT.value) {
			if (paramQuery.getFilterByWorktype()) {
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
			if (paramQuery.getFilterByClosure()) {
				// return empty list if condition code list is empty
				if (closureIds.isEmpty()) {
					return Collections.emptyList();
				}

				// update query conditions
				conditions.add(root.get(EmployeeDataView_.closureId).in(closureIds));

				// check exist before add employment conditions
				if (!paramQuery.getFilterByEmployment()) {
					conditions.add(cb.lessThanOrEqualTo(root.get(EmployeeDataView_.employmentStrDate),
							paramQuery.getBaseDate()));
					conditions.add(cb.greaterThanOrEqualTo(root.get(EmployeeDataView_.employmentEndDate),
							paramQuery.getBaseDate()));
				}
			}
		}
		cq.where(conditions.toArray(new Predicate[] {}));

		// getSortConditions
		List<BsymtEmpOrderCond> sortConditions = this.getSortConditions(comId, paramQuery.getSystemType(),
				paramQuery.getSortOrderNo());

		// sort
		List<Order> orders = this.getOrders(paramQuery.getSystemType(), 1, sortConditions); // TODO: fixed name type
		cq.orderBy(orders);

		// execute query & add to resultList
		resultList.addAll(em.createQuery(cq).getResultList());

		// Filter result list by status of employee
		resultList = resultList.stream().filter(item -> item.isFiltered(paramQuery)).collect(Collectors.toList());

		// Distinct employee in result list.
		resultList = resultList.stream().filter(this.distinctByKey(EmployeeDataView::getSid))
				.collect(Collectors.toList());

		return resultList.stream().map(entity -> RegulationInfoEmployee.builder()
				.classificationCode(Optional.ofNullable(entity.getClassificationCode())).employeeCode(entity.getScd())
				.employeeID(entity.getSid()).employmentCode(Optional.ofNullable(entity.getEmpCd()))
				.hireDate(Optional.ofNullable(entity.getComStrDate()))
				.jobTitleCode(Optional.ofNullable(entity.getJobCd()))
				.name(Optional.ofNullable(entity.getBusinessName()))
				.workplaceId(Optional.ofNullable(entity.getWorkplaceId()))
				.workplaceHierarchyCode(Optional.ofNullable(entity.getWplHierarchyCode()))
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
	private List<BsymtEmpOrderCond> getSortConditions(String comId, Integer systemType, Integer sortOrderNo) {
		if (sortOrderNo == null) {
			return Collections.emptyList();
		}
		BsymtEmployeeOrderPK pk = new BsymtEmployeeOrderPK(comId, sortOrderNo, systemType);
		Optional<BsymtEmployeeOrder> empOrder = this.queryProxy().find(pk, BsymtEmployeeOrder.class);
		return empOrder.isPresent() ? empOrder.get().getLstBsymtEmpOrderCond() : Collections.emptyList();
	}
	
	/**
	 * Distinct by key.
	 *
	 * @param <T> the generic type
	 * @param keyExtractor the key extractor
	 * @return the java.util.function. predicate
	 */
	private <T> java.util.function.Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
	    Set<Object> seen = ConcurrentHashMap.newKeySet();
	    return t -> seen.add(keyExtractor.apply(t));
	}

	/**
	 * Gets the orders.
	 *
	 * @param systemType the system type
	 * @param nameType the name type
	 * @param sortConditions the sort conditions
	 * @return the orders
	 */
	private List<Order> getOrders(Integer systemType, Integer nameType, List<BsymtEmpOrderCond> sortConditions) {
		CriteriaBuilder cb = this.getEntityManager().getCriteriaBuilder();
		CriteriaQuery<EmployeeDataView> cq = cb.createQuery(EmployeeDataView.class);
		Root<EmployeeDataView> root = cq.from(EmployeeDataView.class);

		List<Order> orders = new ArrayList<>();
		// always sort by employee code
		orders.add(cb.asc(root.get(EmployeeDataView_.scd)));

		sortConditions.forEach(cond -> {
			switch (cond.getId().getSearchType()) {
			case 0: // EMPLOYMENT
				orders.add(cb.asc(root.get(EmployeeDataView_.empCd)));
				break;
			case 1: // DEPARTMENT
				break;
			case 2: // WORKPLACE
				orders.add(cb.asc(root.get(EmployeeDataView_.wplHierarchyCode)));
				break;
			case 3: // CLASSIFICATION
				orders.add(cb.asc(root.get(EmployeeDataView_.classificationCode)));
				break;
			case 4: // POSITION
				orders.add(cb.asc(root.get(EmployeeDataView_.jobSeqDisp)));
				orders.add(cb.asc(root.get(EmployeeDataView_.jobCd)));
				break;
			case 5: // HIRE_DATE
				orders.add(cb.asc(root.get(EmployeeDataView_.comStrDate)));
				break;
			case 6: // NAME
				if (nameType == 1) {
					orders.add(cb.asc(root.get(EmployeeDataView_.personNameKana)));
				} else {
					orders.add(cb.asc(root.get(EmployeeDataView_.businessNameKana)));
				}
				break;
			}
		});

		// sort by worktype code
		if (systemType == SystemType.EMPLOYMENT.value) {
			orders.add(cb.asc(root.get(EmployeeDataView_.workTypeCd)));
		}
		return orders;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.query.model.employee.RegulationInfoEmployeeRepository#
	 * findInfoBySIds(java.util.List)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<RegulationInfoEmployee> findInfoBySIds(List<String> sIds, GeneralDate referenceDate) {
		List<Object[]> persons = new ArrayList<>();

		CollectionUtil.split(sIds, MAX_WHERE_IN, (subList) -> {
			persons.addAll(this.getEntityManager().createQuery(FIND_EMPLOYEE).setParameter("listSid", subList)
					.getResultList());
		});

		// get list employee
		Map<String, RegulationInfoEmployee> employeeInfoList = persons.stream().map(item -> {
			BsymtEmployeeDataMngInfo e = (BsymtEmployeeDataMngInfo) item[0];
			BpsmtPerson p = (BpsmtPerson) item[1];

			return RegulationInfoEmployee.builder()
					.employeeID(e.bsymtEmployeeDataMngInfoPk.sId)
					.employeeCode(e.employeeCode)
					.name(Optional.ofNullable(p.businessName))
					.classificationCode(Optional.empty())
					.departmentCode(Optional.empty())
					.employmentCode(Optional.empty())
					.hireDate(Optional.empty())
					.jobTitleCode(Optional.empty())
					.workplaceHierarchyCode(Optional.empty())
					.workplaceCode(Optional.empty())
					.workplaceId(Optional.empty())
					.workplaceName(Optional.empty())
					.build();
		}).collect(Collectors.toMap(RegulationInfoEmployee::getEmployeeID, v -> v, (oldValue, newValue) -> newValue));

		// Get workplace
		List<Object[]> workplaces = new ArrayList<>();
		CollectionUtil.split(sIds, MAX_WHERE_IN, (subList) -> {
			workplaces.addAll(this.getEntityManager().createQuery(FIND_WORKPLACE).setParameter("listSid", subList)
					.setParameter("refDate", referenceDate).getResultList());
		});

		// set workplace
		employeeInfoList.keySet().forEach(empId -> {
			Optional<Object[]> workplace = workplaces.stream().filter(wpl -> {
				String id = (String) wpl[0];
				return id.equals(empId);
			}).findAny();

			if (workplace.isPresent()) {
				String workplaceCode = (String) workplace.get()[1];
				String workplaceId = (String) workplace.get()[2];
				String workplaceName = (String) workplace.get()[3];

				RegulationInfoEmployee employee = employeeInfoList.get(empId); 
				employee.setWorkplaceCode(Optional.ofNullable(workplaceCode));
				employee.setWorkplaceId(Optional.ofNullable(workplaceId));
				employee.setWorkplaceName(Optional.ofNullable(workplaceName));
			}
		});

		return employeeInfoList.values().stream().collect(Collectors.toList());
	}
	
	/* (non-Javadoc)
	 * @see nts.uk.query.model.employee.RegulationInfoEmployeeRepository
	 * #findBySid(java.lang.String, java.lang.String, nts.arc.time.GeneralDateTime)
	 */
	@Override
	public RegulationInfoEmployee findBySid(String comId, String sid, GeneralDateTime baseDate) {
		CriteriaBuilder cb = this.getEntityManager().getCriteriaBuilder();
		CriteriaQuery<EmployeeDataView> cq = cb.createQuery(EmployeeDataView.class);
		Root<EmployeeDataView> root = cq.from(EmployeeDataView.class);
		
		// Constructing condition.
		List<Predicate> conditions = new ArrayList<Predicate>();

		// Add company condition
		conditions.add(cb.equal(root.get(EmployeeDataView_.cid), comId));

		// Add NOT_DELETED condition
		conditions.add(cb.equal(root.get(EmployeeDataView_.delStatusAtr), NOT_DELETED));
		
		// Where SID.
		conditions.add(cb.equal(root.get(EmployeeDataView_.sid), sid));
		
		// Where base date.
		conditions.add(cb.lessThanOrEqualTo(root.get(EmployeeDataView_.wplStrDate), baseDate));
		conditions.add(cb.greaterThanOrEqualTo(root.get(EmployeeDataView_.wplEndDate), baseDate));
		
		// Find fist.
		cq.where(conditions.toArray(new Predicate[] {}));
		List<EmployeeDataView> res = this.getEntityManager().createQuery(cq).getResultList();
		EmployeeDataView entity = res.get(0);
		
		// Convert.
		return RegulationInfoEmployee.builder()
				.classificationCode(Optional.ofNullable(entity.getClassificationCode())).employeeCode(entity.getScd())
				.employeeID(entity.getSid()).employmentCode(Optional.ofNullable(entity.getEmpCd()))
				.hireDate(Optional.ofNullable(entity.getComStrDate()))
				.jobTitleCode(Optional.ofNullable(entity.getJobCd()))
				.name(Optional.ofNullable(entity.getBusinessName()))
				.workplaceId(Optional.ofNullable(entity.getWorkplaceId()))
				.workplaceHierarchyCode(Optional.ofNullable(entity.getWplHierarchyCode()))
				.workplaceCode(Optional.ofNullable(entity.getWplCd()))
				.workplaceName(Optional.ofNullable(entity.getWplName()))
				.build();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.query.model.employee.RegulationInfoEmployeeRepository#
	 * sortEmployees(java.lang.String, java.util.List, java.lang.Integer,
	 * java.lang.Integer, java.lang.Integer, nts.arc.time.GeneralDateTime)
	 */
	@Override
	public List<String> sortEmployees(String comId, List<String> sIds, Integer systemType, Integer orderNo,
			Integer nameType, GeneralDateTime referenceDate) {
		if (sIds.isEmpty()) {
			return Collections.emptyList();
		}
		EntityManager em = this.getEntityManager();

		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<EmployeeDataView> cq = cb.createQuery(EmployeeDataView.class);
		Root<EmployeeDataView> root = cq.from(EmployeeDataView.class);
		List<EmployeeDataView> resultList = new ArrayList<>();

		List<Predicate> conditions = new ArrayList<Predicate>();

		// company id
		conditions.add(cb.equal(root.get(EmployeeDataView_.cid), comId));
		// employee id
		conditions.add(root.get(EmployeeDataView_.sid).in(sIds));
		// employment
		conditions.add(cb.lessThanOrEqualTo(root.get(EmployeeDataView_.employmentStrDate), referenceDate));
		conditions.add(cb.greaterThanOrEqualTo(root.get(EmployeeDataView_.employmentEndDate), referenceDate));
		// workplace
		conditions.add(cb.lessThanOrEqualTo(root.get(EmployeeDataView_.wplStrDate), referenceDate));
		conditions.add(cb.greaterThanOrEqualTo(root.get(EmployeeDataView_.wplEndDate), referenceDate));
		conditions.add(cb.lessThanOrEqualTo(root.get(EmployeeDataView_.wkpConfStrDate), referenceDate));
		conditions.add(cb.greaterThanOrEqualTo(root.get(EmployeeDataView_.wkpConfEndDate), referenceDate));
		// classification
		conditions.add(cb.lessThanOrEqualTo(root.get(EmployeeDataView_.classStrDate), referenceDate));
		conditions.add(cb.greaterThanOrEqualTo(root.get(EmployeeDataView_.classEndDate), referenceDate));
		// job title
		conditions.add(cb.lessThanOrEqualTo(root.get(EmployeeDataView_.jobStrDate), referenceDate));
		conditions.add(cb.greaterThanOrEqualTo(root.get(EmployeeDataView_.jobEndDate), referenceDate));
		conditions.add(cb.lessThanOrEqualTo(root.get(EmployeeDataView_.jobInfoStrDate), referenceDate));
		conditions.add(cb.greaterThanOrEqualTo(root.get(EmployeeDataView_.jobInfoEndDate), referenceDate));

		cq.where(conditions.toArray(new Predicate[] {}));

		// getSortConditions
		List<BsymtEmpOrderCond> sortConditions = this.getSortConditions(comId, systemType, orderNo);

		// sort
		List<Order> orders = this.getOrders(systemType, nameType, sortConditions);
		cq.orderBy(orders);

		// execute query & add to resultList
		resultList.addAll(em.createQuery(cq).getResultList());

		return resultList.stream().map(EmployeeDataView::getSid).distinct().collect(Collectors.toList());
	}

}
