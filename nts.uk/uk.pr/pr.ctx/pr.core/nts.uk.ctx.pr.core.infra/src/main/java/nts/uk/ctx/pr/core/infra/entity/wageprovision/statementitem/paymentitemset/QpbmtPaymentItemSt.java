package nts.uk.ctx.pr.core.infra.entity.wageprovision.statementitem.paymentitemset;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.paymentitemset.PaymentItemSet;
import nts.uk.shr.infra.data.entity.UkJpaEntity;	

/**
 * 支給項目設定
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QPBMT_PAYMENT_ITEM_ST")
public class QpbmtPaymentItemSt extends UkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@EmbeddedId
	public QpbmtPaymentItemStPk paymentItemStPk;

	/**
	 * 内訳項目利用区分
	 */
	@Basic(optional = false)
	@Column(name = "BREAKDOWN_ITEM_USE_ATR")
	public int breakdownItemUseAtr;

	/**
	 * 労働保険区分
	 */
	@Basic(optional = false)
	@Column(name = "LABOR_INSURANCE_CATEGORY")
	public int laborInsuranceCategory;

	/**
	 * 固定的賃金の設定区分
	 */
	@Basic(optional = false)
	@Column(name = "SETTING_ATR")
	public int settingAtr;

	/**
	 * 全員一律の設定
	 */
	@Basic(optional = true)
	@Column(name = "EVERYONE_EQUAL_SET")
	public Integer everyoneEqualSet;

	/**
	 * 月給者
	 */
	@Basic(optional = true)
	@Column(name = "MONTHLY_SALARY")
	public Integer monthlySalary;

	/**
	 * 時給者
	 */
	@Basic(optional = true)
	@Column(name = "HOURLY_PAY")
	public Integer hourlyPay;

	/**
	 * 日給者
	 */
	@Basic(optional = true)
	@Column(name = "DAY_PAYEE")
	public Integer dayPayee;

	/**
	 * 日給月給者
	 */
	@Basic(optional = true)
	@Column(name = "MONTHLY_SALARY_PERDAY")
	public Integer monthlySalaryPerday;

	/**
	 * 平均賃金区分
	 */
	@Basic(optional = false)
	@Column(name = "AVERAGE_WAGE_ATR")
	public int averageWageAtr;

	/**
	 * 社会保険区分
	 */
	@Basic(optional = false)
	@Column(name = "SOCIAL_INSURANCE_CATEGORY")
	public int socialInsuranceCategory;

	/**
	 * 課税区分
	 */
	@Basic(optional = false)
	@Column(name = "TAX_ATR")
	public int taxAtr;

	/**
	 * 課税金額区分
	 */
	@Basic(optional = true)
	@Column(name = "TAXABLE_AMOUNT_ATR")
	public Integer taxableAmountAtr;

	/**
	 * 限度金額
	 */
	@Basic(optional = true)
	@Column(name = "LIMIT_AMOUNT")
	public Long limitAmount;

	/**
	 * 限度金額区分
	 */
	@Basic(optional = true)
	@Column(name = "LIMIT_AMOUNT_ATR")
	public Integer limitAmountAtr;

	/**
	 * 非課税限度額コード
	 */
	@Basic(optional = true)
	@Column(name = "TAX_LIMIT_AMOUNT_CODE")
	public String taxLimitAmountCode;

	/**
	 * 備考
	 */
	@Basic(optional = true)
	@Column(name = "NOTE")
	public String note;

	@Override
	protected Object getKey() {
		return paymentItemStPk;
	}

	public PaymentItemSet toDomain() {
		return new PaymentItemSet(paymentItemStPk.cid, paymentItemStPk.categoryAtr, paymentItemStPk.itemNameCd, breakdownItemUseAtr,
				laborInsuranceCategory, settingAtr, everyoneEqualSet, monthlySalary, hourlyPay, dayPayee,
				monthlySalaryPerday, averageWageAtr, socialInsuranceCategory, taxAtr, taxableAmountAtr, limitAmount,
				limitAmountAtr, taxLimitAmountCode, note);
	}

	public static QpbmtPaymentItemSt toEntity(PaymentItemSet domain) {
		return new QpbmtPaymentItemSt(new QpbmtPaymentItemStPk(domain.getCid(), domain.getCategoryAtr().value, domain.getItemNameCd().v()),
				domain.getBreakdownItemUseAtr().value, domain.getLaborInsuranceCategory().value,
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
				domain.getNote().map(i -> i.v()).orElse(null));
	}

}
