package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.overtimerestappcommon;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.UseAtr;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.AppDateContradictionAtr;

/**
 * @author loivt
 * 残業休出申請共通設定
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
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
	private AppDateContradictionAtr performanceExcessAtr;
	
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
	private Time36AgreeCheckRegister extratimeExcessAtr;
	/**
	 * 申請日矛盾区分
	 */
	private AppDateContradictionAtr appDateContradictionAtr;
	
	/**
	 * 計算残業時間表示区分
	 */
	private UseAtr calculationOvertimeDisplayAtr;
	
	public static OvertimeRestAppCommonSetting createSimpleFromJavaType(String companyID,
																		int appType,
																		int divergenceReasonInputAtr,
																		int divergenceReasonFormAtr,
																		int divergenceReasonRequired,
																		int preDisplayAtr,
																		int preExcessDisplaySetting,
																		int bonusTimeDisplayAtr,
																		int outingSettingAtr,
																		int performanceDisplayAtr,
																		int performanceExcessAtr,
																		int intructDisplayAtr,
																		int extratimeDisplayAtr,
																		int extratimeExcessAtr,
																		int appDateContradictionAtr,
																		int calculationOvertimeDisplayAtr){
		return new OvertimeRestAppCommonSetting(companyID,
				EnumAdaptor.valueOf(appType,ApplicationType.class),
				EnumAdaptor.valueOf(divergenceReasonInputAtr,UseAtr.class),
				EnumAdaptor.valueOf(divergenceReasonFormAtr,UseAtr.class),
				EnumAdaptor.valueOf(divergenceReasonRequired,UseAtr.class),
				EnumAdaptor.valueOf(preDisplayAtr,UseAtr.class),
				EnumAdaptor.valueOf(preExcessDisplaySetting,UseAtr.class),
				EnumAdaptor.valueOf(bonusTimeDisplayAtr,UseAtr.class),
				EnumAdaptor.valueOf(outingSettingAtr,OutingSettingAtr.class), 
				EnumAdaptor.valueOf(performanceDisplayAtr,UseAtr.class),
				EnumAdaptor.valueOf(performanceExcessAtr,AppDateContradictionAtr.class),
				EnumAdaptor.valueOf(intructDisplayAtr,UseAtr.class),
				EnumAdaptor.valueOf(extratimeDisplayAtr,UseAtr.class),
				EnumAdaptor.valueOf(extratimeExcessAtr,Time36AgreeCheckRegister.class),
				EnumAdaptor.valueOf(appDateContradictionAtr,AppDateContradictionAtr.class),
				EnumAdaptor.valueOf(calculationOvertimeDisplayAtr,UseAtr.class));
	}

}
