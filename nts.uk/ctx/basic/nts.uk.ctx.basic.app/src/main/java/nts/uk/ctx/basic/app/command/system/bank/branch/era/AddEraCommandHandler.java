package nts.uk.ctx.basic.app.command.system.bank.branch.era;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.basic.dom.system.era.Era;
import nts.uk.ctx.basic.dom.system.era.EraRepository;

@RequestScoped
public class AddEraCommandHandler extends CommandHandler<AddEraCommand> {
	@Inject
	private EraRepository eraRepository;

	@Override
	public void handle(CommandHandlerContext<AddEraCommand> context) {
		Era era = context.getCommand().toDomain();
		
		// check exist of that era
		Optional<Era> eraOpt = this.eraRepository.getEraDetail(era.getStartDate());
		if(eraOpt.isPresent()){
			throw new BusinessException("this era has already existed!");
		}
		//era.validate();
		era.setEndDate(GeneralDate.ymd(9999, 12, 31));
		Optional<Era> latestEra = this.eraRepository.getLatestEra();
		System.out.println(era.getEndDate());
		if (latestEra.isPresent()) {
			latestEra.get().setEndDate(era.getStartDate().addDays(-1));
			this.eraRepository.update(latestEra.get());
		}
		
		this.eraRepository.add(era);
	}
	
	
	

}
