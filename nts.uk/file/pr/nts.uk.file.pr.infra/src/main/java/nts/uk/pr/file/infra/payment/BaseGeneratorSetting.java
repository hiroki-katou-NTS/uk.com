/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.pr.file.infra.payment;

import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

/**
 * The Class BaseGeneratorSetting.
 */
public abstract class BaseGeneratorSetting extends AsposeCellsReportGenerator {

	/** The Constant MARGIN_CELL. */
	public static final int MARGIN_CELL = 1;

	/** The Constant FIRST_SHEET. */
	public static final int FIRST_SHEET = 0;

	/** The Constant CATEGORY_HEADER_WIDTH. */
	public static final int CATEGORY_HEADER_WIDTH = 1;

	/** The Constant FIRST_COLUMN. */
	public static final int FIRST_COLUMN = 0;

	/** The Constant FIRST_ROW. */
	public static final int FIRST_ROW = 0;

	/** The Constant FIRST_CELL. */
	public static final String FIRST_CELL = "A1";

	/** The Constant FIRST_PERSON. */
	public static final int FIRST_PERSON = 1;

	/** The Constant SECOND_PERSON. */
	public static final int SECOND_PERSON = 2;

	/** The Constant ITEMS_PER_ROW. */
	public static final int ITEMS_PER_ROW = 9;

	/** The Constant NUMBER_OF_ROW_PER_ITEM. */
	public static final int NUMBER_OF_ROW_PER_ITEM = 1;

	/** The Constant EXCEL_ROW_UNIT_IN_MM. */
	public static final double EXCEL_ROW_UNIT_IN_MM = 0.35;

	/** The Constant EXCEL_COL_UNIT_IN_MM. */
	public static final double EXCEL_COL_UNIT_IN_MM = 3.15;
}
