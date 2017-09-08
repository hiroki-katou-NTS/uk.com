package nts.uk.ctx.at.shared.app.command.specialholiday;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.at.shared.dom.specialholiday.SpecialHoliday;
import nts.uk.ctx.at.shared.dom.specialholiday.SpecialHolidayRepository;
import nts.uk.shr.com.context.AppContexts;

@RequestScoped
public class AddSpecialHolidayCommandHandler extends CommandHandlerWithResult<AddSpecialHolidayCommand, List<String>> {

	@Inject
	private SpecialHolidayRepository specialHolidayRepository;

	@Override
	protected List<String> handle(CommandHandlerContext<AddSpecialHolidayCommand> context) {
		List<String> errList = new ArrayList<String>();

		AddSpecialHolidayCommand addSpecialHolidayCommand = context.getCommand();
		String companyId = AppContexts.user().companyId();
		
		// check Error when Add
		boolean existsBranch = specialHolidayRepository.checkExists(companyId,
				addSpecialHolidayCommand.getSpecialHolidayCode());
		if (existsBranch) {
			addMessage(errList, "Msg_3");
		}
				
		SpecialHoliday specialHoliday = addSpecialHolidayCommand.toDomain(companyId);
		
		try {
			specialHoliday.validate();
		} catch (RuntimeException e) {
			BusinessException ex = (BusinessException) e.getCause();
			addMessage(errList, ex.getMessageId());
		}

		// add Special Holiday
		specialHolidayRepository.add(specialHoliday);
		
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
