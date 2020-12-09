package nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class TerminalComStatus {

	// 全端末数
	private int totalNumberOfTer;
	
	// 状態のカウント
	private List<StateCount> listStateCount;
	
	// 通信状態
	private List<ComState> listComstate;
	
	public static TerminalComStatus createTerminalComStatus(List<StateCount> listStateCount, List<ComState> listComstate, int totalNumberOfTer) {
		return new TerminalComStatus(totalNumberOfTer, listStateCount, listComstate);
	}
}
