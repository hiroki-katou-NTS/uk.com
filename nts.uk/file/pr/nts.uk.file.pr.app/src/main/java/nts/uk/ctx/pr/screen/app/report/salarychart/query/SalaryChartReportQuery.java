/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.screen.app.report.salarychart.query;
import java.util.List;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * The Class SalaryChartReportQuery.
 */

/**
 * Instantiates a new salary chart report query.
 */

/**
 * Instantiates a new salary chart report query.
 */

/**
 * Instantiates a new salary chart report query.
 */

/**
 * Instantiates a new salary chart report query.
 */

/**
 * Instantiates a new salary chart report query.
 */

/* (non-Javadoc)
 * @see java.lang.Object#toString()
 */
@Data

/* (non-Javadoc)
 * @see java.lang.Object#toString()
 */
@Builder

/**
 * Gets the checks if is break page by accumulated.
 *
 * @return the checks if is break page by accumulated
 */
@Getter

/**
 * Sets the checks if is break page by accumulated.
 *
 * @param isBreakPageByAccumulated the new checks if is break page by accumulated
 */
@Setter
public class SalaryChartReportQuery {

	/** The target year. */
	private Integer targetYear;
	
	/** The emp id list. */
	private List<String> empIdList;
	
	/** The selected division. */
	private String selectedDivision;	
	
	/** The is print detail item. */
	private Boolean isPrintDetailItem;
	
	/** The is print total of department. */
	private Boolean isPrintTotalOfDepartment;
	
	/** The is print dep hierarchy. */
	private Boolean isPrintDepHierarchy;
	
	/** The selected levels. */ 
	//private int selectedDepHierarchy;
	private List<Integer> selectedLevels;
	
	/** The is calculate total. */
	private Boolean isCalculateTotal;
	
	/** The selected break page code. */
	private Integer selectedBreakPageCode;
	
//	/** The is break page by employee. */
//	private boolean isBreakPageByEmployee;
//	
//	/** The is break page by department. */
//	private boolean isBreakPageByDepartment;
//	
//	/** The is break page by accumulated. */
//	private boolean isBreakPageByAccumulated;

	/** The selected use 2000 yen. */
	private Integer selectedUse2000yen; 
//	
//	/** The is break page hierarchy. */
//	public boolean isBreakPageHierarchy;
	
	/** The selected break page hierarchy code. */
	private Integer selectedBreakPageHierarchyCode;
	
	/** The is break page by accumulated. */
	private Boolean isBreakPageByAccumulated;
}
