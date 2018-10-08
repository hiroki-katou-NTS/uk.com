package nts.uk.ctx.pr.core.app.command.wageprovision.processdatecls;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.ValPayDateSet;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.ValPayDateSetRepository;

@Stateless
@Transactional
public class AddValPayDateSetCommandHandler extends CommandHandler<ValPayDateSetCommand> {

	@Inject
	private ValPayDateSetRepository repository;


	@Override
	protected void handle(CommandHandlerContext<ValPayDateSetCommand> context) {
		ValPayDateSetCommand addCommand = context.getCommand();

		repository.add(new ValPayDateSet(
				addCommand.getCid(),
				addCommand.getProcessCateNo(),
				addCommand.getBasicSetting().getAccountingClosureDate().getProcessMonth(),
				addCommand.getBasicSetting().getAccountingClosureDate().getDisposalDay(),
				addCommand.getBasicSetting().getEmployeeExtractionReferenceDate().getRefeMonth(),
				addCommand.getBasicSetting().getEmployeeExtractionReferenceDate().getRefeDate(),
				addCommand.getBasicSetting().getMonthlyPaymentDate().getDatePayMent(),
				addCommand.getBasicSetting().getWorkDay(),
				addCommand.getAdvancedSetting().getDetailPrintingMon().getPrintingMonth(),
				addCommand.getAdvancedSetting().getSalaryInsuColMon().getMonthCollected(),
				addCommand.getAdvancedSetting().getEmpInsurStanDate().getRefeDate(),
				addCommand.getAdvancedSetting().getEmpInsurStanDate().getBaseMonth(),
				addCommand.getAdvancedSetting().getIncomTaxBaseYear().getRefeDate(),
				addCommand.getAdvancedSetting().getIncomTaxBaseYear().getBaseYear(),
				addCommand.getAdvancedSetting().getIncomTaxBaseYear().getBaseMonth(),
				addCommand.getAdvancedSetting().getSociInsuStanDate().getBaseMonth(),
				addCommand.getAdvancedSetting().getSociInsuStanDate().getBaseYear(),
				addCommand.getAdvancedSetting().getSociInsuStanDate().getRefeDate(),
				addCommand.getAdvancedSetting().getCloseDate().getTimeCloseDate(),
				addCommand.getAdvancedSetting().getCloseDate().getBaseMonth(),
				addCommand.getAdvancedSetting().getCloseDate().getBaseYear(),
				addCommand.getAdvancedSetting().getCloseDate().getRefeDate()
				)
		);

	}
}
