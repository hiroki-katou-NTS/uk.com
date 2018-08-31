package nts.uk.ctx.exio.dom.qmm.paymentItemset;

import java.util.Optional;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.exio.dom.exi.condset.LimitAmountSetting;
import nts.uk.ctx.exio.dom.qmm.deductionItemset.BreakdownItemUseAtr;
import nts.uk.ctx.exio.dom.qmm.timeIiemset.AverageWageAtr;

/**
 * 
 * @author thanh.tq 支給項目設定
 *
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
	 * 固定的賃金の設定
	 */
	private FixedWage fixedWage;

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
	 * 限度金額設定
	 */
	private LimitAmountSetting limitAmountSetting;

	/**
	 * 備考
	 */
	private Optional<String> note;

	public PaymentItemSt(String cid, String salaryItemId, int breakdownItemUseAtr, int laborInsuranceCategory,
			int settingAtr, int everyoneEqualSet, int monthlySalary, int hourlyPay, int dayPayee,
			int monthlySalaryPerday, int averageWageAtr, int socialInsuranceCategory, int taxAtr, int taxableAmountAtr,
			int limitAmount, int limitAmountAtr, String taxLimitAmountCode, String note) {
		super();
		this.cid = cid;
		this.salaryItemId = salaryItemId;
		this.breakdownItemUseAtr = EnumAdaptor.valueOf(breakdownItemUseAtr, BreakdownItemUseAtr.class);
		this.laborInsuranceCategory = EnumAdaptor.valueOf(laborInsuranceCategory, LaborInsuranceCategory.class);
		this.fixedWage = new FixedWage(settingAtr, everyoneEqualSet, monthlySalary, hourlyPay, dayPayee, monthlySalaryPerday);
		this.averageWageAtr = EnumAdaptor.valueOf(averageWageAtr, AverageWageAtr.class);
		this.socialInsuranceCategory = EnumAdaptor.valueOf(socialInsuranceCategory, SocialInsuranceCategory.class);
		this.taxAtr = EnumAdaptor.valueOf(taxAtr, TaxAtr.class);
		this.limitAmountSetting = new LimitAmountSetting(taxableAmountAtr, limitAmount, limitAmountAtr,
				taxLimitAmountCode);
		this.note = Optional.ofNullable(note);
	}
}
