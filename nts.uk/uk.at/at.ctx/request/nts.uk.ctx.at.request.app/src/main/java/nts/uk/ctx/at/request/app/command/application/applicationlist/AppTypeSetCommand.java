package nts.uk.ctx.at.request.app.command.application.applicationlist;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
/**
 * app type setting command for kaf022
 * @author yennth
 *
 */
@Data
@AllArgsConstructor
@Getter
public class AppTypeSetCommand {
	// 申請種類
	private Integer appType;
	// 定型理由の表示
	private Integer displayFixedReason;
	// 申請理由の表示
	private Integer displayAppReason;
	// 新規登録時に自動でメールを送信する (boolean: domain)
	private Integer sendMailWhenRegister;
	// 承認処理時に自動でメールを送信する
	private Integer sendMailWhenApproval;
	// 事前事後区分の初期表示
	private Integer displayInitialSegment;
	// 事前事後区分を変更できる (boolean: domain)
	private Integer canClassificationChange;
}
