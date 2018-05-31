/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.query.pub.person;

import nts.arc.time.GeneralDateTime;
import nts.uk.query.model.person.ClassificationHistoryModel;

/**
 * The Class ClassificationHistoryModel.
 */
public class ClassificationHistoryExport {
	
	/** The employee id. */
	public String employeeId;
	
	/** The classification code. */
	public String classificationCode;
	
	/** The start date. */
	public GeneralDateTime startDate;
	
	/** The end date. */
	public GeneralDateTime endDate;
	
	/**
	 * Instantiates a new employment history export.
	 *
	 * @param res the res
	 */
	public ClassificationHistoryExport(ClassificationHistoryModel res) {
		this.employeeId = res.getEmployeeId();
		this.classificationCode = res.getClassificationCode();
		this.startDate = res.getStartDate();
		this.endDate = res.getEndDate();
	}
}
