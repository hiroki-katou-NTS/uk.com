package nts.uk.ctx.at.shared.app.command.specialholiday.yearservicecom;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.specialholiday.yearservicecom.YearServiceCom;
import nts.uk.ctx.at.shared.dom.specialholiday.yearservicecom.repository.YearServiceComRepository;
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
		Optional<YearServiceCom> yearServiceComOld = yearServiceComRep.find(companyId, context.getCommand().getSpecialHolidayCode());
		if(!yearServiceComOld.isPresent()){
			throw new RuntimeException("対象データがありません。");
		}
		YearServiceCom yearServiceComNew = YearServiceCom.createFromJavaType(companyId, context.getCommand().getSpecialHolidayCode(), context.getCommand().getLengthServiceYearAtr());
		yearServiceComRep.update(yearServiceComNew);
	}
}
