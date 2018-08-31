package nts.uk.ctx.exio.dom.qmm.taxexemptlimit;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.exio.dom.qmm.paymentItemset.TaxLimitAmountCode;

/**
 * 非課税限度額の登録
 */
@Getter
public class TaxExemptLimit extends AggregateRoot {

	/**
	 * 会社ID
	 */
	private String cid;

	/**
	 * 非課税限度額名称
	 */
	private TaxExemptionName taxExemptionName;

	/**
	 * 非課税限度額コード
	 */
	private TaxLimitAmountCode taxFreeamountCode;

	/**
	 * 非課税限度額
	 */
	private TaxExemption taxExemption;

	public TaxExemptLimit(String cid, String taxExemptionName, String taxFreeamountCode, int taxExemption) {
		super();
		this.cid = cid;
		this.taxExemptionName = new TaxExemptionName(taxExemptionName);
		this.taxFreeamountCode = new TaxLimitAmountCode(taxFreeamountCode);
		this.taxExemption = new TaxExemption(taxExemption);
	}

}
