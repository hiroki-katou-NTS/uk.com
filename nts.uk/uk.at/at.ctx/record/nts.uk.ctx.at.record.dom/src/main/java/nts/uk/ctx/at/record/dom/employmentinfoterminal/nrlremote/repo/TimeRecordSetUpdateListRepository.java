package nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.repo;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.TimeRecordSetUpdateList;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;


public interface TimeRecordSetUpdateListRepository {

	// タイムレコード設定更新リストを取得する
	Optional<TimeRecordSetUpdateList> findSettingUpdate(EmpInfoTerminalCode empInfoTerCode, ContractCode contractCode);
	
	// [2]取得する
	List<TimeRecordSetUpdateList> get(ContractCode contractCode, List<EmpInfoTerminalCode> listEmpInfoTerCode);
	
	// [3]Insert(List<タイムレコード設定更新リスト>) 
	void insert(List<TimeRecordSetUpdateList> listTimeRecordSetUpdateList);
	
	// 	[4]delete(List<タイムレコード設定更新リスト>)
	void delete(List<TimeRecordSetUpdateList> listTimeRecordSetUpdateList);
}
