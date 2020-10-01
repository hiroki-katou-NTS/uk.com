package nts.uk.ctx.at.schedule.dom.shift.management.workexpect;

import lombok.Value;
import nts.arc.time.GeneralDate;

/**
 * 一日分の勤務希望の表示情報
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定.シフト管理.シフト勤務.勤務希望.一日分の勤務希望の表示情報
 * @author dan_pv
 *
 */
@Value
public class WorkExpectDisplayInfoOfOneDay {
	
	private String employeeId;
	
	private GeneralDate expectingDate;
	
	private WorkExpectationMemo memo;
	
	private WorkExpectDisplayInfo displayInfo;

}
