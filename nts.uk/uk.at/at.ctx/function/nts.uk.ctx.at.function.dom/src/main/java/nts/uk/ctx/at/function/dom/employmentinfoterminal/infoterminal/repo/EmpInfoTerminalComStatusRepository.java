package nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.repo;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.ContractCode;
import nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalComStatus;

/**
 * 
 * @author huylq
 * 
 * 	就業情報端末通信状況Repository
 *
 */
public interface EmpInfoTerminalComStatusRepository {

	//[1]  insert(就業情報端末通信状況)
	public void insert(EmpInfoTerminalComStatus empInfoTerComStatus);
	
	//[2]  update(就業情報端末通信状況)
	public void update(EmpInfoTerminalComStatus empInfoTerComStatus);
	
	//[3]  delete(就業情報端末通信状況)
	public void delete(EmpInfoTerminalComStatus empInfoTerComStatus);
	
	//[4]取得する
	public Optional<EmpInfoTerminalComStatus> get(ContractCode contractCode, EmpInfoTerminalCode empInfoTerCode);
	
	//[5]取得する
	public List<EmpInfoTerminalComStatus> get(ContractCode contractCode, List<EmpInfoTerminalCode> empInfoTerCodeList);
}
