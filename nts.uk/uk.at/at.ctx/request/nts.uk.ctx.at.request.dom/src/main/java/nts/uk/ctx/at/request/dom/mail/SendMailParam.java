package nts.uk.ctx.at.request.dom.mail;

import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.request.dom.setting.company.emailset.AppEmailSet;

/**
 * UKDesign.UniversalK.就業.KAF_申請.共通ダイアログ.KDL030：申請メール送信ダイアログ.アルゴリズム.メール送信.メール送信情報パラメータ
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class SendMailParam {
	/**
	 * メール内容
	 */
	private String mailTemplate;
	
	/**
	 * 申請メール設定
	 */
	private AppEmailSet appEmailSet;
	
	/**
	 * 申請者ごとメール先リスト
	 */
	private List<SendMailAppInfoParam> appInfoLst;
	
	/**
	 * 申請者にメールを送信するか
	 */
	private Optional<Boolean> opSendMailApplicant;
}
