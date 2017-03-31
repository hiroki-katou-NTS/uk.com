package nts.uk.ctx.pr.report.app.payment.comparing.setting.command;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.report.dom.payment.comparing.setting.ComparingPrintSet;
import nts.uk.ctx.pr.report.dom.payment.comparing.setting.ComparingPrintSetRepository;
import nts.uk.shr.com.context.AppContexts;

@RequestScoped
@Transactional
public class UpdateComparingPrintSetCommandHandler extends CommandHandler<UpdateComparingPrintSetCommand> {

	@Inject
	private ComparingPrintSetRepository printSetRepository;

	@Override
	protected void handle(CommandHandlerContext<UpdateComparingPrintSetCommand> context) {
		String companyCode = AppContexts.user().companyCode();
		UpdateComparingPrintSetCommand updateCommand = context.getCommand();
		if (!this.printSetRepository.getComparingPrintSet(companyCode).isPresent()) {
			throw new BusinessException("2");
		}
		ComparingPrintSet updatePrintSet = ComparingPrintSet.createFromJavaType(companyCode,
				updateCommand.getPlushBackColor(), updateCommand.getMinusBackColor(),
				updateCommand.getShowItemIfCfWithNull(), updateCommand.getShowItemIfSameValue(),
				updateCommand.getShowPayment(), updateCommand.getTotalSet(), updateCommand.getSumEachDeprtSet(),
				updateCommand.getSumDepHrchyIndexSet(), updateCommand.getHrchyIndex1(), updateCommand.getHrchyIndex2(),
				updateCommand.getHrchyIndex3(), updateCommand.getHrchyIndex4(), updateCommand.getHrchyIndex5());
		this.printSetRepository.updateComparingPrintSet(updatePrintSet);
	}

}
