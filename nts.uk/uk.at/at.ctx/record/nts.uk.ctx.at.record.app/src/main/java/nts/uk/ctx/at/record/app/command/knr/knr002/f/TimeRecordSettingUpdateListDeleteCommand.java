package nts.uk.ctx.at.record.app.command.knr.knr002.f;

import java.util.List;

import lombok.Value;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;

@Value
public class TimeRecordSettingUpdateListDeleteCommand {	
	//	復旧先就業情報端末コード<List>
	private List<EmpInfoTerminalCode> restoreDestinationTerminalList;
}
