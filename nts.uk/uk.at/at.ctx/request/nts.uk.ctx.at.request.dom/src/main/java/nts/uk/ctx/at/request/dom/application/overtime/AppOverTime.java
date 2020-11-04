package nts.uk.ctx.at.request.dom.application.overtime;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.common.TimeZoneWithWorkNo;
/**
 * Refactor5 
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.申請承認.申請.残業休出申請
 * @author hoangnd
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
// 残業申請
public class AppOverTime extends Application {
	// 残業区分
	private OvertimeAppAtr overTimeClf;
	
	// 申請時間
	private ApplicationTime applicationTime;
	
	// 休憩時間帯
	private Optional<TimeZoneWithWorkNo> breakTimeOp = Optional.empty();
	
	// 勤務時間帯
	private Optional<TimeZoneWithWorkNo> workHoursOp = Optional.empty();
	
	// 勤務情報
	private Optional<WorkInformation> workInfoOp = Optional.empty(); 
	
	// 時間外時間の詳細
	private Optional<AppOvertimeDetail_Update> detailOverTimeOp = Optional.empty();
	
}
