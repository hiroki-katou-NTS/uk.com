/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.app.wageledger.command;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import nts.uk.ctx.pr.report.app.wageledger.command.dto.CategorySettingDto;

/**
 * The Class OutputSettingCommand.
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OutputSettingCommand {
	/** The code. */
	public String code;
	
	/** The name. */
	public String name;
	
	/** The once sheet per person. */
	public boolean onceSheetPerPerson;
	
	/** The category settings. */
	public List<CategorySettingDto> categorySettings;

}
