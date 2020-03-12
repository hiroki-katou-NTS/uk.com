package nts.uk.ctx.at.schedule.app.command.budget.premium.language;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.schedule.dom.budget.premium.PremiumName;
import nts.uk.ctx.at.schedule.dom.budget.premium.language.PremiumItemLanguage;
import nts.uk.ctx.at.schedule.dom.budget.premium.language.PremiumItemLanguageRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class InsertPremiumItemLanguageCommandHandler extends CommandHandler<ListPremiumItemLanguageCommand> {

	@Inject
	private PremiumItemLanguageRepository repo;

	@Override
	protected void handle(CommandHandlerContext<ListPremiumItemLanguageCommand> context) {
		String companyId = AppContexts.user().companyId();
		ListPremiumItemLanguageCommand command = context.getCommand();
		for(InsertPremiumItemLanguageCommand item : command.getListData()) {
			Optional<PremiumItemLanguage> data = repo.findById(companyId, item.getDisplayNumber(), item.getLangID());
			PremiumItemLanguage premiumItemLanguage = new PremiumItemLanguage(item.getCompanyID(),
					item.getDisplayNumber(), item.getLangID(),
					(item.getName() == null || item.getName().equals("")) ? null
							: new PremiumName(item.getName()));
			if (data.isPresent()) {
				repo.update(premiumItemLanguage);
			} else {
				repo.add(premiumItemLanguage);
			}
		}
		

	}

}
