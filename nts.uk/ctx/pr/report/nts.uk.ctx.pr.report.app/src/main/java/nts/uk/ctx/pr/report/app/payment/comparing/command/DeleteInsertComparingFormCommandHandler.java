package nts.uk.ctx.pr.report.app.payment.comparing.command;

import java.util.Optional;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.report.dom.payment.comparing.ComparingFormHeader;
import nts.uk.ctx.pr.report.dom.payment.comparing.ComparingFormHeaderRepository;
import nts.uk.shr.com.context.AppContexts;

@RequestScoped
@Transactional
public class DeleteInsertComparingFormCommandHandler extends CommandHandler<DeleteComparingFormCommand> {

	@Inject
	private ComparingFormHeaderRepository comparingFormHeaderRepository;

	@Override
	protected void handle(CommandHandlerContext<DeleteComparingFormCommand> context) {
		String companyCode = AppContexts.user().companyCode();
		DeleteComparingFormCommand deleteCommand = context.getCommand();
		
		Optional<ComparingFormHeader> comparingFormHeader = this.comparingFormHeaderRepository
				.getComparingFormHeader(companyCode, deleteCommand.getFormCode());
		
		if (!comparingFormHeader.isPresent()) {
			throw new BusinessException("ER010");
		}
		this.comparingFormHeaderRepository.deleteComparingFormHeader(companyCode, deleteCommand.getFormCode());
	}

}
