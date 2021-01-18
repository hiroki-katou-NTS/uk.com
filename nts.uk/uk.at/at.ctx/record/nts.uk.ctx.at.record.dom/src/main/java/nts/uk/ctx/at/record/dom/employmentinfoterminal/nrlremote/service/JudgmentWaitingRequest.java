package nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminal;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.TimeRecordSetFormatList;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.TimeRecordSetUpdateList;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;

/**
 * 
 * @author dungbn
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.就業情報端末.NRリモート.service.リクエスト待ちの判断
 */
public class JudgmentWaitingRequest {
	
	// 	[1] リクエスト待ち状況の判断
	public static Map<EmpInfoTerminalCode, Boolean> judgmentReqWaitingStatus(Require require, ContractCode contractCode, List<EmpInfoTerminal> empInfoTerminalList) {
		List<EmpInfoTerminalCode> listEmpInfoTerminalCode = empInfoTerminalList.stream().map(e -> e.getEmpInfoTerCode()).collect(Collectors.toList());
		
		// 1: get(契約コード、端末コードList): List<タイムレコード設定フォーマットリス>
		List<TimeRecordSetFormatList> listTimeRecordSetFormatList = require.getTimeRecordSetFormatList(contractCode, listEmpInfoTerminalCode);
		Map<EmpInfoTerminalCode, Integer> mapCodeSeveralItem = listTimeRecordSetFormatList.stream().collect(Collectors.toMap(e -> e.getEmpInfoTerCode(), e -> e.getLstTRSetFormat().size()));
		
		// 2: get(契約コード、端末コードList): List<タイムレコード設定変更リスト>
		List<TimeRecordSetUpdateList> listTimeRecordSetUpdateList = require.getTimeRecordUpdateList(contractCode, listEmpInfoTerminalCode);
		
		// 3: 復旧時リクエスト待ちか(項目の数件: int): boolean
		Map<EmpInfoTerminalCode, Boolean> mapCodeFlag = listTimeRecordSetUpdateList.stream()
				.collect(Collectors.toMap(e -> e.getEmpInfoTerCode(), e -> e.isWaitingReqRecovery(mapCodeSeveralItem.get(e.getEmpInfoTerCode()) != null ? mapCodeSeveralItem.get(e.getEmpInfoTerCode()) : -1)));
		
		if (listTimeRecordSetUpdateList.isEmpty()) {
			return new HashMap<EmpInfoTerminalCode, Boolean>();
		}
		
		return mapCodeFlag;
	}
	

	public static interface Require {
		
		// 	[R-1] タイムレコード設定フォーマットリスを取得する
		List<TimeRecordSetFormatList> getTimeRecordSetFormatList(ContractCode contractCode, List<EmpInfoTerminalCode> listEmpInfoTerCode);
		
		// 	[R-2] タイムレコード設定更新リストを取得する
		List<TimeRecordSetUpdateList> getTimeRecordUpdateList(ContractCode contractCode, List<EmpInfoTerminalCode> listEmpInfoTerCode);
	}
}
