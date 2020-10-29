package nts.uk.ctx.at.request.dom.application.overtime;

import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeOfExistMinus;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.optionalitemvalue.AnyItemValue;

/**
 * Refactor5
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.申請承認.申請.残業休出申請
 * @author hoangnd
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
// 申請時間
public class ApplicationTime {
	// 申請時間
	private List<OvertimeApplicationSetting> applicationTime;
	// フレックス超過時間
	private Optional<AttendanceTimeOfExistMinus> flexOverTime = Optional.empty();
	// 就業時間外深夜時間
	private Optional<OverTimeShiftNight> overTimeShiftNight = Optional.empty();
	// 任意項目
	private Optional<List<AnyItemValue>> anyItem = Optional.empty();
	// 乖離理由
	private Optional<List<ReasonDivergence>> reasonDissociation = Optional.empty();
}
