/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.screen.at.app.kmk004.employment.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The Class Kmk004EmpDeleteCommand.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Kmk004EmpDeleteCommand {

	/** The year. */
	private Integer year;
	
	/** The emp code. */
	private String employmentCode;
	
	/** The is WT setting common remove. */
	private boolean isWTSettingCommonRemove;
}
