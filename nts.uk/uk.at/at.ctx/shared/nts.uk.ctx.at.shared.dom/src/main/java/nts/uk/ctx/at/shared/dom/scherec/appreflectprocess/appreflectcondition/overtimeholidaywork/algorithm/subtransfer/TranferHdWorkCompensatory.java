package nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.overtimeholidaywork.algorithm.subtransfer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.commons.lang3.tuple.Pair;

import lombok.val;
import nts.gul.util.value.Finally;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.TimeDivergenceWithCalculation;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.holidayworktime.HolidayWorkTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.TimeSpanForDailyCalc;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.overtime.overtimeframe.OverTimeFrameNo;
import nts.uk.ctx.at.shared.dom.worktime.common.CompensatoryOccurrenceDivision;

/**
 * @author thanh_nx
 *
 *         休日出勤時間の代休振替
 */
public class TranferHdWorkCompensatory {

	public static IntegrationOfDaily process(Require require, String cid, IntegrationOfDaily dailyRecord) {

		if (!dailyRecord.getAttendanceTimeOfDailyPerformance().isPresent()
				|| !dailyRecord.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily()
						.getTotalWorkingTime().getExcessOfStatutoryTimeOfDaily().getWorkHolidayTime().isPresent()) {
			return dailyRecord;
		}
		
		//最大の時間帯でworkを作成
		IntegrationOfDaily dailyRecordNew = CreateWorkMaxTimeZone.process(require, cid, dailyRecord);
		
		MaximumTimeZone maxTimeZone = new MaximumTimeZone();
		List<OvertimeHourTransfer> maxTime = new ArrayList<>();
		List<OvertimeHourTransfer> timeAfterReflectApp = new ArrayList<>();
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
		.addAll(hdTimeWork.getHolidayWorkFrameTimeSheet().stream()
				.map(x -> Pair.of(new OverTimeFrameNo(x.getHolidayWorkTimeSheetNo().v()),
						new TimeSpanForDailyCalc(x.getTimeSheet().getStart(), x.getTimeSheet().getEnd())))
				.collect(Collectors.toList()));
	
		//計算日別勤怠(work）から休出枠時間（List）の内容を移送して、[最大の時間]を作成する
		maxTime.addAll(IntStream.range(0, hdTimeWork.getHolidayWorkFrameTimeSheet().size()).boxed().map(indx -> {
			return new OvertimeHourTransfer(indx,
					hdTimeWork.getHolidayWorkFrameTimeSheet().get(indx).getHdTimeCalc(),
					hdTimeWork.getHolidayWorkFrameTimeSheet().get(indx).getTranferTimeCalc());
		}).collect(Collectors.toList()));

		// input.日別勤怠(work）から休出枠時間（List）の内容を移送し、[申請を反映させた後の時間]へセットする
		timeAfterReflectApp.addAll(dailyRecord.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily()
				.getTotalWorkingTime().getExcessOfStatutoryTimeOfDaily().getWorkHolidayTime().get()
				.getHolidayWorkFrameTime().stream().map(x -> {
					return new OvertimeHourTransfer(x.getHolidayFrameNo().v(),
							x.getHolidayWorkTime().isPresent() ? x.getHolidayWorkTime().get().getTime()
									: new AttendanceTime(0),
							x.getTransferTime().isPresent() ? x.getTransferTime().get().getTime()
									: new AttendanceTime(0));
				}).collect(Collectors.toList()));
		
		// 代休振替処理
		List<OvertimeHourTransfer> lstMaxTime = SubstituteTransferProcess.process(require, cid,
				dailyRecordNew.getWorkInformation().getRecordInfo().getWorkTimeCodeNotNull().map(x -> x.v()),
				CompensatoryOccurrenceDivision.WorkDayOffTime, maxTimeZone, maxTime, timeAfterReflectApp);

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

	public static interface Require extends CreateWorkMaxTimeZone.Require, SubstituteTransferProcess.Require {

	}
}
