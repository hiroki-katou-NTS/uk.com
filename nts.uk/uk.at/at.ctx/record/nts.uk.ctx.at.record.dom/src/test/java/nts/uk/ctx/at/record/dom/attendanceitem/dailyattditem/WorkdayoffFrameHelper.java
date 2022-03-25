package nts.uk.ctx.at.record.dom.attendanceitem.dailyattditem;

import java.math.BigDecimal;

import nts.uk.ctx.at.shared.dom.workdayoff.frame.NotUseAtr;
import nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrame;
import nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrameName;
import nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrameNo;
import nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrameRole;


public class WorkdayoffFrameHelper {
	public static WorkdayoffFrame createWorkdayoffFrameTestByNoAndUseAtr(int no, NotUseAtr useClassification) {
		return new WorkdayoffFrame("companyId", new WorkdayoffFrameNo(new BigDecimal(no)), useClassification, new WorkdayoffFrameName("transferFrName"),
				new WorkdayoffFrameName("workdayoffFrName"), WorkdayoffFrameRole.MIX_WITHIN_OUTSIDE_STATUTORY);
	}
}
