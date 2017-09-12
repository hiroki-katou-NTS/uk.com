/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.overtime.breakdown;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class OvertimeSaveCommand.
 */
@Getter
@Setter
public class OvertimeBRDItemSaveCommand {

	/** The overtime BRD items. */
	private List<OvertimeBRDItemSaveDto> overtimeBRDItems;
	
}
