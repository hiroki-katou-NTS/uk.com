package nts.uk.ctx.at.shared.app.command.specialholiday.specialholidayevent;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DeleteSpecialHolidayEventCommand {

	/* 特別休暇枠NO */
	private int specialHolidayEventNo;
}
