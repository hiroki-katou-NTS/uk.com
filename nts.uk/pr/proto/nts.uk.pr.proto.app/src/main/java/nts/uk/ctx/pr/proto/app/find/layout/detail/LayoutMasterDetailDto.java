package nts.uk.ctx.pr.proto.app.find.layout.detail;

import lombok.Value;
import nts.uk.ctx.pr.proto.dom.layout.detail.LayoutMasterDetail;

/** Finder DTO of item */
@Value
public class LayoutMasterDetailDto {
	private String layoutCode;
	/**開始年月*/
	private int startYm;
	/** 終了年月 */
	private int endYm;
	/**カテゴリ区分 */
	private int categoryAtr;
	/**項目CD*/
	private String itemCode;
	/**自動採番された行番号	 */
	private String autoLineId;
	/**項目位置（列）	 */
	private int itemPosColumn;
	/** name */
	private String itemAbName;
	/** 合計対象区分 */
	private int sumScopeAtr;
	/**計算方法 */
	private int calculationMethod;
	/**按分設定 */
	private int distributeSet;
	/**按分方法	 */
	private int distributeWay;
	/**個人金額コード	 */
	private String personalWageCode;
	/**エラー範囲上限利用区分	 */
	private int isUseHighError;
	/**エラー範囲上限	 */
	private int errRangeHigh;
	/**エラー範囲下限利用区分	 */
	private int isUseLowError;
	/**エラー範囲下限	 */
	private int errRangeLow;
	/**アラーム範囲下限利用区分 */
	private int isUseHighAlam;
	/**アラーム範囲上限	 */
	private int alamRangeHigh;
	/**アラーム範囲下限利用区分	 */
	private int isUseLowAlam;
	/**アラーム範囲下限	 */
	private int alamRangeLow;
	
	public static LayoutMasterDetailDto fromDomain(LayoutMasterDetail domain)
	{
		return new LayoutMasterDetailDto(
				domain.getLayoutCode().v(),
				domain.getStartYm().v(),
				domain.getEndYm().v(),
				domain.getCategoryAtr().value,
				domain.getItemCode().v(), 
				domain.getAutoLineId().v(),
				domain.getItemPosColumn().v(),
				domain.getItemAbName().v(),
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
