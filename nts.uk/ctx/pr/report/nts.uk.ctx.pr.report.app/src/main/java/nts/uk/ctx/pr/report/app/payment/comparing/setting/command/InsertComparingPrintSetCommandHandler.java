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
public class InsertComparingPrintSetCommandHandler extends CommandHandler<InsertComparingPrintSetCommand> {

	@Inject
	private ComparingPrintSetRepository printSetRepository;

	@Override
	protected void handle(CommandHandlerContext<InsertComparingPrintSetCommand> context) {
		String companyCode = AppContexts.user().companyCode();
		InsertComparingPrintSetCommand insertCommand = context.getCommand();
		this.printSetRepository.getComparingPrintSet(companyCode).ifPresent(s -> {
			throw new BusinessException("1");
		});
		ComparingPrintSet insertPrintSet = ComparingPrintSet.createFromJavaType(companyCode,
				insertCommand.getPlushBackColor(), insertCommand.getMinusBackColor(),
				insertCommand.getShowItemIfCfWithNull(), insertCommand.getShowItemIfSameValue(),
				insertCommand.getShowPayment(), insertCommand.getTotalSet(), insertCommand.getSumEachDeprtSet(),
				insertCommand.getSumDepHrchyIndexSet(), insertCommand.getHrchyIndex1(), insertCommand.getHrchyIndex2(),
				insertCommand.getHrchyIndex3(), insertCommand.getHrchyIndex4(), insertCommand.getHrchyIndex5());

		this.printSetRepository.insertComparingPrintSet(insertPrintSet);

	}

}
