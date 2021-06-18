package nts.uk.ctx.at.record.dom.adapter.employmentinfoterminal.infoterminal;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;

/**
 * 
 * @author dungbn
 *
 */
public interface EmpInfoTerminalComStatusAdapter {

	Optional<EmpInfoTerminalComStatusImport> get(ContractCode contractCode, EmpInfoTerminalCode empInfoTerCode);
	
	// [1] 削除する
	void delete(EmpInfoTerminalComStatusImport empInfoTerminalComStatusImport);
	
	List<EmpInfoTerminalComStatusImport> get(ContractCode contractCode, List<EmpInfoTerminalCode> listEmpInfoTerCode);
}
