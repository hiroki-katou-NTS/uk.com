package nts.uk.screen.at.app.dailyperformance.correction.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author ductm
 *
 */
@Data
@AllArgsConstructor
public class DPCorrectionMenuDto {
	// 一括確認ボタン表示チェック
	public Boolean allConfirmButtonDis;
	// エラー参照ボタン表示チェック
	public Boolean errorReferButtonDis;
	// 休暇残数の参照ボタン表示チェック
	public Boolean restReferButtonDis;
	// 月別実績の参照ボタン表示チェック
	public Boolean monthActualReferButtonDis;
	// 時間外超過の参照ボタン表示チェック
	public Boolean timeExcessReferButtonDis;
}
