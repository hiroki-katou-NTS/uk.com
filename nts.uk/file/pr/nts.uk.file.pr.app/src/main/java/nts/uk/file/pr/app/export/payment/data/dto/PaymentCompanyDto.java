/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.file.pr.app.export.payment.data.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class PaymentCompanyDto.
 */
@Getter
@Setter
public class PaymentCompanyDto implements Serializable {

	private static final long serialVersionUID = 1L;

	/** The japanese year month. */
	private String japaneseYearMonth;

	/** The name. */
	private String name;

	/** The postal code. */
	private String postalCode;

	/** The address one. */
	private String addressOne;

	/** The address two. */
	private String addressTwo;

	/**
	 * Gets the preview data.
	 *
	 * @return the preview data
	 */
	public static PaymentCompanyDto getPreviewData() {
		PaymentCompanyDto dto = new PaymentCompanyDto();
		dto.setJapaneseYearMonth("9999年99月給与");
		return dto;
	}
}
