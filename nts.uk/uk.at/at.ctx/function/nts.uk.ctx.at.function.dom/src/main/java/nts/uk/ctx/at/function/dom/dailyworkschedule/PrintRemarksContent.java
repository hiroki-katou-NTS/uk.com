/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.function.dom.dailyworkschedule;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * The Class PrintRemarksContent.
 * @author HoangDD
 */
// 印刷する備考内容
@Getter
public class PrintRemarksContent extends DomainObject{
	
	/** The used classification. */
	// 使用区分
	private boolean usedClassification;
	
	/** The printitem. */
	// 印刷項目
	private RemarksContentChoice printItem;
	
	/** The Constant USE. */
	private static final int USE = 1;

	/**
	 * Instantiates a new prints the remarks content.
	 *
	 * @param usedClassification the used classification
	 * @param printitem the printitem
	 */
	public PrintRemarksContent(int usedClassification, int printitem) {
		super();
		this.usedClassification = usedClassification == USE ? true : false;
		this.printItem = RemarksContentChoice.valueOf(printitem);
	}
}
