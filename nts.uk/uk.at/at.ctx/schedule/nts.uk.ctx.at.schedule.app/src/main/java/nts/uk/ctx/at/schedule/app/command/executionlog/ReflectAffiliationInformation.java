package nts.uk.ctx.at.schedule.app.command.executionlog;

import lombok.Getter;
import nts.arc.time.GeneralDate;

@Getter
public class ReflectAffiliationInformation {
	/** 社員ID＝パラメータ（Temporary）の。社員ID */
	private String SID;
	
	/** 年月日＝対象日 */
	private GeneralDate targetDate;
	
	/** 日別勤怠の所属情報＝勤務予定。所属情報 - Chưa làm nên để tạm */
	private String affInfor;
	
	/** 特定期間の社員情報＝パラメータ（Temporary）の特定期間の社員情報 */
	private EmployeeGeneralInfo employeeInfor;
}
