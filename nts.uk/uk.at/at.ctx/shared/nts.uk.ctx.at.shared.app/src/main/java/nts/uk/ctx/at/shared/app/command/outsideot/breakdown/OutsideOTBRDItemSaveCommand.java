/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.outsideot.breakdown;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.app.find.outsideot.dto.OutsideOTBRDItemDto;

/**
 * The Class OutsideOTBRDItemSaveCommand.
 */
@Getter
@Setter
public class OutsideOTBRDItemSaveCommand {

	/** The overtime BRD items. */
	private List<OutsideOTBRDItemDto> overtimeBRDItems;
	
}
