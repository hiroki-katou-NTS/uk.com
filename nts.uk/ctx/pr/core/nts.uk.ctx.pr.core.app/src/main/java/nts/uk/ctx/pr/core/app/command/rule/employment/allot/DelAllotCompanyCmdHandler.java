package nts.uk.ctx.pr.core.app.command.rule.employment.allot;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.core.dom.rule.employment.layout.allot.CompanyAllotSetting;
import nts.uk.ctx.pr.core.dom.rule.employment.layout.allot.CompanyAllotSettingRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class DelAllotCompanyCmdHandler extends CommandHandler<DelAllotCompanyCmd> {
	@Inject
	private CompanyAllotSettingRepository companyRepo;

	@Override
	protected void handle(CommandHandlerContext<DelAllotCompanyCmd> context) {
		DelAllotCompanyCmd command = context.getCommand();
		String companyCode = AppContexts.user().companyCode();

		// delete this record
		this.companyRepo.remove(command.getHistoryId());

		// update previous record
		YearMonth endDate = new YearMonth(command.getStartDate()).previousMonth();
		Optional<CompanyAllotSetting> entity = companyRepo.getPreviousHistory(companyCode, endDate.v());

		if (entity.isPresent()) {
			CompanyAllotSetting _entity = entity.get();
			_entity.setEndDate(new YearMonth(999912));
			companyRepo.update(_entity);
		}
	}

}
