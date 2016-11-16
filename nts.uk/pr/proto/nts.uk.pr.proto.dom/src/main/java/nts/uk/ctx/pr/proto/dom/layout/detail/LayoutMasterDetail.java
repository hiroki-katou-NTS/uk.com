package nts.uk.ctx.pr.proto.dom.layout.detail;

import java.util.List;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.YearMonth;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.proto.dom.enums.CategoryAtr;
import nts.uk.ctx.pr.proto.dom.enums.CommuteAtr;
import nts.uk.ctx.pr.proto.dom.enums.DisplayAtr;
import nts.uk.ctx.pr.proto.dom.itemmaster.ItemCode;
import nts.uk.ctx.pr.proto.dom.layout.LayoutCode;
import nts.uk.ctx.pr.proto.dom.layout.detail.distribute.Distribute;
import nts.uk.ctx.pr.proto.dom.personalinformation.wage.wagename.PersonalWageCode;
/**
 * 
 * 明 細 書 マ ス タ 明 細
 *
 */
public class LayoutMasterDetail extends AggregateRoot{
	/**会社ＣＤ */
	@Getter
	private CompanyCode companyCode;
	/**カテゴリ区分 */
	@Getter
	private CategoryAtr categoryAttribute;
	@Getter
	private List<RangeChecker> alarm;
	/**計算方法 */
	@Getter
	private CalculationMethod calculationMethod;
	/**項目位置 */
	@Getter
	private ColumnPosition columnPosition;
	/** 按分設定  */
	@Getter
	private List<Distribute> distribute;	
	/** 表示区分 */
	@Getter
	private DisplayAtr displayAtr;
	@Getter
	private List<RangeChecker> error;
	/**会社ＣＤ */
	@Getter
	private ItemCode itemCode;
	/**明細書コード*/
	@Getter
	private LayoutCode layoutCode;
	/**開始年月*/
	@Getter
	private YearMonth startYM;
	/** 終了年月 */
	@Getter
	private YearMonth endYM;
	/** 合計対象区分 */
	@Getter
	private SumScopeAtr sumScopeAtr;
	//今回、対応対象外	
//	/** 計算式コード */
//	@Getter
//	private FormulaCode formulaCode;	
//	/** 賃金テーブルコード */
//	@Getter
//	private WageTableCode wageTableCode;
//	/** 共通金額 */
//	@Getter
//	private CommonAmount commonAmount;
	
	/** 支給相殺コード */
	@Getter
	private ItemCode setOffItemCode;
	
	@Getter
	/**通勤区分*/
	private CommuteAtr commuteAtr;
	/**個人金額コード	 */
	@Getter
	private PersonalWageCode personalWageCode;
	
	public LayoutMasterDetail(CompanyCode companyCode, CategoryAtr categoryAttribute,
			CalculationMethod calculationMethod, ColumnPosition columnPosition,
			ItemCode itemCode, LayoutCode layoutCode, YearMonth startYM, SumScopeAtr sumScopeAtr,
			List<RangeChecker> alarm, List<RangeChecker> error,List<Distribute> distribute,PersonalWageCode personalWageCode) {
		super();
		this.companyCode = companyCode;
		this.categoryAttribute = categoryAttribute;
		this.calculationMethod = calculationMethod;
		this.columnPosition = columnPosition;
		this.itemCode = itemCode;
		this.layoutCode = layoutCode;
		this.startYM = startYM;
		this.sumScopeAtr = sumScopeAtr;
		this.alarm = alarm;
		this.error = error;
		this.distribute = distribute;
		this.personalWageCode = personalWageCode;
	}
	
	
}
