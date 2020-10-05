package nts.uk.ctx.at.request.dom.application.stamp;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.GoingOutReason;
import nts.uk.shr.com.time.AttendanceClock;
/**
 * Refactor4
 * @author hoangnd
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.申請承認.申請.打刻申請
 *
 */
@AllArgsConstructor
@Getter
//レコーダイメージ申請
public class AppRecordImage extends Application{
//	打刻区分
	private EngraveAtr appStampCombinationAtr;	
//	申請時刻
	private AttendanceClock attendanceTime;
//	外出理由
	private Optional<GoingOutReason> appStampGoOutAtr; 
}
