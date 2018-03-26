package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.overtime;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.ScheAndRecordSameChangeFlg;

public interface AfterOvertimeReflectProcess {
	/**
	 * 予定勤種就時を反映できるかチェックする
	 * @param employeeId
	 * @param baseDate
	 * @param scheAndRecordSameChangeFlg
	 * @param workTimeCode
	 * @return
	 */
	public boolean checkScheReflect(String employeeId, GeneralDate baseDate, ScheAndRecordSameChangeFlg scheAndRecordSameChangeFlg, String workTimeCode);

}
