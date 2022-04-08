package nts.uk.ctx.at.request.ac.record.infoterminal;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.pub.employmentinfoterminal.infoterminal.setting.EmpInfoTerminalPub;
import nts.uk.ctx.at.record.pub.employmentinfoterminal.infoterminal.setting.dto.EmpInfoTerminalExport;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.infoterminal.EmpInfoTerminalAdapter;
import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.EmpInfoTerminal;
import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.EmpInfoTerminal.EmpInfoTerminalBuilder;
import nts.uk.ctx.at.shared.dom.workrule.goingout.GoingOutReason;

/**
 * @author ThanhNX 就業情報端末Repository
 *
 */
@Stateless
public class EmpInfoTerminalAdapterImpl implements EmpInfoTerminalAdapter {

	@Inject
	private EmpInfoTerminalPub pub;

	@Override
	public Optional<EmpInfoTerminal> getEmpInfoTerminal(String empInfoTerCode, String contractCode, Optional<String> leavCategory) {
		return pub.getEmpInfoTerminal(empInfoTerCode, contractCode, leavCategory).map(x -> convertTo(x));
	}

	private EmpInfoTerminal convertTo(EmpInfoTerminalExport setting) {
		return new EmpInfoTerminalBuilder(setting.getIpAddress(), setting.getMacAddress(), setting.getEmpInfoTerCode(),
				setting.getTerSerialNo(), setting.getEmpInfoTerName(), setting.getContractCode())
						.modelEmpInfoTer(setting.getModelEmpInfoTer()).intervalTime(setting.getIntervalTime())
						.empInfoTerMemo(setting.getEmpInfoTerMemo())
						.goOutReason(setting.getGoOutReason().map(x -> GoingOutReason.valueOf(x)))
						.build();
	}
}
