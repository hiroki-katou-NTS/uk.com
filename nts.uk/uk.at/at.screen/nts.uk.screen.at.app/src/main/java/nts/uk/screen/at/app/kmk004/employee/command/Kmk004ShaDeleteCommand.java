/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.screen.at.app.kmk004.employee.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The Class Kmk004ShaDeleteCommand.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Kmk004ShaDeleteCommand {

	/** The year. */
	private Integer year;
	
	/** The sid. */
	private String sid;
	
	/** The is WT setting common remove. */
	private boolean isWTSettingCommonRemove;
}
