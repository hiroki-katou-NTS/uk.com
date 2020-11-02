package nts.uk.ctx.at.request.dom.application.overtime;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Refactor5
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.申請承認.設定.会社別.申請承認設定.残業休出申請共通設定.アルゴリズム.事前申請の超過状態をチェックする
 * @author hoangnd
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
// 申請時間の超過状態
public class OutDateApplication {
	// ﾌﾚｯｸｽの超過状態
	private ExcessState flex;
	// 休出深夜時間
	private List<ExcessStateMidnight> excessStateMidnight;
	// 残業深夜の超過状態
	private ExcessState overTimeLate;
	// 申請時間
	private List<ExcessStateDetail> excessStateDetail;
}
