package nts.uk.ctx.pr.core.dom.rule.employment.layout.detail;
import java.math.BigDecimal;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.YearMonth;
import nts.gul.util.Range;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.enums.CategoryAtr;
import nts.uk.ctx.pr.core.dom.enums.CommuteAtr;
import nts.uk.ctx.pr.core.dom.enums.DisplayAtr;
import nts.uk.ctx.pr.core.dom.enums.UseOrNot;
import nts.uk.ctx.pr.core.dom.itemmaster.ItemCode;
import nts.uk.ctx.pr.core.dom.itemmaster.ItemName;
import nts.uk.ctx.pr.core.dom.personalinfo.wage.wagename.PersonalWageCode;
import nts.uk.ctx.pr.core.dom.rule.employment.layout.LayoutCode;
import nts.uk.ctx.pr.core.dom.rule.employment.layout.detail.distribute.Distribute;
import nts.uk.ctx.pr.core.dom.rule.employment.layout.detail.distribute.DistributeSet;
import nts.uk.ctx.pr.core.dom.rule.employment.layout.detail.distribute.DistributeWay;
import nts.uk.ctx.pr.core.dom.rule.employment.layout.line.AutoLineId;
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
	@Getter
	private String historyId;
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
	
	@Getter
	private ItemName itemAbName;
	
	public LayoutMasterDetail(
			CompanyCode companyCode,
			LayoutCode layoutCode,
			YearMonth startYm,
			YearMonth endYm,
			CategoryAtr categoryAtr,	
			ItemCode itemCode,
			AutoLineId autoLineId,
			ItemPosColumn itemPosColumn,
			RangeChecker alarm,
			CalculationMethod calculationMethod,
			Distribute distribute,
			DisplayAtr displayAtr,
			RangeChecker error,
			SumScopeAtr sumScopeAtr,
			ItemCode setOffItemCode,
			CommuteAtr commuteAtr,
			PersonalWageCode personalWageCode,
			String historyId) {
		super();
		this.companyCode = companyCode;
		this.layoutCode = layoutCode;
		this.startYm = startYm;
		this.endYm = endYm;
		this.categoryAtr = categoryAtr;	
		this.itemCode = itemCode;
		this.autoLineId = autoLineId;
		this.itemPosColumn = itemPosColumn;
		this.alarm = alarm;
		this.calculationMethod = calculationMethod;
		this.distribute = distribute;
		this.displayAtr = displayAtr;
		this.error = error;
		this.sumScopeAtr = sumScopeAtr;
		this.setOffItemCode = setOffItemCode;
		this.commuteAtr = commuteAtr;
		this.personalWageCode = personalWageCode;
		this.historyId = historyId;
	}
	
	public static LayoutMasterDetail createFromJavaType(
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
			BigDecimal errorRangeHigh,
			int isErrorUserLow,
			BigDecimal errorRangeLow,
			int isAlamUseHigh,
			BigDecimal alamRangeHigh,
			int isAlamUseLow,
			BigDecimal alamRangeLow,
			int itemPosColumn,
			String historyId){
		
		Range<BigDecimal> error = Range.between(errorRangeLow, errorRangeHigh);
		Range<BigDecimal> alam = Range.between(alamRangeLow, alamRangeHigh);
		
		return new LayoutMasterDetail(
				new CompanyCode(companyCode),
				new LayoutCode(layoutCode),
				YearMonth.of(startYm),
				YearMonth.of(endYm),
				EnumAdaptor.valueOf(categoryAtr, CategoryAtr.class),
				new ItemCode(itemCode), 
				new AutoLineId(autoLineId),
				new ItemPosColumn(itemPosColumn),
				new RangeChecker(EnumAdaptor.valueOf(isAlamUseHigh, UseOrNot.class),
						EnumAdaptor.valueOf(isAlamUseLow, UseOrNot.class), 
						alam),
				EnumAdaptor.valueOf(calculationMethod, CalculationMethod.class),
				new Distribute(
						EnumAdaptor.valueOf(distributeWay, DistributeWay.class), 
						EnumAdaptor.valueOf(distributeSet, DistributeSet.class)),
				EnumAdaptor.valueOf(displayAtr, DisplayAtr.class),
				new RangeChecker(EnumAdaptor.valueOf(isErrorUseHigh, UseOrNot.class),
						EnumAdaptor.valueOf(isErrorUserLow, UseOrNot.class),
						error), 
				EnumAdaptor.valueOf(sumScopeAtr, SumScopeAtr.class),
				new ItemCode(setOffItemCode),
				EnumAdaptor.valueOf(commuteAtr, CommuteAtr.class),
				new PersonalWageCode(personalWageCode),
				historyId
				);
		
	}
	
	/**
	 * with ItemAbName of ItemCode
	 * @param companyCode
	 * @param layoutCode
	 * @param startYm
	 * @param endYm
	 * @param categoryAtr
	 * @param itemCode
	 * @param autoLineId
	 * @param displayAtr
	 * @param sumScopeAtr
	 * @param calculationMethod
	 * @param distributeWay
	 * @param distributeSet
	 * @param personalWageCode
	 * @param setOffItemCode
	 * @param commuteAtr
	 * @param isErrorUseHigh
	 * @param errorRangeHigh
	 * @param isErrorUserLow
	 * @param errorRangeLow
	 * @param isAlamUseHigh
	 * @param alamRangeHigh
	 * @param isAlamUseLow
	 * @param alamRangeLow
	 * @param itemPosColumn
	 * @return
	 */
	public static LayoutMasterDetail createFromJavaTypeWithName(
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
			BigDecimal errorRangeHigh,
			int isErrorUserLow,
			BigDecimal errorRangeLow,
			int isAlamUseHigh,
			BigDecimal alamRangeHigh,
			int isAlamUseLow,
			BigDecimal alamRangeLow,
			int itemPosColumn,
			String itemAbName,
			String historyId){
				
		LayoutMasterDetail result = createFromJavaType(companyCode, layoutCode, startYm, endYm, categoryAtr, itemCode, 
				autoLineId, displayAtr, sumScopeAtr, calculationMethod, distributeWay, 
				distributeSet, personalWageCode, setOffItemCode, commuteAtr, isErrorUseHigh, 
				errorRangeHigh, isErrorUserLow, errorRangeLow, isAlamUseHigh, alamRangeHigh, 
				isAlamUseLow, alamRangeLow, itemPosColumn, historyId);		
		
		result.setItemAbName(itemAbName);
		return result;
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
			PersonalWageCode personalWageCode,
			String historyId) {
		
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
				personalWageCode,
				historyId);
	}
	
	public void setItemAbName(String itemAbName) {
		this.itemAbName = new ItemName(itemAbName);
	}
	/**
	 * CalculationMethod = PERSONAL_INFORMATION
	 * @return
	 */
	public boolean isCalMethodPesonalInfomation() {
		return CalculationMethod.PERSONAL_INFORMATION == this.calculationMethod;
	}
	
	/**
	 * CalculationMethod = MANUAL_ENTRY || FORMULA || WAGE_TABLE || COMMON_AMOUNT_MONEY
	 * @return
	 */
	public boolean isCalMethodManualOrFormulaOrWageOrCommon() {
		return this.calculationMethod == CalculationMethod.MANUAL_ENTRY 
				|| this.calculationMethod == CalculationMethod.FORMULA 
				|| this.calculationMethod == CalculationMethod.WAGE_TABLE 
				|| this.calculationMethod == CalculationMethod.COMMON_AMOUNT_MONEY;
	}
	
	/**
	 * CalculationMethod = MANUAL_ENTRY || FORMULA || WAGE_TABLE || COMMON_AMOUNT_MONEY || PAYMENT_CANCELED
	 * @return
	 */
	public boolean isCalMethodManualOrFormulaOrWageOrCommonOrPaymentCanceled() {
		return this.isCalMethodManualOrFormulaOrWageOrCommon() 
				|| this.calculationMethod == CalculationMethod.PAYMENT_CANCELED;
	}
	
	/**
	 * Check Category = PERSONAL_TIME
	 * @return
	 */
	public boolean isCategoryPersonalTime() {
		return CategoryAtr.PERSONAL_TIME == this.categoryAtr;
	}
	
	/**
	 * Check Category = PAYMENT
	 * @return
	 */
	public boolean isCategoryPayment() {
		return CategoryAtr.PAYMENT == this.categoryAtr;
	}
	
	/**
	 * Check Category = DEDUCTION
	 * @return
	 */
	public boolean isCategoryDeduction() {
		return CategoryAtr.DEDUCTION == this.categoryAtr;
	}
	
	/**
	 * Check Category = ARTICLES || OTHER
	 * @return
	 */
	public boolean isCategoryArticles() {
		return CategoryAtr.ARTICLES == this.categoryAtr;
	}
	
	/**
	 * Check Category = OTHER
	 * @return
	 */
	public boolean isCategoryOther() {
		return CategoryAtr.OTHER == this.categoryAtr;
	}
}
