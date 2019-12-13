package nts.uk.ctx.at.shared.app.command.scherec.totaltimes.language;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.language.TotalTimesLang;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.language.TotalTimesLangRepository;
import nts.uk.shr.com.context.AppContexts;
/**
 * 
 * @author phongtq
 *
 */
@Stateless
public class InsertTotalTimesLanguageCommandHandler extends CommandHandler<InsertTotalTimesLanguageCommand> {

	@Inject TotalTimesLangRepository repo;
	
	@Override
	protected void handle(CommandHandlerContext<InsertTotalTimesLanguageCommand> context) {
		String companyId = AppContexts.user().companyId();
		InsertTotalTimesLanguageCommand command = context.getCommand();
		
		TotalTimesLang timesLang = command.toDomain(command.getTotalCountNo(), command.getLangId(), command.getTotalTimesNameEn());
		Optional<TotalTimesLang> optional = this.repo.findById(companyId, command.getTotalCountNo(), command.getLangId());
		
		if(optional.isPresent())
			this.repo.update(timesLang);
		else
			this.repo.add(timesLang);
	}
}
