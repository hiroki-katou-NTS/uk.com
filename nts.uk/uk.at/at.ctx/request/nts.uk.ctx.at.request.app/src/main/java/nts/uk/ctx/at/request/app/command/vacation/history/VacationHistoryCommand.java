/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.request.app.command.vacation.history;


import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.request.app.command.setting.vacation.history.dto.VacationHistoryDto;

@Setter
@Getter
public class VacationHistoryCommand {
	
	/** The is create mode. */
	private Boolean isCreateMode;
	
	/** The max day. */
	private Integer maxDay;
	
	/** The work type code. */
	private String workTypeCode;
	
	/** The vacation dto. */
	private VacationHistoryDto vacationHistory;
}
