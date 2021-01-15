package nts.uk.ctx.at.shared.dom.scherec.aggregation.perdaily;

import lombok.Value;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.affiliationinfor.AffiliationInforOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.WorkInfoOfDailyAttendance;

/**
 * 所属情報付き勤務情報
 * @author kumiko_otake
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared(勤務予定、勤務実績).集計処理.日単位集計.所属情報付き勤務情報
 */
@Value
public class WorkInfoWithAffiliationInfo {

	/** 所属情報 **/
	private final AffiliationInforOfDailyAttd affiliationInfo;

	/** 勤務情報 **/
	private final WorkInfoOfDailyAttendance workInfo;

}
