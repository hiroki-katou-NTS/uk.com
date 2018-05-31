package nts.uk.ctx.at.shared.app.command.specialholiday;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.at.shared.dom.specialholiday.grantdate.GrantDatePer;
import nts.uk.ctx.at.shared.dom.specialholiday.grantday.GrantRegularRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author Tanlv
 *
 */
@Transactional
@Stateless
public class AddGrantDatePerCommandHandler extends CommandHandlerWithResult<GrantDatePerCommand, List<String>> {
	@Inject
	private GrantRegularRepository grantRegularRepository;

	@Override
	protected List<String> handle(CommandHandlerContext<GrantDatePerCommand> context) {
		GrantDatePerCommand command = context.getCommand();
		String companyId = AppContexts.user().companyId();
		List<String> errList = new ArrayList<String>();
		
		GrantDatePer domain = command.toDomain();
		
		domain.validate();
		
		// check exists code
		Optional<GrantDatePer> grantDatePer = grantRegularRepository.getPerByCode(companyId, command.getSpecialHolidayCode(), command.getPersonalGrantDateCode());
		if (grantDatePer.isPresent()) {
			errList.add("Msg_3");
		} 
		
		errList.addAll(domain.validateInput());
		
		if (errList.isEmpty()) {
			// Add new data
			if(domain.getProvision() == 1) {
				grantRegularRepository.changeAllProvision();
			}
			
			grantRegularRepository.addPer(domain);
		}
		
		return errList;
	}
}
