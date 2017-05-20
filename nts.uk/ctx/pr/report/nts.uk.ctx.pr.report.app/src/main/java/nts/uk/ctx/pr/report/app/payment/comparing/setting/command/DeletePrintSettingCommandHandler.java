package nts.uk.ctx.pr.report.app.payment.comparing.setting.command;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.report.dom.payment.comparing.setting.ComparingPrintSetRepository;
import nts.uk.shr.com.context.AppContexts;

@RequestScoped
@Transactional
public class DeletePrintSettingCommandHandler extends CommandHandler<Object> {

	@Inject
	private ComparingPrintSetRepository printSetRepository;

	@Override
	protected void handle(CommandHandlerContext<Object> context) {
		String companyCode = AppContexts.user().companyCode();
		if (!this.printSetRepository.getComparingPrintSet(companyCode).isPresent()) {
			throw new BusinessException("1");
		}
		this.printSetRepository.deleteComparingPrintSet(companyCode);
	}

}
