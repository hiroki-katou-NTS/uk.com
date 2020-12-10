package nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDateTime;

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
	
	public Map<EmpInfoTerminalCode, ComStateobject>  getMapByCode(List<ComState> listComstate) {
		return listComstate.stream().
					collect(Collectors.toMap(e -> e.getEmpInfoTerCode(), e -> new ComStateobject(e.getTerminalCurrentState(), e.getSignalLastTime())));
	}
	
	@AllArgsConstructor
	@Getter
	public class ComStateobject {
		// 端末の現在の状態
		private TerminalCurrentState terminalCurrentState;
		
		// 最終通信日時
		private Optional<GeneralDateTime> signalLastTime;
	}
}
