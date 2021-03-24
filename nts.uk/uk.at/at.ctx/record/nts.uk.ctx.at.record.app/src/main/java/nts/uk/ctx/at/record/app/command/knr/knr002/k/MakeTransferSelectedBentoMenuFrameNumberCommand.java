package nts.uk.ctx.at.record.app.command.knr.knr002.k;

import java.util.List;

import lombok.Value;

/**
 * 
 * @author xuannt
 *
 */
@Value
public class MakeTransferSelectedBentoMenuFrameNumberCommand {
	//	就業情報端末コード
	private String terminalCode;
	//	選択した弁当メニュー枠番<List>
	private List<Integer> selectedBentoMenuFrameNumbers;
}
