package nts.uk.ctx.at.schedule.dom.shift.management.workavailability;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import lombok.Value;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMasterCode;

/**
 * 勤務希望の表示情報
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定.シフト管理.シフト勤務.勤務希望.勤務希望の表示情報
 * @author dan_pv
 *
 */
@Value
public class WorkAvailabilityDisplayInfo {
	/** 種類 */
	private AssignmentMethod method;
	
	/** シフトリスト */
	private Map<ShiftMasterCode, Optional<String>> shiftList;
	
	/** 時間帯リスト*/
	private List<TimeSpanForCalc> timeZoneList;

}
