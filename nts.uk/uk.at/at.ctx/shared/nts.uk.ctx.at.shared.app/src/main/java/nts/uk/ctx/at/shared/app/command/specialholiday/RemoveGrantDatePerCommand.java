package nts.uk.ctx.at.shared.app.command.specialholiday;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RemoveGrantDatePerCommand {
	/*付与日のID*/
	private String specialHolidayCode;

	/*特別休暇コード*/
	private String personalGrantDateCode;
}
