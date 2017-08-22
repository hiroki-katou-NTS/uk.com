package nts.uk.ctx.at.shared.app.command.specialholiday;

import lombok.Data;
import nts.uk.ctx.at.shared.dom.specialholiday.SpecialHolidayCode;

@Data
public class RemoveSpecialHolidayCommand {

	/*会社ID*/
	private String companyId;

	/*特別休暇コード*/
	private String specialHolidayCode;
}
