package nts.uk.ctx.at.request.dom.application.common.adapter.record.infoterminal;

import java.util.Optional;

import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.EmpInfoTerminal;

/**
 * @author ThanhNX 就業情報端末Adapter
 *
 */
public interface EmpInfoTerminalAdapter {

	/**
	 * 就業情報端末を取得する
	 * 
	 * @param empInfoTerCode
	 * @param contractCode
	 * @return
	 */
	public Optional<EmpInfoTerminal> getEmpInfoTerminal(Integer empInfoTerCode, String contractCode);
	
}
