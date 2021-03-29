package nts.uk.ctx.at.request.dom.application.approvalstatus.service;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * refactor 5
 * UKDesign.UniversalK.就業.KAF_申請.KAF018_承認状況の照会.ユーザー固有情報.承認状況照会の初期表示
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class InitDisplayOfApprovalStatus {
	// ページング行数
	private int numberOfPage;
	// ユーザーID
	private String userID;
	// 会社ID
	private String companyID;
	// 月別実績の本人確認・上長承認の状況を表示する
	private boolean confirmAndApprovalMonthFlg;
	// 就業確定の状況を表示する
	private boolean confirmEmploymentFlg;
	// 申請の承認状況を表示する
	private boolean applicationApprovalFlg;
	// 日別実績の本人確認・上長承認の状況を表示する
	private boolean confirmAndApprovalDailyFlg;
}
