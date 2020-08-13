package nts.uk.ctx.at.shared.app.find.workrule.shiftmaster;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.shared.app.command.worktime.worktimeset.dto.WorkTimeDisplayNameDto;
import nts.uk.ctx.at.shared.app.command.worktime.worktimeset.dto.WorkTimeDivisionDto;

@Data
@AllArgsConstructor
public class WorkTimeSettingsDto {
	public String companyId;

	/** The worktime code. */
	public String worktimeCode;

	/** The work time division. */
	public WorkTimeDivisionDto workTimeDivision;

	/** The is abolish. */
	public int isAbolish;

	/** The color code. */
	public String colorCode;

	/** The work time display name. */
	public WorkTimeDisplayNameDto workTimeDisplayName;

	/** The memo. */
	public String memo;

	/** The note. */
	public String note;
}
