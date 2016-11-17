package nts.uk.ctx.pr.proto.app.layout.detail.command;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.pr.proto.dom.layout.detail.LayoutMasterDetail;

@Getter
@Setter
public class UpdateLayoutDetailCommand {
	/**会社ＣＤ */
	private String companyCode;
	/**明細書コード*/
	private String layoutCode;
	/**開始年月*/
	private int startYm;
	/** 終了年月 */
	private int endYm;
	/**カテゴリ区分 */
	private int categoryAtr;	
	/**項目CD */
	private String itemCode;
	/**自動採番された行番号	 */
	private String autoLineId;
	/**項目位置（列）	 */
	private int itemPosColumn;
	/**計算方法 */	
	private int calculationMethod;
	/**項目位置 */
	private String columnPosition;
	/** 按分設定  */
	private int distribute;	
	/** 表示区分 */	
	private int displayAtr;
	/** 合計対象区分 */	
	private int sumScopeAtr;	
	//今回、対応対象外	
//	/** 計算式コード */
//	private String formulaCode;	
//	/** 賃金テーブルコード */
//	private String wageTableCode;
//	/** 共通金額 */
//	private String commonAmount;	
	/** 支給相殺コード */	
	private String setOffItemCode;	
	/**通勤区分*/
	private int commuteAtr;
	/**個人金額コード	 */
	private String personalWageCode;	
	/**按分方法	 */
	private int distributeWay;
	/**按分設定	 */
	private int distributeSet;
	/**	エラー範囲上限利用区分 */
	private int isErrorUseHigh;
	/**	エラー範囲上限 */
	private int errorRangeHigh;
	/**エラー範囲下限利用区分	 */
	private int isErrorUserLow;
	/**エラー範囲下限 */
	private int errorRangeLow;
	/**アラーム範囲上限利用区分	 */
	private int isAlamUseHigh;
	/**アラーム範囲上限	 */
	private int alamRangeHigh;
	/**	 アラーム範囲下限利用区分*/
	private int isAlamUseLow;
	/**アラーム範囲下限	 */
	private int alamRangeLow;
	
	public LayoutMasterDetail toDomain(){
		return LayoutMasterDetail.createSimpleFromJavaType(
				companyCode, 
				layoutCode, 
				startYm, 
				endYm, 
				categoryAtr, 
				itemCode, 
				autoLineId, 
				displayAtr, 
				sumScopeAtr, 
				calculationMethod, 
				distributeWay, 
				distributeSet,
				personalWageCode, 
				setOffItemCode,
				commuteAtr,
				isErrorUseHigh, 
				errorRangeHigh, 
				isErrorUserLow, 
				errorRangeLow,
				isAlamUseHigh, 
				alamRangeHigh, 
				isAlamUseLow,
				alamRangeLow,
				itemPosColumn);
	}
}
