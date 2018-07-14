package nts.uk.ctx.at.shared.app.command.specialholiday.specialholidayevent;

import lombok.Data;

@Data
public class DeleteSpecialHolidayEventCommand {
	/* 会社ID */
	private String companyId;

	/* 特別休暇枠NO */
	private int specialHolidayEventNo;
}
