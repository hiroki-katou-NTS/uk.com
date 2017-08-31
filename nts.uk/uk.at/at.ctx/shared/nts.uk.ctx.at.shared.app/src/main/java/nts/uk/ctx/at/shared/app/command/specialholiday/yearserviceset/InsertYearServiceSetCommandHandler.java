package nts.uk.ctx.at.shared.app.command.specialholiday.yearserviceset;

import java.util.Optional;
import java.util.concurrent.TimeoutException;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.specialholiday.yearserviceset.YearServiceSet;
import nts.uk.ctx.at.shared.dom.specialholiday.yearserviceset.repository.YearServiceSetRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class InsertYearServiceSetCommandHandler extends CommandHandler<InsertYearServiceSetCommand>{
	@Inject
	private YearServiceSetRepository yearServiceSetRep;

	@Override
	protected void handle(CommandHandlerContext<InsertYearServiceSetCommand> context) {
		String companyId = AppContexts.user().companyId();
		YearServiceSet yearServiceSet = YearServiceSet.createFromJavaType(companyId, context.getCommand().getSpecialHolidayCode(), context.getCommand().getYearServiceType(), context.getCommand().getYear(), context.getCommand().getMonth(), context.getCommand().getDate());
		Optional<YearServiceSet> yearServiceSetOld = yearServiceSetRep.find(companyId, context.getCommand().getSpecialHolidayCode(), context.getCommand().getYearServiceType());
		if(yearServiceSetOld.isPresent()){
			try{
				throw new TimeoutException();
			}catch(TimeoutException e){
			}
		}
		yearServiceSetRep.insert(yearServiceSet);
	}
}
