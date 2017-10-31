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
import nts.uk.ctx.bs.employee.dom.classification.affiliate.AffClassHistory;
import nts.uk.ctx.bs.employee.dom.classification.affiliate.AffClassHistoryRepository;
import nts.uk.ctx.bs.employee.pub.classification.SClsHistExport;
import nts.uk.ctx.bs.employee.pub.classification.SyClassificationPub;

/**
 * The Class ClassificationPubImp.
 */
@Stateless
public class ClassificationPubImp implements SyClassificationPub {

	/** The employment repository. */
	@Inject
	private ClassificationRepository classificationRepository;

	/** The employment history repository. */
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
		// Query
		Optional<AffClassHistory> optAffEmploymentHist = affClassHistoryRepository
				.getAssignedClassificationBy(employeeId, baseDate);

		// Check exist
		if (!optAffEmploymentHist.isPresent()) {
			return Optional.empty();
		}

		AffClassHistory empHist = optAffEmploymentHist.get();

		// Find emp by empCd
		Optional<Classification> optClassification = classificationRepository
				.findClassification(companyId, empHist.getClassificationCode().v());

		// Get employment info
		Classification classification = optClassification.get();

		// Return
		return Optional.of(SClsHistExport.builder().employeeId(employeeId)
				.classificationCode(classification.getClassificationCode().v())
				.classificationName(classification.getClassificationName().v())
				.period(empHist.getPeriod()).build());
	}

}
