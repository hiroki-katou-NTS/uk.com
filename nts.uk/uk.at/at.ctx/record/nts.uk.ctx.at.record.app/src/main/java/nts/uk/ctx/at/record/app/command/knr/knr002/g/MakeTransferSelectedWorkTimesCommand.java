package nts.uk.ctx.at.record.app.command.knr.knr002.g;

import java.util.List;

import lombok.Value;

/**
 * @author xuannt
 *
 */
@Value
public class MakeTransferSelectedWorkTimesCommand {

	//	就業情報端末コード
	private String terminalCode;
	//	選択した就業時間帯コード<List>
	private List<String> selectedWorkTimes;
}
