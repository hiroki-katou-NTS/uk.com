package nts.uk.ctx.at.record.infra.repository.employmentinfoterminal.infoterminal;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.TimeRecordReqSetting;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.repo.TimeRecordReqSettingSendEmployeeRepository;
import nts.uk.ctx.at.record.infra.entity.employmentinfoterminal.infoterminal.KrcmtTrSendEmployee;
import nts.uk.ctx.at.record.infra.entity.employmentinfoterminal.infoterminal.KrcmtTrSendEmployeePK;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class JpaTimeRecordReqSettingSendEmployeeRepository extends JpaRepository
	   implements TimeRecordReqSettingSendEmployeeRepository{

	@Override
	public void insert(TimeRecordReqSetting reqSetting) {
		List<KrcmtTrSendEmployee> employeeList = reqSetting.getEmployeeIds().stream()
				.map(e -> toEntity(reqSetting, e.v())).collect(Collectors.toList());
		
		this.commandProxy().insertAll(employeeList);
	}

	@Override
	public void update(TimeRecordReqSetting reqSetting) {
		List<KrcmtTrSendEmployee> employeeList = reqSetting.getEmployeeIds().stream()
				.map(e -> toEntity(reqSetting, e.v())).collect(Collectors.toList());

		this.commandProxy().updateAll(employeeList);
	}

	@Override
	public void delete(TimeRecordReqSetting reqSetting) {
		String contractCode = reqSetting.getContractCode().v();
		String timeRecordCode = reqSetting.getTerminalCode().v();

		List<KrcmtTrSendEmployeePK> employeeList = reqSetting.getEmployeeIds().stream()
				.map(e -> new KrcmtTrSendEmployeePK(contractCode, timeRecordCode, e.v())).collect(Collectors.toList());

		this.commandProxy().removeAll(KrcmtTrSendEmployee.class, employeeList);
		this.getEntityManager().flush();
	}
	
	private KrcmtTrSendEmployee toEntity(TimeRecordReqSetting setting, String employeeId) {
		return new KrcmtTrSendEmployee(
				new KrcmtTrSendEmployeePK(
						setting.getContractCode().v(),
						setting.getTerminalCode().v(),
						employeeId));
	}
}
