package nts.uk.ctx.pr.transfer.dom.sourcebank;

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
		this.code = code == null || code.isEmpty() ? null : new EntrustorCode(code);
		this.entrustorNo = entrustorNo;
		this.use = use == null || use.isEmpty() ? null : new EntrustorUse(use);
	}

}
