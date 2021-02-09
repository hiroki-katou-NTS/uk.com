package nts.uk.ctx.exio.app.find.exi.category;

import lombok.AllArgsConstructor;
import lombok.Value;

/**
 * 外部受入カテゴリ項目データ
 */
@AllArgsConstructor
@Value
public class ExAcpCtgItemDatDto {

	/**
	 * 外部受入カテゴリID
	 */
	private int categoryId;
	
	/**カテゴリ項目NO	 */
	private int itemNo;

	/**	外部受入カテゴリ 項目名*/
	private String itemName;
	
	/**	テーブル名 */
	private String tableName;
	
	/**	カラム名 */
	private String columnName;
	
	/**	データ型 */
	private int dataType;
	
	/**	アルファベット禁止項目 */
	private Integer alphaUseFlg;
	
	/**主キー区分	 */
	private int primatyKeyFlg;
	
	/**primitiveValue名	 */
	private String primitiveName;
	
	/**小数部桁数	 */
	private Integer decimalDigit;
	
	/**	小数部単位区分 */
	private Integer decimalUnit;

	/**	必須区分 */
	private int requiredFlg;
	
	/**数値範囲開始	 */
	private Double numberRangeStart;
	
	/**	数値範囲終了 */
	private Double numberRangeEnd;

	/**	数値範囲開始２ */
	private Double numberRangeStart2;
	
	/**数値範囲終了２	 */
	private Double numberRangeEnd2;
	
	/**	特殊区分 */
	private int specialFlg;
	
	/**	必須桁数 */
	private Integer requiredNumber;
	
	/**	表示区分 */
	private int displayFlg;
	
	/**	履歴区分 */
	private int historyFlg;
	
	/**	履歴継続区分 */
	private int historyContiFlg;
}
