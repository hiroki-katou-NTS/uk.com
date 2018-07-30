package nts.uk.ctx.at.shared.app.command.specialholiday.grantinformation;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.at.shared.dom.specialholiday.grantinformation.GrantDateTbl;
import nts.uk.ctx.at.shared.dom.specialholiday.grantinformation.GrantDateTblRepository;

/**
 * 
 * @author tanlv
 *
 */
@Stateless
public class UpdateGrantDateTblCommandHandler extends CommandHandlerWithResult<GrantDateTblCommand, List<String>> {
	@Inject
	private GrantDateTblRepository repo;

	@Override
	protected List<String> handle(CommandHandlerContext<GrantDateTblCommand> context) {
		GrantDateTblCommand command = context.getCommand();
		
		GrantDateTbl domain = command.toDomain();
		
		List<String> errList = domain.validateInput();
		
		if (errList.isEmpty()) {
			// Add new data
			if(domain.isSpecified()) {
				repo.changeAllProvision(domain.getSpecialHolidayCode().v());
			}
			
			repo.update(domain);
		}
		
		return errList;
	}
}
