/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.screen.at.app.kmk004.workplace.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The Class Kmk004WkpDeleteCommand.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Kmk004WkpDeleteCommand {

	/** The year. */
	private Integer year;
	
	/** The wkp id. */
	private String workplaceId;
	
	/** The is WT setting common remove. */
	private boolean isWTSettingCommonRemove;
	
}
