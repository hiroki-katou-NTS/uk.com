package nts.uk.ctx.pr.transfer.dom.desbank;

import lombok.Getter;

/**
 * 
 * @author HungTT - 委託者情報
 *
 */

@Getter
public class EntrustorInfor {

	/**
	 * コード
	 */
	private EntrustorCode code;

	/**
	 * 委託者Noは 1 ~ 5
	 */
	private int entrustorNo;

	/**
	 * 用途
	 */
	private EntrustorUse use;

	public EntrustorInfor(String code, int entrustorNo, String use) {
		super();
		this.code = new EntrustorCode(code);
		this.entrustorNo = entrustorNo;
		this.use = new EntrustorUse(use);
	}

}
