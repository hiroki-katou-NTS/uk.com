package nts.uk.ctx.basic.app.command.system.era;

import java.util.List;
import java.util.Optional;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.basic.dom.system.era.Era;
import nts.uk.ctx.basic.dom.system.era.EraRepository;

@RequestScoped
public class UpdateEraCommandHandler  extends CommandHandler<UpdateEraCommand>{
	@Inject
	private EraRepository eraRepository;

	@Override
	protected void handle(CommandHandlerContext<UpdateEraCommand> context) {
		// TODO Auto-generated method stub
		Era era = context.getCommand().toDomain();
		//era.validate();
		//List<Era> endDate = this.eraRepository.endDate();
//		if(endDate.isEmpty()){
//			this.eraRepository.update(era);
//		}
		Optional<Era> latestEra = this.eraRepository.getLatestEra();
		latestEra.get().setEndDate(era.getStartDate().addDays(-1));
		//Optional<Era> eraa = this.eraRepository.getEraDetail(era.getStartDate());
		this.eraRepository.update(latestEra.get());
		
	}
	
	
	
}
