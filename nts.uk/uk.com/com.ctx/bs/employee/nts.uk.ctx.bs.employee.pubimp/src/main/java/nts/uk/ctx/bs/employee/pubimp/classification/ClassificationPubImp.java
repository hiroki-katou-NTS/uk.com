/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.pubimp.classification;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.dom.classification.Classification;
import nts.uk.ctx.bs.employee.dom.classification.ClassificationRepository;
import nts.uk.ctx.bs.employee.dom.classification.affiliate.AffClassHistItem;
import nts.uk.ctx.bs.employee.dom.classification.affiliate.AffClassHistItemRepository;
import nts.uk.ctx.bs.employee.dom.classification.affiliate.AffClassHistoryRepository;
import nts.uk.ctx.bs.employee.pub.classification.SClsHistExport;
import nts.uk.ctx.bs.employee.pub.classification.SyClassificationPub;
import nts.uk.shr.com.history.DateHistoryItem;

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

}
