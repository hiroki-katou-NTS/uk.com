package nts.uk.ctx.at.shared.app.command.specialholiday.yearserviceset;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.specialholiday.yearserviceset.YearServiceSet;
import nts.uk.ctx.at.shared.dom.specialholiday.yearserviceset.repository.YearServiceComRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class InsertYearServiceSetCommandHandler extends CommandHandler<InsertYearServiceSetCommand>{
	@Inject
	private YearServiceComRepository yearServiceSetRep;

	@Override
	protected void handle(CommandHandlerContext<InsertYearServiceSetCommand> context) {
		
		List<YearServiceSet> lst = new ArrayList<>();
		InsertYearServiceSetCommand insert = context.getCommand();
		String companyId = AppContexts.user().companyId();
		List<YearServiceSetCommand> yearServiceSets = insert.getYearServiceSets();
//		for(YearServiceSetCommand obj : yearServiceSets){
//			lst.add()
//		}
		for(YearServiceSetCommand obj : yearServiceSets){
			YearServiceSet o = YearServiceSet.update(companyId, obj.getSpecialHolidayCode(), obj.getYearServiceNo(), obj.getYear(), obj.getMonth(), obj.getDate());
			lst.add(o);
		}
		yearServiceSetRep.insertSet(lst);
	}
}
