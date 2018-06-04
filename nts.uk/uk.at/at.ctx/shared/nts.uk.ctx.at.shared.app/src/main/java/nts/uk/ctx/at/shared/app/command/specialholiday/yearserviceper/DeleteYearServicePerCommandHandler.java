package nts.uk.ctx.at.shared.app.command.specialholiday.yearserviceper;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.specialholiday.yearservice.yearserviceper.YearServicePer;
import nts.uk.ctx.at.shared.dom.specialholiday.yearservice.yearserviceper.repository.YearServicePerRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class DeleteYearServicePerCommandHandler extends CommandHandler<DeleteYearServicePerCommand>{
	@Inject
	private YearServicePerRepository yearServicePerRep;

	@Override
	protected void handle(CommandHandlerContext<DeleteYearServicePerCommand> context) {
		String companyId = AppContexts.user().companyId();
		Optional<YearServicePer> yearServicePerOld = yearServicePerRep.findPer(companyId, 
																				context.getCommand().getSpecialHolidayCode(),
																				context.getCommand().getYearServiceCode());
		if(!yearServicePerOld.isPresent()){
			throw new RuntimeException("対象データがありません。");
		}
		
		if(yearServicePerOld.get().getProvision() == 1) {
			throw new BusinessException("Msg_1219");
		}
		
		yearServicePerRep.delete(companyId, context.getCommand().getSpecialHolidayCode(), context.getCommand().yearServiceCode);
	}
	
}
