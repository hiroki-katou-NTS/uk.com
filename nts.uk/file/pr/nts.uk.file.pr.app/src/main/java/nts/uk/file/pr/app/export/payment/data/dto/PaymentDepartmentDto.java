/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.file.pr.app.export.payment.data.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentDepartmentDto {

	/** The department code. */
	private String departmentCode;

	/** The department name. */
	private String departmentName;

	/** The department output. */
	private String departmentOutput;

	/**
	 * Gets the preview data.
	 *
	 * @return the preview data
	 */
	public static PaymentDepartmentDto getPreviewData() {
		PaymentDepartmentDto dto = new PaymentDepartmentDto();
		dto.setDepartmentCode(PreviewData.DEPARTMENT_CODE);
		dto.setDepartmentName(PreviewData.DEPARTMENT_NAME);
		return dto;
	}
}
