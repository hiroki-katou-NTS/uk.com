/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.calculation.holiday;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.calculation.holiday.HolidayAddtionRepository;
import nts.uk.ctx.at.shared.dom.calculation.holiday.HolidayAddtionSet;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class AddHolidayAddtimeCommandHandler.
 */
@Stateless
public class AddHolidayAddtimeCommandHandler extends  CommandHandler<AddHolidayAddtimeCommand> {

	/** The repository. */
	@Inject
	private HolidayAddtionRepository repository;

	/* (non-Javadoc)
	 * @see nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command.CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<AddHolidayAddtimeCommand> context) {
		AddHolidayAddtimeCommand command = context.getCommand();
		String companyId = AppContexts.user().companyId();
		// convert to domain
		HolidayAddtionSet holidayAddtime = command.toDomain(companyId);
		if(holidayAddtime.getReferComHolidayTime() == 0){
			int morning = holidayAddtime.getMorning().intValue();
			int afternoon = holidayAddtime.getAfternoon().intValue();
			
			if(morning + afternoon > 1440){
				throw new BusinessException("Msg_143");
			}
		}
		
		holidayAddtime.validate();
		Optional<HolidayAddtionSet> optionalHoliday = this.repository.findByCId(companyId);
		
		if (optionalHoliday.isPresent()) {
			// update Holiday Addtime
			this.repository.update(holidayAddtime);
		}else {
			// add Holiday Addtime
			this.repository.add(holidayAddtime);
		};

	}
}
