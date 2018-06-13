/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.file.at.app.export.monthlyschedule;

import java.util.List;

import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.uk.file.at.app.export.dailyschedule.FileOutputType;

/**
 * The Class MonthlyWorkScheduleQuery.
 */

@Data
public class MonthlyWorkScheduleQuery implements Cloneable {
	
	/** The start date. */
	private GeneralDate startDate;
	
	/** The end date. */
	private GeneralDate endDate;
	
	/** The workplace ids. */
	private List<String> workplaceIds;
	
	/** The condition. */
	private MonthlyWorkScheduleCondition condition;
	
	/** The file type. */
	private FileOutputType fileType;
	
}
