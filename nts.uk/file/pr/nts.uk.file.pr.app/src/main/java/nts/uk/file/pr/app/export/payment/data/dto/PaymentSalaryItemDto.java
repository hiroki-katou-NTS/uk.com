/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.file.pr.app.export.payment.data.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentSalaryItemDto {

	/** The item name. */
	private String itemName;

	/** The item val. */
	private BigDecimal itemVal;

	/** The is view. */
	private boolean isView;

	public static PaymentSalaryItemDto defaultData() {
		PaymentSalaryItemDto dto = new PaymentSalaryItemDto();
		dto.setItemName("");
		dto.setItemVal(BigDecimal.ZERO);
		dto.setView(false);
		return dto;
	}
}
