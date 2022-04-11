package nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.repo;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.ReqComStatusMonitoring;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;

public interface ReqComStatusMonitoringRepository {

	// [1]  insert(リクエスト通信の状態監視)
	void insert(ReqComStatusMonitoring reqComStatusMonitoring);
	
	// [2]  update(リクエスト通信の状態監視)
	void update(ReqComStatusMonitoring reqComStatusMonitoring);
	
	// [３]  delete(リクエスト通信の状態監視)
	void delete(ReqComStatusMonitoring reqComStatusMonitoring);
	
	// [4] 取得する
	List<ReqComStatusMonitoring> get(ContractCode contractCode, List<EmpInfoTerminalCode> listTerminalCode, boolean connecting);
	
	// [5] キーで取得する
	Optional<ReqComStatusMonitoring> getWithKey(ContractCode contractCode, EmpInfoTerminalCode terCode);
}
