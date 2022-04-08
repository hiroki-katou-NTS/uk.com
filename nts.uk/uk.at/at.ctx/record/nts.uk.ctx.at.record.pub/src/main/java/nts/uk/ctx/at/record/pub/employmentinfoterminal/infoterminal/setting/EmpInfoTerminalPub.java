package nts.uk.ctx.at.record.pub.employmentinfoterminal.infoterminal.setting;

import java.util.Optional;

import nts.uk.ctx.at.record.pub.employmentinfoterminal.infoterminal.setting.dto.EmpInfoTerminalExport;

/**
 * @author ThanhNX 
 * 
 * 就業情報端末を取得する
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
	public Optional<EmpInfoTerminalExport> getEmpInfoTerminal(String empInfoTerCode, String contractCode);
	
	
	/**
	 * 就業情報端末を取得する
	 * 
	 * @param empInfoTerCode
	 * @param contractCode
	 * @param leavCategory
	 * @return
	 */
	public Optional<EmpInfoTerminalExport> getEmpInfoTerminal(String empInfoTerCode, String contractCode, Optional<String> leavCategory);
	
	
	/**
	 * 就業情報端末を取得する
	 * 
	 * @param empInfoTerCode
	 * @param contractCode
	 * @return
	 */
	public Optional<String> getEmpInfoTerminalCode(String contractCode, String macAddr);
	
}
