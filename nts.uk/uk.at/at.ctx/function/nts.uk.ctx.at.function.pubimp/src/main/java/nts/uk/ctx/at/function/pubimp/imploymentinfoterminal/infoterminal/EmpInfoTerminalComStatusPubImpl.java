package nts.uk.ctx.at.function.pubimp.imploymentinfoterminal.infoterminal;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.ContractCode;
import nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalComStatus;
import nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.repo.EmpInfoTerminalComStatusRepository;
import nts.uk.ctx.at.function.pub.employmentinfoterminal.infoterminal.EmpInfoTerminalComStatusExport;
import nts.uk.ctx.at.function.pub.employmentinfoterminal.infoterminal.EmpInfoTerminalComStatusPub;

/**
 * 
 * @author dungbn
 *
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class EmpInfoTerminalComStatusPubImpl implements EmpInfoTerminalComStatusPub {

	@Inject
	private EmpInfoTerminalComStatusRepository repository;

	@Override
	public Optional<EmpInfoTerminalComStatusExport> get(String contractCode, String empInfoTerCode) {
		Optional<EmpInfoTerminalComStatus> statusOpt = repository.get(new ContractCode(contractCode), new EmpInfoTerminalCode(empInfoTerCode));
		return statusOpt.map(s -> convertToExport(s));
	}
	
	@Override
	public void delete(EmpInfoTerminalComStatusExport empInfoTerminalComStatusExport) {
		repository.delete(convertToDomain(empInfoTerminalComStatusExport));
	}
	
	private EmpInfoTerminalComStatusExport convertToExport(EmpInfoTerminalComStatus domain) {
		return new EmpInfoTerminalComStatusExport(domain.getContractCode().v(), domain.getEmpInfoTerCode().v(), domain.getSignalLastTime());			
	}
	
	private EmpInfoTerminalComStatus convertToDomain(EmpInfoTerminalComStatusExport export) {
		return new EmpInfoTerminalComStatus(new ContractCode(export.getContractCode()), new EmpInfoTerminalCode(export.getEmpInfoTerCode()), export.getSignalLastTime());
	}

	@Override
	public List<EmpInfoTerminalComStatusExport> get(String contractCode, List<String> empInfoTerCodeList) {
		List<EmpInfoTerminalComStatus> listEmpInfoTerminalComStatus = repository.get(new ContractCode(contractCode), 
																			empInfoTerCodeList.stream().map(e -> new EmpInfoTerminalCode(e)).collect(Collectors.toList()));
		if (listEmpInfoTerminalComStatus.isEmpty()) {
			return Collections.emptyList();
		}
		return listEmpInfoTerminalComStatus.stream()
				 						   .map(e -> convertToExport(e))
				 						   .collect(Collectors.toList());
	}
	
	
}
