package nts.uk.ctx.at.request.dom.application.common.service.application.output;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.request.dom.setting.company.emailset.AppEmailSet;

/**
 * UKDesign.UniversalK.就業.KAF_申請.共通ダイアログ.KDL030：申請メール送信ダイアログ.アルゴリズム.ダイアログを開く.メール送信の初期データ
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class ApplicationForSendOutput {
	
	/**
	 * メール本文
	 */
	private String mailTemplate;
	
	/**
	 * 申請メール設定
	 */
	private AppEmailSet appEmailSet;
	
	/**
	 * 申請者ごと情報
	 */
	private List<AppSendMailByEmp> appSendMailByEmpLst;
}
