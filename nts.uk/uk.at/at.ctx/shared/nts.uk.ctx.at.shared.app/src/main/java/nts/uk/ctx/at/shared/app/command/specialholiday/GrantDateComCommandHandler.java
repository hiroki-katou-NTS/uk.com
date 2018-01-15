package nts.uk.ctx.at.shared.app.command.specialholiday;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.at.shared.dom.specialholiday.grantdate.GrantDateCom;
import nts.uk.ctx.at.shared.dom.specialholiday.grantday.GrantRegularRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author Tanlv
 *
 */
@Transactional
@Stateless
public class GrantDateComCommandHandler extends CommandHandlerWithResult<GrantDateComCommand, List<String>> {
	
	@Inject
	private GrantRegularRepository grantRegularRepository;

	@Override
	protected List<String> handle(CommandHandlerContext<GrantDateComCommand> context) {
		GrantDateComCommand command = context.getCommand();
		String companyId = AppContexts.user().companyId();
		
		GrantDateCom domain = command.toDomain();
		
		domain.validate();
		
		List<String> errList = domain.validateInput();
		if (!errList.isEmpty()) {
			return errList;
		}
		
		// check exists code
		Optional<GrantDateCom> grantDateCom = grantRegularRepository.getComByCode(companyId, command.getSpecialHolidayCode());
		if (grantDateCom.isPresent()) {
			// Update data
			grantRegularRepository.updateGrantDateCom(domain);
		} else {
			// Add new data
			grantRegularRepository.addGrantDateCom(domain);
		}
		
		return errList;
	}
}
