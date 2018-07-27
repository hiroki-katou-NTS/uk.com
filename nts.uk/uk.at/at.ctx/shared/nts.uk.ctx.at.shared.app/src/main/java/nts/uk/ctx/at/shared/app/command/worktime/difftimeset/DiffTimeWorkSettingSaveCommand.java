/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.worktime.difftimeset;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.app.command.worktime.common.WorkTimeCommonSaveCommand;
import nts.uk.ctx.at.shared.app.command.worktime.difftimeset.dto.DiffTimeWorkSettingDto;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeWorkSetting;

/**
 * The Class DiffTimeWorkSettingSaveCommand.
 */
@Getter
@Setter
public class DiffTimeWorkSettingSaveCommand extends WorkTimeCommonSaveCommand {

	/** The diff time work setting dto. */
	private DiffTimeWorkSettingDto diffTimeWorkSetting;

	/**
	 * To domain diff time work setting.
	 *
	 * @return the diff time work setting
	 */
	public DiffTimeWorkSetting toDomainDiffTimeWorkSetting() {
		return new DiffTimeWorkSetting(this.diffTimeWorkSetting);
	}
}
