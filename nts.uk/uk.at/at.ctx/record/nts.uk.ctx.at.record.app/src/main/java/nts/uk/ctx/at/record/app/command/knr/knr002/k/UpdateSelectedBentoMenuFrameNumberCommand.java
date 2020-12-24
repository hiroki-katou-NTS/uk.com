package nts.uk.ctx.at.record.app.command.knr.knr002.k;

import java.util.List;

import lombok.Value;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;

/**
 * 
 * @author xuannt
 *
 */
@Value
public class UpdateSelectedBentoMenuFrameNumberCommand {
	//	就業情報端末コード
	private EmpInfoTerminalCode terminalCode;
	//	選択した弁当メニュー枠番<List>
	private List<Integer> selectedBentoMenuFrameNumbers;
}
