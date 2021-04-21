package nts.uk.ctx.at.request.dom.mail;

import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.request.dom.application.Application;

/**
 * UKDesign.UniversalK.就業.KAF_申請.共通ダイアログ.KDL030：申請メール送信ダイアログ.アルゴリズム.メール送信.申請者別メール先
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class SendMailAppInfoParam {
	/**
	 * メール送信する承認者リスト
	 */
	private List<SendMailApproverInfoParam> approverInfoLst;
	
	/**
	 * 申請
	 */
	private Application application;
	
	/**
	 * 申請者のメールアドレス
	 */
	private Optional<String> opApplicantMail;
}
