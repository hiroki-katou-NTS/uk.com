package nts.uk.ctx.pr.proto.dom.layout.detail;
import lombok.AllArgsConstructor;
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
import nts.uk.ctx.pr.proto.dom.layout.line.AutoLineId;
import nts.uk.ctx.pr.proto.dom.personalinfo.wage.wagename.PersonalWageCode;
/**
 * 
 * 明 細 書 マ ス タ 明 細
 *
 */
@AllArgsConstructor
public class LayoutMasterDetail extends AggregateRoot{
	/**会社ＣＤ */
	@Getter
	private CompanyCode companyCode;
	/**明細書コード*/
	@Getter
	private LayoutCode layoutCode;
	/**開始年月*/
	@Getter
	private YearMonth startYm;
	/** 終了年月 */
	@Getter
	private YearMonth endYm;
	/**カテゴリ区分 */
	@Getter
	private CategoryAtr categoryAtr;	
	/**項目CD */
	@Getter
	private ItemCode itemCode;
	/**自動採番された行番号	 */
	@Getter
	private AutoLineId autoLineId;
	/**項目位置（列）	 */
	@Getter
	private ItemPosColumn itemPosColumn;
	@Getter
	private RangeChecker alarm;
	/**計算方法 */
	@Getter
	private CalculationMethod calculationMethod;
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
	
//	public LayoutMasterDetail(
//			CompanyCode companyCode,
//			LayoutCode layoutCode,
//			YearMonth startYm,
//			YearMonth endYm,
//			CategoryAtr categoryAtr,
//			ItemCode itemCode,
//			AutoLineId autoLineId,
//			DisplayAtr displayAtr,
//			SumScopeAtr sumScopeAtr,
//			CalculationMethod calculationMethod, 
//			Distribute distribute,
//			PersonalWageCode personalWageCode, 
//			ItemCode setOffItemCode,
//			CommuteAtr commuteAtr,
//			RangeChecker error,
//			RangeChecker alarm) {
//		super();
//		this.companyCode = companyCode;
//		this.layoutCode = layoutCode;
//		this.startYm = startYm;
//		this.endYm = endYm;
//		this.categoryAtr = categoryAtr;
//		this.itemCode = itemCode;
//		this.autoLineId = autoLineId;
//		this.displayAtr = displayAtr;
//		this.sumScopeAtr = sumScopeAtr;
//		this.calculationMethod = calculationMethod;		
//		this.distribute = distribute;
//		this.personalWageCode = personalWageCode;		
//		this.setOffItemCode = setOffItemCode;
//		this.commuteAtr = commuteAtr;
//		this.error = error;
//		this.alarm = alarm;
//	}
	
	public static LayoutMasterDetail createSimpleFromJavaType(
			String companyCode,
			String layoutCode,
			int startYm,
			int endYm,
			int categoryAtr,
			String itemCode,
			String autoLineId,
			int displayAtr,
			int sumScopeAtr,
			int calculationMethod,
			int distributeWay,
			int distributeSet, 
			String personalWageCode,
			String setOffItemCode,
			int commuteAtr,
			int isErrorUseHigh,
			int errorRangeHigh,
			int isErrorUserLow,
			int errorRangeLow,
			int isAlamUseHigh,
			int alamRangeHigh,
			int isAlamUseLow,
			int alamRangeLow,
			int itemPosColumn){
				Range<Integer> error = Range.between(errorRangeLow, errorRangeHigh);
				Range<Integer> alam = Range.between(alamRangeLow, alamRangeHigh);
				return new LayoutMasterDetail(
						new CompanyCode(companyCode),
						new LayoutCode(layoutCode),
						YearMonth.of(startYm),
						YearMonth.of(endYm),
						EnumAdaptor.valueOf(categoryAtr, CategoryAtr.class),
						new ItemCode(itemCode), 
						new AutoLineId(autoLineId),
						new ItemPosColumn(itemPosColumn),
						new RangeChecker(EnumAdaptor.valueOf(isErrorUseHigh, UseOrNot.class),
								EnumAdaptor.valueOf(isErrorUserLow, UseOrNot.class),
								error), 
						EnumAdaptor.valueOf(calculationMethod, CalculationMethod.class),
						new Distribute(
								EnumAdaptor.valueOf(distributeWay, DistributeWay.class), 
								EnumAdaptor.valueOf(distributeSet, DistributeSet.class)),
						EnumAdaptor.valueOf(displayAtr, DisplayAtr.class),
						new RangeChecker(EnumAdaptor.valueOf(isAlamUseHigh, UseOrNot.class),
								EnumAdaptor.valueOf(isAlamUseLow, UseOrNot.class), 
								alam),
						EnumAdaptor.valueOf(sumScopeAtr, SumScopeAtr.class),
						new ItemCode(setOffItemCode),
						EnumAdaptor.valueOf(commuteAtr, CommuteAtr.class),
						new PersonalWageCode(personalWageCode)
						);
		
	}
	

	public static LayoutMasterDetail createFromDomain(
			CompanyCode companyCode,
			LayoutCode layoutCode,
			YearMonth startYm,
			YearMonth endYm,
			CategoryAtr categoryAtr,
			ItemCode itemCode, 
			AutoLineId autoLineId,
			ItemPosColumn itemPosColumn,
			RangeChecker rangeError, 
			CalculationMethod calculationMethod, 
			Distribute distribute,
			DisplayAtr displayAtr,
			RangeChecker rangeAlarm,
			SumScopeAtr sumScopeAtr, 
			ItemCode setOffItemCode,
			CommuteAtr commuteAtr, 
			PersonalWageCode personalWageCode) {
		
		return new LayoutMasterDetail(
				companyCode,
				layoutCode,
				startYm,
				endYm,
				categoryAtr,
				itemCode, 
				autoLineId,
				itemPosColumn,
				rangeError, 
				calculationMethod, 
				distribute,
				displayAtr,
				rangeAlarm,
				sumScopeAtr, 
				setOffItemCode,
				commuteAtr, 
				personalWageCode);
	}
	
}
