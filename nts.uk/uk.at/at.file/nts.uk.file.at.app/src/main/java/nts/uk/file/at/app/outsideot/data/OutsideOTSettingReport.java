/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.file.at.app.outsideot.data;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class OutsideOTSettingReport.
 */
@Getter
@Setter
public class OutsideOTSettingReport {
	
	/** The number rows. */
	private int numberRows;
	
	/** The number cols. */
	private int numberCols;
	
	/** The value. */
	private Object value;
	

	public OutsideOTSettingReport(int numberRows, int numberCols, Object value) {
		super();
		this.numberRows = numberRows;
		this.numberCols = numberCols;
		this.value = value;
	}
	
	

}
