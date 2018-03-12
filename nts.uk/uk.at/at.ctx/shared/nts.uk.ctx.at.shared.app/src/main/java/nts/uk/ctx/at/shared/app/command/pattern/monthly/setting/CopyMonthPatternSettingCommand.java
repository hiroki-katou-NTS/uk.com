/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.pattern.monthly.setting;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class CopyMonthPatternSettingCommand.
 */
@Getter
@Setter
public class CopyMonthPatternSettingCommand {

	/** The source sid. */
	private String sourceSid;

	/** The dest sid. */
	private List<String> destSid;
	
	/** The is overwrite. */
	private int isOverwrite;
}
