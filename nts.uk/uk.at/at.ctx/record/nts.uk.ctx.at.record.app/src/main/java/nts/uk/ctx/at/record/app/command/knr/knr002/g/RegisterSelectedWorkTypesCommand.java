package nts.uk.ctx.at.record.app.command.knr.knr002.g;

import java.util.List;

import lombok.Value;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;

/**
 * @author xuannt
 *
 */
@Value
public class RegisterSelectedWorkTypesCommand {
	//	就業情報端末コード
	private EmpInfoTerminalCode terminalCode;
	//	選択した勤務種類コード<List>
	private List<WorkTypeCode> selectedWorkTypes;
}
