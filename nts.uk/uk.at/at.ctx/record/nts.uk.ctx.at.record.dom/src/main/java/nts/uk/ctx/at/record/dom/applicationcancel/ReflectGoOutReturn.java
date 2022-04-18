package nts.uk.ctx.at.record.dom.applicationcancel;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.val;
import nts.uk.ctx.at.record.dom.applicationcancel.ReflectTimeStamp.ReflectTimeStampResult;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.Stamp;
import nts.uk.ctx.at.shared.dom.scherec.application.stamp.EngraveShareAtr;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.DailyRecordOfApplication;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.OutingTimeOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.DailyRecordToAttendanceItemConverter;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.function.algorithm.ChangeDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.algorithmdailyper.OutputTimeReflectForWorkinfo;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.algorithmdailyper.StampReflectRangeOutput;
import nts.uk.ctx.at.shared.dom.workrecord.workperfor.dailymonthlyprocessing.ErrorMessageInfo;
import nts.uk.ctx.at.shared.dom.worktime.predset.WorkNo;
import nts.uk.shr.com.time.AttendanceClock;

/**
 * @author thanh_nx
 *
 *         外出・戻りの打刻を反映する
 */
public class ReflectGoOutReturn {

	public static ReflectTimeStampResult process(Require require, String companyId, DailyRecordOfApplication dailyRecordApp,
			OutputTimeReflectForWorkinfo timeReflectWork, AttendanceClock attendanceTime,
			EngraveShareAtr appStampComAtr, Optional<Stamp> stamp, ChangeDailyAttendance changeDailyAtt) {
		DailyRecordToAttendanceItemConverter converter = require.createDailyConverter();

		// 反映前の日別勤怠(work）.外出時間帯を保持する
		if(!dailyRecordApp.getOutingTime().isPresent()) {
			dailyRecordApp.setOutingTime(Optional.of(new OutingTimeOfDailyAttd(new ArrayList<>())));
		}
		Optional<OutingTimeOfDailyAttd> outingTimeBefore = dailyRecordApp.getOutingTime();
		converter = require.createDailyConverter();
		Optional<OutingTimeOfDailyAttd> outingTimeCopyFromBefore = converter
				.withOutingTime(outingTimeBefore.orElseGet(null))
				.toDomain().getOutingTime();

		
		converter = require.createDailyConverter();
		// 日別勤怠(計算用work）に日別勤怠(work）をコピーする
		IntegrationOfDaily dailyCopy = converter.setData(dailyRecordApp.getDomain()).toDomain();

		// 打刻を反映する
		require.reflectStamp(companyId, stamp.get(), timeReflectWork.getStampReflectRangeOutput(), dailyCopy, changeDailyAtt);

		// 日別勤怠(work）に日別勤怠(計算用work）の外出時間帯をコピーする
		converter = require.createDailyConverter();
		Optional<OutingTimeOfDailyAttd> outingTimeCopyAfter = converter.setData(dailyCopy).toDomain()
				.getOutingTime();

		if ((!outingTimeCopyFromBefore.isPresent() && outingTimeCopyAfter.isPresent())
				|| (outingTimeCopyFromBefore.isPresent() && !outingTimeCopyAfter.isPresent())) {
			return new ReflectTimeStampResult();
		}
		// 反映した外出枠NOを取得する
		val result = GetReflectOutGoFrameNumber.process(outingTimeCopyAfter.get().getOutingTimeSheets(),
				outingTimeBefore.get().getOutingTimeSheets());
		dailyRecordApp.getDomain().setOutingTime(outingTimeCopyAfter);
		return new ReflectTimeStampResult(dailyRecordApp.getDomain(), result.isReflect(), new WorkNo(result.getNo()));
	}

	public static interface Require {

		// DailyRecordConverter
		DailyRecordToAttendanceItemConverter createDailyConverter();

		// TemporarilyReflectStampDailyAttd
		List<ErrorMessageInfo> reflectStamp(String companyId, Stamp stamp, StampReflectRangeOutput stampReflectRangeOutput,
				IntegrationOfDaily integrationOfDaily, ChangeDailyAttendance changeDailyAtt);
	}
}
