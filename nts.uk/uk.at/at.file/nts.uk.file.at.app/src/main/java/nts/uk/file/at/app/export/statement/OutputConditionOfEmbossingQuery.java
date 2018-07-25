/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.file.at.app.export.statement;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;

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

	/**
	 * Instantiates a new output condition of embossing query.
	 *
	 * @param startDate the start date
	 * @param endDate the end date
	 * @param lstEmployee the lst employee
	 * @param outputSetCode the output set code
	 * @param cardNumNotRegister the card num not register
	 */
//	public OutputConditionOfEmbossingQuery(String startDate, String endDate, List<EmployeeInfor> lstEmployee,
//			String outputSetCode, boolean cardNumNotRegister) {
//		super();
//		this.startDate = GeneralDate.fromString(startDate, "YYYY/MM/DD");
//		this.endDate = GeneralDate.fromString(endDate, "YYYY/MM/DD");
//		this.lstEmployee = lstEmployee;
//		this.outputSetCode = outputSetCode;
//		this.cardNumNotRegister = cardNumNotRegister;
//	}
}
