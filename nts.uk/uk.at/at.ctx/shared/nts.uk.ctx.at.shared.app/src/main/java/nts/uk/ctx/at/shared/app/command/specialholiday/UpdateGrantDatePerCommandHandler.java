package nts.uk.ctx.at.shared.app.command.specialholiday;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.error.BusinessException;
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
		List<String> errList = new ArrayList<String>();
		
		GrantDatePer domain = command.toDomain();
		
		try {
			domain.validateInput();
		} catch (BusinessException e) {
			addMessage(errList, e.getMessageId());
		}
		
		if (errList.isEmpty()) {
			// Add new data
			grantRegularRepository.updatePer(domain);
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
