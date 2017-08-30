package nts.uk.ctx.at.shared.app.command.specialholiday.yearservicecom;

import java.util.Optional;
import java.util.concurrent.TimeoutException;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.specialholiday.yearservicecom.YearServiceCom;
import nts.uk.ctx.at.shared.dom.specialholiday.yearservicecom.repository.YearServiceComRepository;
import nts.uk.shr.com.context.AppContexts;
/**
 * insert year month date
 * @author yennth
 *
 */
@Stateless
public class InsertYearServiceComCommandHandler extends CommandHandler<InsertYearServiceComCommand>{
	@Inject
	private YearServiceComRepository yearServiceComRep;

	@Override
	protected void handle(CommandHandlerContext<InsertYearServiceComCommand> context) {
		String companyId = AppContexts.user().companyId();
		YearServiceCom yearServiceCom = YearServiceCom.createFromJavaType(companyId, context.getCommand().getSpecialHolidayCode(), context.getCommand().getLengthServiceYearAtr());
		Optional<YearServiceCom> yearServiceComOld = yearServiceComRep.find(companyId, context.getCommand().getSpecialHolidayCode());
		if(yearServiceComOld.isPresent()){
			try{
				throw new TimeoutException();
			}catch(TimeoutException e){
			}
		}
		yearServiceComRep.insert(yearServiceCom);
	}
}
