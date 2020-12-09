package nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Value;

/**
 * 
 * @author dungbn
 * 通信状態
 * 
 */

@AllArgsConstructor
@Getter
@Setter
public class ComState {

	// 就業情報端末コード
	private EmpInfoTerminalCode empInfoTerCode;
	
	// 端末の現在の状態
	private TerminalCurrentState terminalCurrentState;
	
	public static ComState createComState(EmpInfoTerminalCode empInfoTerCode, TerminalCurrentState terminalCurrentState) {
		return new ComState(empInfoTerCode, terminalCurrentState);
	}
	
}
