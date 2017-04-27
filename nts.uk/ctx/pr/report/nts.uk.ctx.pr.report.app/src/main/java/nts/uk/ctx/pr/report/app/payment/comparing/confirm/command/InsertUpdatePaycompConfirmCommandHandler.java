package nts.uk.ctx.pr.report.app.payment.comparing.confirm.command;

import java.util.List;
import java.util.Optional;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.report.dom.payment.comparing.confirm.ComfirmDifferentRepository;
import nts.uk.ctx.pr.report.dom.payment.comparing.confirm.DetailDifferential;
import nts.uk.ctx.pr.report.dom.payment.comparing.confirm.PaycompConfirm;
import nts.uk.shr.com.context.AppContexts;

@RequestScoped
@Transactional
public class InsertUpdatePaycompConfirmCommandHandler extends CommandHandler<InsertUpdatePaycompConfirmCommand> {

	@Inject
	private ComfirmDifferentRepository comfirmDiffRepository;

	@Override
	protected void handle(CommandHandlerContext<InsertUpdatePaycompConfirmCommand> context) {
		String companyCode = AppContexts.user().companyCode();
		InsertUpdatePaycompConfirmCommand insertUpdateCommand = context.getCommand();
		List<PaycompConfirm> paycompConfirm = this.comfirmDiffRepository.getPayCompComfirm(companyCode,
				insertUpdateCommand.getPersionIDs(), insertUpdateCommand.getProcessingYMEarlier(),
				insertUpdateCommand.getProcessingYMLater());

		insertUpdateCommand.getLstInsertUpdatedata().stream().map(c -> {
			Optional<PaycompConfirm> paycompConfirmFilter = paycompConfirm.stream().filter(
					p -> p.getCategoryAtr().value == c.getCategoryAtr() && p.getItemCode().v().equals(c.getItemCode()))
					.findFirst();
			if(paycompConfirmFilter.isPresent()){
				paycompConfirmFilter.get();
			}
			return null;
		});

	}
}
