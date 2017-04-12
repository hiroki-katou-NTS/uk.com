/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
/**
 * 
 */
package nts.uk.file.pr.app.export.detailpaymentsalary.query;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class PaymentSalaryQuery.
 */

/**
 * Sets the end date.
 *
 * @param endDate
 *            the new end date
 */
@Setter

/**
 * Gets the end date.
 *
 * @return the end date
 */
@Getter
public class PaymentSalaryQuery {

	/** The output setting code. */
	private String outputSettingCode;

	/** The employee codes. */
	private List<String> employeeCodes;

	/** The start date. */
	private Integer startDate;

	/** The end date. */
	private Integer endDate;
	
	/** The output format type. */
	private String outputFormatType;


	/** The is vertical line. */
	private boolean isVerticalLine;

	/** The is horizontal line. */
	private boolean isHorizontalLine;

	/** The output language. */
	private String outputLanguage;

	/** The page break setting. */
	private String pageBreakSetting;

	/** The hierarchy. */
	private String hierarchy;

}
