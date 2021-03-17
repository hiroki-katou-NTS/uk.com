package nts.uk.ctx.at.record.app.command.knr.knr002.g;

import java.util.List;

import lombok.Value;

/**
 * @author xuannt
 *
 */
@Value
public class MakeTransferSelectedWorkTypesCommand {
	//	就業情報端末コード
	private String terminalCode;
	//	選択した勤務種類コード<List>
	private List<String> selectedWorkTypes;
}
