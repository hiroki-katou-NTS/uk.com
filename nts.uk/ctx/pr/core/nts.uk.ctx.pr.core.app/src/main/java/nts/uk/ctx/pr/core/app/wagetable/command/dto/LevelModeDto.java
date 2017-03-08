/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.wagetable.command.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.pr.core.dom.wagetable.element.CodeItem;

/**
 * The Class LevelMode.
 */
@Getter
@Setter
public class LevelModeDto extends BaseModeDto {

	/** The items. */
	private List<CodeItem> items;

}
