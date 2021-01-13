package nts.uk.ctx.at.request.dom.application.overtime;

import lombok.NoArgsConstructor;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Refactor5
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.申請承認.設定.会社別.申請承認設定.残業休出申請共通設定.アルゴリズム.事前申請・実績の時間超過をチェックする
 * @author hoangnd
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
// 事前申請・実績の超過状態
public class OverStateOutput {
	// 事前申請なし
	private Boolean isExistApp;
	// 事前超過
	private OutDateApplication advanceExcess;
	// 実績状態
	private ExcessState achivementStatus;
	// 実績超過
	private OutDateApplication achivementExcess;
}
