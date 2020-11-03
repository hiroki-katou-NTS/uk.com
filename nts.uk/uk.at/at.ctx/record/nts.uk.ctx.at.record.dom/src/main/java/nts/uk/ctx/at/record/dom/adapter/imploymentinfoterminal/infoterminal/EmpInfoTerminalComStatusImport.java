package nts.uk.ctx.at.record.dom.adapter.imploymentinfoterminal.infoterminal;

import lombok.Value;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;

@Value
public class EmpInfoTerminalComStatusImport {

	private ContractCode contractCode;
	
 	private EmpInfoTerminalCode empInfoTerCode;
	
 	private GeneralDateTime signalLastTime;
	
}
