package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.function.functionalgorithm.setautomatic;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TimeLeavingWork;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.TimeActualStamp;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.ReasonTimeChange;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkStamp;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkTimeInformation;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.function.algorithm.DetermineClassifiByWorkInfoCond.AutoStampSetClassifi;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.temporarytime.WorkNo;
import nts.uk.ctx.at.shared.dom.worktime.predset.TimezoneUse;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * @author thanh_nx
 *
 *         時間帯を出退勤に追加する
 */
@Stateless
public class AddTimeZoneToAttendance {

	@Inject
	private RoundAttendanceTime roundAttendanceTime;

	public TimeLeavingWork addTimeZone(String companyId, String workTimeCode, TimezoneUse timezoneUse,
			AutoStampSetClassifi autoStampClasssifi) {

		// 出退勤 ← 時間帯

		// 出勤系時刻を丸める (Làm tròn 出勤系時刻)
		int goingTime = roundAttendanceTime.process(companyId, timezoneUse.getStart().v(), workTimeCode,
				AttLeavAtr.GOING_TO_WORK);

		// 出勤系時刻を丸める (Làm tròn 出勤系時刻)
		int leavTime = roundAttendanceTime.process(companyId, timezoneUse.getEnd().v(), workTimeCode,
				AttLeavAtr.LEAVING_WORK);
		// ドメインモデル「所属職場履歴」を取得する (Lấy dữ liệu)

		// 出退勤 ← 自動打刻セット詳細
		TimeLeavingWork work = new TimeLeavingWork(new WorkNo(timezoneUse.getWorkNo()),
				new TimeActualStamp(null, new WorkStamp(new TimeWithDayAttr(goingTime),
						new WorkTimeInformation(new ReasonTimeChange(autoStampClasssifi.getAttendanceStamp(), null),
								new TimeWithDayAttr(goingTime)),
						Optional.empty()), 0),
				new TimeActualStamp(null,
						new WorkStamp(new TimeWithDayAttr(leavTime),
								new WorkTimeInformation(new ReasonTimeChange(autoStampClasssifi.getLeaveStamp(), null),
										new TimeWithDayAttr(leavTime)),
								Optional.empty()),
						0));
		return work;
	}

	/**
	 * 出退勤区分
	 * 
	 */
	@AllArgsConstructor
	public static enum AttLeavAtr {
		/**
		 * 出勤
		 */
		GOING_TO_WORK,
		/**
		 * 退勤
		 */
		LEAVING_WORK;
	}
}
