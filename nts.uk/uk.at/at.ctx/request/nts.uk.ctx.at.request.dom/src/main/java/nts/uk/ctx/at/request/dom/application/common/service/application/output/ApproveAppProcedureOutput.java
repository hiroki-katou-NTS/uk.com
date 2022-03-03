package nts.uk.ctx.at.request.dom.application.common.service.application.output;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ApproveAppProcedureOutput {
	// メールアドレスエラー社員リスト
	private List<String> failList;
	// 承認失敗申請IDリスト
	private List<String> approveFailLst;
	// 申請データ削除済みリスト
	private List<String> deleteLst;
	// 送信失敗社員リスト
	private List<String> failServerList;
	// 送信成功社員リスト
	private List<String> successList;
}
