package nts.uk.ctx.at.request.dom.application.overtime.CommonAlgorithm;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.dom.application.overtime.OutDateApplication;
/**
 * Refactor5 Output
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.申請承認.設定.会社別.申請承認設定.残業休出申請共通設定.アルゴリズム.事前申請の超過状態をチェックする
 * @author hoangnd
 *
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ExcessStatusOutput {
	///事前申請なしアラーム(boolean)
	private Boolean isAlarm;
	//申請時間の超過状態
	private OutDateApplication outDateApplication;

}
