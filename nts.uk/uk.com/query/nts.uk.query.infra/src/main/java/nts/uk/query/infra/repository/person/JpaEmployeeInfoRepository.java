/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.query.infra.repository.person;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDateTime;
import nts.gul.collection.CollectionUtil;
import nts.uk.query.infra.entity.employee.EmployeeDataView;
import nts.uk.query.infra.entity.employee.EmployeeDataView_;
import nts.uk.query.model.person.BusinessTypeHistoryModel;
import nts.uk.query.model.person.ClassificationHistoryModel;
import nts.uk.query.model.person.EmployeeInfoQueryModel;
import nts.uk.query.model.person.EmployeeInfoRepository;
import nts.uk.query.model.person.EmployeeInfoResultModel;
import nts.uk.query.model.person.EmploymentHistoryModel;
import nts.uk.query.model.person.JobTitleHistoryModel;
import nts.uk.query.model.person.WorkPlaceHistoryModel;

/**
 * The Class JpaEmployeeInfoRepository.
 */
@Stateless
public class JpaEmployeeInfoRepository extends JpaRepository implements EmployeeInfoRepository {

	/*
	 * (non-Javadoc)
	 * @see
	 * nts.uk.query.model.person.EmployeeInfoRepository#find(nts.uk.query.model.
	 * person.EmployeeInfoQueryModel)
	 */
	@Override
	public List<EmployeeInfoResultModel> find(EmployeeInfoQueryModel query) {
		// Check param.
		if (CollectionUtil.isEmpty(query.getEmployeeIds())) {
			return Collections.emptyList();
		}

		EntityManager em = this.getEntityManager();

		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<EmployeeDataView> cq = cb.createQuery(EmployeeDataView.class);
		Root<EmployeeDataView> root = cq.from(EmployeeDataView.class);
		List<EmployeeDataView> resultList = new ArrayList<>();

		// Init condition query.
		List<Predicate> conditions = new ArrayList<Predicate>();
		GeneralDateTime startDateTime = GeneralDateTime.legacyDateTime(query.getPeroid().start().date());
		GeneralDateTime endDateTime = GeneralDateTime.legacyDateTime(query.getPeroid().end().date());
		Predicate workPlacePredicate = cb.conjunction();
		Predicate classificationPredicate = cb.conjunction();
		Predicate jobPredicate = cb.conjunction();
		Predicate employmentPredicate = cb.conjunction();
		Predicate businessTypePredicate = cb.conjunction();
		// Predicate[] orPredicates = new Predicate[2];

		// Find workplace.
		if (query.isFindWorkPlaceInfo()) {
			workPlacePredicate = cb.and(cb.lessThanOrEqualTo(root.get(EmployeeDataView_.wplStrDate), endDateTime),
					cb.greaterThanOrEqualTo(root.get(EmployeeDataView_.wplEndDate), startDateTime));
		}

		// Find classification.
		if (query.isFindClasssificationInfo()) {
			classificationPredicate = cb.and(
					cb.lessThanOrEqualTo(root.get(EmployeeDataView_.classStrDate), endDateTime),
					cb.greaterThanOrEqualTo(root.get(EmployeeDataView_.classEndDate), startDateTime));
		}

		// Find JobTitle.
		if (query.isFindJobTilteInfo()) {
			jobPredicate = cb.and(cb.lessThanOrEqualTo(root.get(EmployeeDataView_.jobStrDate), endDateTime),
					cb.greaterThanOrEqualTo(root.get(EmployeeDataView_.jobEndDate), startDateTime));
		}

		// Find Employment
		if (query.isFindEmploymentInfo()) {
			employmentPredicate = cb.and(
					cb.lessThanOrEqualTo(root.get(EmployeeDataView_.employmentStrDate), endDateTime),
					cb.greaterThanOrEqualTo(root.get(EmployeeDataView_.employmentEndDate), startDateTime));
		}

		// Find BusinessType.
		if (query.isFindBussinessTypeInfo()) {
			businessTypePredicate = cb.and(
					cb.lessThanOrEqualTo(root.get(EmployeeDataView_.workTypeStrDate), query.getPeroid().end()),
					cb.greaterThanOrEqualTo(root.get(EmployeeDataView_.workTypeEndDate), query.getPeroid().start()));
		}

		// Add to condition list.
		conditions.add(cb.or(workPlacePredicate, classificationPredicate, jobPredicate, employmentPredicate,
				businessTypePredicate));

		// Where in employee ids.
		CollectionUtil.split(query.getEmployeeIds(), DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {
			conditions.add(root.get(EmployeeDataView_.sid).in(subList));

			cq.where(conditions.toArray(new Predicate[] {}));
			resultList.addAll(em.createQuery(cq).getResultList());
			
			// remove element 2 in array.
			conditions.remove(1);
		});

		// Group by employee id;
		Map<String, List<EmployeeDataView>> resultMap = resultList.stream()
				.collect(Collectors.groupingBy(EmployeeDataView::getSid));

		// Convert to result model.
		return resultMap.keySet().stream().map(key -> this.convertData(resultMap.get(key), query))
				.collect(Collectors.toList());
	}

	/**
	 * Convert data.
	 *
	 * @param datas the datas
	 * @param query the query
	 * @return the employee info result model
	 */
	private EmployeeInfoResultModel convertData(List<EmployeeDataView> datas, EmployeeInfoQueryModel query) {
		EmployeeInfoResultModel info = new EmployeeInfoResultModel();
		info.setEmployeeId(datas.get(0).getSid());

		// Add workplace info.
		if (query.isFindWorkPlaceInfo()) {
			List<WorkPlaceHistoryModel> workPlaces = datas.stream()
					.filter(data -> data.getWorkplaceId() != null)
					.filter(this.distinctByKey(EmployeeDataView::getWplStrDate))
					.map(data -> {
						return WorkPlaceHistoryModel.builder().employeeId(data.getSid())
								.workplaceId(data.getWorkplaceId()).startDate(data.getWplStrDate())
								.endDate(data.getWplEndDate()).build();
					}).collect(Collectors.toList());
			info.setWorkPlaceHistorys(workPlaces);
		} else {
			info.setWorkPlaceHistorys(Collections.emptyList());
		}

		// Add classification info.
		if (query.isFindClasssificationInfo()) {
			List<ClassificationHistoryModel> classifications = datas.stream()
					.filter(data -> data.getClassificationCode() != null)
					.filter(this.distinctByKey(EmployeeDataView::getClassStrDate))
					.map(data -> {
						return ClassificationHistoryModel.builder().employeeId(data.getSid())
								.classificationCode(data.getClassificationCode()).startDate(data.getClassStrDate())
								.endDate(data.getClassEndDate()).build();
					}).collect(Collectors.toList());
			info.setClassificationHistorys(classifications);
		} else {
			info.setClassificationHistorys(Collections.emptyList());
		}

		// Add Employment info.
		if (query.isFindEmploymentInfo()) {
			List<EmploymentHistoryModel> employments = datas.stream()
					.filter(data -> data.getEmpCd() != null)
					.filter(this.distinctByKey(EmployeeDataView::getEmploymentStrDate))
					.map(data -> {
						return EmploymentHistoryModel.builder().employeeId(data.getSid())
								.employmentCode(data.getEmpCd()).startDate(data.getEmploymentStrDate())
								.endDate(data.getEmploymentEndDate()).build();
					}).collect(Collectors.toList());
			info.setEmploymentHistorys(employments);
		} else {
			info.setEmploymentHistorys(Collections.emptyList());
		}

		// Add Job title info.
		if (query.isFindJobTilteInfo()) {
			List<JobTitleHistoryModel> jobTitles = datas.stream()
					.filter(data -> data.getJobTitleId() != null)
					.filter(this.distinctByKey(EmployeeDataView::getJobStrDate))
					.map(data -> {
						return JobTitleHistoryModel.builder().employeeId(data.getSid()).jobId(data.getJobTitleId())
								.startDate(data.getJobStrDate()).endDate(data.getJobEndDate()).build();
					}).collect(Collectors.toList());
			info.setJobTitleHistorys(jobTitles);
		} else {
			info.setJobTitleHistorys(Collections.emptyList());
		}

		// Add Bussiness type info.
		if (query.isFindBussinessTypeInfo()) {
			List<BusinessTypeHistoryModel> businessTypes = datas.stream()
					.filter(data -> data.getWorkTypeCd() != null)
					.filter(this.distinctByKey(EmployeeDataView::getWorkTypeStrDate))
					.map(data -> {
						return BusinessTypeHistoryModel.builder().employeeId(data.getSid())
								.businessTypeCode(data.getWorkTypeCd()).startDate(data.getWorkTypeStrDate())
								.endDate(data.getWorkTypeEndDate()).build();
					}).collect(Collectors.toList());
			info.setBusinessTypeHistorys(businessTypes);
		} else {
			info.setBusinessTypeHistorys(Collections.emptyList());
		}
		return info;
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

}
