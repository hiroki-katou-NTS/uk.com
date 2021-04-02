package nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.overtimeholidaywork.algorithm.subtransfer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.val;
import nts.gul.util.value.Finally;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.TimeDivergenceWithCalculation;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.holidayworktime.HolidayWorkTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.TimeSpanForDailyCalc;

/**
 * @author thanh_nx
 *
 *         休日出勤時間の代休振替
 */
public class TranferHdWorkCompensatory {

	public static IntegrationOfDaily process(Require require, String cid, IntegrationOfDaily dailyRecord) {

		//最大の時間帯でworkを作成
		IntegrationOfDaily dailyRecordNew = CreateWorkMaxTimeZone.process(require, cid, dailyRecord);
		
		MaximumTimeZone maxTimeZone = new MaximumTimeZone();
		List<MaximumTime> maxTime = new ArrayList<>();
		List<MaximumTime> timeAfterReflectApp = new ArrayList<>();
		if (!dailyRecordNew.getAttendanceTimeOfDailyPerformance().isPresent()
				|| !dailyRecordNew.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily()
						.getTotalWorkingTime().getExcessOfStatutoryTimeOfDaily().getWorkHolidayTime().isPresent()) {
			return dailyRecord;
		}

		HolidayWorkTimeOfDaily hdTimeWork = dailyRecordNew.getAttendanceTimeOfDailyPerformance().get()
				.getActualWorkingTimeOfDaily().getTotalWorkingTime().getExcessOfStatutoryTimeOfDaily().getWorkHolidayTime()
				.get();
		
		// 計算日別勤怠(work）から休出枠時間帯（List）の内容を移送し、[最大時間帯]へセットする
		maxTimeZone.getTimeSpan()
				.putAll(hdTimeWork.getHolidayWorkFrameTimeSheet().stream().collect(Collectors.toMap(
						x -> x.getHolidayWorkTimeSheetNo().v(),
						x -> new TimeSpanForDailyCalc(x.getTimeSheet().getStart(), x.getTimeSheet().getEnd()))));
		
		//計算日別勤怠(work）から休出枠時間（List）の内容を移送して、[最大の時間]を作成する
		maxTime.addAll(hdTimeWork.getHolidayWorkFrameTime().stream().map(x -> {
			return new MaximumTime(x.getHolidayFrameNo().v(), x.getHolidayWorkTime().isPresent() ? x.getHolidayWorkTime().get().getCalcTime() : new AttendanceTime(0),
					x.getTransferTime().isPresent() ? x.getTransferTime().get().getCalcTime(): new AttendanceTime(0));
			}).collect(Collectors.toList()));
			
		//input.日別勤怠(work）から休出枠時間（List）の内容を移送し、[申請を反映させた後の時間]へセットする
		timeAfterReflectApp.addAll(hdTimeWork.getHolidayWorkFrameTime().stream().map(x -> {
			return new MaximumTime(x.getHolidayFrameNo().v(),
					x.getHolidayWorkTime().isPresent() ? x.getHolidayWorkTime().get().getTime() : new AttendanceTime(0),
					x.getTransferTime().isPresent() ? x.getTransferTime().get().getTime() : new AttendanceTime(0));
		}).collect(Collectors.toList()));
		
		// 代休振替処理
		List<MaximumTime> lstMaxTime = SubstituteTransferProcess.process(maxTimeZone, maxTime, timeAfterReflectApp);
		
		//[振替をした後の時間(List）]のすべての内容を[日別勤怠(work）]にセットする
		dailyRecord.getAttendanceTimeOfDailyPerformance().get()
		.getActualWorkingTimeOfDaily().getTotalWorkingTime().getExcessOfStatutoryTimeOfDaily().getWorkHolidayTime()
		.ifPresent(overtimeOld -> {
			overtimeOld.getHolidayWorkFrameTime().stream().map(x -> {
				val maxTimeTemp = lstMaxTime.stream().filter(y -> y.getNo() == x.getHolidayFrameNo().v()).findFirst();
				if (!maxTimeTemp.isPresent()) {
					return x;
				}
				if(!x.getHolidayWorkTime().isPresent()) {
					x.setHolidayWorkTime(Finally.of(TimeDivergenceWithCalculation.defaultValue()));
				}
				if(!x.getTransferTime().isPresent()) {
					x.setTransferTime(Finally.of(TimeDivergenceWithCalculation.defaultValue()));
				}
				x.getHolidayWorkTime().get().setTime(maxTimeTemp.get().getTime());
				x.getTransferTime().get().setTime(maxTimeTemp.get().getTransferTime());
				return x;
			}).collect(Collectors.toList());
		});

		return dailyRecord;
		
	}

	public static interface Require extends CreateWorkMaxTimeZone.Require {

	}
}
