/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.query.infra.repository.employee;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.collection.CollectionUtil;
import nts.uk.query.infra.entity.employee.EmployeeDataView;
import nts.uk.query.infra.entity.employee.EmployeeDataView_;
import nts.uk.query.model.EmployeeQueryModel;
import nts.uk.query.model.EmployeeQueryModelRepository;
import nts.uk.query.model.EmployeeSearchQuery;

/**
 * The Class JpaEmployeeDataViewRepository.
 */
@Stateless
public class JpaEmployeeDataViewRepository extends JpaRepository implements EmployeeQueryModelRepository {

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
	public List<EmployeeQueryModel> find(EmployeeSearchQuery paramQuery) {
		EntityManager em = this.getEntityManager();

		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<EmployeeDataView> cq = cb.createQuery(EmployeeDataView.class);
		Root<EmployeeDataView> root = cq.from(EmployeeDataView.class);
		List<EmployeeDataView> resultList = new ArrayList<>();

		// Constructing condition.
		List<Predicate> conditions = new ArrayList<Predicate>();

		CollectionUtil.split(paramQuery.getEmploymentCodes(), LIST_MAX_QUERY_IN, employmentCodes -> {
			CollectionUtil.split(paramQuery.getWorkplaceCodes(), LIST_MAX_QUERY_IN, workplaceCodes -> {
				CollectionUtil.split(paramQuery.getClassificationCodes(), LIST_MAX_QUERY_IN, classificationCodes -> {
					CollectionUtil.split(paramQuery.getJobTitleCodes(), LIST_MAX_QUERY_IN, jobTitleCodes -> {
						// employment condition
						if (paramQuery.getFilterByEmployment()) {
							conditions.add(root.get(EmployeeDataView_.empCd).in(employmentCodes));
							conditions.add(cb.greaterThanOrEqualTo(root.get(EmployeeDataView_.employmentStrDate),
									paramQuery.getBaseDate()));
							conditions.add(cb.lessThanOrEqualTo(root.get(EmployeeDataView_.employmentEndDate),
									paramQuery.getBaseDate()));
						}
						// workplace condition
						if (paramQuery.getFilterByWorkplace()) {
							conditions.add(root.get(EmployeeDataView_.workplaceId).in(workplaceCodes));
							conditions.add(cb.greaterThanOrEqualTo(root.get(EmployeeDataView_.wplStrDate),
									paramQuery.getBaseDate()));
							conditions.add(cb.lessThanOrEqualTo(root.get(EmployeeDataView_.wplEndDate),
									paramQuery.getBaseDate()));
							conditions.add(cb.greaterThanOrEqualTo(root.get(EmployeeDataView_.wplInfoStrDate),
									paramQuery.getBaseDate()));
							conditions.add(cb.lessThanOrEqualTo(root.get(EmployeeDataView_.wplInfoEndDate),
									paramQuery.getBaseDate()));
						}
						// classification condition
						if (paramQuery.getFilterByClassification()) {
							conditions.add(root.get(EmployeeDataView_.classificationCode).in(classificationCodes));
							conditions.add(cb.greaterThanOrEqualTo(root.get(EmployeeDataView_.classStrDate),
									paramQuery.getBaseDate()));
							conditions.add(cb.lessThanOrEqualTo(root.get(EmployeeDataView_.classEndDate),
									paramQuery.getBaseDate()));
						}
						// jobtitle condition
						if (paramQuery.getFilterByJobTitle()) {
							conditions.add(root.get(EmployeeDataView_.jobTitleId).in(jobTitleCodes));
							conditions.add(cb.greaterThanOrEqualTo(root.get(EmployeeDataView_.jobStrDate),
									paramQuery.getBaseDate()));
							conditions.add(cb.lessThanOrEqualTo(root.get(EmployeeDataView_.jobEndDate),
									paramQuery.getBaseDate()));
							conditions.add(cb.greaterThanOrEqualTo(root.get(EmployeeDataView_.jobInfoStrDate),
									paramQuery.getBaseDate()));
							conditions.add(cb.lessThanOrEqualTo(root.get(EmployeeDataView_.jobInfoEndDate),
									paramQuery.getBaseDate()));
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
									cb.greaterThanOrEqualTo(root.get(EmployeeDataView_.comStrDate),
											paramQuery.getRetireStart()),
									cb.lessThanOrEqualTo(root.get(EmployeeDataView_.comEndDate),
											paramQuery.getRetireStart()),
									cb.greaterThanOrEqualTo(root.get(EmployeeDataView_.comStrDate),
											paramQuery.getRetireEnd()),
									cb.lessThanOrEqualTo(root.get(EmployeeDataView_.comEndDate),
											paramQuery.getRetireEnd()));
							Predicate absDateCondition = cb.or(
									cb.lessThan(root.get(EmployeeDataView_.absStrDate), paramQuery.getRetireEnd()),
									cb.greaterThan(root.get(EmployeeDataView_.absEndDate),
											paramQuery.getRetireStart()));
							incumbentCondition = cb.and(comDateCondition, absDateCondition);
						}
						// include workers on leave condition
						if (paramQuery.getIncludeWorkersOnLeave()) {
							Predicate comDateCondition = cb.or(
									cb.greaterThanOrEqualTo(root.get(EmployeeDataView_.comStrDate),
											paramQuery.getRetireStart()),
									cb.lessThanOrEqualTo(root.get(EmployeeDataView_.comEndDate),
											paramQuery.getRetireStart()),
									cb.greaterThanOrEqualTo(root.get(EmployeeDataView_.comStrDate),
											paramQuery.getRetireEnd()),
									cb.lessThanOrEqualTo(root.get(EmployeeDataView_.comEndDate),
											paramQuery.getRetireEnd()));
							Predicate absDateCondition = cb.or(
									cb.greaterThanOrEqualTo(root.get(EmployeeDataView_.absStrDate),
											paramQuery.getRetireStart()),
									cb.lessThanOrEqualTo(root.get(EmployeeDataView_.comEndDate),
											paramQuery.getRetireStart()),
									cb.greaterThanOrEqualTo(root.get(EmployeeDataView_.absStrDate),
											paramQuery.getRetireEnd()),
									cb.lessThanOrEqualTo(root.get(EmployeeDataView_.comEndDate),
											paramQuery.getRetireEnd()));
							Predicate frameNoCondition = cb.equal(root.get(EmployeeDataView_.tempAbsFrameNo),
									LEAVE_ABSENCE_QUOTA_NO);
							workerOnLeaveCondition = cb.and(comDateCondition, absDateCondition, frameNoCondition);
						}
						// include occupancy condition
						if (paramQuery.getIncludeOccupancy()) {
							Predicate comDateCondition = cb.or(
									cb.greaterThanOrEqualTo(root.get(EmployeeDataView_.comStrDate),
											paramQuery.getRetireStart()),
									cb.lessThanOrEqualTo(root.get(EmployeeDataView_.comEndDate),
											paramQuery.getRetireStart()),
									cb.greaterThanOrEqualTo(root.get(EmployeeDataView_.comStrDate),
											paramQuery.getRetireEnd()),
									cb.lessThanOrEqualTo(root.get(EmployeeDataView_.comEndDate),
											paramQuery.getRetireEnd()));
							Predicate absDateCondition = cb.or(
									cb.greaterThanOrEqualTo(root.get(EmployeeDataView_.absStrDate),
											paramQuery.getRetireStart()),
									cb.lessThanOrEqualTo(root.get(EmployeeDataView_.comEndDate),
											paramQuery.getRetireStart()),
									cb.greaterThanOrEqualTo(root.get(EmployeeDataView_.absStrDate),
											paramQuery.getRetireEnd()),
									cb.lessThanOrEqualTo(root.get(EmployeeDataView_.comEndDate),
											paramQuery.getRetireEnd()));
							Predicate frameNoCondition = cb.notEqual(root.get(EmployeeDataView_.tempAbsFrameNo),
									LEAVE_ABSENCE_QUOTA_NO);
							occupancyCondition = cb.and(comDateCondition, absDateCondition, frameNoCondition);

						}
						// include retire condition
						if (paramQuery.getIncludeRetirees()) {
							retireCondition = cb.greaterThan(root.get(EmployeeDataView_.comEndDate),
									paramQuery.getRetireStart());
						}

						// set condition
						statusOfEmployeeCondition = cb.or(incumbentCondition, workerOnLeaveCondition,
								occupancyCondition, retireCondition);
						conditions.add(statusOfEmployeeCondition);
						cq.where(conditions.toArray(new Predicate[] {}));

						// execute query & add to resultList
						resultList.addAll(em.createQuery(cq).getResultList());
					});
				});
			});
		});

		return resultList.stream()
				.map(entity -> EmployeeQueryModel.builder()
						.classificationCode(Optional.of(entity.getClassificationCode())).employeeCode(entity.getScd())
						.employeeID(entity.getSid()).employmentCode(Optional.of(entity.getEmpCd()))
						.hireDate(Optional.of(entity.getComStrDate())).jobTitleCode(Optional.of(entity.getJobCd()))
						.name(Optional.of(entity.getBusinessNameKana())).workplaceCode(Optional.of(entity.getWplCd()))
						.build())
				.collect(Collectors.toList());
	}

}
