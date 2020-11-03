package nts.uk.ctx.at.function.pubimp.imploymentinfoterminal.infoterminal;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.ContractCode;
import nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalComStatus;
import nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.repo.EmpInfoTerminalComStatusRepository;
import nts.uk.ctx.at.function.pub.imploymentinfoterminal.infoterminal.EmpInfoTerminalComStatusExport;
import nts.uk.ctx.at.function.pub.imploymentinfoterminal.infoterminal.EmpInfoTerminalComStatusPub;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class EmpInfoTerminalComStatusPubImpl implements EmpInfoTerminalComStatusPub {

	@Inject
	private EmpInfoTerminalComStatusRepository repository;

	@Override
	public Optional<EmpInfoTerminalComStatusExport> get(String contractCode, int empInfoTerCode) {
		return Optional.of(convertToExport(repository.get(new ContractCode(contractCode), new EmpInfoTerminalCode(empInfoTerCode)).get()));
	}
	
	@Override
	public void delete(EmpInfoTerminalComStatusExport empInfoTerminalComStatusExport) {
		repository.delete(convertToDomain(empInfoTerminalComStatusExport));
	}
	
	private EmpInfoTerminalComStatusExport convertToExport(EmpInfoTerminalComStatus domain) {
		return new EmpInfoTerminalComStatusExport(domain.getContractCode().v(), domain.getEmpInfoTerCode().v().intValue(), domain.getSignalLastTime());			
	}
	
	private EmpInfoTerminalComStatus convertToDomain(EmpInfoTerminalComStatusExport export) {
		return new EmpInfoTerminalComStatus(new ContractCode(export.getContractCode()), new EmpInfoTerminalCode(export.getEmpInfoTerCode()), export.getSignalLastTime());
	}
	
	
}
