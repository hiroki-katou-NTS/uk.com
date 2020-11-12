package nts.uk.ctx.at.record.pub.employmentinfoterminal.infoterminal.setting;

import java.util.Optional;

import nts.uk.ctx.at.record.pub.employmentinfoterminal.infoterminal.setting.dto.EmpInfoTerminalExport;

/**
 * @author ThanhNX 就業情報端末Repository
 *
 */
public interface EmpInfoTerminalPub {

	/**
	 * 就業情報端末を取得する
	 * 
	 * @param empInfoTerCode
	 * @param contractCode
	 * @return
	 */
	public Optional<EmpInfoTerminalExport> getEmpInfoTerminal(Integer empInfoTerCode, String contractCode);
	
}
