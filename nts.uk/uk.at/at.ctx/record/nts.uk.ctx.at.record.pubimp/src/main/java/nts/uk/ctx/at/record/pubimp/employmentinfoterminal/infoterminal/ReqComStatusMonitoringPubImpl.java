package nts.uk.ctx.at.record.pubimp.employmentinfoterminal.infoterminal;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.ReqComStatusMonitoring;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.repo.ReqComStatusMonitoringRepository;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;
import nts.uk.ctx.at.record.pub.employmentinfoterminal.infoterminal.ReqComStatusMonitoringPub;

@Stateless
public class ReqComStatusMonitoringPubImpl implements ReqComStatusMonitoringPub {

	@Inject
	private ReqComStatusMonitoringRepository reqComStatusMonitoringRepo;

	@Override
	public void update(String contractCode, String terminalCode, boolean statusConnect) {
		Optional<ReqComStatusMonitoring> dataExist = reqComStatusMonitoringRepo
				.getWithKey(new ContractCode(contractCode), new EmpInfoTerminalCode(terminalCode));
		ReqComStatusMonitoring update = new ReqComStatusMonitoring(new ContractCode(contractCode),
				new EmpInfoTerminalCode(terminalCode), statusConnect);
		if (dataExist.isPresent()) {
			reqComStatusMonitoringRepo.update(update);
		} else {
			reqComStatusMonitoringRepo.insert(update);
		}
	}

}
