package nts.uk.ctx.at.request.dom.application.overtime.service;
/**
 * Refactor5
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.申請承認.設定.会社別.申請承認設定.残業休出申請共通設定.アルゴリズム.初期表示する出退勤時刻を取得する
 * @author hoangnd
 *
 */

import lombok.NoArgsConstructor;
import nts.uk.shr.com.time.TimeWithDayAttr;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@NoArgsConstructor
@Data
// 勤務時間
public class WorkHours {
	// 開始時刻1
	private Optional<TimeWithDayAttr> startTimeOp1 = Optional.empty();
	// 開始時刻2
	private Optional<TimeWithDayAttr> startTimeOp2 = Optional.empty();
	// 終了時刻1
	private Optional<TimeWithDayAttr> endTimeOp1 = Optional.empty();
	// 終了時刻2
	private Optional<TimeWithDayAttr> endTimeOp2 = Optional.empty();
	
	public WorkHours(
			Integer startTimeOp1,
			Integer startTimeOp2,
			Integer endTimeOp1,
			Integer endTimeOp2
			) {
		if (startTimeOp1 != null) {
			this.startTimeOp1 = Optional.of(new TimeWithDayAttr(startTimeOp1));
		}
		if (startTimeOp2 != null) {
			this.startTimeOp2 = Optional.of(new TimeWithDayAttr(startTimeOp2));
		}
		if (endTimeOp1 != null) {
			this.endTimeOp1 = Optional.of(new TimeWithDayAttr(endTimeOp1));
		}
		if (endTimeOp2 != null) {
			this.endTimeOp2 = Optional.of(new TimeWithDayAttr(endTimeOp2));
		}
		
	}
}
