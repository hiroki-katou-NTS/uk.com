//package nts.uk.ctx.at.shared.app.command.specialholiday.yearserviceset;
//
//import java.util.ArrayList;
//import java.util.List;
//import javax.ejb.Stateless;
//import javax.inject.Inject;
//
//import nts.arc.layer.app.command.CommandHandler;
//import nts.arc.layer.app.command.CommandHandlerContext;
//import nts.uk.ctx.at.shared.dom.specialholiday.yearserviceset.YearServiceSet;
//import nts.uk.ctx.at.shared.dom.specialholiday.yearserviceset.repository.YearServiceComRepository;
//import nts.uk.shr.com.context.AppContexts;
//@Stateless
//public class UpdateYearServiceSetCommandHandler extends CommandHandler<UpdateYearServiceSetCommand>{
//	@Inject
//	private YearServiceComRepository yearServiceSetRep;
//
//	@Override
//	protected void handle(CommandHandlerContext<UpdateYearServiceSetCommand> context) {
////		String companyId = AppContexts.user().companyId();
////		Optional<YearServiceSet> yearServiceSetOld = yearServiceSetRep.findSet(companyId, context.getCommand().getYearServiceSets().get(0).getSpecialHolidayCode(), yearServiceType);
////		if(yearServiceSetOld.isPresent()){
////			throw new RuntimeException("対象データがありません。");
////		}
////		YearServiceSet yearServiceSetNew = YearServiceSet.createFromJavaType(companyId, context.getCommand().getSpecialHolidayCode(), context.getCommand().getYearServiceType(), context.getCommand().getYear(), context.getCommand().getMonth(), context.getCommand().getDate());
////		yearServiceSetRep.update(yearServiceSetNew);
//		
//		List<YearServiceSet> lstUpdate = new ArrayList<>();
//		List<YearServiceSet> lstInsert = new ArrayList<>();
//		
//		
//		UpdateYearServiceSetCommand update = context.getCommand();
//		String companyId = AppContexts.user().companyId();
//		List<YearServiceSetCommand> yearServiceSets = update.getYearServiceSets();
//		
//		List<YearServiceSet> lst = yearServiceSetRep.findAllSet(companyId);
//		
//		for(YearServiceSet object : lst){
//			for(YearServiceSetCommand obj : yearServiceSets){
//				if(obj.getYear() == object.getYear()){
//					YearServiceSet o = YearServiceSet.update(companyId, obj.getSpecialHolidayCode(), obj.getYearServiceNo(), obj.getYear(), obj.getMonth(), obj.getDate());
//					lstUpdate.add(o);
//				}
//				else{
//					YearServiceSet a = YearServiceSet.update(companyId, obj.getSpecialHolidayCode(), obj.getYearServiceNo(), obj.getYear(), obj.getMonth(), obj.getDate());
//					lstInsert.add(a);
//				}
//			}
//		}
////		yearServiceSetRep.updateSet(lst);
////		for(YearServiceSetCommand obj : yearServiceSets){
////			YearServiceSet o = YearServiceSet.update(companyId, obj.getSpecialHolidayCode(), obj.getYearServiceType(), obj.getYear(), obj.getMonth(), obj.getDate());
////			lst.add(o);
////		}
//		yearServiceSetRep.updateSet(lstUpdate);
//		yearServiceSetRep.insertSet(lstInsert);
//	}
//}
