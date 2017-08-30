package nts.uk.ctx.at.shared.app.command.specialholiday.yearserviceset;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.specialholiday.yearserviceset.YearServiceSet;
import nts.uk.ctx.at.shared.dom.specialholiday.yearserviceset.repository.YearServiceSetRepository;
import nts.uk.shr.com.context.AppContexts;
@Stateless
public class UpdateYearServiceSetCommandHandler extends CommandHandler<UpdateYearServiceSetCommand>{
	@Inject
	private YearServiceSetRepository yearServiceSetRep;

	@Override
	protected void handle(CommandHandlerContext<UpdateYearServiceSetCommand> context) {
		String companyId = AppContexts.user().companyId();
		Optional<YearServiceSet> yearServiceSetOld = yearServiceSetRep.find(companyId, context.getCommand().getSpecialHolidayCode(), context.getCommand().getYearServiceType());
		if(yearServiceSetOld.isPresent()){
			throw new RuntimeException("対象データがありません。");
		}
		YearServiceSet yearServiceSetNew = YearServiceSet.createFromJavaType(companyId, context.getCommand().getSpecialHolidayCode(), context.getCommand().getYearServiceType(), context.getCommand().getYear(), context.getCommand().getMonth(), context.getCommand().getDate());
		yearServiceSetRep.update(yearServiceSetNew);
	}
}
