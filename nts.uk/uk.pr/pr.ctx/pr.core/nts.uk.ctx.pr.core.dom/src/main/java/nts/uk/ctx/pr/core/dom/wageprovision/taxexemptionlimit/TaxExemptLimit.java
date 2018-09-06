package nts.uk.ctx.pr.core.dom.wageprovision.taxexemptionlimit;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

/**
 * 
 * @author thanh.tq 非課税限度額の登録
 *
 */
@Getter
public class TaxExemptLimit extends AggregateRoot {

	/**
	 * 会社ID
	 */
	private String cid;

	/**
	 * 非課税限度額コード
	 */
	private TaxLimitAmountCode taxFreeamountCode;

	/**
	 * 非課税限度額名称
	 */
	private TaxExemptionName taxExemptionName;

	/**
	 * 非課税限度額
	 */
	private TaxExemption taxExemption;

	public TaxExemptLimit(String cid, String taxFreeamountCode, String taxExemptionName, int taxExemption) {
		super();
		this.cid = cid;
		this.taxFreeamountCode = new TaxLimitAmountCode(taxFreeamountCode);
		this.taxExemptionName = new TaxExemptionName(taxExemptionName);
		this.taxExemption = new TaxExemption(taxExemption);
	}

}
