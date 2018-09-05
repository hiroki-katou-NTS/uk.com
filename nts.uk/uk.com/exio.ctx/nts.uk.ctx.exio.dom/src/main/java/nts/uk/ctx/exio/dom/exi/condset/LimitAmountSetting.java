package nts.uk.ctx.exio.dom.exi.condset;

import java.util.Optional;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.exio.dom.qmm.paymentItemset.LimitAmount;
import nts.uk.ctx.exio.dom.qmm.paymentItemset.LimitAmountClassification;
import nts.uk.ctx.exio.dom.qmm.paymentItemset.TaxLimitAmountCode;
import nts.uk.ctx.exio.dom.qmm.paymentItemset.TaxableAmountClassification;

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

	public LimitAmountSetting(int taxableAmountAtr, int limitAmount, int limitAmountAtr, String taxLimitAmountCode) {
		super();
		this.taxableAmountAtr = Optional
				.ofNullable(EnumAdaptor.valueOf(taxableAmountAtr, TaxableAmountClassification.class));
		this.limitAmount = Optional.ofNullable(new LimitAmount(limitAmount));
		this.limitAmountAtr = Optional.ofNullable(EnumAdaptor.valueOf(limitAmountAtr, LimitAmountClassification.class));
		this.taxLimitAmountCode = Optional.ofNullable(new TaxLimitAmountCode(taxLimitAmountCode));
	}

}
