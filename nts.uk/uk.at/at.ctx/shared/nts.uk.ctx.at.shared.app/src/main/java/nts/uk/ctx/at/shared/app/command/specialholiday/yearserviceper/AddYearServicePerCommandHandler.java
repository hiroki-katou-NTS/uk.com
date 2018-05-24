package nts.uk.ctx.at.shared.app.command.specialholiday.yearserviceper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
/**
 * add yeare service per item
 * @author yennth
 *
 */
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.at.shared.dom.specialholiday.yearservice.yearserviceper.YearServicePer;
import nts.uk.ctx.at.shared.dom.specialholiday.yearservice.yearserviceper.YearServicePerSet;
import nts.uk.ctx.at.shared.dom.specialholiday.yearservice.yearserviceper.repository.YearServicePerRepository;
import nts.uk.shr.com.context.AppContexts;
@Stateless
public class AddYearServicePerCommandHandler extends CommandHandlerWithResult<AddYearServicePerCommand, List<String>>{
	@Inject
	private YearServicePerRepository yearServicePerRep;

	@Override
	protected List<String> handle(CommandHandlerContext<AddYearServicePerCommand> context) {
		String companyId = AppContexts.user().companyId();
		List<String> errors = new ArrayList<>();
		List<YearServicePerSet> yearServicePerSets = null;
		if(context.getCommand().getYearServicePerSets() != null){
			yearServicePerSets = context.getCommand().getYearServicePerSets()
					.stream()
					.map(x->x.toDomainPerSet(companyId, context.getCommand().getSpecialHolidayCode(), context.getCommand().getYearServiceCode(), x.getYearServiceNo()))
					.collect(Collectors.toList());
			errors = YearServicePerSet.validateInput(yearServicePerSets);
		}
		if(!errors.isEmpty()){
			return errors;
		}
		YearServicePer yearServicePer = YearServicePer.createFromJavaType(companyId, 
																			context.getCommand().getSpecialHolidayCode(), 
																			context.getCommand().getYearServiceCode(), 
																			context.getCommand().getYearServiceName(), 
																			context.getCommand().getProvision(), 
																			context.getCommand().getYearServiceCls(), 
																			yearServicePerSets);
		Optional<YearServicePer> yearServicePerOld = yearServicePerRep.findPer(companyId, 
																				context.getCommand().getSpecialHolidayCode(), 
																				context.getCommand().getYearServiceCode());
		if(yearServicePerOld.isPresent()){
			throw new BusinessException("Msg_3");
		}
		
		if(yearServicePer.getProvision() == 1) {
			yearServicePerRep.changeAllProvision(context.getCommand().getSpecialHolidayCode());
		}
		
		yearServicePerRep.insertPer(yearServicePer);
		
		return errors;
	}
	
}
