package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.function.functionalgorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.dailyattdcal.dailywork.CorrectLateArrivalDepartureTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TimeLeavingWork;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.TimeActualStamp;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.ReasonTimeChange;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkStamp;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkTimeInformation;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.function.algorithm.DetermineClassifiByWorkInfoCond.AutoStampSetClassifi;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.WorkInfoOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.workingcondition.NotUseAtr;

/**
 * @author thanh_nx
 *
 *         出退勤の時刻をセットする
 */
@Stateless
public class SetTimeOfAttendance {

	
	@Inject
	private CorrectLateArrivalDepartureTime correctLateArrivalDepartureTime;


	public List<TimeLeavingWork> process(String companyId, WorkInfoOfDailyAttendance workInfo,
			AutoStampSetClassifi autoStampClasssifi) {
		// INPUT．「日別実績の勤務情報」の勤務実績と勤務予定を比較する
		List<TimeLeavingWork> lstTimeLeavingWork = new ArrayList<>();
//		if (compareWorkInfo(workInfo.getRecordInfo(), workInfo.getScheduleInfo())) {
			// 「出退勤（List）」を作成する
			lstTimeLeavingWork.addAll(workInfo.getScheduleTimeSheets().stream().map(x -> {
				return new TimeLeavingWork(x.getWorkNo(),
						new TimeActualStamp(null, new WorkStamp(
								new WorkTimeInformation(new ReasonTimeChange(autoStampClasssifi.getAttendanceStamp(), Optional.empty()),
										x.getAttendance()),
								Optional.empty()), 0),
						new TimeActualStamp(null,
								new WorkStamp(
										new WorkTimeInformation(
												new ReasonTimeChange(autoStampClasssifi.getLeaveStamp(), Optional.empty()),
												x.getLeaveWork()),
										Optional.empty()),
								0));
			}).collect(Collectors.toList()));
//		} else {
//			// 所定時間帯をセットする
//			lstTimeLeavingWork
//					.addAll(setPredetermineTimeZone.setTimeZone(companyId, workInfo.getRecordInfo().getWorkTypeCode(),
//							workInfo.getRecordInfo().getWorkTimeCode(), autoStampClasssifi));
//		}

//		// ジャスト遅刻早退時刻を補正する
			lstTimeLeavingWork = correctLateArrivalDepartureTime.process(companyId, workInfo.getRecordInfo().getWorkTimeCode().v(),
				lstTimeLeavingWork);

		// 「自動打刻セット区分」元に「出退勤（List）」を整理する
		lstTimeLeavingWork.forEach(data -> {
			if (autoStampClasssifi.getAttendanceReflect() == NotUseAtr.NOTUSE) {
				data.getAttendanceStamp().get().getStamp().get().getTimeDay().setTimeWithDay(Optional.empty());
			}

			if (autoStampClasssifi.getLeaveWorkReflect() == NotUseAtr.NOTUSE) {
				data.getLeaveStamp().get().getStamp().get().getTimeDay().setTimeWithDay(Optional.empty());
			}
		});

		// 「出退勤（List）」を返す
		return lstTimeLeavingWork;
	}

}