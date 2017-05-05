/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.app.payment.contact.find.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ContactPersonalSettingDto {
	private String employeeId;
	private String comment;
}
