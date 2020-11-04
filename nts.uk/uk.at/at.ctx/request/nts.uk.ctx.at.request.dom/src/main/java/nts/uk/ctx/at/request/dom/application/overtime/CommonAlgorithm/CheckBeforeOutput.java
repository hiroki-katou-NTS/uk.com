package nts.uk.ctx.at.request.dom.application.overtime.CommonAlgorithm;

import java.util.Collections;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.output.ConfirmMsgOutput;
import nts.uk.ctx.at.request.dom.application.overtime.AppOverTime;
/**
 * Refactor5
 * UKDesign.UniversalK.就業.KAF_申請.KAF005_残業申請.AB画面の共通アルゴリズム.残業申請の個別登録前チェッ処理
 * @author hoangnd
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CheckBeforeOutput {
	// 残業申請
	private AppOverTime appOverTime;
	//確認メッセージリスト
	private List<ConfirmMsgOutput> confirmMsgOutputs = Collections.emptyList();
}
