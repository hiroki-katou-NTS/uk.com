package nts.uk.ctx.at.function.ac.employmentinfoterminal.infoterminal;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.infoterminal.FuncEmpInfoTerminalImport;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.infoterminal.FuncEmpInfoTerminalImport.FuncEmpInfoTerminalBuilder;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.infoterminal.RQEmpInfoTerminalAdapter;
import nts.uk.ctx.at.record.pub.employmentinfoterminal.infoterminal.setting.EmpInfoTerminalPub;

@Stateless
public class RQEmpInfoTerminalAC implements RQEmpInfoTerminalAdapter {

	@Inject
	private EmpInfoTerminalPub pub;

	@Override
	public Optional<String> getEmpInfoTerminalCode(String contractCode, String macAddr) {
		return pub.getEmpInfoTerminalCode(contractCode, macAddr);
	}

	@Override
	public Optional<FuncEmpInfoTerminalImport> getEmpInfoTerminal(String empInfoTerCode, String contractCode) {
		return pub.getEmpInfoTerminal(empInfoTerCode, contractCode).map(x -> {
			return new FuncEmpInfoTerminalBuilder(x.getIpAddress(), x.getMacAddress(), x.getEmpInfoTerCode(),
					x.getTerSerialNo(), x.getEmpInfoTerName(), x.getContractCode())
							.modelEmpInfoTer(x.getModelEmpInfoTer()).intervalTime(x.getIntervalTime())
							.empInfoTerMemo(x.getEmpInfoTerMemo()).build();
		});
	}

}
