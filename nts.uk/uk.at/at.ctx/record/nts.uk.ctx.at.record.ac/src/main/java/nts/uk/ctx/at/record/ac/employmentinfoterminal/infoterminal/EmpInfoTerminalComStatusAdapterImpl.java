package nts.uk.ctx.at.record.ac.employmentinfoterminal.infoterminal;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.uk.ctx.at.function.pub.employmentinfoterminal.infoterminal.EmpInfoTerminalComStatusExport;
import nts.uk.ctx.at.function.pub.employmentinfoterminal.infoterminal.EmpInfoTerminalComStatusPub;
import nts.uk.ctx.at.record.dom.adapter.employmentinfoterminal.infoterminal.EmpInfoTerminalComStatusAdapter;
import nts.uk.ctx.at.record.dom.adapter.employmentinfoterminal.infoterminal.EmpInfoTerminalComStatusImport;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;
/**
 * 
 * @author dungbn
 *
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class EmpInfoTerminalComStatusAdapterImpl implements EmpInfoTerminalComStatusAdapter {

	@Inject
	private EmpInfoTerminalComStatusPub pub;
	
	@Override
	public Optional<EmpInfoTerminalComStatusImport> get(ContractCode contractCode, EmpInfoTerminalCode empInfoTerCode) {
		Optional<EmpInfoTerminalComStatusExport> statusExportOpt = this.pub.get(contractCode.v(), empInfoTerCode.v());
		return statusExportOpt.map(s -> convertToImport(s));
	}
	
	@Override
	public void delete(EmpInfoTerminalComStatusImport empInfoTerminalComStatusImport) {
		this.pub.delete(convertToExport(empInfoTerminalComStatusImport));
	}
	
	private EmpInfoTerminalComStatusImport convertToImport(EmpInfoTerminalComStatusExport data) {
		return new EmpInfoTerminalComStatusImport(new ContractCode(data.getContractCode()), new EmpInfoTerminalCode(data.getEmpInfoTerCode()), data.getSignalLastTime());
	}
	
	private EmpInfoTerminalComStatusExport convertToExport(EmpInfoTerminalComStatusImport data) {
		return new EmpInfoTerminalComStatusExport(data.getContractCode().v(), data.getEmpInfoTerCode().v(), data.getSignalLastTime());
	}

	@Override
	public List<EmpInfoTerminalComStatusImport> get(ContractCode contractCode,
			List<EmpInfoTerminalCode> listEmpInfoTerCode) {
		List<EmpInfoTerminalComStatusExport> empInfoTerminalComStatusExport = this.pub.get(contractCode.v(),
			listEmpInfoTerCode.stream().map(e -> e.v()).collect(Collectors.toList()));
		return empInfoTerminalComStatusExport.stream()
											 .map(e -> convertToImport(e))
											 .collect(Collectors.toList());
	}

	
}
