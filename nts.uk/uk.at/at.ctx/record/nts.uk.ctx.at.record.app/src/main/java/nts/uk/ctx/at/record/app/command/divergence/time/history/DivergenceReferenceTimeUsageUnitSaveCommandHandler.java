package nts.uk.ctx.at.record.app.command.divergence.time.history;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.divergence.time.history.DivergenceReferenceTimeUsageUnit;
import nts.uk.ctx.at.record.dom.divergence.time.history.DivergenceReferenceTimeUsageUnitRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class DivergenceReferenceTimeUsageUnitSaveCommandHandler extends CommandHandler<DivergenceReferenceTimeUsageUnitCommand>{
	
	/** The repository. */
	@Inject
	private DivergenceReferenceTimeUsageUnitRepository divergenceRepo;

	@Override
	@Transactional
	protected void handle(CommandHandlerContext<DivergenceReferenceTimeUsageUnitCommand> context) {
		// TODO Auto-generated method stub
		
		/*Get company ID*/	
		String companyId = AppContexts.user().companyId();
		
		DivergenceReferenceTimeUsageUnitCommand command = context.getCommand();
		DivergenceReferenceTimeUsageUnit domain = new DivergenceReferenceTimeUsageUnit(command.getDivergenceReferenceTimeUsageUnitDto());
		
		domain = this.divergenceRepo.findByCompanyId(companyId);
		if (domain != null){
			this.divergenceRepo.update(domain);
		}
	}

}
