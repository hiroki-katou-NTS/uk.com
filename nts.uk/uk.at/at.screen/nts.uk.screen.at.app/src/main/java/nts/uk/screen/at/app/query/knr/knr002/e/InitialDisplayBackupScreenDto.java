package nts.uk.screen.at.app.query.knr.knr002.e;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminal;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.TimeRecordSetFormatBak;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class InitialDisplayBackupScreenDto {

	List<EmpInfoTerminalEDto> listEmpInfoTerminal;
	
	List<TimeRecordSetFormatBakEDto> listTimeRecordSetFormatBakEDto;
	
	List<FlagByCode> listCodeFlag;
}
