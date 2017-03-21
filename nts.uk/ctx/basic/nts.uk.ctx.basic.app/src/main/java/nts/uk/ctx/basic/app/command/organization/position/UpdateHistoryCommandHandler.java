package nts.uk.ctx.basic.app.command.organization.position;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDate;

import nts.uk.ctx.basic.dom.organization.position.JobHistory;
import nts.uk.ctx.basic.dom.organization.position.PositionRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class UpdateHistoryCommandHandler extends CommandHandler<UpdateHistoryCommand> {

	@Inject
	private PositionRepository positionRepository;

	@Override
	protected void handle(CommandHandlerContext<UpdateHistoryCommand> context) {

		//
		// String companyCode = AppContexts.user().companyCode();
		// String hitoryId = IdentifierUtil.randomUniqueId();
		// JobHistory jobHistory = new
		// JobHistory(companyCode,hitoryId,context.getCommand().getEndDate(),context.getCommand().getStartDate());
		// positionRepository.updateHistory(jobHistory);

		String companyCode = AppContexts.user().companyCode();
		String test = context.getCommand().getCompanyCode().toString();

		
		JobHistory jobHist = new JobHistory(companyCode, context.getCommand().getHistoryId(),
				context.getCommand().getStartDate(), context.getCommand().getEndDate());

		
		GeneralDate pos = context.getCommand().getStartDate();
		GeneralDate endDate = pos.addDays(-1);
		Optional<JobHistory> histEndDate = positionRepository.getHistoryByEdate(companyCode, test);
		if (histEndDate.isPresent()) {
			JobHistory jobHis = histEndDate.get();
			jobHis.setEndDate(endDate);
			positionRepository.updateHistory(jobHis);
			positionRepository.updateHistory(jobHist);

		}

	}

}
