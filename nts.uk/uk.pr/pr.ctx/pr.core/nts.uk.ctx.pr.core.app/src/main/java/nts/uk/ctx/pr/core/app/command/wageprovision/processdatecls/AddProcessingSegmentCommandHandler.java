package nts.uk.ctx.pr.core.app.command.wageprovision.processdatecls;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.*;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.time.Year;

@Stateless
@Transactional
public class AddProcessingSegmentCommandHandler extends CommandHandler<ProcessingSegmentCommand> {

    @Inject
    ValPayDateSetRepository valPayDateSetRepository;

    @Inject
    ProcessInformationRepository processInformationRepository;

    @Inject
    SpecPrintYmSetRepository specPrintYmSetRepository;

    @Inject
    SetDaySupportRepository setDaySupportRepository;

    @Inject
    CurrProcessDateRepository currProcessDateRepository;

    @Inject
    EmpTiedProYearRepository empTiedProYearRepository;


    @Override
    protected void handle(CommandHandlerContext<ProcessingSegmentCommand> commandHandlerContext) {
        ProcessingSegmentCommand addCommand = commandHandlerContext.getCommand();

        this.valPayDateSetRepository.add(new ValPayDateSet(
                        addCommand.getValPayDateSet().getCid(),
                        addCommand.getValPayDateSet().getProcessCateNo(),
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
                        addCommand.getValPayDateSet().getAdvancedSetting().getCloseDate().getBaseMonth(),
                        addCommand.getValPayDateSet().getAdvancedSetting().getCloseDate().getBaseYear(),
                        addCommand.getValPayDateSet().getAdvancedSetting().getCloseDate().getRefeDate()
                )
        );


        this.processInformationRepository.add(new ProcessInformation(
                        addCommand.getProcessInformation().getCid(),
                        addCommand.getProcessInformation().getProcessCateNo(),
                        addCommand.getProcessInformation().getDeprecatCate(),
                        addCommand.getProcessInformation().getProcessDivisionName()
                )
        );

        addSpecPrintYmSet(addCommand);





    }


    public void addSpecPrintYmSet(ProcessingSegmentCommand addCommand) {
        String cid = addCommand.getValPayDateSet().getCid();
        int processCateNo = addCommand.getValPayDateSet().getProcessCateNo();
        int currentYear = GeneralDate.today().year();

        int montOption = addCommand.getValPayDateSet().getAdvancedSetting().getDetailPrintingMon().getPrintingMonth();


        if (montOption == PreviousMonthClassification.THIS_MONTH.value) {
            for (int i = 1; i < 13; i++) {
                this.specPrintYmSetRepository.add(new SpecPrintYmSet(cid, processCateNo, currentYear * 100 + i,currentYear * 100 + i));

                this.specPrintYmSetRepository.add(new SpecPrintYmSet(cid,processCateNo,(currentYear + 1) * 100 + i,(currentYear + 1) * 100 + i));

            }
        }


        if (montOption == PreviousMonthClassification.LAST_MONTH.value) {
            this.specPrintYmSetRepository.add(new SpecPrintYmSet(cid, processCateNo,currentYear*100+1, (currentYear - 1) * 100 + 12));
            for (int i = 1; i < 12; i++) {
                this.specPrintYmSetRepository.add(new SpecPrintYmSet(cid, processCateNo, currentYear*100+1+i,currentYear * 100 + i));
            }
            this.specPrintYmSetRepository.add(new SpecPrintYmSet(cid, processCateNo,(currentYear+1)*100+1, (currentYear ) * 100 + 12));
            for (int i = 1; i < 12; i++) {
                this.specPrintYmSetRepository.add(new SpecPrintYmSet(cid, processCateNo,(currentYear+1)*100+i+1, (currentYear + 1) * 100 + i));
            }

        }
    }



    public void addSetDaySupport(ProcessingSegmentCommand addCommand){
        String cid=addCommand.getValPayDateSet().getCid();
        int processCateNo = addCommand.getValPayDateSet().getProcessCateNo();





        //this.setDaySupportRepository.add(new SetDaySupport());
    }

}
