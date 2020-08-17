package nts.uk.ctx.at.request.dom.application.stamp;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.breakout.GoOutReasonAtr;
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
	private AttendanceTime attendanceTime;
//	外出理由
	private Optional<GoOutReasonAtr> appStampGoOutAtr; 
}
