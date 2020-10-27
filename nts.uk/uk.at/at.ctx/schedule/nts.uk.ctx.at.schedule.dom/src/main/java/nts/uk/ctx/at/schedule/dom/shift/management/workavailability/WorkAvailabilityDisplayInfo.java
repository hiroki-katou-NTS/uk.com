package nts.uk.ctx.at.schedule.dom.shift.management.workavailability;

import java.util.List;

import lombok.Value;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;

/**
 * 勤務希望の表示情報
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定.シフト管理.シフト勤務.勤務希望.勤務希望の表示情報
 * @author dan_pv
 *
 */
@Value
public class WorkAvailabilityDisplayInfo {
	
	private AssignmentMethod method;
	
	private List<String> nameList;
	
	private List<TimeSpanForCalc> timeZoneList;

}
