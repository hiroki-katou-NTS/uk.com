package nts.uk.ctx.at.shared.app.command.specialholiday.yearservicecom;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.at.shared.dom.specialholiday.yearservice.yearservicecom.YearServiceCom;
import nts.uk.ctx.at.shared.dom.specialholiday.yearservice.yearserviceset.YearServiceSet;
import nts.uk.ctx.at.shared.dom.specialholiday.yearservice.yearserviceset.repository.YearServiceComRepository;
import nts.uk.shr.com.context.AppContexts;
/**
 * update length Service Year Atr
 * @author yennth
 *
 */	
@Stateless
public class UpdateYearServiceComCommandHandler extends CommandHandlerWithResult<UpdateYearServiceComCommand, List<String>>{
	@Inject
	private YearServiceComRepository yearServiceComRep;
	@Override
	protected List<String> handle(CommandHandlerContext<UpdateYearServiceComCommand> context) {
		String companyId = AppContexts.user().companyId();
		Optional<YearServiceCom> yearServiceComOld = yearServiceComRep.findCom(companyId, context.getCommand().getSpecialHolidayCode());
		
		List<String> errors = new ArrayList<>();
		List<YearServiceSet> yearServiceSets = null;
		if (context.getCommand().getYearServiceSets() != null) {
			
			yearServiceSets = context.getCommand().getYearServiceSets().stream()
					.map(x -> x.toDomain(context.getCommand().getSpecialHolidayCode(), companyId))
					.collect(Collectors.toList());
			errors = YearServiceSet.validateInput(yearServiceSets);
		}
		if(!errors.isEmpty()){
			return errors;
		}
		YearServiceCom yearServiceComNew = YearServiceCom.createFromJavaType(
				companyId, 
				context.getCommand().getSpecialHolidayCode(), 
				context.getCommand().getLengthServiceYearAtr(),
				yearServiceSets
				);
		
		yearServiceComNew.validate();
		
		if(!yearServiceComOld.isPresent()){
			yearServiceComRep.insertCom(yearServiceComNew);
		} else {
			yearServiceComRep.updateCom(yearServiceComNew);
		}
		
		return errors;
	}
}
