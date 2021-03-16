package nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminal;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.TimeRecordReqSetting;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;

/**
 * 
 * @author dungbn
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.就業情報端末.端末情報.端末のリクエスト状態の判断
 *
 */
public class DeterminingReqStatusTerminal {
	
	// [1] 端末のリクエスト状態の判断
	public static Map<EmpInfoTerminalCode, Boolean> determiningReqStatusTerminal(RequireDetermining require, ContractCode contractCode, List<EmpInfoTerminal> empInfoTerminalList) {
		List<EmpInfoTerminalCode> empInfoTerminalCodeList = empInfoTerminalList.stream().map(e -> e.getEmpInfoTerCode()).collect(Collectors.toList());
		
		// 1: get(契約コード、就業情報端末コードList): List<就業情報端末のリクエスト一覧>
		List<TimeRecordReqSetting> empInfoTerRequestList = require.get(contractCode, empInfoTerminalCodeList);
		
		// 2: 端末のリクエスト状態の判断(): boolean
		if (empInfoTerRequestList.isEmpty()) {
			return new HashMap<EmpInfoTerminalCode, Boolean>();
		}
		return empInfoTerRequestList.stream().collect(Collectors.toMap(e -> e.getTerminalCode(), e -> e.determiningReqStatusTerminal()));
	}

	public static interface RequireDetermining {
		
		// [R-1] 就業情報端末のリクエスト一覧を取得する
		List<TimeRecordReqSetting> get(ContractCode contractCode, List<EmpInfoTerminalCode> listCode);
	}
}
