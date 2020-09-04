package nts.uk.ctx.at.shared.dom.application.stamp;

import java.util.Optional;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.application.common.ApplicationShare;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.breakout.GoOutReasonAtr;

/**
 * @author thanh_nx
 * 
 *         レコーダイメージ申請
 *
 */
@Getter
public class AppRecordImageShare extends ApplicationShare {

	// 打刻区分
	private AppStampCombinationAtrShare appStampCombinationAtr;

	// 申請時刻
	private AttendanceTime attendanceTime;
	// 外出理由
	private Optional<GoOutReasonAtr> appStampGoOutAtr;

	public AppRecordImageShare(AppStampCombinationAtrShare appStampCombinationAtr, AttendanceTime attendanceTime,
			Optional<GoOutReasonAtr> appStampGoOutAtr, ApplicationShare application) {
		super(application);
		this.appStampCombinationAtr = appStampCombinationAtr;
		this.attendanceTime = attendanceTime;
		this.appStampGoOutAtr = appStampGoOutAtr;
	}

}
