package nts.uk.screen.hr.app.databeforereflecting.command;

import lombok.Data;

@Data
public class ApprovedCommand {
	// 処理区分
	int approvalType;

	// 履歴ID
	String hisId;

	// コメント
	String approveComment;

	// メール送信済みフラグ
	boolean approveSendMailFlg;

}