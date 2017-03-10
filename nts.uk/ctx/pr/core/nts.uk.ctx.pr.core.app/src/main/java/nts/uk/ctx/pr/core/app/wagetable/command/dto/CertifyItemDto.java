/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.wagetable.command.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class CertifyItem.
 */
@Getter
@Setter
public class CertifyItemDto extends CodeItemDto {

	CertifyItemDto(String referenceCode, String uuid) {
		super(referenceCode, uuid);
	}

}
