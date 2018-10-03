package nts.uk.ctx.pr.core.app.command.wageprovision.processdatecls;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.RegisterProcessing;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.ProcessInformation;

import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.ValPayDateSet;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

@Stateless
@Transactional
public class AddProcessingSegmentCommandHandler extends CommandHandler<ProcessingSegmentCommand> {

    @Inject
    private RegisterProcessing registrationProcessing;

    @Override
    protected void handle(CommandHandlerContext<ProcessingSegmentCommand> commandHandlerContext) {
        String cid = AppContexts.user().companyId();
        ProcessingSegmentCommand addCommand = commandHandlerContext.getCommand();

        ProcessInformation processInformation = new ProcessInformation(cid, addCommand.getProcessInformation().getProcessCateNo(),
                addCommand.getProcessInformation().getDeprecatCate(),
                addCommand.getProcessInformation().getProcessDivisionName());

        ValPayDateSet valPayDateSet = new ValPayDateSet(cid, addCommand.getValPayDateSet().getProcessCateNo(),
                addCommand.getValPayDateSet().getBasicSetting().getAccountingClosureDate().getProcessMonth(),
                addCommand.getValPayDateSet().getBasicSetting().getAccountingClosureDate().getDisposalDay(),
                addCommand.getValPayDateSet().getBasicSetting().getEmployeeExtractionReferenceDate().getRefeMonth(),
                addCommand.getValPayDateSet().getBasicSetting().getEmployeeExtractionReferenceDate().getRefeDate(),
                addCommand.getValPayDateSet().getBasicSetting().getMonthlyPaymentDate().getDatePayMent(),
                addCommand.getValPayDateSet().getBasicSetting().getWorkDay(),
                addCommand.getValPayDateSet().getAdvancedSetting().getDetailPrintingMon().getPrintingMonth(),
                addCommand.getValPayDateSet().getAdvancedSetting().getSalaryInsuColMon().getMonthCollected(),
                addCommand.getValPayDateSet().getAdvancedSetting().getEmpInsurStanDate().getRefeDate(),
                addCommand.getValPayDateSet().getAdvancedSetting().getEmpInsurStanDate().getBaseMonth(),
                addCommand.getValPayDateSet().getAdvancedSetting().getIncomTaxBaseYear().getRefeDate(),
                addCommand.getValPayDateSet().getAdvancedSetting().getIncomTaxBaseYear().getBaseYear(),
                addCommand.getValPayDateSet().getAdvancedSetting().getIncomTaxBaseYear().getBaseMonth(),
                addCommand.getValPayDateSet().getAdvancedSetting().getSociInsuStanDate().getBaseMonth(),
                addCommand.getValPayDateSet().getAdvancedSetting().getSociInsuStanDate().getBaseYear(),
                addCommand.getValPayDateSet().getAdvancedSetting().getSociInsuStanDate().getRefeDate(),
                addCommand.getValPayDateSet().getAdvancedSetting().getCloseDate().getTimeCloseDate(),
                addCommand.getValPayDateSet().getAdvancedSetting().getCloseDate().getTimeCloseDate()==1 ? addCommand.getValPayDateSet().getAdvancedSetting().getCloseDate().getBaseMonth() : null,
                addCommand.getValPayDateSet().getAdvancedSetting().getCloseDate().getTimeCloseDate()==1 ? addCommand.getValPayDateSet().getAdvancedSetting().getCloseDate().getBaseYear(): null,
                addCommand.getValPayDateSet().getAdvancedSetting().getCloseDate().getTimeCloseDate()==1 ? addCommand.getValPayDateSet().getAdvancedSetting().getCloseDate().getRefeDate(): null
        );

        registrationProcessing.registerProcessInformation(processInformation);
        registrationProcessing.registerValPayDateSet(valPayDateSet);
        registrationProcessing.registerSpecPrintYmSet(valPayDateSet);
        registrationProcessing.registerSetDaySupport(valPayDateSet);

    }
}
