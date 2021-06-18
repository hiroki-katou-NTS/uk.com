package nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.record.dom.adapter.employmentinfoterminal.infoterminal.EmpInfoTerminalComStatusImport;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.ComState;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminal;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.MonitorIntervalTime;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.StateCount;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.TerminalComStatus;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.TerminalCurrentState;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;


/**
 * 
 * @author dungbn
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.就業機能.就業情報端末.端末情報.就業情報端末の現在の状態判断
 * 就業情報端末の現在の状態判断	
 *
 */
public class JudgCurrentStatusEmpInfoTerminal {

	public static TerminalComStatus judgingTerminalCurrentState(Require require, ContractCode contractCode, List<EmpInfoTerminal> empInfoTerList) {
		
		// 1: get(契約コード、就業情報端末コードList): List<就業情報端末通信状況>
		List<EmpInfoTerminalCode> empInfoTerCodeList = empInfoTerList.stream().map(e -> e.getEmpInfoTerCode()).collect(Collectors.toList());
		List<EmpInfoTerminalComStatusImport> listEmpInfoTerminalComStatus = require.get(contractCode, empInfoTerCodeList);
		Map<EmpInfoTerminalCode, Optional<GeneralDateTime>> mapSignalLastTime = listEmpInfoTerminalComStatus.stream()
										.collect(Collectors.toMap(e -> e.getEmpInfoTerCode(), e -> Optional.ofNullable(e.getSignalLastTime())));
		int totalNumberOfTer = empInfoTerCodeList.size();
		
		List<ComState> listComstate = empInfoTerList.stream()
										.map(e -> ComState.createComState(e.getEmpInfoTerCode(), judgmentComStatus(listEmpInfoTerminalComStatus, e.getEmpInfoTerCode(), e.getIntervalTime()),
												mapSignalLastTime.get(e.getEmpInfoTerCode()) == null ? Optional.empty() : mapSignalLastTime.get(e.getEmpInfoTerCode())))
										.collect(Collectors.toList());
		List<StateCount> listStateCount = new ArrayList<StateCount>();
		
		
		int numOfNormal = (int) listComstate.stream()
										.filter(e -> e.getTerminalCurrentState().value == TerminalCurrentState.NORMAL.value).count();
		int numOfAbnormal = (int) listComstate.stream()
										.filter(e -> e.getTerminalCurrentState().value == TerminalCurrentState.ABNORMAL.value).count();
		int numOfNotCom = (int) listComstate.stream()
										.filter(e -> e.getTerminalCurrentState().value == TerminalCurrentState.NOT_COMMUNICATED.value).count();
		
		listStateCount.add(StateCount.createStateCount(TerminalCurrentState.NORMAL, numOfNormal));
		listStateCount.add(StateCount.createStateCount(TerminalCurrentState.ABNORMAL, numOfAbnormal));
		listStateCount.add(StateCount.createStateCount(TerminalCurrentState.NOT_COMMUNICATED, numOfNotCom));
		
		return TerminalComStatus.createTerminalComStatus(listStateCount, listComstate, totalNumberOfTer);
	}
	
	public static TerminalCurrentState judgmentComStatus(List<EmpInfoTerminalComStatusImport> listEmpInfoTerminalComStatus, EmpInfoTerminalCode empInfoTerCode, MonitorIntervalTime intervalTime) {
		List<EmpInfoTerminalComStatusImport> filteredList = listEmpInfoTerminalComStatus.stream()
																.filter(e -> e.getEmpInfoTerCode().equals(empInfoTerCode))
																.collect(Collectors.toList());
		if (filteredList.isEmpty()) {
			return TerminalCurrentState.NOT_COMMUNICATED;
		}
		
		// 2:  通信異常があったか判断する(監視間隔時間): boolean
		if (filteredList.get(0).isCommunicationError(intervalTime)) {
			return TerminalCurrentState.ABNORMAL;
		}
		return TerminalCurrentState.NORMAL;
	}

	public static interface Require {
		
		// 	[R-1] 就業情報端末通信状況を取得する
		List<EmpInfoTerminalComStatusImport> get(ContractCode contractCode, List<EmpInfoTerminalCode> empInfoTerCodeList);
	}
}
