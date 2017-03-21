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
import nts.uk.ctx.pr.report.dom.payment.comparing.FormName;
import nts.uk.shr.com.context.AppContexts;

@RequestScoped
@Transactional
public class UpdateComparingFormHeaderCommandHandler extends CommandHandler<UpdateComparingFormHeaderCommand> {

	@Inject
	private ComparingFormHeaderRepository comparingFormHeaderRepository;

	@Override
	protected void handle(CommandHandlerContext<UpdateComparingFormHeaderCommand> context) {
		String companyCode = AppContexts.user().companyCode();
		UpdateComparingFormHeaderCommand updateCommand = context.getCommand();
		
		Optional<ComparingFormHeader> comparingFormHeader = this.comparingFormHeaderRepository
				.getComparingFormHeader(companyCode, updateCommand.getFormCode());

		if (!comparingFormHeader.isPresent()) {
			throw new BusinessException("ER010");
		}

		ComparingFormHeader newComparingFormHeader = comparingFormHeader.get();
		newComparingFormHeader.setFormName(new FormName(updateCommand.getFormName()));
		
		this.comparingFormHeaderRepository.updateComparingFormHeader(newComparingFormHeader);
	}

}
