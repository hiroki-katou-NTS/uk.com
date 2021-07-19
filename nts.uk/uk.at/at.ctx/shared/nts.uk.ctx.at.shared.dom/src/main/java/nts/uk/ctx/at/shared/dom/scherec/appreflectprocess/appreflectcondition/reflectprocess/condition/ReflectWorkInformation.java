package nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.condition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import lombok.val;
import nts.uk.ctx.at.shared.dom.WorkInfoAndTimeZone;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.common.TimeZoneWithWorkNo;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.DailyRecordOfApplication;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.ScheduleRecordClassifi;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.stampapplication.algorithm.CancelAppStamp;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.workchangeapp.ReflectWorkChangeApp.WorkInfoDto;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.TimeChangeMeans;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.WorkInfoOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.worktime.common.TimeZone;

/**
 * @author thanh_nx
 *
 *         勤務情報の反映
 */
public class ReflectWorkInformation {

	public static List<Integer> reflectInfo(Require require, String cid, WorkInfoDto workInfo, DailyRecordOfApplication dailyApp,
			Optional<Boolean> changeWorkType, Optional<Boolean> changeWorkTime) {
		
		//#118580
		if(!workInfo.getWorkTypeCode().isPresent()) {
			return new ArrayList<>();
		}
		List<Integer> lstItemId = new ArrayList<>();
		if (changeWorkType.orElse(false) || changeWorkTime.orElse(false)) {
			// [input. 勤務種類を反映する]をチェック
			if (changeWorkType.orElse(false)) {

			// 勤怠項目ID一覧 = [勤務種類コード]、[振休振出として扱う区分]、[振休振出として扱う日数]に該当する勤怠項目ID
			lstItemId.addAll(Arrays.asList(28, 1292, 1293));
			// 申請反映状態にする
			UpdateEditSttCreateBeforeAppReflect.update(dailyApp, lstItemId);

			}

			// [input. 就業時間帯を反映する]をチェック
			if (changeWorkTime.orElse(false)) {
				// 勤怠項目ID一覧 = [就業時間帯コード]に該当する勤怠項目ID
				lstItemId.addAll(Arrays.asList(29));
			}
			
			// 勤務情報と始業終業を変更する
			dailyApp.getWorkInformation().changeWorkSchedule(require,
					new WorkInformation(workInfo.getWorkTypeCode().orElse(null), 
										workInfo.getWorkTimeCode().orElse(null)), 
					changeWorkType.orElse(false), changeWorkTime.orElse(false));
			
			//申請の反映先をチェックする
			if(dailyApp.getClassification() == ScheduleRecordClassifi.SCHEDULE) {
				//予定に出退勤の反映
				val resultLeav = reflectAttLeavSchedule(require, cid, ScheduleRecordClassifi.RECORD, dailyApp);
				lstItemId.addAll(resultLeav.getLstItemId());
			}
			
			if(!lstItemId.isEmpty()) {

				/// 申請反映状態にする
				UpdateEditSttCreateBeforeAppReflect.update(dailyApp, lstItemId);
			}
		}
		
		return lstItemId;
	}

	//予定に出退勤の反映
	private static DailyAfterAppReflectResult reflectAttLeavSchedule(Require require, String cid, ScheduleRecordClassifi clasifi,
			DailyRecordOfApplication dailyApp) {
		List<Integer> lstItemId = new ArrayList<>();
		//所定時間帯を取得する
		Optional<WorkInfoAndTimeZone> timeZoneOpt = dailyApp.getWorkInformation().getRecordInfo()
				.getWorkInfoAndTimeZone(require);
		if (!timeZoneOpt.isPresent()) {
			// 勤務時刻情報．時刻をクリアー
			dailyApp.getAttendanceLeave().ifPresent(attLeav -> {
				attLeav.getTimeLeavingWorks().forEach(data -> {
					// 出勤
					data.getAttendanceStamp().ifPresent(att -> {
						att.getStamp().ifPresent(st -> {
							st.getTimeDay().setTimeWithDay(Optional.empty());
							st.getTimeDay().getReasonTimeChange().setTimeChangeMeans(TimeChangeMeans.APPLICATION);
							lstItemId.add(CancelAppStamp.createItemId(31, data.getWorkNo().v(), 10));
						});
					});
					
					// 退勤
					data.getLeaveStamp().ifPresent(att -> {
						att.getStamp().ifPresent(st -> {
							st.getTimeDay().setTimeWithDay(Optional.empty());
							st.getTimeDay().getReasonTimeChange().setTimeChangeMeans(TimeChangeMeans.APPLICATION);
							lstItemId.add(CancelAppStamp.createItemId(34, data.getWorkNo().v(), 10));
						});
					});
					
				});
			});
			return new DailyAfterAppReflectResult(dailyApp, new ArrayList<Integer>());
		}
		
		List<TimeZoneWithWorkNo> timeZoneWithWorkNoLst = IntStream.range(0,timeZoneOpt.get().getTimeZones().size()).boxed().map(indx -> {
			TimeZone timeZone = timeZoneOpt.get().getTimeZones().get(indx);
			return new TimeZoneWithWorkNo(indx+1, timeZone.getStart().v(), timeZone.getEnd().v());
		}).collect(Collectors.toList());
		
		//出退勤の反映
		lstItemId.addAll(ReflectAttendance.reflect(require, cid, timeZoneWithWorkNoLst, clasifi, dailyApp, Optional.of(true),
				Optional.of(true), Optional.of(TimeChangeMeans.APPLICATION)));
		return new DailyAfterAppReflectResult(dailyApp, lstItemId);
	}
	
	public static interface Require extends WorkInfoOfDailyAttendance.Require, ReflectAttendance.Require {

	}
}
