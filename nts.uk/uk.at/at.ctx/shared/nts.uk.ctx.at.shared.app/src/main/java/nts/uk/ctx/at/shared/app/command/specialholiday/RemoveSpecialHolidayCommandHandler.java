//package nts.uk.ctx.at.shared.app.command.specialholiday;
//
//import javax.ejb.Stateless;
//
//import nts.arc.layer.app.command.CommandHandler;
//import nts.arc.layer.app.command.CommandHandlerContext;
//import nts.uk.ctx.at.shared.dom.specialholiday.SpecialHolidayRepository;
//import nts.uk.shr.com.context.AppContexts;
//
//@Stateless
//public class RemoveSpecialHolidayCommandHandler extends CommandHandler<RemoveSpecialHolidayCommand> {
//
//	
//	private SpecialHolidayRepository specialHolidayRepository;
//	
//	@Override
//	protected void handle(CommandHandlerContext<RemoveSpecialHolidayCommand> context) {
//		RemoveSpecialHolidayCommand removeSpecialHolidayCommand = context.getCommand();
//		String companyId = AppContexts.user().companyId();
//		String specialHolidayCode = removeSpecialHolidayCommand.getSpecialHolidayCode();
//		
//		// Delete Special Holiday
//		specialHolidayRepository.delete(companyId, specialHolidayCode);
//		
//	}
//
//}
