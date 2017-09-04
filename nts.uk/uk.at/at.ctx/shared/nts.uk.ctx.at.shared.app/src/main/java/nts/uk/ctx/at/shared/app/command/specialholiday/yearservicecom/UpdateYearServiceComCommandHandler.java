package nts.uk.ctx.at.shared.app.command.specialholiday.yearservicecom;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.specialholiday.yearservicecom.YearServiceCom;
import nts.uk.ctx.at.shared.dom.specialholiday.yearserviceset.repository.YearServiceComRepository;
import nts.uk.shr.com.context.AppContexts;
/**
 * update length Service Year Atr
 * @author yennth
 *
 */
@Stateless
public class UpdateYearServiceComCommandHandler extends CommandHandler<UpdateYearServiceComCommand>{
	@Inject
	private YearServiceComRepository yearServiceComRep;
	@Override
	protected void handle(CommandHandlerContext<UpdateYearServiceComCommand> context) {
		String companyId = AppContexts.user().companyId();
		Optional<YearServiceCom> yearServiceComOld = yearServiceComRep.findCom(companyId, context.getCommand().getSpecialHolidayCode());
		YearServiceCom yearServiceComNew = YearServiceCom.createFromJavaType(companyId, context.getCommand().getSpecialHolidayCode(), context.getCommand().getLengthServiceYearAtr(),context.getCommand().getYearServiceSets());
		if(!yearServiceComOld.isPresent()){
			yearServiceComRep.insertCom(yearServiceComNew);
			return;
		}
		yearServiceComRep.updateCom(yearServiceComNew);
	}
}
