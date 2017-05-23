/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.file.pr.app.export.payment.data.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class CompanyDto.
 */
@Getter
@Setter
public class PaymentCompanyDto implements Serializable{

	private static final long serialVersionUID = 1L;
	
	/** The japanese year month. */
	private String japaneseYearMonth;
	
	/** The name. */
	private String name;

}
