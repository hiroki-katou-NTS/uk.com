package nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author dungbn
 * 状態のカウント
 * 
 */
@AllArgsConstructor
@Getter
@Setter
public class StateCount {

	// 端末の現在の状態
	private TerminalCurrentState terminalCurrentState;
	
	// 端末数
	private int numberOfTerminal;
	
	public static StateCount createStateCount(TerminalCurrentState terminalCurrentState, int numberOfTerminal) {
		return new StateCount(terminalCurrentState, numberOfTerminal);
	}
}
