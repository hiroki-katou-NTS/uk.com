/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.query.infra.repository.employee;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
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
import nts.uk.query.model.employee.CCG001SystemType;
import nts.uk.query.model.employee.EmployeeSearchQuery;
import nts.uk.query.model.employee.RegularSortingType;
import nts.uk.query.model.employee.RegulationInfoEmployee;
import nts.uk.query.model.employee.RegulationInfoEmployeeRepository;
import nts.uk.query.model.employee.SortingConditionOrder;

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

	/** The Constant NAME_TYPE. */
	// 現在は、氏名の種類を選択する機能がないので、「ビジネスネーム日本語」固定で
	// => 「氏名カナ」 ＝ 「ビジネスネームカナ」
	private static final int NAME_TYPE = 1;

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

	private static final String EMPTY_LIST = "EMPTY_LIST";
	private static final Integer ELEMENT_300 = 300;
	private static final Integer ELEMENT_2000 = 2000;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.query.model.EmployeeQueryModelRepository#find(nts.uk.query.model.
	 * EmployeeSearchQuery)
	 */
	@Override
	public List<RegulationInfoEmployee> find(String comId, EmployeeSearchQuery paramQuery) {
		// max paramenter count = 2100
		int countParameter = 0;
		
		// Return empty list if all status of employee = 対象外
		if (!paramQuery.getIncludeIncumbents() && !paramQuery.getIncludeOccupancy() && !paramQuery.getIncludeRetirees()
				&& !paramQuery.getIncludeWorkersOnLeave()) {
			return Collections.emptyList();
		}
		EntityManager em = this.getEntityManager();

		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<EmployeeDataView> cq = cb.createQuery(EmployeeDataView.class);
		Root<EmployeeDataView> root = cq.from(EmployeeDataView.class);

		// Constructing condition.
		List<Predicate> conditions = new ArrayList<>();
		List<String> employmentCodes = new ArrayList<>(Optional.ofNullable(paramQuery.getEmploymentCodes()).orElse(Collections.EMPTY_LIST));
		List<String> workplaceCodes = new ArrayList<>(Optional.ofNullable(paramQuery.getWorkplaceCodes()).orElse(Collections.EMPTY_LIST));
		List<String> classificationCodes = new ArrayList<>(Optional.ofNullable(paramQuery.getClassificationCodes()).orElse(Collections.EMPTY_LIST));
		List<String> jobTitleCodes = new ArrayList<>(Optional.ofNullable(paramQuery.getJobTitleCodes()).orElse(Collections.EMPTY_LIST));
		List<String> worktypeCodes = new ArrayList<>(Optional.ofNullable(paramQuery.getWorktypeCodes()).orElse(Collections.EMPTY_LIST));
		List<Integer> closureIds = new ArrayList<>(Optional.ofNullable(paramQuery.getClosureIds()).orElse(Collections.EMPTY_LIST));
		GeneralDateTime baseDate = paramQuery.getBaseDate();
		
		// Add company condition 
		conditions.add(cb.equal(root.get(EmployeeDataView_.cid), comId));

		// Add NOT_DELETED condition
		conditions.add(cb.equal(root.get(EmployeeDataView_.delStatusAtr), NOT_DELETED));
		countParameter += 2;

		// employment condition
		Predicate empCondition = cb.and(cb.lessThanOrEqualTo(root.get(EmployeeDataView_.employmentStrDate), baseDate),
				cb.greaterThanOrEqualTo(root.get(EmployeeDataView_.employmentEndDate), baseDate));
		if (paramQuery.getFilterByEmployment()) {
			// return empty list if condition code list is empty
			if (employmentCodes.isEmpty()) {
				return Collections.emptyList();
			}

			// update query conditions
			conditions.add(empCondition);
			countParameter += 2;				
		} else {
			conditions.add(cb.or(cb.isNull(root.get(EmployeeDataView_.employmentStrDate)), empCondition));
			countParameter += 3;
		}

		// workplace condition
		Predicate wplCondition = cb.and(cb.lessThanOrEqualTo(root.get(EmployeeDataView_.wplStrDate), baseDate),
				cb.greaterThanOrEqualTo(root.get(EmployeeDataView_.wplEndDate), baseDate),
				cb.lessThanOrEqualTo(root.get(EmployeeDataView_.wplInfoStrDate), baseDate),
				cb.greaterThanOrEqualTo(root.get(EmployeeDataView_.wplInfoEndDate), baseDate),
				cb.greaterThanOrEqualTo(root.get(EmployeeDataView_.wkpConfEndDate), baseDate),
				cb.lessThanOrEqualTo(root.get(EmployeeDataView_.wkpConfStrDate), baseDate));
		if (paramQuery.getFilterByWorkplace()) {
			// return empty list if condition code list is empty
			if (workplaceCodes.isEmpty()) {
				return Collections.emptyList();
			}

			// update query conditions
			conditions.add(wplCondition);
			countParameter += 6;
		} else {
			conditions.add(cb.or(cb.isNull(root.get(EmployeeDataView_.wplStrDate)), wplCondition));
			countParameter += 7;
		}

		// classification condition
		Predicate clsCondition = cb.and(cb.lessThanOrEqualTo(root.get(EmployeeDataView_.classStrDate), baseDate),
				cb.greaterThanOrEqualTo(root.get(EmployeeDataView_.classEndDate), baseDate));
		if (paramQuery.getFilterByClassification()) {
			// return empty list if condition code list is empty
			if (classificationCodes.isEmpty()) {
				return Collections.emptyList();
			}

			// update query conditions
			conditions.add(clsCondition);
			countParameter += 2;
		} else {
			conditions.add(cb.or(cb.isNull(root.get(EmployeeDataView_.classStrDate)), clsCondition));
			countParameter += 3;
		}

		// jobtitle condition
		Predicate jobCondition = cb.and(cb.lessThanOrEqualTo(root.get(EmployeeDataView_.jobStrDate), baseDate),
				cb.greaterThanOrEqualTo(root.get(EmployeeDataView_.jobEndDate), baseDate),
				cb.lessThanOrEqualTo(root.get(EmployeeDataView_.jobInfoStrDate), baseDate),
				cb.greaterThanOrEqualTo(root.get(EmployeeDataView_.jobInfoEndDate), baseDate));
		if (paramQuery.getFilterByJobTitle()) {
			// return empty list if condition code list is empty
			if (jobTitleCodes.isEmpty()) {
				return Collections.emptyList();
			}

			// update query conditions
			conditions.add(jobCondition);
			countParameter += 4;
		} else {
			conditions.add(cb.or(cb.isNull(root.get(EmployeeDataView_.jobStrDate)), jobCondition));
			countParameter += 5;
		}
		if (paramQuery.getSystemType() == CCG001SystemType.EMPLOYMENT.value) {
			if (paramQuery.getFilterByWorktype()) {
				// return empty list if condition code list is empty
				if (worktypeCodes.isEmpty()) {
					return Collections.emptyList();
				}

				// update query conditions
				conditions.add(root.get(EmployeeDataView_.workTypeCd).in(worktypeCodes));
				conditions.add(cb.lessThanOrEqualTo(root.get(EmployeeDataView_.workTypeStrDate),
						GeneralDate.localDate(baseDate.toLocalDate())));
				conditions.add(cb.greaterThanOrEqualTo(root.get(EmployeeDataView_.workTypeEndDate),
						GeneralDate.localDate(baseDate.toLocalDate())));
				countParameter += 2 + worktypeCodes.size();
			}
			if (paramQuery.getFilterByClosure()) {
				// return empty list if condition code list is empty
				if (closureIds.isEmpty()) {
					return Collections.emptyList();
				}

				// update query conditions
				conditions.add(root.get(EmployeeDataView_.closureId).in(closureIds));
				countParameter += closureIds.size();

				// check exist before add employment conditions
				if (!paramQuery.getFilterByEmployment()) {
					conditions.add(cb.lessThanOrEqualTo(root.get(EmployeeDataView_.employmentStrDate),
							baseDate));
					conditions.add(cb.greaterThanOrEqualTo(root.get(EmployeeDataView_.employmentEndDate),
							baseDate));
					countParameter += 2;
				}
			}
		}

		// Filter result list by status of employee
		GeneralDateTime retireStart = paramQuery.getRetireStart() == null ? paramQuery.getPeriodStart()
				: paramQuery.getRetireStart();
		GeneralDateTime retireEnd = paramQuery.getRetireEnd() == null ? paramQuery.getPeriodEnd()
				: paramQuery.getRetireEnd();
		GeneralDateTime start = paramQuery.getPeriodStart();
		GeneralDateTime end = paramQuery.getPeriodEnd();

		Predicate isWorking = cb.or(
				cb.and(cb.isNull(root.get(EmployeeDataView_.tempAbsFrameNo)),
						cb.isNull(root.get(EmployeeDataView_.absStrDate))),
				cb.greaterThan(root.get(EmployeeDataView_.absStrDate), end),
				cb.lessThan(root.get(EmployeeDataView_.absEndDate), start));

		// is in company
		conditions.add(cb.not(cb.or(cb.greaterThan(root.get(EmployeeDataView_.comStrDate), end),
				cb.lessThan(root.get(EmployeeDataView_.comEndDate), start))));

		Predicate incumbentCondition = cb.disjunction();
		Predicate workerOnLeaveCondition = cb.disjunction();
		Predicate occupancyCondition = cb.disjunction();
		Predicate retireCondition = cb.disjunction();

		// includeIncumbents
		if (paramQuery.getIncludeIncumbents()) {
			incumbentCondition = isWorking;
		}

		// workerOnLeave
		if (paramQuery.getIncludeWorkersOnLeave()) {
			workerOnLeaveCondition = cb.and(cb.not(isWorking),
					cb.equal(root.get(EmployeeDataView_.tempAbsFrameNo), LEAVE_ABSENCE_QUOTA_NO));
		}

		// Occupancy
		if (paramQuery.getIncludeOccupancy()) {
			occupancyCondition = cb.and(cb.not(isWorking),
					cb.notEqual(root.get(EmployeeDataView_.tempAbsFrameNo), LEAVE_ABSENCE_QUOTA_NO));
		}

		// retire
		if (paramQuery.getIncludeRetirees()) {
			retireCondition = cb.and(cb.greaterThanOrEqualTo(root.get(EmployeeDataView_.comEndDate), retireStart),
					cb.lessThanOrEqualTo(root.get(EmployeeDataView_.comEndDate), retireEnd));
		}

		conditions.add(cb.or(incumbentCondition, workerOnLeaveCondition, occupancyCondition, retireCondition));
		countParameter += 10;

		// getSortConditions
		List<BsymtEmpOrderCond> sortConditions = this.getSortConditions(comId, paramQuery.getSystemType(),
				paramQuery.getSortOrderNo());

		// sort
		if (paramQuery.getSystemType() != CCG001SystemType.ADMINISTRATOR.value) {
			List<Order> orders = this.getOrders(paramQuery.getSystemType(), NAME_TYPE,
					this.toSortingConditionQueryModel(sortConditions));
			cq.orderBy(orders);
		}
		List<EmployeeDataView> resultList = new ArrayList<>();
		int countParameterFinal = countParameter; 
		// Fix bug #100057
		if (CollectionUtil.isEmpty(employmentCodes)) {
			employmentCodes.add(EMPTY_LIST);
		}
		if (CollectionUtil.isEmpty(jobTitleCodes)) {
			jobTitleCodes.add(EMPTY_LIST);
		}
		if (CollectionUtil.isEmpty(classificationCodes)) {
			classificationCodes.add(EMPTY_LIST);
		}
		if (CollectionUtil.isEmpty(workplaceCodes)) {
			workplaceCodes.add(EMPTY_LIST);
		}
		// employment condition
		CollectionUtil.split(employmentCodes, ELEMENT_300, splitEmploymentCodes -> {
			// workplace condition
			CollectionUtil.split(jobTitleCodes, ELEMENT_300, splitJobTitleCodes -> {
				// classification condition
				CollectionUtil.split(classificationCodes, ELEMENT_300, splitClassificationCodes -> {
					// jobtitle condition
					CollectionUtil.split(workplaceCodes, ELEMENT_2000 - (splitEmploymentCodes.size() + splitJobTitleCodes.size() + splitClassificationCodes.size() - countParameterFinal), splitWorkplaceCodes -> {
						resultList.addAll(executeQuery(paramQuery.getFilterByEmployment(), splitEmploymentCodes, 
								paramQuery.getFilterByWorkplace(), splitWorkplaceCodes, 
								paramQuery.getFilterByClassification(), splitClassificationCodes, 
								paramQuery.getFilterByJobTitle(), splitJobTitleCodes, conditions, cb, cq, comId, paramQuery, em, root));
					});
				});
			});
		});

		// Distinct employee in result list.
		List<EmployeeDataView> resultListDistinct = resultList.stream().filter(this.distinctByKey(EmployeeDataView::getSid))
				.collect(Collectors.toList());

		return resultListDistinct.stream().map(entity -> RegulationInfoEmployee.builder()
				.classificationCode(Optional.ofNullable(entity.getClassificationCode())).employeeCode(entity.getScd())
				.employeeID(entity.getSid()).employmentCode(Optional.ofNullable(entity.getEmpCd()))
				.hireDate(Optional.ofNullable(entity.getComStrDate()))
				.jobTitleCode(Optional.ofNullable(entity.getJobCd()))
				.name(Optional.ofNullable(entity.getBusinessName()))
				.workplaceId(Optional.ofNullable(entity.getWorkplaceId()))
				.workplaceHierarchyCode(Optional.ofNullable(entity.getWplHierarchyCode()))
				.workplaceCode(Optional.ofNullable(entity.getWplCd()))
				.workplaceName(Optional.ofNullable(entity.getWplName()))
				.build())
				.sorted(Comparator.comparing(RegulationInfoEmployee::getEmployeeCode))
				.collect(Collectors.toList());
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
	 * To sorting condition query model.
	 *
	 * @param sortConditions the sort conditions
	 * @return the list
	 */
	private List<SortingConditionOrder> toSortingConditionQueryModel(List<BsymtEmpOrderCond> sortConditions) {
		return sortConditions.stream().map(cond -> {
			SortingConditionOrder mapped = new SortingConditionOrder();
			mapped.setOrder(cond.getConditionOrder());
			mapped.setType(RegularSortingType.valueOf(cond.getId().getOrderType()));
			return mapped;
		}).collect(Collectors.toList());
	}

	/**
	 * Gets the criteria query of sorting employees.
	 *
	 * @param comId the com id
	 * @param sIds the s ids
	 * @param referenceDate the reference date
	 * @return the criteria query of sorting employees
	 */
	private CriteriaQuery<EmployeeDataView> getCriteriaQueryOfSortingEmployees(String comId, List<String> sIds,
			GeneralDateTime referenceDate) {
		CriteriaBuilder cb = this.getEntityManager().getCriteriaBuilder();
		CriteriaQuery<EmployeeDataView> cq = cb.createQuery(EmployeeDataView.class);
		Root<EmployeeDataView> root = cq.from(EmployeeDataView.class);

		List<Predicate> conditions = new ArrayList<>();

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
		return cq;
	}

	/**
	 * Gets the orders.
	 *
	 * @param systemType the system type
	 * @param nameType the name type
	 * @param sortConditions the sort conditions
	 * @return the orders
	 */
	private List<Order> getOrders(Integer systemType, Integer nameType, List<SortingConditionOrder> sortConditions) {
		CriteriaBuilder cb = this.getEntityManager().getCriteriaBuilder();
		CriteriaQuery<EmployeeDataView> cq = cb.createQuery(EmployeeDataView.class);
		Root<EmployeeDataView> root = cq.from(EmployeeDataView.class);

		sortConditions.sort((a,b) -> a.getOrder() - b.getOrder());
		Iterator<SortingConditionOrder> iterator = sortConditions.iterator();

		List<Order> orders = new ArrayList<>();

		while (iterator.hasNext()) {
			SortingConditionOrder cond = iterator.next();
			switch (cond.getType()) {
			case EMPLOYMENT: // EMPLOYMENT
				orders.add(cb.asc(root.get(EmployeeDataView_.empCd)));
				break;
			case DEPARTMENT: // DEPARTMENT
				// TODO: not covered
				break;
			case WORKPLACE: // WORKPLACE
				orders.add(cb.asc(root.get(EmployeeDataView_.wplHierarchyCode)));
				break;
			case CLASSIFICATION: // CLASSIFICATION
				orders.add(cb.asc(root.get(EmployeeDataView_.classificationCode)));
				break;
			case POSITION: // POSITION
				orders.add(cb.asc(root.get(EmployeeDataView_.jobSeqDisp)));
				orders.add(cb.asc(root.get(EmployeeDataView_.jobCd)));
				break;
			case HIRE_DATE: // HIRE_DATE
				orders.add(cb.asc(root.get(EmployeeDataView_.comStrDate)));
				break;
			case NAME: // NAME
				// 現在は、氏名の種類を選択する機能がないので、「ビジネスネーム日本語」固定で
				// => 「氏名カナ」 ＝ 「ビジネスネームカナ」
				orders.add(cb.asc(root.get(EmployeeDataView_.businessNameKana)));
				// TODO: orders.add(cb.asc(root.get(EmployeeDataView_.personNameKana)));
				break;
			}
		}

		// sort by worktype code
		if (systemType == CCG001SystemType.EMPLOYMENT.value) {
			// Katou said: その資料の記載は現在未対応の「並び替え機能」で使用するものです。
			// 現状は全システム区分で「社員コード（ASC）」でソートしてもらえませんでしょうか？
			// => hien tai chua doi ung cai nay
			// TODO: not covered
			// orders.add(cb.asc(root.get(EmployeeDataView_.workTypeCd)));
		}

		// always sort by employee code
		orders.add(cb.asc(root.get(EmployeeDataView_.scd)));
		return orders;
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

		if (CollectionUtil.isEmpty(res)) {
			return null;
		}

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
		List<EmployeeDataView> resultList = new ArrayList<>();
		EntityManager em = this.getEntityManager();
		CriteriaQuery<EmployeeDataView> cq = this.getCriteriaQueryOfSortingEmployees(comId, sIds, referenceDate);

		// getSortConditions
		List<BsymtEmpOrderCond> sortConditions = this.getSortConditions(comId, systemType, orderNo);

		// sort
		List<Order> orders = this.getOrders(systemType, nameType, this.toSortingConditionQueryModel(sortConditions));
		cq.orderBy(orders);

		// execute query & add to resultList
		resultList.addAll(em.createQuery(cq).getResultList());

		return resultList.stream().map(EmployeeDataView::getSid).distinct().collect(Collectors.toList());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.query.model.employee.RegulationInfoEmployeeRepository#
	 * sortEmployees(java.lang.String, java.util.List, java.util.List,
	 * nts.arc.time.GeneralDateTime)
	 */
	@Override
	public List<String> sortEmployees(String comId, List<String> sIds, List<SortingConditionOrder> orders,
			GeneralDateTime referenceDate) {
		if (sIds.isEmpty()) {
			return Collections.emptyList();
		}
		EntityManager em = this.getEntityManager();
		CriteriaQuery<EmployeeDataView> cq = this.getCriteriaQueryOfSortingEmployees(comId, sIds, referenceDate);

		// System type is required when sorting by workTypeCd
		List<Order> listOrder = this.getOrders(CCG001SystemType.ADMINISTRATOR.value, NAME_TYPE, orders);
		cq.orderBy(listOrder);

		return em.createQuery(cq).getResultList().stream().map(EmployeeDataView::getSid).distinct()
				.collect(Collectors.toList());
	}
	
	/**
	 * Execute query.
	 *
	 * @param getFilterByEmployment the get filter by employment
	 * @param splitEmploymentCodes the split employment codes
	 * @param getFilterByWorkplace the get filter by workplace
	 * @param splitWorkplaceCodes the split workplace codes
	 * @param getFilterByClassification the get filter by classification
	 * @param splitClassificationCodes the split classification codes
	 * @param getFilterByJobTitle the get filter by job title
	 * @param splitJobTitleCodes the split job title codes
	 * @param conditions the conditions
	 * @param cb the cb
	 * @param cq the cq
	 * @param comId the com id
	 * @param paramQuery the param query
	 * @param em the em
	 * @param root the root
	 * @return the list
	 */
	private List<EmployeeDataView> executeQuery(boolean getFilterByEmployment, List<String> splitEmploymentCodes, 
								boolean getFilterByWorkplace, List<String> splitWorkplaceCodes, 
								boolean getFilterByClassification, List<String> splitClassificationCodes, 
								boolean getFilterByJobTitle, List<String> splitJobTitleCodes, List<Predicate> conditions, CriteriaBuilder cb,
								CriteriaQuery<EmployeeDataView> cq, String comId, EmployeeSearchQuery paramQuery,
								EntityManager em, Root<EmployeeDataView> root
								) {
		int countFilterTrue = 0;
		
		// employment condition
		if (getFilterByEmployment) {
			if (splitEmploymentCodes.size() == 1 && splitEmploymentCodes.get(0).compareTo(EMPTY_LIST) == 0) {
				splitEmploymentCodes.clear();
			}
			conditions.add(root.get(EmployeeDataView_.empCd).in(splitEmploymentCodes));
			countFilterTrue++;
		}
		
		// workplace condition
		if (getFilterByWorkplace) {
			if (splitWorkplaceCodes.size() == 1 && splitWorkplaceCodes.get(0).compareTo(EMPTY_LIST) == 0) {
				splitWorkplaceCodes.clear();
			}
			conditions.add(root.get(EmployeeDataView_.workplaceId).in(splitWorkplaceCodes));
			countFilterTrue++;
		}
		
		// classification condition
		if (getFilterByClassification) {
			if (splitClassificationCodes.size() == 1 && splitClassificationCodes.get(0).compareTo(EMPTY_LIST) == 0) {
				splitClassificationCodes.clear();
			}
			conditions.add(root.get(EmployeeDataView_.classificationCode).in(splitClassificationCodes));
			countFilterTrue++;
		}
		
		// jobtitle condition
		if (getFilterByJobTitle) { 
			if (splitJobTitleCodes.size() == 1 && splitJobTitleCodes.get(0).compareTo(EMPTY_LIST) == 0) {
				splitJobTitleCodes.clear();
			}
			conditions.add(root.get(EmployeeDataView_.jobTitleId).in(splitJobTitleCodes));
			countFilterTrue++;
		}
		
		List<EmployeeDataView> resultListInFunc = new ArrayList<>();
		cq.where(conditions.toArray(new Predicate[] {}));

		// execute query & add to resultListInFunc
		resultListInFunc.addAll(em.createQuery(cq).getResultList());
		while (countFilterTrue > 0) {
			conditions.remove(conditions.size()-1);
			countFilterTrue--;
		}
		return resultListInFunc;
	}

}
