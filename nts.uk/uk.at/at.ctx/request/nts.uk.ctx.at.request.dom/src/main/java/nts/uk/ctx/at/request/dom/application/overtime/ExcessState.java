package nts.uk.ctx.at.request.dom.application.overtime;

import lombok.AllArgsConstructor;

/**
 * Refactor5
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.申請承認.設定.会社別.申請承認設定.残業休出申請共通設定.アルゴリズム.事前申請の超過状態をチェックする
 * @author hoangnd
 *
 */
@AllArgsConstructor
// 超過状態
public enum ExcessState {
	// 超過なし
	NO_EXCESS(0),
	// 超過アラーム
	EXCESS_ALARM(1),
	// 超過エラー
	EXCESS_ERROR(2);
	
	public Integer value;
}
