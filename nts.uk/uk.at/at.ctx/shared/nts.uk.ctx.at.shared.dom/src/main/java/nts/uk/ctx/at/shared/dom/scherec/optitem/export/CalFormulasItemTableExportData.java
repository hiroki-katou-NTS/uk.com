package nts.uk.ctx.at.shared.dom.scherec.optitem.export;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CalFormulasItemTableExportData {
	// 任意項目NO
	private String optionalItemNo;

	// 任意項目名称
	private String optionalItemName;

	//// 雇用条件区分
	private String empConditionAtr;
	// 実績区分
	private Integer performanceAtr;

	// 任意項目計算式
	// 計算式ID
	private String formulaId;

	// 任意項目計算式の並び順
	// 並び順
	private Integer dispOrder;

	// 任意項目計算式
	// 記号
	private String symbol;

	// 属性
	private Integer formulaAtr;

	// 計算式名称
	private String formulaName;
	
	// 任意項目計算式設定
	// 計算区分
	private Integer calcAtr;
	
	/** The unit. */
	// 単位
	private Integer unit;
	
	/** The rounding. */
	// 端数処理
	private Integer rounding;

	/** The minus segment. A10_13 */
	// マイナス区分
	private Integer minusSegment;

}
