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

/**
 * Gets the view time.
 *
 * @return the view time
 */
@Getter
@Setter
public class OvertimeNameLanguageData{

	/** The language. */
	private String language;
	
	/** The overtime no. */
	private Integer overtimeNo;
	
	/** The is use. */
	private Boolean isUse;
	
	/** The view time. */
	private String viewTime;

	/**
	 * Instantiates a new overtime name language data.
	 *
	 * @param language the language
	 * @param overtimeNo the overtime no
	 * @param isUse the is use
	 */
	public OvertimeNameLanguageData(String language, Integer overtimeNo, Boolean isUse) {
		super();
		this.language = language;
		this.overtimeNo = overtimeNo;
		this.isUse = isUse;
	}

	

}
