package nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.repo;

import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.ErrorNRCom;

/**
 * @author ThanhNX
 *
 *         エラーNR-通信Repository
 */
public interface ErrorNRComRepository {
	/**
	 * ラーNR-通信を作る
	 * 
	 * @param errorNR
	 */
	public void insert(ErrorNRCom errorNR);
}
