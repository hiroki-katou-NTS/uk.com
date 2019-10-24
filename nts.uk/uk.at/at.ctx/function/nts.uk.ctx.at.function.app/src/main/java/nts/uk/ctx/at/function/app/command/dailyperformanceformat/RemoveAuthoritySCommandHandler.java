package nts.uk.ctx.at.function.app.command.dailyperformanceformat;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.enums.PCSmartPhoneAtt;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.primitivevalue.DailyPerformanceFormatCode;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.repository.AuthorityDailySItemRepository;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.repository.AuthorityDailyPerformanceSFormatRepository;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.repository.AuthorityFormatInitialDisplayRepository;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.repository.AuthorityFormatMonthlySRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * @author anhdt
 * 
 * 
 * 
 */
@Stateless
public class RemoveAuthoritySCommandHandler extends CommandHandler<RemoveAuthorityCommand> {

	@Inject
	private AuthorityDailySItemRepository authDailyItemRepository;
	
	@Inject
	private AuthorityFormatMonthlySRepository authSFormatMonthlyRepository;

	@Inject
	private AuthorityFormatInitialDisplayRepository authorityFormatInitialDisplayRepository;

	@Inject
	private AuthorityDailyPerformanceSFormatRepository authDailyPerMobFormatRepository;

	@Override
	protected void handle(CommandHandlerContext<RemoveAuthorityCommand> context) {

		LoginUserContext login = AppContexts.user();
		String companyId = login.companyId();

		RemoveAuthorityCommand command = context.getCommand();
		
		/*日別実績の会社のフォーマットを削除する（スマホ版）*/
		this.authDailyItemRepository.remove(companyId,
				new DailyPerformanceFormatCode(command.getDailyPerformanceFormatCode()));

		this.authDailyPerMobFormatRepository.remove(companyId,
				new DailyPerformanceFormatCode(command.getDailyPerformanceFormatCode()));
		
		this.authSFormatMonthlyRepository.remove(companyId,
				new DailyPerformanceFormatCode(command.getDailyPerformanceFormatCode()));
		
		/*削除対象のフォーマットが初期表示フォーマットでないかチェックす*/
		if(this.authorityFormatInitialDisplayRepository.checkExistDataByCompanyId(companyId, PCSmartPhoneAtt.SMART_PHONE)) {
			this.authorityFormatInitialDisplayRepository.remove(
					companyId,
					new DailyPerformanceFormatCode(command.getDailyPerformanceFormatCode()),
					PCSmartPhoneAtt.SMART_PHONE
					);
		}
	}

}
