package nts.uk.ctx.pr.report.app.payment.comparing.command;

import java.util.Optional;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.report.dom.payment.comparing.ComparingFormHeader;
import nts.uk.ctx.pr.report.dom.payment.comparing.ComparingFormHeaderRepository;
import nts.uk.shr.com.context.AppContexts;

@RequestScoped
@Transactional
public class DeleteInsertComparingFormHeaderCommandHandler extends CommandHandler<DeleteComparingFormHeaderCommand> {

	@Inject
	private ComparingFormHeaderRepository comparingFormHeaderRepository;

	@Override
	protected void handle(CommandHandlerContext<DeleteComparingFormHeaderCommand> context) {
		String companyCode = AppContexts.user().companyCode();
		DeleteComparingFormHeaderCommand deleteCommand = context.getCommand();

		if (StringUtils.isBlank(deleteCommand.getFormCode())) {
			throw new BusinessException("ER001");
		}

		Optional<ComparingFormHeader> comparingFormHeader = this.comparingFormHeaderRepository
				.getComparingFormHeader(companyCode, deleteCommand.getFormCode());
		
		if (!comparingFormHeader.isPresent()) {
			throw new BusinessException("ER010");
		}
		this.comparingFormHeaderRepository.DeleteComparingFormHeader(companyCode, deleteCommand.getFormCode());
	}

}
