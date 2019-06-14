package nts.uk.ctx.pr.core.dom.wageprovision.statementitem.paymentitemset;

import java.util.Optional;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.pr.core.dom.wageprovision.taxexemptionlimit.TaxLimitAmountCode;

/**
 * 限度金額設定
 */
@Getter
public class LimitAmountSetting extends DomainObject {
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

	public LimitAmountSetting(Integer taxableAmountAtr, Long limitAmount, Integer limitAmountAtr,
			String taxLimitAmountCode) {
		super();

		this.taxableAmountAtr = taxableAmountAtr == null ? Optional.empty()
				: Optional.of(EnumAdaptor.valueOf(taxableAmountAtr, TaxableAmountClassification.class));
		this.limitAmount = limitAmount == null ? Optional.empty() : Optional.of(new LimitAmount(limitAmount));
		this.limitAmountAtr = limitAmountAtr == null ? Optional.empty()
				: Optional.of(EnumAdaptor.valueOf(limitAmountAtr, LimitAmountClassification.class));
		this.taxLimitAmountCode = taxLimitAmountCode == null ? Optional.empty()
				: Optional.of(new TaxLimitAmountCode(taxLimitAmountCode));
	}

}
