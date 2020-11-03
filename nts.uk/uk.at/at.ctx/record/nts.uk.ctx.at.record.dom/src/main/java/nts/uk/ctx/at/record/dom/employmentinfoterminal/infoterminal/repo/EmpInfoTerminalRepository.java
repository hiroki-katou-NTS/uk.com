package nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.repo;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerSerialNo;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminal;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.MacAddress;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;

/**
 * @author ThanhNX 就業情報端末Repository
 *
 */
public interface EmpInfoTerminalRepository {

	/**
	 * 就業情報端末を取得する
	 * 
	 * @param empInfoTerCode
	 * @param contractCode
	 * @return
	 */
	public Optional<EmpInfoTerminal> getEmpInfoTerminal(EmpInfoTerminalCode empInfoTerCode, ContractCode contractCode);
	
	public Optional<EmpInfoTerminal> getEmpInfoTerWithMac(MacAddress macAdd, ContractCode contractCode);

	public void updateSerialNo(EmpInfoTerminalCode empInfoTerCode, ContractCode contractCode,
			EmpInfoTerSerialNo terSerialNo);
	
	//	[2]  insert(就業情報端末)
	void insert(EmpInfoTerminal empInfoTerminal);
	
	// 	[3]  update(就業情報端末)
	void update(EmpInfoTerminal empInfoTerminal);
	
	//  [4]  delete(就業情報端末) 
	void delete(EmpInfoTerminal empInfoTerminal);
	
	// 	[6] 取得する
	List<EmpInfoTerminal> getAllByContractCode(ContractCode contractCode);

}
