package nts.uk.ctx.pr.proto.dom.layout.detail;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.YearMonth;
import nts.gul.util.Range;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.proto.dom.enums.CategoryAtr;
import nts.uk.ctx.pr.proto.dom.enums.CommuteAtr;
import nts.uk.ctx.pr.proto.dom.enums.DisplayAtr;
import nts.uk.ctx.pr.proto.dom.enums.UseOrNot;
import nts.uk.ctx.pr.proto.dom.itemmaster.ItemCode;
import nts.uk.ctx.pr.proto.dom.layout.LayoutCode;
import nts.uk.ctx.pr.proto.dom.layout.detail.distribute.Distribute;
import nts.uk.ctx.pr.proto.dom.layout.detail.distribute.DistributeSet;
import nts.uk.ctx.pr.proto.dom.layout.detail.distribute.DistributeWay;
import nts.uk.ctx.pr.proto.dom.personalinfo.wage.wagename.PersonalWageCode;
/**
 * 
 * 明 細 書 マ ス タ 明 細
 *
 */
public class LayoutMasterDetail extends AggregateRoot{
	/**会社ＣＤ */
	@Getter
	private CompanyCode companyCode;
	/**明細書コード*/
	@Getter
	private LayoutCode layoutCode;
	/**開始年月*/
	@Getter
	private YearMonth startYM;
	/** 終了年月 */
	@Getter
	private YearMonth endYM;
	/**カテゴリ区分 */
	@Getter
	private CategoryAtr categoryAtr;	
	/**項目CD */
	@Getter
	private ItemCode itemCode;
	@Getter
	private RangeChecker alarm;
	/**計算方法 */
	@Getter
	private CalculationMethod calculationMethod;
	/**項目位置 */
	@Getter
	private ColumnPosition columnPosition;
	/** 按分設定  */
	@Getter
	private Distribute distribute;	
	/** 表示区分 */
	@Getter
	private DisplayAtr displayAtr;
	@Getter
	private RangeChecker error;
	
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
	public LayoutMasterDetail(ItemCode itemCode,
			SumScopeAtr sumScopeAtr,
			CalculationMethod calculationMethod, 
			Distribute distribute,
			PersonalWageCode personalWageCode, 
			RangeChecker error,
			RangeChecker alarm) {
		super();
		
		this.itemCode = itemCode;
		this.sumScopeAtr = sumScopeAtr;
		this.calculationMethod = calculationMethod;		
		this.distribute = distribute;
		this.personalWageCode = personalWageCode;		
		this.error = error;
		this.alarm = alarm;
	}
	
	public static LayoutMasterDetail createSimpleFromJavaType(String itemCode,
			int sumScopeAtr,
			int calculationMethod,
			int distributeWay,
			int distributeSet, 
			String personalWageCode,
			int isErrorUseHigh,
			int errorRangeHigh,
			int isErrorUserLow,
			int errorRangeLow,
			int isAlamUseHigh,
			int alamRangeHigh,
			int isAlamUseLow,
			int alamRangeLow){
				Range<Integer> error = Range.between(errorRangeLow, errorRangeHigh);
				Range<Integer> alam = Range.between(alamRangeLow, alamRangeHigh);
				return new LayoutMasterDetail(new ItemCode(itemCode), 
						EnumAdaptor.valueOf(sumScopeAtr, SumScopeAtr.class),
						EnumAdaptor.valueOf(calculationMethod, CalculationMethod.class), 
						new Distribute(EnumAdaptor.valueOf(distributeWay, DistributeWay.class)
								, EnumAdaptor.valueOf(distributeSet, DistributeSet.class)),
						new PersonalWageCode(personalWageCode),
						new RangeChecker(EnumAdaptor.valueOf(isErrorUseHigh, UseOrNot.class),
								EnumAdaptor.valueOf(isErrorUserLow, UseOrNot.class),
								error), 
						new RangeChecker(EnumAdaptor.valueOf(isAlamUseHigh, UseOrNot.class),
								EnumAdaptor.valueOf(isAlamUseLow, UseOrNot.class), 
								alam));
		
	}
	
}
