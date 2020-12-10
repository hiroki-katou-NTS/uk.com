package nts.uk.ctx.at.shared.dom.application.stamp;

import java.util.Optional;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.application.common.ApplicationShare;
import nts.uk.ctx.at.shared.dom.workrule.goingout.GoingOutReason;
import nts.uk.shr.com.time.AttendanceClock;

/**
 * @author thanh_nx
 * 
 *         レコーダイメージ申請
 *
 */
@Getter
public class AppRecordImageShare extends ApplicationShare {

	//	打刻区分
	private EngraveShareAtr appStampCombinationAtr;
	
	//	申請時刻
	private AttendanceClock attendanceTime;
	
	//	外出理由
	private Optional<GoingOutReason> appStampGoOutAtr;

	public AppRecordImageShare(EngraveShareAtr appStampCombinationAtr, AttendanceClock attendanceTime,
			Optional<GoingOutReason> appStampGoOutAtr, ApplicationShare application) {
		super(application);
		this.appStampCombinationAtr = appStampCombinationAtr;
		this.attendanceTime = attendanceTime;
		this.appStampGoOutAtr = appStampGoOutAtr;
	}

}
