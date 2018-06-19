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

@Stateless
public class EraNameDeleteCommandHandler extends CommandHandler<EraNameDeleteCommand>{
	
	private final GeneralDate LAST_END_DATE = GeneralDate.localDate(LocalDate.of(9999, Month.DECEMBER, 31));
	
	@Inject EraNameDomRepository repo;
	
	@Override
	protected void handle(CommandHandlerContext<EraNameDeleteCommand> context) {
		
		EraNameDeleteCommand command = context.getCommand();
		
		// get by Id
		EraNameDom domain = this.repo.getEraNameById(command.getEraId());
		if(domain != null) {
			// check if the last item
			if(domain.getEndDate().equals(LAST_END_DATE))
				// delete item
				this.repo.deleteEraName(command.getEraId());
			throw new RuntimeException("Invalid EraName");
		}
		else throw new RuntimeException("Invalid EraName");
	}

}
