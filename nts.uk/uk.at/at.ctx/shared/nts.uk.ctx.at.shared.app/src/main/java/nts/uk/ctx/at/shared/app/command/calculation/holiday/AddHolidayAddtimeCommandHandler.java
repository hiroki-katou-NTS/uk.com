/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.calculation.holiday;

import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.AddSetManageWorkHour;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.HolidayAddtionRepository;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.HolidayAddtionSet;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.HourlyPaymentAdditionSet;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.WorkDeformedLaborAdditionSet;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.WorkFlexAdditionSet;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.WorkRegularAdditionSet;
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
		Map<String, AggregateRoot> mapAggre = command.toDomain(companyId);
		
		HolidayAddtionSet holidayAddtime = (HolidayAddtionSet) mapAggre.get("holidayAddtionSet");
		WorkRegularAdditionSet workRegularAdditionSet = (WorkRegularAdditionSet) mapAggre.get("regularWork");
		WorkFlexAdditionSet flexAdditionSet = (WorkFlexAdditionSet) mapAggre.get("flexWork");
		WorkDeformedLaborAdditionSet deformedLaborAdditionSet = (WorkDeformedLaborAdditionSet) mapAggre.get("irregularWork");
		AddSetManageWorkHour addSetManageWorkHour = (AddSetManageWorkHour) mapAggre.get("addSetManageWorkHour");
		HourlyPaymentAdditionSet hourlyPaymentAdditionSet = (HourlyPaymentAdditionSet) mapAggre.get("hourlyPaymentAdditionSet");
		
		if(holidayAddtime.getWorkRecord().get().getTimeReferenceDestination().get().value == 0){
			
			int morning = holidayAddtime.getWorkRecord().get().getAdditionTimeCompany().get().getMorning().v();
			int afternoon = holidayAddtime.getWorkRecord().get().getAdditionTimeCompany().get().getAfternoon().v();
			
			if(morning + afternoon > 1440){
				throw new BusinessException("Msg_143");
			}
		}
		
		holidayAddtime.validate();
//		Optional<HolidayAddtionSet> optionalHoliday = this.repository.findByCId(companyId);
		Map<String, AggregateRoot> mapAggreRepo = this.repository.findByCompanyId(companyId);
		
		if (!mapAggreRepo.isEmpty()) {
			// update Holiday Addtime
			this.repository.update(holidayAddtime, workRegularAdditionSet, 
									flexAdditionSet, deformedLaborAdditionSet,
									addSetManageWorkHour, hourlyPaymentAdditionSet);
		}else {
			// add Holiday Addtime
			this.repository.add(holidayAddtime, workRegularAdditionSet, 
									flexAdditionSet, deformedLaborAdditionSet,
									addSetManageWorkHour, hourlyPaymentAdditionSet);
		};

	}
}
