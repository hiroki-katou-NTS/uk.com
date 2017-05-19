package nts.uk.ctx.pr.report.app.payment.comparing.setting.command;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.report.dom.payment.comparing.setting.ComparingPrintSet;
import nts.uk.ctx.pr.report.dom.payment.comparing.setting.ComparingPrintSetRepository;
import nts.uk.shr.com.context.AppContexts;

@RequestScoped
@Transactional
public class InsertUpdatePrintSettingCommandHandler extends CommandHandler<InsertUpdatePrintSettingCommand> {

	@Inject
	private ComparingPrintSetRepository printSetRepository;

	@Override
	protected void handle(CommandHandlerContext<InsertUpdatePrintSettingCommand> context) {
		String companyCode = AppContexts.user().companyCode();
		InsertUpdatePrintSettingCommand insertUpdateCommand = context.getCommand();
		ComparingPrintSet insertPrintSetting = ComparingPrintSet.createFromJavaType(companyCode,
				insertUpdateCommand.getPlushBackColor(), insertUpdateCommand.getMinusBackColor(),
				insertUpdateCommand.getShowItemIfCfWithNull(), insertUpdateCommand.getShowItemIfSameValue(),
				insertUpdateCommand.getShowPayment(), insertUpdateCommand.getTotalSet(),
				insertUpdateCommand.getSumEachDeprtSet(), insertUpdateCommand.getSumDepHrchyIndexSet(),
				insertUpdateCommand.getHrchyIndex1(), insertUpdateCommand.getHrchyIndex2(),
				insertUpdateCommand.getHrchyIndex3(), insertUpdateCommand.getHrchyIndex4(),
				insertUpdateCommand.getHrchyIndex5());
		
		if(this.printSetRepository.getComparingPrintSet(companyCode).isPresent()){
			this.printSetRepository.updateComparingPrintSet(insertPrintSetting);
			return;
		}		
		this.printSetRepository.insertComparingPrintSet(insertPrintSetting);
	}

}
