package nts.uk.ctx.at.shared.dom.workdayoff.frame;

import java.math.BigDecimal;


public class WorkdayoffFrameHelper {
	public static WorkdayoffFrame createWorkdayoffFrameTestByNoAndUseAtr(int no, NotUseAtr useClassification) {
		return new WorkdayoffFrame("companyId", new WorkdayoffFrameNo(new BigDecimal(no)), useClassification, new WorkdayoffFrameName("transferFrName"),
				new WorkdayoffFrameName("workdayoffFrName"), WorkdayoffFrameRole.MIX_WITHIN_OUTSIDE_STATUTORY);
	}
}
