package nts.uk.ctx.at.shared.app.command.specialholiday.grantinformation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.at.shared.dom.specialholiday.grantinformation.GrantDateTbl;
import nts.uk.ctx.at.shared.dom.specialholiday.grantinformation.GrantDateTblRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author tanlv
 *
 */
@Stateless
public class AddGrantDateTblCommandHandler extends CommandHandlerWithResult<GrantDateTblCommand, List<String>> {
	@Inject
	private GrantDateTblRepository repo;

	@Override
	protected List<String> handle(CommandHandlerContext<GrantDateTblCommand> context) {
		List<String> errList = new ArrayList<String>();
		GrantDateTblCommand command = context.getCommand();
		String companyId = AppContexts.user().companyId();
		
		// check exists code
		Optional<GrantDateTbl> grantDateTbl = repo.findByCode(companyId, command.getSpecialHolidayCode(), command.getGrantDateCode());
		if (grantDateTbl.isPresent()) {
			addMessage(errList, "Msg_3");
		}
		
		GrantDateTbl domain = command.toDomain();
		
		errList.addAll(domain.validateInput());
		
		if (errList.isEmpty()) {
			if(domain.isSpecified()) {
				repo.changeAllProvision(command.getSpecialHolidayCode());
			}
			
			// add to db		
			repo.add(domain);
		}

		return errList;
	}
	
	/**
	 * Add exception message
	 * 
	 * @param exceptions
	 * @param messageId
	 */
	private void addMessage(List<String> errorsList, String messageId) {
		if (!errorsList.contains(messageId)) {
			errorsList.add(messageId);
		}
	}
}
