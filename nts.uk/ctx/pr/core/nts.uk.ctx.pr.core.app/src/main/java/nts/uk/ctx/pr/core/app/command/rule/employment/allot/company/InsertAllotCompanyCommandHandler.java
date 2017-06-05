package nts.uk.ctx.pr.core.app.command.rule.employment.allot.company;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.YearMonth;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.pr.core.dom.rule.employment.layout.allot.CompanyAllotSetting;
import nts.uk.ctx.pr.core.dom.rule.employment.layout.allot.CompanyAllotSettingRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class InsertAllotCompanyCommandHandler extends CommandHandler<InsertAllotCompanyCommand> {
	@Inject
	private CompanyAllotSettingRepository companyRepo;

	@Override
	protected void handle(CommandHandlerContext<InsertAllotCompanyCommand> context) {
		String companyCode = AppContexts.user().companyCode();
		InsertAllotCompanyCommand command = context.getCommand();
		

		// Find hist Item with max date
		Optional<CompanyAllotSetting> update = companyRepo.findMax(companyCode, 999912);
		// Update lastest model
		if (update.isPresent()) {
			CompanyAllotSetting _update = update.get();
			{
				// Set enDate to previous month
				_update.setEndDate(new YearMonth(command.getStartDate()).previousMonth());
				
				// Update to database
				companyRepo.update(_update);
			}
		}

		// Create new entity from command
		CompanyAllotSetting model = command.toDomain(IdentifierUtil.randomUniqueId());

		// Set endate to lastest value
		model.setEndDate(new YearMonth(999912));

		// Insert to database
		companyRepo.insert(model);
	}
}
