package nts.uk.ctx.at.record.app.command.knr.knr002.e;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.TimeRecordSetFormatList;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;

@AllArgsConstructor
@Getter
@Setter
public class TimeRecordSetFormatBakRegisterCommand {

	private ContractCode contractCode;
	
	private EmpInfoTerminalCode empInfoTerminalCode;
	
	private TimeRecordSetFormatList timeRecordSetFormatList;
	
}
