package nts.uk.screen.hr.app.databeforereflecting.command;

import lombok.Data;

@Data
public class ApprovedCommand {
	// 処理区分
	int approvalStatus;

	// 履歴ID
	String historyId;

	// コメント
	String comment;

	// メール送信済みフラグ
	boolean sendEmail;

}