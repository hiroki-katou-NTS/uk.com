package nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.condition;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.shared.dom.common.TimeZoneWithWorkNo;
import nts.uk.ctx.at.shared.dom.scherec.application.common.PrePostAtrShare;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.DailyRecordOfApplication;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.ScheduleRecordClassifi;

/**
 * @author thanh_nx
 *
 *         始業終業の反映
 */
public class ReflectStartEndWork {

	//「出勤.時刻」と「退勤.時刻」を更新する
	public static List<Integer> reflect(Require require, String cid, DailyRecordOfApplication dailyApp,
			List<TimeZoneWithWorkNo> timeZoneWithWorkNoLst, PrePostAtrShare prePostAtr) {
		List<Integer> itemIds = new ArrayList<Integer>();
		// [input:事前事後区分]をチェック
		if (prePostAtr == PrePostAtrShare.PREDICT) {
			// 日別勤怠(work)の予定勤務時間帯に[input:申請勤務時間帯]をセットする
			itemIds.addAll(ReflectAttendance.reflect(require, cid, timeZoneWithWorkNoLst, ScheduleRecordClassifi.SCHEDULE, dailyApp,
					Optional.of(true), Optional.of(true), Optional.empty()));
		}

		return itemIds;
	}

	public static interface Require  extends ReflectAttendance.Require{
		
	}
}
