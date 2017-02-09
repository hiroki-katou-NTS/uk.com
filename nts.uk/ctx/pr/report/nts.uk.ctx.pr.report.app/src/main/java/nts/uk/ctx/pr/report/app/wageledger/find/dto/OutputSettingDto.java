/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.app.wageledger.find.dto;

import java.util.List;

import lombok.Builder;

/**
 * The Class OutputSettingDto.
 */
@Builder
public class OutputSettingDto {
	
	/** The code. */
	public String code;
	
	/** The name. */
	public String name;
	
	/** The is once sheet per person. */
	public boolean isOnceSheetPerPerson;
	
	/** The category settings. */
	public List<CategorySettingDto> categorySettings;
	
}
