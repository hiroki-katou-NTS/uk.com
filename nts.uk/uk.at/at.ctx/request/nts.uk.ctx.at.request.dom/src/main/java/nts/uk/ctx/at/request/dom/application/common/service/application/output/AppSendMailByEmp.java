package nts.uk.ctx.at.request.dom.application.common.service.application.output;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.request.dom.application.Application;

/**
 * UKDesign.UniversalK.就業.KAF_申請.共通ダイアログ.KDL030：申請メール送信ダイアログ.アルゴリズム.ダイアログを開く.申請者別情報
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class AppSendMailByEmp {
	/**
	 * 承認ルートインスタンス
	 */
	private ApprovalRootOutput approvalRoot;
	/**
	 * 申請
	 */
	private Application application;
	/**
	 * 申請者名
	 */
	private String applicantName;
	/**
	 * 申請者のメールアドレス
	 */
	private String applicantMail;
}
