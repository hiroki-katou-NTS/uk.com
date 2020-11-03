package nts.uk.ctx.at.record.dom.adapter.imploymentinfoterminal.infoterminal;

import java.util.Optional;

import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;


public interface EmpInfoTerminalComStatusAdapter {

	Optional<EmpInfoTerminalComStatusImport> get(ContractCode contractCode, EmpInfoTerminalCode empInfoTerCode);
	
	void delete(EmpInfoTerminalComStatusImport empInfoTerminalComStatusImport);
}
