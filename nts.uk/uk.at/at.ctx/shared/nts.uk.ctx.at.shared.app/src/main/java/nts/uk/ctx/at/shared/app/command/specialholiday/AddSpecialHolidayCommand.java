package nts.uk.ctx.at.shared.app.command.specialholiday;

import lombok.Data;
import nts.uk.ctx.at.shared.dom.specialholiday.GrantPeriodicCls;
import nts.uk.ctx.at.shared.dom.specialholiday.SpecialHolidayCode;
import nts.uk.ctx.at.shared.dom.specialholiday.SpecialHolidayName;
import nts.uk.shr.com.primitive.Memo;

@Data
public class AddSpecialHolidayCommand {

	/*会社ID*/
	private String companyId;

	/*特別休暇コード*/
	private SpecialHolidayCode specialHolidayCode;

	/*特別休暇名称*/
	private SpecialHolidayName specialHolidayName;

	/*定期付与*/
	private GrantPeriodicCls grantPeriodicCls;

	/*メモ*/
	private Memo memo;
	
}
