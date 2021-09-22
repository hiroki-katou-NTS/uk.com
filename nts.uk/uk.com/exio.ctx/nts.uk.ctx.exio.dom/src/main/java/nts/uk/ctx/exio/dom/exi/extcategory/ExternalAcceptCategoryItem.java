package nts.uk.ctx.exio.dom.exi.extcategory;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.exio.dom.exo.categoryitemdata.DataType;
import nts.uk.shr.com.enumcommon.NotUseAtr;
/**
 * 外部受入カテゴリ項目データ
 * @author do_dt
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ExternalAcceptCategoryItem extends AggregateRoot {
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
	private DataType dataType;
	
	/**	アルファベット禁止項目 */
	private Optional<AlphaUseFlg> alphaUseFlg;
	
	/**主キー区分	 */
	private NotUseAtr primatyKeyFlg;
	
	/**primitiveValue名	 */
	private Optional<String> primitiveName;
	
	/**小数部桁数	 */
	private Optional<Integer> decimalDigit;
	
	/**	小数部単位区分 */
	private Optional<ExiDecimalUnit> decimalUnit;
	
	/**	必須区分 */
	private NotUseAtr requiredFlg;
	
	/**数値範囲開始	 */
	private Optional<Double> numberRangeStart;
	
	/**	数値範囲終了 */
	private Optional<Double> numberRangeEnd;
	
	/**	数値範囲開始２ */
	private Optional<Double> numberRangeStart2;
	
	/**数値範囲終了２	 */
	private Optional<Double> numberRangeEnd2;
	
	/**	特殊区分 */
	private SpecialExternalItem specialFlg;
	
	/**	必須桁数 */
	private Optional<Integer> requiredNumber;
	
	/**	表示区分 */
	private NotUseAtr displayFlg;
	
	/**	履歴区分 */
	private Optional<NotUseAtr> historyFlg;
	
	/**	履歴継続区分 */
	private Optional<ExternalHistoryContiFlg> historyContiFlg;
	
}
