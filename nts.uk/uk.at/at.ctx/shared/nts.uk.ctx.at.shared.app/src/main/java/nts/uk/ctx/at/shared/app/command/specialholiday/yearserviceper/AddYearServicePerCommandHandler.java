package nts.uk.ctx.at.shared.app.command.specialholiday.yearserviceper;

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
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.specialholiday.yearserviceper.YearServicePer;
import nts.uk.ctx.at.shared.dom.specialholiday.yearserviceper.YearServicePerSet;
import nts.uk.ctx.at.shared.dom.specialholiday.yearserviceper.repository.YearServicePerRepository;
import nts.uk.shr.com.context.AppContexts;
@Stateless
public class AddYearServicePerCommandHandler extends CommandHandler<AddYearServicePerCommand>{
	@Inject
	private YearServicePerRepository yearServicePerRep;

	@Override
	protected void handle(CommandHandlerContext<AddYearServicePerCommand> context) {
		String companyId = AppContexts.user().companyId();
		List<YearServicePerSet> yearServicePerSets = null;
		if(context.getCommand().getYearServicePerSets() != null){
			yearServicePerSets = context.getCommand().getYearServicePerSets()
					.stream()
					.map(x->x.toDomainPerSet(companyId, context.getCommand().getSpecialHolidayCode(), context.getCommand().getYearServiceCode(), x.getYearServiceNo()))
					.collect(Collectors.toList());
			YearServicePerSet.validateInput(yearServicePerSets);
		}
		YearServicePer yearServicePer = YearServicePer.createFromJavaType(companyId, 
																			context.getCommand().getSpecialHolidayCode(), 
																			context.getCommand().getYearServiceCode(), 
																			context.getCommand().getYearServiceName(), 
																			context.getCommand().getYearServiceCls(), 
																			yearServicePerSets);
		Optional<YearServicePer> yearServicePerOld = yearServicePerRep.findPer(companyId, 
																				context.getCommand().getSpecialHolidayCode(), 
																				context.getCommand().getYearServiceCode());
		if(yearServicePerOld.isPresent()){
			throw new BusinessException("Msg_3");
		}
		yearServicePerRep.insertPer(yearServicePer);
	}
	
}
