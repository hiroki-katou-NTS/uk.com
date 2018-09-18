package nts.uk.ctx.pr.core.app.find.wageprovision.statementitem;

import java.util.Optional;

import lombok.Value;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.paymentitemset.PaymentItemSet;
import nts.uk.ctx.pr.core.dom.wageprovision.taxexemptionlimit.TaxExemptionLimit;

@Value
public class PaymentItemSetDto {

	/**
	 * 内訳項目利用区分
	 */
	private int breakdownItemUseAtr;

	/**
	 * 労働保険区分
	 */
	private int laborInsuranceCategory;

	/**
	 * 固定的賃金の設定区分
	 */
	private int settingAtr;

	/**
	 * 全員一律の設定
	 */
	private Integer everyoneEqualSet;

	/**
	 * 月給者
	 */
	private Integer monthlySalary;

	/**
	 * 時給者
	 */
	private Integer hourlyPay;

	/**
	 * 日給者
	 */
	private Integer dayPayee;

	/**
	 * 日給月給者
	 */
	private Integer monthlySalaryPerday;

	/**
	 * 平均賃金区分
	 */
	private int averageWageAtr;

	/**
	 * 社会保険区分
	 */
	private int socialInsuranceCategory;

	/**
	 * 課税区分
	 */
	private int taxAtr;

	/**
	 * 課税金額区分
	 */
	private Integer taxableAmountAtr;

	/**
	 * 限度金額
	 */
	private Long limitAmount;

	/**
	 * 限度金額区分
	 */
	private Integer limitAmountAtr;

	/**
	 * 非課税限度額コード
	 */
	private String taxLimitAmountCode;
	
	/**
	 * 非課税限度額名称
	 */
	private String taxExemptionName;

	/**
	 * 備考
	 */
	private String note;

	public static PaymentItemSetDto fromDomain(PaymentItemSet domain, Optional<TaxExemptionLimit> taxDomain) {
		return new PaymentItemSetDto(domain.getBreakdownItemUseAtr().value, domain.getLaborInsuranceCategory().value,
				domain.getFixedWage().getSettingAtr().value,
				domain.getFixedWage().getEveryoneEqualSet().map(i -> i.value).orElse(null),
				domain.getFixedWage().getPerSalaryContractType().getMonthlySalary().map(i -> i.value).orElse(null),
				domain.getFixedWage().getPerSalaryContractType().getHourlyPay().map(i -> i.value).orElse(null),
				domain.getFixedWage().getPerSalaryContractType().getDayPayee().map(i -> i.value).orElse(null),
				domain.getFixedWage().getPerSalaryContractType().getMonthlySalaryPerday().map(i -> i.value)
						.orElse(null),
				domain.getAverageWageAtr().value, domain.getSocialInsuranceCategory().value, domain.getTaxAtr().value,
				domain.getLimitAmountSetting().getTaxableAmountAtr().map(i -> i.value).orElse(null),
				domain.getLimitAmountSetting().getLimitAmount().map(i -> i.v()).orElse(null),
				domain.getLimitAmountSetting().getLimitAmountAtr().map(i -> i.value).orElse(null),
				domain.getLimitAmountSetting().getTaxLimitAmountCode().map(i -> i.v()).orElse(null),
				taxDomain.map(i -> i.getTaxExemptionName().v()).orElse(null),
				domain.getNote().map(i -> i.v()).orElse(null));
	}
}
