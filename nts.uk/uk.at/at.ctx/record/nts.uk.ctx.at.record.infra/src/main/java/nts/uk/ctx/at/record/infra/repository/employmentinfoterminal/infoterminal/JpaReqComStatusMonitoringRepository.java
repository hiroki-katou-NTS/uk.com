package nts.uk.ctx.at.record.infra.repository.employmentinfoterminal.infoterminal;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.ReqComStatusMonitoring;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.repo.ReqComStatusMonitoringRepository;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;
import nts.uk.ctx.at.record.infra.entity.employmentinfoterminal.infoterminal.KrcdtTrRqStMonitor;
import nts.uk.ctx.at.record.infra.entity.employmentinfoterminal.infoterminal.KrcdtTrRqStMonitorPK;

@Stateless
public class JpaReqComStatusMonitoringRepository extends JpaRepository implements ReqComStatusMonitoringRepository {

	private static final String FIND_CONTRACTCD_CODE_CONNECTING = "SELECT m FROM KrcdtTrRqStMonitor m WHERE m.pk.contractCode = :contractCode AND m.connecting = :connecting AND m.pk.timeRecordCode IN :listCode";
	
	@Override
	public void insert(ReqComStatusMonitoring reqComStatusMonitoring) {
		this.commandProxy().insert(KrcdtTrRqStMonitor.toEntity(reqComStatusMonitoring));
	}

	@Override
	public void update(ReqComStatusMonitoring reqComStatusMonitoring) {
		KrcdtTrRqStMonitorPK key = new KrcdtTrRqStMonitorPK(reqComStatusMonitoring.getContractCode().v(), reqComStatusMonitoring.getTerminalCode().v());
		KrcdtTrRqStMonitor entity = this.queryProxy().find(key, KrcdtTrRqStMonitor.class).get();
		entity.connecting = reqComStatusMonitoring.isConnecting();
		this.commandProxy().update(entity);
	}

	@Override
	public void delete(ReqComStatusMonitoring reqComStatusMonitoring) {
		KrcdtTrRqStMonitorPK key = new KrcdtTrRqStMonitorPK(reqComStatusMonitoring.getContractCode().v(), reqComStatusMonitoring.getTerminalCode().v());
		this.commandProxy().remove(KrcdtTrRqStMonitor.class, key);
	}

	@Override
	public List<ReqComStatusMonitoring> get(ContractCode contractCode, List<EmpInfoTerminalCode> listTerminalCode,
			boolean connecting) {
		List<String> listCode = listTerminalCode.stream().map(e -> e.v()).collect(Collectors.toList());
		
		if (CollectionUtil.isEmpty(listCode)) return Collections.emptyList();
		
		return this.queryProxy().query(FIND_CONTRACTCD_CODE_CONNECTING, KrcdtTrRqStMonitor.class)
					.setParameter("contractCode", contractCode.v())
					.setParameter("listCode", listCode)
					.setParameter("connecting", connecting)
					.getList()
					.stream()
					.map(e -> e.toDomain()).collect(Collectors.toList());
	}

	@Override
	public Optional<ReqComStatusMonitoring> getWithKey(ContractCode contractCode, EmpInfoTerminalCode terCode) {
		return this.queryProxy().find(new KrcdtTrRqStMonitorPK(contractCode.v(), terCode.v()), KrcdtTrRqStMonitor.class).map(x -> {
			return new ReqComStatusMonitoring(contractCode, terCode, x.connecting);
		});
	}

}
