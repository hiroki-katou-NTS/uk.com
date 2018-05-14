package nts.uk.ctx.at.function.app.command.holidaysremaining;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.function.dom.holidaysremaining.HolidaysRemainingManagement;
import nts.uk.ctx.at.function.dom.holidaysremaining.ItemOutputForm;
import nts.uk.ctx.at.function.dom.holidaysremaining.repository.HolidaysRemainingManagementRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

@Stateless
@Transactional
public class AddHdRemainManageCommandHandler extends CommandHandler<HdRemainManageCommand> {
	
	@Inject
	private HolidaysRemainingManagementRepository holidaysRemainingManagementRepository;
	
	@Override
	protected void handle(CommandHandlerContext<HdRemainManageCommand> context) {
		LoginUserContext login = AppContexts.user();
		String companyId = login.companyId();
		HdRemainManageCommand command = context.getCommand();
		ItemOutputForm itemOutputForm = new ItemOutputForm(
				command.isNursingLeave(),
				command.isRemainingChargeSubstitute(), command.isRepresentSubstitute(), command.isOutputItemSubstitute(),
				command.isOutputHolidayForward(), command.isMonthlyPublic(), command.isOutputItemsHolidays(),
				command.isChildNursingLeave(),
				command.isYearlyHoliday(), command.isInsideHours(), command.isInsideHalfDay(), 
				command.isNumberRemainingPause(), command.isUnDigestedPause(), command.isPauseItem(),
				command.isYearlyReserved(), command.getListSpecialHoliday());
		
		if(!itemOutputForm.hasOutput()){
			throw new BusinessException("Msg_880");
		}
		HolidaysRemainingManagement domain = new HolidaysRemainingManagement(
				companyId,
				command.getCd(),
				command.getName(),
				itemOutputForm 
				);
		
		holidaysRemainingManagementRepository.insert(domain);

	}
	
	
}
