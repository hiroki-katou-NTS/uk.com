/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.app.insurance.command;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.pr.report.app.insurance.command.dto.CheckListPrintSettingDto;

/**
 * The Class CheckListPrintSettingSaveCommand.
 */
@Getter
@Setter
public class CheckListPrintSettingSaveCommand implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The labor insurance office. */
	private CheckListPrintSettingDto checkListPrintSettingDto;
}
