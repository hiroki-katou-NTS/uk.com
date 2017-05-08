package nts.uk.ctx.pr.report.app.payment.comparing.settingoutputitem.command;

import java.util.Optional;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.report.dom.payment.comparing.settingoutputitem.ComparingFormDetailRepository;
import nts.uk.ctx.pr.report.dom.payment.comparing.settingoutputitem.ComparingFormHeader;
import nts.uk.ctx.pr.report.dom.payment.comparing.settingoutputitem.ComparingFormHeaderRepository;
import nts.uk.shr.com.context.AppContexts;

@RequestScoped
@Transactional
public class DeleteComparingFormCommandHandler extends CommandHandler<DeleteComparingFormCommand> {

	@Inject
	private ComparingFormHeaderRepository comparingFormHeaderRepository;
	@Inject
	private ComparingFormDetailRepository comparingFormDetailRepository;

	@Override
	protected void handle(CommandHandlerContext<DeleteComparingFormCommand> context) {
		String companyCode = AppContexts.user().companyCode();
		DeleteComparingFormCommand deleteCommand = context.getCommand();

		Optional<ComparingFormHeader> comparingFormHeader = this.comparingFormHeaderRepository
				.getComparingFormHeader(companyCode, deleteCommand.getFormCode());

		if (!comparingFormHeader.isPresent()) {
			throw new BusinessException(new RawErrorMessage("対象データがありません。"));
		}
		
		this.comparingFormHeaderRepository.deleteComparingFormHeader(companyCode, deleteCommand.getFormCode());
		this.comparingFormDetailRepository.deleteComparingFormDetail(companyCode, deleteCommand.getFormCode());
	}

}
