package nts.uk.ctx.at.record.app.command.knr.knr002.g;

import java.util.List;

import lombok.Value;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;


/**
 * @author xuannt
 *
 */
@Value
public class UpdateSelectedWorkTimesCommand {
	//	就業情報端末コード
	private EmpInfoTerminalCode terminalCode;
	//	選択した就業時間帯コード<List>
	private List<WorkTimeCode> selectedWorkTimes;
}
