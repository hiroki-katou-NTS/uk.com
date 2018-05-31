package nts.uk.ctx.at.function.app.command.dailyperformanceformat;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.primitivevalue.DailyPerformanceFormatCode;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.repository.AuthorityDailyPerformanceFormatRepository;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.repository.AuthorityFormatDailyRepository;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.repository.AuthorityFormatInitialDisplayRepository;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.repository.AuthorityFormatMonthlyRepository;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.repository.AuthorityFormatSheetRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

@Stateless
public class RemoveAuthorityCommandHandler extends CommandHandler<RemoveAuthorityCommand> {

	@Inject
	private AuthorityFormatDailyRepository authorityFormatDailyRepository;

	@Inject
	private AuthorityFormatSheetRepository authorityFormatSheetRepository;

	@Inject
	private AuthorityFormatMonthlyRepository authorityFormatMonthlyRepository;

	@Inject
	private AuthorityFormatInitialDisplayRepository authorityFormatInitialDisplayRepository;

	@Inject
	private AuthorityDailyPerformanceFormatRepository authorityDailyPerformanceFormatRepository;

	@Override
	protected void handle(CommandHandlerContext<RemoveAuthorityCommand> context) {

		LoginUserContext login = AppContexts.user();
		String companyId = login.companyId();

		RemoveAuthorityCommand command = context.getCommand();

		this.authorityFormatDailyRepository.remove(companyId,
				new DailyPerformanceFormatCode(command.getDailyPerformanceFormatCode()));

		this.authorityFormatSheetRepository.remove(companyId,
				new DailyPerformanceFormatCode(command.getDailyPerformanceFormatCode()));

		this.authorityFormatMonthlyRepository.remove(companyId,
				new DailyPerformanceFormatCode(command.getDailyPerformanceFormatCode()));
		
		if(this.authorityFormatInitialDisplayRepository.checkExistDataByCompanyId(companyId)) {
			this.authorityFormatInitialDisplayRepository.remove(companyId,
					new DailyPerformanceFormatCode(command.getDailyPerformanceFormatCode()));
		}
		this.authorityDailyPerformanceFormatRepository.remove(companyId,
				new DailyPerformanceFormatCode(command.getDailyPerformanceFormatCode()));
	}

}
