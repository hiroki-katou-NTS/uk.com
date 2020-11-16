package nts.uk.ctx.at.record.dom.adapter.imploymentinfoterminal.infoterminal;

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
}
