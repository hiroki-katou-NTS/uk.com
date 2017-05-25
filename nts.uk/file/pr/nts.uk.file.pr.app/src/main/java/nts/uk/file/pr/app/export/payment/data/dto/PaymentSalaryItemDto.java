/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.file.pr.app.export.payment.data.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class PaymentSalaryItemDto.
 */
@Getter
@Setter
public class PaymentSalaryItemDto {

	/** The item name. */
	private String itemName;

	/** The item val. */
	private BigDecimal itemVal;

	/** The is view. */
	private boolean isView;

	/**
	 * Default data.
	 *
	 * @return the payment salary item dto
	 */
	public static PaymentSalaryItemDto defaultData() {
		PaymentSalaryItemDto dto = new PaymentSalaryItemDto();
		dto.setItemName("");
		dto.setItemVal(BigDecimal.ZERO);
		dto.setView(false);
		return dto;
	}

	/**
	 * Gets the preview data.
	 *
	 * @return the preview data
	 */
	public static PaymentSalaryItemDto getPreviewData() {
		PaymentSalaryItemDto dto = new PaymentSalaryItemDto();
		dto.setItemName("NNNNNN");
		dto.setItemVal(BigDecimal.valueOf(999999999));
		dto.setView(true);
		return dto;
	}

	/**
	 * Gets the preview time data.
	 *
	 * @return the preview time data
	 */
	public static PaymentSalaryItemDto getPreviewTimeData() {
		PaymentSalaryItemDto dto = new PaymentSalaryItemDto();
		dto.setItemName("NNNNNN");
		dto.setItemVal(BigDecimal.valueOf(99959));
		dto.setView(true);
		return dto;
	}
}
