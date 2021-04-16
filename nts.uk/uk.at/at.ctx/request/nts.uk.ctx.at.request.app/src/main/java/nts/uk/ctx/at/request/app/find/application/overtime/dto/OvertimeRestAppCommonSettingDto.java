package nts.uk.ctx.at.request.app.find.application.overtime.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OvertimeRestAppCommonSettingDto {
	/**
	 * 会社ID
	 * companyID
	 */
	private String companyID;
	
	/**
	 * 申請種類
	 */
	private int appType;
	
	/**
	 * 乖離理由入力区分
	 */
	private int divergenceReasonInputAtr;
	
	/**
	 * 乖離理由定型区分
	 */
	private int divergenceReasonFormAtr;
	
	/**
	 * 乖離理由必須
	 */
	private int divergenceReasonRequired;
	
	/**
	 * 事前表示区分
	 */
	private int preDisplayAtr;
	
	/**
	 * 事前超過表示設定
	 */
	private int preExcessDisplaySetting;
	
	/**
	 * 加給時間表示区分
	 */
	private int bonusTimeDisplayAtr;
	
	/**
	 * 外出区分設定
	 */
	private int outingSettingAtr;
	
	/**
	 * 実績表示区分
	 */
	private int performanceDisplayAtr;
	
	/**
	 * 実績超過区分
	 */
	private int performanceExcessAtr;
	
	/**
	 * 指示表示区分
	 */
	private int intructDisplayAtr;
	
	/**
	 * 時間外表示区分
	 */
	private int extratimeDisplayAtr;
	
	/**
	 * 時間外超過区分
	 */
	private int extratimeExcessAtr;
	/**
	 * 申請日矛盾区分
	 */
	private int appDateContradictionAtr;
	
	/**
	 * 計算残業時間表示区分
	 */
	private int calculationOvertimeDisplayAtr;
//	public static OvertimeRestAppCommonSettingDto convertToDto(OvertimeRestAppCommonSetting domain) {
//		if(domain==null) return null;
//		return new OvertimeRestAppCommonSettingDto(
//				domain.getCompanyID(),
//				domain.getAppType().value,
//				domain.getDivergenceReasonInputAtr().value,
//				domain.getDivergenceReasonFormAtr().value,
//				domain.getDivergenceReasonRequired().value,
//				domain.getPreDisplayAtr().value,
//				domain.getPreExcessDisplaySetting().value,
//				domain.getBonusTimeDisplayAtr().value,
//				domain.getOutingSettingAtr().value,
//				domain.getPerformanceDisplayAtr().value,
//				domain.getPerformanceExcessAtr().value,
//				domain.getIntructDisplayAtr().value,
//				domain.getExtratimeDisplayAtr().value,
//				domain.getExtratimeExcessAtr().value,
//				domain.getAppDateContradictionAtr().value,
//				domain.getCalculationOvertimeDisplayAtr().value);
//	}
//
//	public OvertimeRestAppCommonSetting toDomain() {
//		return OvertimeRestAppCommonSetting.createSimpleFromJavaType(
//				companyID,
//				appType,
//				divergenceReasonInputAtr,
//				divergenceReasonFormAtr,
//				divergenceReasonRequired,
//				preDisplayAtr,
//				preExcessDisplaySetting,
//				bonusTimeDisplayAtr,
//				outingSettingAtr,
//				performanceDisplayAtr,
//				performanceExcessAtr,
//				intructDisplayAtr,
//				extratimeDisplayAtr,
//				extratimeExcessAtr,
//				appDateContradictionAtr,
//				calculationOvertimeDisplayAtr);
//	}
}
