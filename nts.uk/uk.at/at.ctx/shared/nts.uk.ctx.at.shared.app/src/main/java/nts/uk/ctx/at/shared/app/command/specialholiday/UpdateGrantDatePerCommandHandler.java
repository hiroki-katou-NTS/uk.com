package nts.uk.ctx.at.shared.app.command.specialholiday;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.at.shared.dom.specialholiday.grantdate.GrantDatePer;
import nts.uk.ctx.at.shared.dom.specialholiday.grantday.GrantRegularRepository;

@Transactional
@Stateless
public class UpdateGrantDatePerCommandHandler extends CommandHandlerWithResult<GrantDatePerCommand, List<String>> {
	@Inject
	private GrantRegularRepository grantRegularRepository;
	
	@Override
	protected List<String> handle(CommandHandlerContext<GrantDatePerCommand> context) {
		GrantDatePerCommand command = context.getCommand();
		
		GrantDatePer domain = command.toDomain();
		
		List<String> errList = domain.validateInput();
		
		if (errList.isEmpty()) {
			// Add new data
			if(domain.getProvision() == 1) {
				grantRegularRepository.changeAllProvision();
			}
			
			grantRegularRepository.updatePer(domain);
		}
		
		return errList;
	}
}
