package nts.uk.ctx.at.shared.app.command.holidaysetting.configuration;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.app.find.holidaysetting.configuration.PublicHolidayManagementUsageUnitFindDto;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration.PublicHolidayManagementUsageUnit;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration.PublicHolidayManagementUsageUnitRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class PublicHolidayManagementUsageUnitSaveCommandHandler.
 */
@Stateless
public class PublicHolidayManagementUsageUnitSaveCommandHandler extends CommandHandler<PublicHolidayManagementUsageUnitFindDto>{
	
	/** The repository. */
	@Inject
	private PublicHolidayManagementUsageUnitRepository repository;
	
	/* (non-Javadoc)
	 * @see nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command.CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<PublicHolidayManagementUsageUnitFindDto> context) {
		//get company id
		String companyId = AppContexts.user().companyId();
		
		//get command
		PublicHolidayManagementUsageUnitFindDto command = context.getCommand();
		
		//find unit setting
		Optional<PublicHolidayManagementUsageUnit> opt = this.repository.get(companyId);
		
		// check setting is existed and update.
		if(opt.isPresent()){
			PublicHolidayManagementUsageUnit domain = opt.get();
			domain.setIsManageEmployeePublicHd(command.getIsManageEmployeePublicHd());
			domain.setIsManageEmpPublicHd(command.getIsManageEmpPublicHd());
			domain.setIsManageWkpPublicHd(command.getIsManageWkpPublicHd());
			
			this.repository.update(domain);
		} else {
			PublicHolidayManagementUsageUnit domain = new PublicHolidayManagementUsageUnit(
																	command.getIsManageEmployeePublicHd(), 
																	command.getIsManageWkpPublicHd(), 
																	command.getIsManageEmpPublicHd());
			this.repository.insert(domain); 
		}
	}
}
