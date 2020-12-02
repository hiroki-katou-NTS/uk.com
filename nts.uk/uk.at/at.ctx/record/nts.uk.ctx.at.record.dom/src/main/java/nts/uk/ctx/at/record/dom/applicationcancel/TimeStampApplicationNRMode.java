package nts.uk.ctx.at.record.dom.applicationcancel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.applicationcancel.ReflectTimeStamp.ReflectTimeStampResult;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.Stamp;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.DailyRecordOfApplication;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.UpdateEditSttCreateBeforeAppReflect;
import nts.uk.ctx.at.shared.dom.application.stamp.AppRecordImageShare;
import nts.uk.ctx.at.shared.dom.application.stamp.EngraveShareAtr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.function.algorithm.ChangeDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.WorkInfoOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.algorithmdailyper.OutputTimeReflectForWorkinfo;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * @author thanh_nx
 *
 *         打刻申請（NRモード）を反映する
 */
public class TimeStampApplicationNRMode {

	private static Map<Integer, Integer> START_ATTEN;

	private static Map<Integer, Integer> END_ATTEN ;

	private static Map<Integer, Integer> START_OUT ;

	private static Map<Integer, Integer> END_OUT;

	private static Map<Integer, Integer> OUT_CATE;

	static {

		// 31, 41 ・出勤、早出、休出の場合→[出勤時刻1～2]
		START_ATTEN = IntStream.range(31, 42).boxed().filter(x -> (x - 31) % 10 == 0)
				.collect(Collectors.toMap(x -> ((x - 31) / 10) + 1, x -> x));

		// 34, 44・退勤、退勤（残業）の場合→[退勤時刻1～2]
		END_ATTEN = IntStream.range(34, 45).boxed().filter(x -> (x - 34) % 10 == 0)
				.collect(Collectors.toMap(x -> ((x - 34) / 10) + 1, x -> x));

		// 88, 95, 102...151・外出の場合→[外出時刻1～10]、
		START_OUT = IntStream.range(88, 152).boxed().filter(x -> (x - 88) % 7 == 0)
				.collect(Collectors.toMap(x -> ((x - 88) / 7) + 1, x -> x));

		// 91...154・戻りの場合→[戻り時刻1～10]
		END_OUT = IntStream.range(91, 155).boxed().filter(x -> (x - 91) % 7 == 0)
				.collect(Collectors.toMap(x -> ((x - 91) / 7) + 1, x -> x));

		// 86, 93, ... 149・[外出区分1～10]
		OUT_CATE = IntStream.range(86, 150).boxed().filter(x -> (x - 86) % 7 == 0)
				.collect(Collectors.toMap(x -> ((x - 86) / 7) + 1, x -> x));
	}

	public static List<Integer> process(Require require, GeneralDate baseDate, AppRecordImageShare appNr,
			DailyRecordOfApplication dailyRecordApp, Optional<Stamp> stamp, ChangeDailyAttendance changeDailyAtt) {

		// 勤務情報から打刻反映時間帯を取得する
		OutputTimeReflectForWorkinfo timeReflectWork = require.getTimeReflect(dailyRecordApp.getEmployeeId(),
				dailyRecordApp.getYmd(), dailyRecordApp.getWorkInformation());

		// 反映する時刻に変換する
		TimeWithDayAttr attr = ConvertToReflectTime.convert(baseDate, appNr.getAppDate().getApplicationDate(),
				appNr.getAttendanceTime());

		List<Integer> lstItemId = new ArrayList<>();
		// [input.レコーダイメージ申請.打刻区分]をチェック
		if (appNr.getAppStampCombinationAtr() == EngraveShareAtr.GO_OUT
				|| appNr.getAppStampCombinationAtr() == EngraveShareAtr.RETURN) {
			/// 外出・戻りの打刻を反映する
			ReflectTimeStampResult stampResult = ReflectGoOutReturn.process(require, dailyRecordApp, timeReflectWork,
					appNr.getAttendanceTime(), appNr.getAppStampCombinationAtr(), stamp, changeDailyAtt);
			
			if (!stampResult.isReflect())
				return lstItemId;
			
			// 申請反映状態にする
			if (ReflectTimeStamp.groupAtt(appNr.getAppStampCombinationAtr())) {
				lstItemId.add(START_OUT.get(stampResult.getWorkNoReflect().v()));
			} else {
				lstItemId.add(END_OUT.get(stampResult.getWorkNoReflect().v()));
			}
			lstItemId.add(OUT_CATE.get(stampResult.getWorkNoReflect().v()));
			UpdateEditSttCreateBeforeAppReflect.update(dailyRecordApp, lstItemId);
			return lstItemId;
		} else if (appNr.getAppStampCombinationAtr() == EngraveShareAtr.ATTENDANCE
				|| appNr.getAppStampCombinationAtr() == EngraveShareAtr.EARLY
				|| appNr.getAppStampCombinationAtr() == EngraveShareAtr.HOLIDAY
				|| appNr.getAppStampCombinationAtr() == EngraveShareAtr.OFFICE_WORK
				|| appNr.getAppStampCombinationAtr() == EngraveShareAtr.OVERTIME) {
			/// 出退勤の打刻を反映する
			ReflectTimeStampResult stampResult = ReflectTimeStamp.reflect(require, dailyRecordApp, timeReflectWork,
					appNr.getAttendanceTime(), attr, appNr.getAppStampCombinationAtr(), stamp);

			if (!stampResult.isReflect())
				return lstItemId;

			// 計算区分の更新
			UpdateCalculationCategory.updateCalc(dailyRecordApp.getDomain(), appNr.getAppStampCombinationAtr());

			if (ReflectTimeStamp.groupAtt(appNr.getAppStampCombinationAtr())) {
				lstItemId.add(START_ATTEN.get(stampResult.getWorkNoReflect().v()));
			} else {
				lstItemId.add(END_ATTEN.get(stampResult.getWorkNoReflect().v()));
			}
			UpdateEditSttCreateBeforeAppReflect.update(dailyRecordApp, lstItemId);
			return lstItemId;
		}

		return lstItemId;
	}

	public static interface Require extends ReflectTimeStamp.Require, ReflectGoOutReturn.Require {

		// TimeReflectFromWorkinfo.get
		public OutputTimeReflectForWorkinfo getTimeReflect(String employeeId, GeneralDate ymd,
				WorkInfoOfDailyAttendance workInformation);
	}
}
