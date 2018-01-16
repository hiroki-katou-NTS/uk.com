/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.file.at.app.export.outsideot.data;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class OutsideOTBRDItemNameLangData.
 */
@Getter
@Setter
public class OutsideOTBRDItemNameLangData{
	
	/** The language. */
	private String language;
	
	/** The overtime no. */
	private Integer breakdownItemNo;
	
	/** The is use. */
	private Boolean isUse;
	
	/** The product number. */
	private int productNumber;
	
	/** The language other. */
	private String languageOther;
	

	/**
	 * Instantiates a new outside OTBRD item name lang data.
	 *
	 * @param language the language
	 * @param breakdownItemNo the breakdown item no
	 */
	public OutsideOTBRDItemNameLangData(String language, Integer breakdownItemNo) {
		this.language = language;
		this.breakdownItemNo = breakdownItemNo;
	}

	

}
