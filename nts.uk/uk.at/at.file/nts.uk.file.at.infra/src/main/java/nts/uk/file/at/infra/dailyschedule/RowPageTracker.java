package nts.uk.file.at.infra.dailyschedule;

import lombok.Data;
import nts.uk.file.at.app.export.dailyschedule.FormOutputType;

/**
 * The Class RowPageTracker.
 *
 * @author HoangNDH
 * Class for tracking remaining row and page count and perform page break when there is insufficient row
 */
@Data
public class RowPageTracker {
	
	/** The remaining row. */
	int remainingRow;
	
	/** The page count. */
	int pageCount;
	
	/** The max row allowed. */
	int maxRowAllowed;
	
	private static final int MAX_ROW_PER_PAGE_EMPLOYEE_1 = 24;
	private static final int MAX_ROW_PER_PAGE_EMPLOYEE_2 = 22;
	private static final int MAX_ROW_PER_PAGE_EMPLOYEE_3 = 23;
	
	private static final int MAX_ROW_PER_PAGE_DATE_1 = 24;
	private static final int MAX_ROW_PER_PAGE_DATE_2 = 22;
	private static final int MAX_ROW_PER_PAGE_DATE_3 = 20;
	
	/**
	 * Check remaining row sufficient.
	 *
	 * @param rowUse the row use
	 * @return the int
	 */
	int checkRemainingRowSufficient(int rowUse) {
		return remainingRow - rowUse;
	}
	
	/**
	 * Adds the page count.
	 */
	void addPageCount () {
		pageCount++;
	}
	
	/**
	 * Inits the max row allowed.
	 *
	 * @param dataRowCount the data row count
	 */
	void initMaxRowAllowed(int dataRowCount, FormOutputType outputType) {
		switch(dataRowCount) {
		case 1:
			if (outputType == FormOutputType.BY_EMPLOYEE)
				maxRowAllowed = MAX_ROW_PER_PAGE_EMPLOYEE_1;
			else
				maxRowAllowed = MAX_ROW_PER_PAGE_DATE_1;
			break;
		case 2:
			if (outputType == FormOutputType.BY_EMPLOYEE)
				maxRowAllowed = MAX_ROW_PER_PAGE_EMPLOYEE_2;
			else
				maxRowAllowed = MAX_ROW_PER_PAGE_DATE_2;
			break;
		case 3:
			if (outputType == FormOutputType.BY_EMPLOYEE)
				maxRowAllowed = MAX_ROW_PER_PAGE_EMPLOYEE_3;
			else
				maxRowAllowed = MAX_ROW_PER_PAGE_DATE_3;
			break;
		}
		remainingRow = maxRowAllowed;
	}
	
	/**
	 * Use remaining row.
	 *
	 * @param dataRowCount the data row count
	 */
	void useRemainingRow(int dataRowCount) {
		remainingRow -= dataRowCount;
	}
	
	/**
	 * Use one row and check reset remaining row.
	 */
	void useOneRowAndCheckResetRemainingRow() {
		remainingRow--;
		if (remainingRow == 0) {
			resetRemainingRow();
		}
	}
	
	/**
	 * Reset remaining row.
	 */
	void resetRemainingRow() {
		remainingRow = maxRowAllowed;;
	}
}
