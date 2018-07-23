package nts.uk.ctx.at.request.app.find.setting.company.otrestappcommon;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.overtimerestappcommon.OvertimeRestAppCommonSetting;
@AllArgsConstructor
@Data
@NoArgsConstructor
public class OvertimeRestAppCommonSetDto {
	/**
	 * 加給時間表示区分
	 */
	public int bonusTimeDisplayAtr;
	
	/**
	 * 乖離理由定型区分
	 */
	public int divergenceReasonFormAtr;
	
	/**
	 * 乖離理由入力区分
	 */
	public int divergenceReasonInputAtr;
	
	/**
	 * 実績表示区分
	 */
	public int performanceDisplayAtr;
	
	/**
	 * 事前表示区分
	 */
	public int preDisplayAtr;
	
	/**
	 * 計算残業時間表示区分
	 */
	public int calculationOvertimeDisplayAtr;
	
	/**
	 * 時間外表示区分
	 */
	public int extratimeDisplayAtr;
	
	/**
	 * 事前超過表示設定
	 */
	public int preExcessDisplaySetting;
	
	/**
	 * 実績超過区分
	 */
	public int performanceExcessAtr;
	
	/**
	 * 時間外超過区分
	 */
	public int extratimeExcessAtr;
	
	/**
	 * 申請日矛盾区分
	 */
	public int appDateContradictionAtr;
	
	public static OvertimeRestAppCommonSetDto convertToDto(OvertimeRestAppCommonSetting domain){
		return new OvertimeRestAppCommonSetDto(domain.getBonusTimeDisplayAtr().value, 
												domain.getDivergenceReasonFormAtr().value, 
												domain.getDivergenceReasonInputAtr().value, 
												domain.getPerformanceDisplayAtr().value, 
												domain.getPreDisplayAtr().value, 
												domain.getCalculationOvertimeDisplayAtr().value, 
												domain.getExtratimeDisplayAtr().value, 
												domain.getPreExcessDisplaySetting().value,
												domain.getPerformanceExcessAtr().value, 
												domain.getExtratimeExcessAtr().value, 
												domain.getAppDateContradictionAtr().value);
	}
}
