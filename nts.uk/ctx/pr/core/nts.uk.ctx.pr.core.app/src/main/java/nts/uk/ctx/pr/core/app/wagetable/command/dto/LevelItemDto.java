/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.wagetable.command.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class LevelItem.
 */
@Getter
@Setter
public class LevelItemDto extends CodeItemDto {

	LevelItemDto(String referenceCode, String uuid) {
		super(referenceCode, uuid);
	}

}
