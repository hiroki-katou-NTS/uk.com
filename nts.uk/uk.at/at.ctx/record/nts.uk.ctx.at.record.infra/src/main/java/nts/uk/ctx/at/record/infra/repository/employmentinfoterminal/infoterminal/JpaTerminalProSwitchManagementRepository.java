package nts.uk.ctx.at.record.infra.repository.employmentinfoterminal.infoterminal;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.TerminalProSwitchManagement;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.repo.TerminalProSwitchManagementRepository;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;
import nts.uk.ctx.at.record.infra.entity.employmentinfoterminal.infoterminal.KrcmtTrSwitchMng;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;

@Stateless
public class JpaTerminalProSwitchManagementRepository extends JpaRepository implements TerminalProSwitchManagementRepository {

	public final String GET_ALL = "SELECT e FROM KrcmtTrSwitchMng e WHERE e.contractCode = :contractCode";
	
	@Override
	public Optional<TerminalProSwitchManagement> get(ContractCode contractCode) {
		// TODO Auto-generated method stub
		
		Optional<KrcmtTrSwitchMng> entity = this.queryProxy().query(GET_ALL, KrcmtTrSwitchMng.class)
									  .setParameter("contractCode", contractCode)
									  .getSingle();
		
		if (!entity.isPresent()) {
			return Optional.empty();
		}
		
		TerminalProSwitchManagement terminalProSwitchManagement = new TerminalProSwitchManagement(new ContractCode(entity.get().contractCode),
																		EnumAdaptor.valueOf(entity.get().managementAtr, ManageDistinct.class));
		
		return Optional.of(terminalProSwitchManagement);
	}

}
