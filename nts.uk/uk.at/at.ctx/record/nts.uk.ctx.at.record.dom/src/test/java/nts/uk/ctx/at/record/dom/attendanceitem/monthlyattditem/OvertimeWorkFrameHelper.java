package nts.uk.ctx.at.record.dom.attendanceitem.monthlyattditem;

import java.math.BigDecimal;

import nts.uk.ctx.at.shared.dom.ot.frame.NotUseAtr;
import nts.uk.ctx.at.shared.dom.ot.frame.OvertimeWorkFrame;
import nts.uk.ctx.at.shared.dom.ot.frame.OvertimeWorkFrameName;
import nts.uk.ctx.at.shared.dom.ot.frame.OvertimeWorkFrameNo;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.roleofovertimework.roleofovertimework.RoleOvertimeWorkEnum;

public class OvertimeWorkFrameHelper {
	public static OvertimeWorkFrame createOvertimeWorkFrameByNoAndUseAtr(int no, NotUseAtr useClassification) {
		return new OvertimeWorkFrame("companyId", new OvertimeWorkFrameNo(new BigDecimal(no)), useClassification,
				new OvertimeWorkFrameName("transferFrName"), new OvertimeWorkFrameName("overtimeWorkFrName"),
				RoleOvertimeWorkEnum.MIX_IN_OUT_STATUTORY, NotUseAtr.USE);
	}
}
