package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.overtimerestappcommon;

import lombok.Data;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.UseAtr;

@Data
public class OvertimeRestAppCommonSetting {
	
	/**
	 * 会社ID
	 * companyID
	 */
	private String companyID;
	
	/**
	 * 申請種類
	 */
	private ApplicationType appType;
	
	/**
	 * 乖離理由入力区分
	 */
	private UseAtr divergenceReasonInputAtr;
	
	/**
	 * 乖離理由定型区分
	 */
	private UseAtr divergenceReasonFormAtr;
	
	/**
	 * 乖離理由必須
	 */
	private UseAtr divergenceReasonRequired;
	
	/**
	 * 事前表示区分
	 */
	private UseAtr preDisplayAtr;
	
	/**
	 * 事前超過表示設定
	 */
	private UseAtr preExcessDisplaySetting;
	
	/**
	 * 加給時間表示区分
	 */
	private UseAtr bonusTimeDisplayAtr;
	
	/**
	 * 外出区分設定
	 */
	private OutingSettingAtr outingSettingAtr;
	
	/**
	 * 実績表示区分
	 */
	private UseAtr performanceDisplayAtr;
	
	/**
	 * 実績超過区分
	 */
	private UseAtr performanceExcessAtr;
	
	/**
	 * 指示表示区分
	 */
	private UseAtr intructDisplayAtr;
	
	/**
	 * 時間外表示区分
	 */
	private UseAtr extratimeDisplayAtr;
	
	/**
	 * 時間外超過区分
	 */
	private UseAtr extratimeExcessAtr;
	/**
	 * 申請日矛盾区分
	 */
	private AppDateContradictionAtr appDateContradictionAtr;
	
	/**
	 * 計算残業時間表示区分
	 */
	private UseAtr calculationOvertimeDisplayAtr;
	

}
