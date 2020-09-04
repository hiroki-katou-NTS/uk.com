package nts.uk.ctx.at.record.dom.applicationcancel;

import java.util.List;
import java.util.Optional;

import lombok.val;
import nts.uk.ctx.at.record.dom.applicationcancel.ReflectTimeStamp.ReflectTimeStampResult;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.Stamp;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.DailyRecordOfApplication;
import nts.uk.ctx.at.shared.dom.application.stamp.AppStampCombinationAtrShare;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.dailyattdcal.converter.DailyRecordToAttendanceItemConverter;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.breakouting.OutingTimeOfDailyAttd;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.dailyattdcal.workinfo.timereflectfromworkinfo.OutputTimeReflectForWorkinfo;
import nts.uk.ctx.at.shared.dom.dailyattdcal.workinfo.timereflectfromworkinfo.StampReflectRangeOutput;
import nts.uk.ctx.at.shared.dom.workrecord.workperfor.dailymonthlyprocessing.ErrorMessageInfo;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkNo;

/**
 * @author thanh_nx
 *
 *         外出・戻りの打刻を反映する
 */
public class ReflectGoOutReturn {

	public static ReflectTimeStampResult process(Require require, DailyRecordOfApplication dailyRecordApp,
			OutputTimeReflectForWorkinfo timeReflectWork, AttendanceTime attendanceTime,
			AppStampCombinationAtrShare appStampComAtr, Optional<Stamp> stamp) {
		DailyRecordToAttendanceItemConverter converter = require.createDailyConverter();

		// 日別勤怠(計算用work）に日別勤怠(work）をコピーする
		IntegrationOfDaily dailyCopy = converter.setData(dailyRecordApp.getDomain()).toDomain();

		// 打刻を反映する
		require.reflectStamp(stamp.get(), timeReflectWork.getStampReflectRangeOutput(), dailyCopy);

		// 反映前の日別勤怠(work）.外出時間帯を保持する
		Optional<OutingTimeOfDailyAttd> outingTimeBefore = dailyRecordApp.getOutingTime();

		converter = require.createDailyConverter();
		Optional<OutingTimeOfDailyAttd> outingTimeCopyFromBefore = converter
				.withOutingTime(dailyRecordApp.getEmployeeId(), dailyRecordApp.getYmd(),
						outingTimeBefore.orElseGet(null))
				.toDomain().getOutingTime();

		// 日別勤怠(work）に日別勤怠(計算用work）の外出時間帯をコピーする
		converter = require.createDailyConverter();
		Optional<OutingTimeOfDailyAttd> outingTimeCopyFromCopy = converter.setData(dailyCopy).toDomain()
				.getOutingTime();

		if ((!outingTimeCopyFromBefore.isPresent() && outingTimeCopyFromCopy.isPresent())
				|| (outingTimeCopyFromBefore.isPresent() && !outingTimeCopyFromCopy.isPresent())) {
			return new ReflectTimeStampResult();
		}
		// 反映した外出枠NOを取得する
		val result = GetReflectOutGoFrameNumber.process(outingTimeCopyFromCopy.get().getOutingTimeSheets(),
				outingTimeBefore.get().getOutingTimeSheets());
		return new ReflectTimeStampResult(dailyRecordApp.getDomain(), result.isReflect(), new WorkNo(result.getNo()));
	}

	public static interface Require {

		// DailyRecordConverter
		DailyRecordToAttendanceItemConverter createDailyConverter();

		// TemporarilyReflectStampDailyAttd
		List<ErrorMessageInfo> reflectStamp(Stamp stamp, StampReflectRangeOutput stampReflectRangeOutput,
				IntegrationOfDaily integrationOfDaily);
	}
}
