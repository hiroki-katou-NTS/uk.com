/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.file.at.app.export.statement;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.function.app.find.statement.export.EmployeeInfor;

/**
 * The Class OutputConditionOfEmbossingQuery.
 */

@Getter
@NoArgsConstructor
public class OutputConditionOfEmbossingQuery {
	
	/** The start date. */
	private String startDate;
	
	/** The end date. */
	private String endDate;
	
	/** The lst employee. */
	private List<EmployeeInfor> lstEmployee;
	
	/** The output set code. */
	private String outputSetCode;
	
	/** The card num not register. */
	private boolean cardNumNotRegister;
}
