package nts.uk.ctx.pr.proto.app.layoutmasterdetail.find;

import lombok.Value;
import nts.uk.ctx.pr.proto.dom.layout.detail.LayoutMasterDetail;

/** Finder DTO of item */
@Value
public class LayoutMasterDetailDto {
	/**項目CD*/
	String itemCode;
	/** 合計対象区分 */
	int sumScopeAtr;
	/**計算方法 */
	int calculationMethod;
	/**按分設定 */
	int distributeSet;
	/**按分方法	 */
	int distributeWay;
	/**個人金額コード	 */
	String personalWageCode;
	/**エラー範囲上限利用区分	 */
	int isUseHighError;
	/**エラー範囲上限	 */
	int errRangeHigh;
	/**エラー範囲下限利用区分	 */
	int isUseLowError;
	/**エラー範囲下限	 */
	int errRangeLow;
	/**アラーム範囲下限利用区分 */
	int isUseHighAlam;
	/**アラーム範囲上限	 */
	int alamRangeHigh;
	/**アラーム範囲下限利用区分	 */
	int isUseLowAlam;
	/**アラーム範囲下限	 */
	int alamRangeLow;
	
	public static LayoutMasterDetailDto fromDomain(LayoutMasterDetail domain)
	{
		return new LayoutMasterDetailDto( 
				domain.getItemCode().v(), 
				domain.getSumScopeAtr().value,
				domain.getCalculationMethod().value,
				domain.getDistribute().getSetting().value, 
				domain.getDistribute().getMethod().value,
				domain.getPersonalWageCode().v(),
				domain.getError().getIsUseHigh().value,
				domain.getError().getRange().max(),
				domain.getError().getIsUseLow().value,				
				domain.getError().getRange().min(),				
				domain.getAlarm().getIsUseHigh().value,
				domain.getAlarm().getRange().max(),
				domain.getAlarm().getIsUseLow().value,
				domain.getAlarm().getRange().min());		
	}
	
}
