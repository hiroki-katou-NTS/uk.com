package nts.uk.ctx.pr.screen.app.query.paymentdata.result;

import java.util.List;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.pr.proto.dom.layout.detail.RangeChecker;

/**
 * 
 * 明 細 書 マ ス タ 明 細
 *
 */
public class LayoutMasterDetailDto extends AggregateRoot {
	/** 会社ＣＤ */
	@Getter
	private String companyCode;
	/** カテゴリ区分 */
	@Getter
	private int categoryAttribute;
	@Getter
	private List<RangeChecker> alarm;
	/** 計算方法 */
	@Getter
	private int calculationMethod;
	/** 項目位置 */
	@Getter
	private int columnPosition;
	// /** 按分設定 */
	// @Getter
	// private List<Distribute> distribute;
	/** 表示区分 */
	@Getter
	private int displayAtr;
	@Getter
	private List<RangeChecker> error;
	/** 会社ＣＤ */
	@Getter
	private String itemCode;
	/** 明細書コード */
	@Getter
	private String layoutCode;
	/** 開始年月 */
	@Getter
	private int startYM;
	/** 終了年月 */
	@Getter
	private int endYM;
	/** 合計対象区分 */
	@Getter
	private int sumScopeAtr;
	// 今回、対応対象外
	// /** 計算式コード */
	// @Getter
	// private FormulaCode formulaCode;
	// /** 賃金テーブルコード */
	// @Getter
	// private WageTableCode wageTableCode;
	// /** 共通金額 */
	// @Getter
	// private CommonAmount commonAmount;

	/** 支給相殺コード */
	@Getter
	private String setOffItemCode;

	@Getter
	/** 通勤区分 */
	private int commuteAtr;
	/** 個人金額コード */
	@Getter
	private String personalWageCode;

}
