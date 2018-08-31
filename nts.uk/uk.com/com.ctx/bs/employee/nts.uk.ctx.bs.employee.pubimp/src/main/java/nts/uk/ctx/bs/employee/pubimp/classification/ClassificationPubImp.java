/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.pubimp.classification;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.dom.classification.Classification;
import nts.uk.ctx.bs.employee.dom.classification.ClassificationRepository;
import nts.uk.ctx.bs.employee.dom.classification.affiliate.AffClassHistItem;
import nts.uk.ctx.bs.employee.dom.classification.affiliate.AffClassHistItemRepository;
import nts.uk.ctx.bs.employee.dom.classification.affiliate.AffClassHistory;
import nts.uk.ctx.bs.employee.dom.classification.affiliate.AffClassHistoryRepository;
import nts.uk.ctx.bs.employee.pub.classification.SClsHistExport;
import nts.uk.ctx.bs.employee.pub.classification.SyClassificationPub;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * The Class ClassificationPubImp.
 */
@Stateless
public class ClassificationPubImp implements SyClassificationPub {

	/** The classification repository. */
	@Inject
	private ClassificationRepository classificationRepository;

	/** The aff class hist item repository ver 1. */
	@Inject
	private AffClassHistItemRepository affClassHistItemRepository;

	/** The aff class history repository ver 1. */
	@Inject
	private AffClassHistoryRepository affClassHistoryRepository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.bs.employee.pub.employment.SyEmploymentPub#findSEmpHistBySid(
	 * java.lang.String, java.lang.String, nts.arc.time.GeneralDate)
	 */
	@Override
	public Optional<SClsHistExport> findSClsHistBySid(String companyId, String employeeId,
			GeneralDate baseDate) {

		Optional<DateHistoryItem> dateHistoryItem = affClassHistoryRepository
				.getByEmpIdAndStandardDate(employeeId, baseDate);

		// Check exist
		if (!dateHistoryItem.isPresent()) {
			return Optional.empty();
		}

		Optional<AffClassHistItem> opAffClassHistItem = affClassHistItemRepository
				.getByHistoryId(dateHistoryItem.get().identifier());

		if (!opAffClassHistItem.isPresent()) {
			return Optional.empty();
		}

		// Find emp by empCd
		Optional<Classification> optClassification = classificationRepository.findClassification(
				companyId, opAffClassHistItem.get().getClassificationCode().v());

		if (!optClassification.isPresent()) {
			return Optional.empty();
		}

		// Get employment info
		Classification classification = optClassification.get();

		// Return
		return Optional.of(SClsHistExport.builder().employeeId(employeeId)
				.classificationCode(classification.getClassificationCode().v())
				.classificationName(classification.getClassificationName().v())
				.period(dateHistoryItem.get().span()).build());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.bs.employee.pub.classification.SyClassificationPub#
	 * findSClsHistBySid(java.lang.String, java.lang.String,
	 * nts.uk.shr.com.time.calendar.period.DatePeriod)
	 */
	@Override
	public List<SClsHistExport> findSClsHistBySid(String companyId, List<String> employeeIds,
			DatePeriod datePeriod) {
		
		List<AffClassHistory> dateHistoryItem = affClassHistoryRepository
				.getByEmployeeListWithPeriod(employeeIds, datePeriod);

		Map<String, DatePeriod> histPeriodMap = dateHistoryItem.stream()
				.map(AffClassHistory::getPeriods).flatMap(listContainer -> listContainer.stream())
				.collect(Collectors.toMap(DateHistoryItem::identifier, DateHistoryItem::span));

		List<String> histIds = dateHistoryItem.stream().map(AffClassHistory::getPeriods)
				.flatMap(listContainer -> listContainer.stream()).map(DateHistoryItem::identifier)
				.collect(Collectors.toList());

		List<AffClassHistItem> affClassHistItems = affClassHistItemRepository
				.getByHistoryIds(histIds);

		List<String> clsCds = affClassHistItems.stream()
				.map(item -> item.getClassificationCode().v()).collect(Collectors.toList());

		// Find emp by empCd
		List<Classification> lstClassification = classificationRepository
				.getClassificationByCodes(companyId, clsCds);

		Map<String, String> mapCls = lstClassification.stream()
				.collect(Collectors.toMap(item -> item.getClassificationCode().v(),
						item -> item.getClassificationName().v()));

		// Return
		return affClassHistItems.stream()
				.map(item -> SClsHistExport.builder().employeeId(item.getEmployeeId())
						.classificationCode(item.getClassificationCode().v())
						.classificationName(mapCls.get(item.getClassificationCode().v()))
						.period(histPeriodMap.get(item.getHistoryId())).build())
				.collect(Collectors.toList());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.bs.employee.pub.classification.SyClassificationPub#
	 * getClassificationMap(java.lang.String, java.util.List)
	 */
	@Override
	public Map<String, String> getClassificationMapCodeName(String companyId, List<String> clsCds) {
		List<Classification> lstClassification = classificationRepository
				.getClassificationByCodes(companyId, clsCds);
		return lstClassification.stream()
				.collect(Collectors.toMap(item -> item.getClassificationCode().v(),
						item -> item.getClassificationName().v()));
	}

}
