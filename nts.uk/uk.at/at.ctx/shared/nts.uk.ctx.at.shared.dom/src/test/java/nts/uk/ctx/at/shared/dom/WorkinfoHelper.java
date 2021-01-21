package nts.uk.ctx.at.shared.dom;

import nts.uk.ctx.at.shared.dom.common.color.ColorCode;
import nts.uk.ctx.at.shared.dom.worktime.common.AbolishAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeDisplayName;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeDivision;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeNote;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.shr.com.primitive.Memo;

public class WorkinfoHelper {
	public static WorkTimeSetting getWorkTimeSettingDefault() {
		return new WorkTimeSetting("companyId",
				new WorkTimeCode("WorkTimeCode"), 
				new WorkTimeDivision(), AbolishAtr.ABOLISH, new ColorCode("colorCode"),
				new WorkTimeDisplayName(), new Memo("memo"), new WorkTimeNote("WorkTimeNote"));
		
	}
}
