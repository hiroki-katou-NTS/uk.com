package nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.service;

import java.util.Optional;

import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminal;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;

public class DeleteEmpInfoTerminalService {

	public static interface Require {
		
		Optional<EmpInfoTerminal> getEmpInfoTerminal(EmpInfoTerminalCode empInfoTerCode, ContractCode contractCode);
		
		void delete(EmpInfoTerminal empInfoTerminal);
	}
}
