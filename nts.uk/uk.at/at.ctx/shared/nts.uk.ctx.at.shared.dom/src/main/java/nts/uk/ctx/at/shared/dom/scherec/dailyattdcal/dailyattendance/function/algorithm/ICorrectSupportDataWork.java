package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.function.algorithm;

import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.ScheduleRecordClassifi;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;

public interface ICorrectSupportDataWork {
	public SupportDataWork correctSupportDataWork(IGetAppForCorrectionRuleRequire require, IntegrationOfDaily integrationOfDaily, ScheduleRecordClassifi classification);
}
