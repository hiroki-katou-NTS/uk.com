package nts.uk.ctx.at.record.ac.imploymentinfoterminal.infoterminal;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.uk.ctx.at.function.pub.imploymentinfoterminal.infoterminal.EmpInfoTerminalComStatusExport;
import nts.uk.ctx.at.function.pub.imploymentinfoterminal.infoterminal.EmpInfoTerminalComStatusPub;
import nts.uk.ctx.at.record.dom.adapter.imploymentinfoterminal.infoterminal.EmpInfoTerminalComStatusAdapter;
import nts.uk.ctx.at.record.dom.adapter.imploymentinfoterminal.infoterminal.EmpInfoTerminalComStatusImport;
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

	
}
