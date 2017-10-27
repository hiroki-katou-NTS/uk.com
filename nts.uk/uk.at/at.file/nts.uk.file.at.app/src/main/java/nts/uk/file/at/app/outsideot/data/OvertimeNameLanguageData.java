/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.file.at.app.outsideot.data;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class OvertimeNameLanguageData.
 */

@Getter
@Setter
public class OvertimeNameLanguageData{

	/** The language. */
	private String language;
	
	/** The overtime no. */
	private Integer overtimeNo;

	public OvertimeNameLanguageData(String language, Integer overtimeNo) {
		this.language = language;
		this.overtimeNo = overtimeNo;
	}
	

}
