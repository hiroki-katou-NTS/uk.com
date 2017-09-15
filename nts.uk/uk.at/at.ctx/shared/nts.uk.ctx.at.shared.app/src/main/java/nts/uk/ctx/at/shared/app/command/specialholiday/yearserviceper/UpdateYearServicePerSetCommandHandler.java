package nts.uk.ctx.at.shared.app.command.specialholiday.yearserviceper;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.specialholiday.yearserviceper.YearServicePerSet;
import nts.uk.ctx.at.shared.dom.specialholiday.yearserviceper.repository.YearServicePerRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class UpdateYearServicePerSetCommandHandler extends CommandHandler<UpdateYearServicePerSetCommand>{
	@Inject
	private YearServicePerRepository yearServicePerRep;

	@Override
	protected void handle(CommandHandlerContext<UpdateYearServicePerSetCommand> context) {
		List<YearServicePerSet> lstUpdate = new ArrayList<>();
		List<YearServicePerSet> lstInsert = new ArrayList<>();
		UpdateYearServicePerSetCommand update = context.getCommand();
		String companyId = AppContexts.user().companyId();
		List<YearServicePerSetCommand> yearServicePerSets = update.getYearServicePerSets();
		
		List<YearServicePerSet> lst = yearServicePerRep.findAllPerSet(companyId);
		for(YearServicePerSet object : lst){
			for(YearServicePerSetCommand obj : yearServicePerSets){
				if(obj.getYear() == object.getYear()){
					YearServicePerSet o = YearServicePerSet.createFromJavaType(companyId, obj.getSpecialHolidayCode(), obj.getYearServiceCode(), obj.getYearServiceNo(), obj.getYear(), obj.getMonth(), obj.getDate());
					lstUpdate.add(o);
				}
				else{
					YearServicePerSet a = YearServicePerSet.createFromJavaType(companyId, obj.getSpecialHolidayCode(), obj.getYearServiceCode(), obj.getYearServiceNo(), obj.getYear(), obj.getMonth(), obj.getDate());
					lstInsert.add(a);
				}
			}
		}
		yearServicePerRep.updatePerSet(lstUpdate);
		yearServicePerRep.insertPerSet(lstInsert);
	}
}
