package nts.uk.ctx.at.shared.app.command.era.name;

import java.time.LocalDate;
import java.time.Month;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.era.name.EraNameDom;
import nts.uk.ctx.at.shared.dom.era.name.EraNameDomRepository;
import nts.uk.ctx.at.shared.dom.era.name.SystemType;

/**
 * The Class EraNameDeleteCommandHandler.
 */
@Stateless
public class EraNameDeleteCommandHandler extends CommandHandler<EraNameDeleteCommand>{
	
	/** The last end date. */
	private static final GeneralDate LAST_END_DATE = GeneralDate.localDate(LocalDate.of(9999, Month.DECEMBER, 31));
	
	/** The repo. */
	@Inject EraNameDomRepository repo;
	
	/* (non-Javadoc)
	 * @see nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command.CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<EraNameDeleteCommand> context) {
		
		EraNameDeleteCommand command = context.getCommand();
		
		// get by Id
		EraNameDom domain = this.repo.getEraNameById(command.getEraId());
		if(domain != null) {
			// check if the last item or not system => can delete
			if(domain.getEndDate().equals(LAST_END_DATE) || SystemType.NOT_SYSTEM.equals(domain.getSystemType())) {
				// get start date of deleted item
				GeneralDate strDate = domain.getStartDate();
				// delete item
				this.repo.deleteEraName(command.getEraId());
				// update end date of pre item
				EraNameDom preItem = this.repo.getEraNameByEndDate(GeneralDate.localDate(strDate.localDate().minusDays(1)));
				preItem.setEndDate(LAST_END_DATE);
				this.repo.updateEraName(preItem);
			}
			else throw new RuntimeException("Invalid EraName");
		}
		else throw new RuntimeException("Invalid EraNameId");
	}

}
