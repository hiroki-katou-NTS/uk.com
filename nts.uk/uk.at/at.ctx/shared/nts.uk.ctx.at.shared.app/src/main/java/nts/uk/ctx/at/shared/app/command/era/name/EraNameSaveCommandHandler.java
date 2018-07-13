package nts.uk.ctx.at.shared.app.command.era.name;

import java.time.LocalDate;
import java.time.Month;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.era.name.EraName;
import nts.uk.ctx.at.shared.dom.era.name.EraNameDom;
import nts.uk.ctx.at.shared.dom.era.name.EraNameDomRepository;
import nts.uk.ctx.at.shared.dom.era.name.SymbolName;
import nts.uk.ctx.at.shared.dom.era.name.SystemType;

/**
 * The Class EraNameSaveCommandHandler.
 */
@Stateless
public class EraNameSaveCommandHandler extends CommandHandler<EraNameSaveCommand> {
	
	/** The Constant LAST_END_DATE. */
	private static final GeneralDate LAST_END_DATE = GeneralDate.localDate(LocalDate.of(9999, Month.DECEMBER, 31));
	
	/** The repo. */
	@Inject
	private EraNameDomRepository repo;

	/* (non-Javadoc)
	 * @see nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command.CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<EraNameSaveCommand> context) {
		
		EraNameSaveCommand command = context.getCommand();
		GeneralDate newStrDate = GeneralDate.fromString(command.getStartDate(),"yyyy/MM/dd");
		
		if(command.getEraId() != null) {
			// get era name from db
			EraNameDom domain = this.repo.getEraNameById(command.getEraId());
			// update
			if(domain != null) {
				if(SystemType.SYSTEM.value.equals(command.getSystemType())){
					throw new RuntimeException("Invalid EraName");
				}
				
				// get previous era name item
				EraNameDom preItem = this.repo.getEraNameByEndDate(GeneralDate.localDate(domain.getStartDate().localDate().minusDays(1)));
				if(preItem == null)
					throw new RuntimeException("Not found previous EraName item"); 
				
				// update saved item
				domain.setEraName(new EraName(command.getEraName()));
				domain.setSymbol(new SymbolName(command.getEraSymbol()));
				domain.setStartDate(newStrDate);

				// check if updated start date is invalid
				if(!newStrDate.after(preItem.getStartDate()))
					throw new BusinessException("Msg_452");
				// check if the last item
				else if(!domain.getEndDate().equals(LAST_END_DATE)) {
					// get next era name items
					EraNameDom nextItem = this.repo.getEraNameByStartDate(domain.getEndDate().addDays(1));
					// check if updated start date is invalid
					if(!newStrDate.before(nextItem.getStartDate()))
						throw new BusinessException("Msg_453");
				}
				this.repo.updateEraName(domain);
				
				// update end date of previous item
				preItem.setEndDate(GeneralDate.localDate(newStrDate.localDate().minusDays(1)));
				this.repo.updateEraName(preItem);
				
			}
			else throw new RuntimeException("Invalid EraNameId");
		}
		// add new
		else {
			// get previous era name item
			EraNameDom preItem = this.repo.getEraNameByEndDate(LAST_END_DATE);
			if(preItem == null)
				throw new RuntimeException("Not found the last EraName item");

			// add new era name item
			EraNameDom domain = command.toDomain();
			// check if updated start date is invalid
			if(!domain.getStartDate().after(preItem.getStartDate()))
				throw new BusinessException("Msg_452");
			
			domain.setSystemType(SystemType.NOT_SYSTEM);
			domain.setEndDate(LAST_END_DATE);
			this.repo.addNewEraName(domain);

			// update end date of previous item
			preItem.setEndDate(GeneralDate.localDate(newStrDate.localDate().minusDays(1)));
			this.repo.updateEraName(preItem);
		}
	}
	
}