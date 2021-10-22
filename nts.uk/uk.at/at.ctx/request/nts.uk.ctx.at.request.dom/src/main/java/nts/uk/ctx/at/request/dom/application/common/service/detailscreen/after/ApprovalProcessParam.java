package nts.uk.ctx.at.request.dom.application.common.service.detailscreen.after;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.applicationtypesetting.AppTypeSetting;

/**
 * UKDesign.UniversalK.就業.KAF_申請.共通ユースケース.承認時の設定パラメータ
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class ApprovalProcessParam {
	/**
	 * メールサーバ設定済区分か
	 */
	private boolean mailServerSet;
	
	/**
	 * 申請種類別設定
	 */
	private AppTypeSetting appTypeSetting; 
}
