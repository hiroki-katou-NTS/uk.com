package nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Value;
import nts.arc.time.GeneralDateTime;

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
	
	// 最終通信日時
	private Optional<GeneralDateTime> signalLastTime;
	
	public static ComState createComState(EmpInfoTerminalCode empInfoTerCode, TerminalCurrentState terminalCurrentState, Optional<GeneralDateTime> signalLastTime) {
		return new ComState(empInfoTerCode, terminalCurrentState, signalLastTime);
	}
	
}
