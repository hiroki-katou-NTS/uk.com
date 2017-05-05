/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.app.payment.contact.find.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class ContactItemsSettingFindDto.
 */
@Getter
@Setter
public class ContactItemsSettingFindDto {

	/** The emp cds. */
	private List<String> empCds;

	/** The processing no. */
	private int processingNo;

	/** The processing ym. */
	private int processingYm;

}
