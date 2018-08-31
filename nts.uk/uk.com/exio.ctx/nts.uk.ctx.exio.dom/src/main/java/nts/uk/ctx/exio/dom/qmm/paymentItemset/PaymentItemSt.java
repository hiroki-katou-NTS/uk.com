package nts.uk.ctx.exio.dom.qmm.paymentItemset;

import java.util.Optional;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.exio.dom.qmm.deductionItemset.BreakdownItemUseAtr;
import nts.uk.ctx.exio.dom.qmm.timeIiemset.AverageWageAtr;

/**
 * 支給項目設定
 */
@Getter
public class PaymentItemSt extends AggregateRoot {

	/**
	 * 会社ID
	 */
	private String cid;

	/**
	 * 給与項目ID
	 */
	private String salaryItemId;

	/**
	 * 内訳項目利用区分
	 */
	private BreakdownItemUseAtr breakdownItemUseAtr;

	/**
	 * 労働保険区分
	 */
	private LaborInsuranceCategory laborInsuranceCategory;

	/**
	 * 固定的賃金の設定区分
	 */
	private SettingClassification settingAtr;

	/**
	 * 全員一律の設定
	 */
	private Optional<CategoryFixedWage> everyoneEqualSet;

	/**
	 * 月給者
	 */
	private Optional<CategoryFixedWage> monthlySalary;

	/**
	 * 時給者
	 */
	private Optional<CategoryFixedWage> hourlyPay;

	/**
	 * 日給者
	 */
	private Optional<CategoryFixedWage> dayPayee;

	/**
	 * 日給月給者
	 */
	private Optional<CategoryFixedWage> monthlySalaryPerday;

	/**
	 * 平均賃金区分
	 */
	private AverageWageAtr averageWageAtr;

	/**
	 * 社会保険区分
	 */
	private SocialInsuranceCategory socialInsuranceCategory;

	/**
	 * 課税区分
	 */
	private TaxAtr taxAtr;

	/**
	 * 課税金額区分
	 */
	private Optional<TaxableAmountClassification> taxableAmountAtr;

	/**
	 * 限度金額
	 */
	private Optional<LimitAmount> limitAmount;

	/**
	 * 限度金額区分
	 */
	private Optional<LimitAmountClassification> limitAmountAtr;

	/**
	 * 非課税限度額コード
	 */
	private Optional<TaxLimitAmountCode> taxLimitAmountCode;

	/**
	 * 備考
	 */
	private Optional<String> note;

	public PaymentItemSt(String cid, String salaryItemId, int breakdownItemUseAtr,
			int laborInsuranceCategory, int settingAtr,
			int everyoneEqualSet, int monthlySalary,
			int hourlyPay, int dayPayee,
			int monthlySalaryPerday, int averageWageAtr,
			int socialInsuranceCategory, int taxAtr,
			int taxableAmountAtr, int limitAmount,
			int limitAmountAtr, String taxLimitAmountCode,
			String note) {
		super();
		this.cid = cid;
		this.salaryItemId = salaryItemId;
		this.breakdownItemUseAtr = EnumAdaptor.valueOf(breakdownItemUseAtr, BreakdownItemUseAtr.class);
		this.laborInsuranceCategory = EnumAdaptor.valueOf(laborInsuranceCategory, LaborInsuranceCategory.class);
		this.settingAtr = EnumAdaptor.valueOf(settingAtr, SettingClassification.class);
		this.everyoneEqualSet = Optional.ofNullable(EnumAdaptor.valueOf(everyoneEqualSet, CategoryFixedWage.class));
		this.monthlySalary = Optional.ofNullable(EnumAdaptor.valueOf(everyoneEqualSet, CategoryFixedWage.class));
		this.hourlyPay = Optional.ofNullable(EnumAdaptor.valueOf(hourlyPay, CategoryFixedWage.class));
		this.dayPayee = Optional.ofNullable(EnumAdaptor.valueOf(dayPayee, CategoryFixedWage.class));
		this.monthlySalaryPerday = Optional.ofNullable(EnumAdaptor.valueOf(monthlySalaryPerday, CategoryFixedWage.class));
		this.averageWageAtr = EnumAdaptor.valueOf(averageWageAtr, AverageWageAtr.class) ;
		this.socialInsuranceCategory = EnumAdaptor.valueOf(socialInsuranceCategory, SocialInsuranceCategory.class) ;
		this.taxAtr = EnumAdaptor.valueOf(taxAtr, TaxAtr.class);
		this.taxableAmountAtr = Optional.ofNullable(EnumAdaptor.valueOf(taxableAmountAtr, TaxableAmountClassification.class));
		this.limitAmount = Optional.ofNullable(new LimitAmount(limitAmount));
		this.limitAmountAtr = Optional.ofNullable(EnumAdaptor.valueOf(limitAmountAtr, LimitAmountClassification.class));
		this.taxLimitAmountCode = Optional.ofNullable(new TaxLimitAmountCode(taxLimitAmountCode));
		this.note = Optional.ofNullable(note);
	}
	
	

}
