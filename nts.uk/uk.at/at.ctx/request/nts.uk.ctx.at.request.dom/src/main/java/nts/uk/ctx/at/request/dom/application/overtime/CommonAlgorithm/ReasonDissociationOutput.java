package nts.uk.ctx.at.request.dom.application.overtime.CommonAlgorithm;

import java.util.Collections;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.deviationtime.deviationtimeframe.DivergenceTimeRoot;

/**
 * Refactor5 
 * output of UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.申請承認.設定.会社別.申請承認設定.残業休出申請共通設定.アルゴリズム.利用する乖離理由のを取得する
 * @author hoangnd
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReasonDissociationOutput {
	// List<乖離時間>
	private List<DivergenceTimeRoot> divergenceTimeRoots = Collections.emptyList();
	// List<乖離理由の入力方法>
	private List<DivergenceReasonInputMethod> divergenceReasonInputMethod = Collections.emptyList();
}
