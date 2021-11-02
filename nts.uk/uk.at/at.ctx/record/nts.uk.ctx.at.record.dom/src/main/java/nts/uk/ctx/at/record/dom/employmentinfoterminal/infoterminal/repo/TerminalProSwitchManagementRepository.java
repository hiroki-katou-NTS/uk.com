package nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.repo;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.TerminalProSwitchManagement;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;

/**
 * 
 * @author dungbn
 *	端末の本番切替管理Repository
 */
public interface TerminalProSwitchManagementRepository {

	Optional<TerminalProSwitchManagement> get(ContractCode contractCode);
}
