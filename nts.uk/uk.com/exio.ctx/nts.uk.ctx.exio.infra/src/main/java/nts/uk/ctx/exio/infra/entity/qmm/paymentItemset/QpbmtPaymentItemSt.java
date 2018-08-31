package nts.uk.ctx.exio.infra.entity.qmm.paymentItemset;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.exio.dom.qmm.paymentItemset.PaymentItemSt;
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
	public int everyoneEqualSet;

	/**
	 * 月給者
	 */
	@Basic(optional = true)
	@Column(name = "MONTHLY_SALARY")
	public int monthlySalary;

	/**
	 * 時給者
	 */
	@Basic(optional = true)
	@Column(name = "HOURLY_PAY")
	public int hourlyPay;

	/**
	 * 日給者
	 */
	@Basic(optional = true)
	@Column(name = "DAY_PAYEE")
	public int dayPayee;

	/**
	 * 日給月給者
	 */
	@Basic(optional = true)
	@Column(name = "MONTHLY_SALARY_PERDAY")
	public int monthlySalaryPerday;

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
	public int taxableAmountAtr;

	/**
	 * 限度金額
	 */
	@Basic(optional = true)
	@Column(name = "LIMIT_AMOUNT")
	public int limitAmount;

	/**
	 * 限度金額区分
	 */
	@Basic(optional = true)
	@Column(name = "LIMIT_AMOUNT_ATR")
	public int limitAmountAtr;

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

	public PaymentItemSt toDomain() {
		return new PaymentItemSt(this.paymentItemStPk.cid, this.paymentItemStPk.salaryItemId, this.breakdownItemUseAtr,
				this.laborInsuranceCategory, this.settingAtr, this.everyoneEqualSet, this.monthlySalary, this.hourlyPay,
				this.dayPayee, this.monthlySalaryPerday, this.averageWageAtr, this.socialInsuranceCategory, this.taxAtr,
				this.taxableAmountAtr, this.limitAmount, this.limitAmountAtr, this.taxLimitAmountCode, this.note);
	}

	public static QpbmtPaymentItemSt toEntity(PaymentItemSt domain) {
		return new QpbmtPaymentItemSt(new QpbmtPaymentItemStPk(domain.getCid(), domain.getSalaryItemId()),
				domain.getBreakdownItemUseAtr() != null ? domain.getBreakdownItemUseAtr().value : null,
				domain.getLaborInsuranceCategory() != null ? domain.getLaborInsuranceCategory().value : null,
				domain.getSettingAtr() != null ? domain.getSettingAtr().value : null,
				domain.getEveryoneEqualSet().map(i -> i.value).orElse(null),
				domain.getMonthlySalary().map(i -> i.value).orElse(null),
				domain.getHourlyPay().map(i -> i.value).orElse(null),
				domain.getDayPayee().map(i -> i.value).orElse(null),
				domain.getMonthlySalaryPerday().map(i -> i.value).orElse(null),
				domain.getAverageWageAtr() != null ? domain.getAverageWageAtr().value : null,
				domain.getSocialInsuranceCategory() != null ? domain.getSocialInsuranceCategory().value : null,
				domain.getTaxAtr() != null ? domain.getTaxAtr().value : null,
				domain.getTaxableAmountAtr().map(i -> i.value).orElse(null),
				domain.getLimitAmount().map(i -> i.v()).orElse(null),
				domain.getLimitAmountAtr().map(i -> i.value).orElse(null),
				domain.getTaxLimitAmountCode().map(i -> i.v()).orElse(null), domain.getNote().orElse(null));
	}

}
