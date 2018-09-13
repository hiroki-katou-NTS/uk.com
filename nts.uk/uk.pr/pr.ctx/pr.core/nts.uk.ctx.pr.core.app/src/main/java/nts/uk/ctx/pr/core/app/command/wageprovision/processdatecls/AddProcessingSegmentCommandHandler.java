package nts.uk.ctx.pr.core.app.command.wageprovision.processdatecls;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.*;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.time.LocalDate;
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
                this.specPrintYmSetRepository.add(new SpecPrintYmSet(cid, processCateNo, currentYear * 100 + i, currentYear * 100 + i));

                this.specPrintYmSetRepository.add(new SpecPrintYmSet(cid, processCateNo, (currentYear + 1) * 100 + i, (currentYear + 1) * 100 + i));

            }
        }


        if (montOption == PreviousMonthClassification.LAST_MONTH.value) {
            this.specPrintYmSetRepository.add(new SpecPrintYmSet(cid, processCateNo, currentYear * 100 + 1, (currentYear - 1) * 100 + 12));
            for (int i = 1; i < 12; i++) {
                this.specPrintYmSetRepository.add(new SpecPrintYmSet(cid, processCateNo, currentYear * 100 + 1 + i, currentYear * 100 + i));
            }
            this.specPrintYmSetRepository.add(new SpecPrintYmSet(cid, processCateNo, (currentYear + 1) * 100 + 1, (currentYear) * 100 + 12));
            for (int i = 1; i < 12; i++) {
                this.specPrintYmSetRepository.add(new SpecPrintYmSet(cid, processCateNo, (currentYear + 1) * 100 + i + 1, (currentYear + 1) * 100 + i));
            }

        }
    }


    public void addSetDaySupport(ProcessingSegmentCommand addCommand) {
        int currentYear = GeneralDate.today().year();
        String cid = addCommand.getValPayDateSet().getCid();
        int processCateNo = addCommand.getValPayDateSet().getProcessCateNo();
        //basic
        int payMentDate = addCommand.getValPayDateSet().getBasicSetting().getMonthlyPaymentDate().getDatePayMent();
        int refeDate=addCommand.getValPayDateSet().getBasicSetting().getEmployeeExtractionReferenceDate().getRefeDate();
        int refeMonth=addCommand.getValPayDateSet().getBasicSetting().getEmployeeExtractionReferenceDate().getRefeMonth();
        int disposalDay=addCommand.getValPayDateSet().getBasicSetting().getAccountingClosureDate().getDisposalDay();
        int processMonth=addCommand.getValPayDateSet().getBasicSetting().getAccountingClosureDate().getProcessMonth();
        //advanc
        int monthCollected=addCommand.getValPayDateSet().getAdvancedSetting().getSalaryInsuColMon().getMonthCollected();

        int baseYear=addCommand.getValPayDateSet().getAdvancedSetting().getSociInsuStanDate().getBaseYear();
        int baseMonth=addCommand.getValPayDateSet().getAdvancedSetting().getSociInsuStanDate().getBaseMonth();
        int baseDate=addCommand.getValPayDateSet().getAdvancedSetting().getSociInsuStanDate().getRefeDate();


        int referDateEmploymentInsuranceStanDate=addCommand.getValPayDateSet().getAdvancedSetting().getEmpInsurStanDate().getRefeDate();
        int baseMonthEmploymentInsuranceStanDate=addCommand.getValPayDateSet().getAdvancedSetting().getEmpInsurStanDate().getBaseMonth();




        GeneralDate generalDate = GeneralDate.ymd(1996, 06, 05);
        YearMonth a=new YearMonth(201811);




        GeneralDate incomeTaxDate;


        for (int i = 1; i < 13; i++) {
            GeneralDate closeDateTime=generalDate;
            GeneralDate empInsurdStanDate=GeneralDate.ymd(currentYear, i, (referDateEmploymentInsuranceStanDate == DateSelectClassification.LAST_DAY_MONTH.value) ? GeneralDate.today().lastDateInMonth() : referDateEmploymentInsuranceStanDate);
            if(baseMonthEmploymentInsuranceStanDate>i)
                empInsurdStanDate.addYears(-1);
            GeneralDate closureDateAccounting=convertDate(GeneralDate.ymd(currentYear, i, (disposalDay == DateSelectClassification.LAST_DAY_MONTH.value) ? GeneralDate.today().lastDateInMonth() : disposalDay));
            if(processMonth==PreviousMonthClassification.LAST_MONTH.value){
                closureDateAccounting.addMonths(-1);
                convertDate(closureDateAccounting);
            }

            GeneralDate paymentDate=convertDate(GeneralDate.ymd(currentYear, i, (payMentDate == DateSelectClassification.LAST_DAY_MONTH.value) ? GeneralDate.today().lastDateInMonth() : payMentDate));

            GeneralDate empExtraRefeDate=convertDate(GeneralDate.ymd(currentYear, i, (refeDate == DateSelectClassification.LAST_DAY_MONTH.value) ? GeneralDate.today().lastDateInMonth() : refeDate));
            if(refeMonth==PreviousMonthClassification.LAST_MONTH.value){
                empExtraRefeDate.addMonths(-1);
                convertDate(empExtraRefeDate);
            }

            GeneralDate socialInsurdStanDate=GeneralDate.ymd(currentYear, i, (baseDate == DateSelectClassification.LAST_DAY_MONTH.value) ? GeneralDate.today().lastDateInMonth() : baseDate);
            if(baseMonth==InsuranceStanMonthClassification.LAST_MONTH.value)
                socialInsurdStanDate.addMonths(-1);
            else if(baseMonth==InsuranceStanMonthClassification.MONTH.value)
                socialInsurdStanDate.addMonths(0);
            else
                socialInsurdStanDate=GeneralDate.ymd(currentYear, baseMonth-1, (baseDate == DateSelectClassification.LAST_DAY_MONTH.value) ? GeneralDate.today().lastDateInMonth() : baseDate);;




            YearMonth socialInsurdCollecMonth=YearMonth.of(currentYear,i);
            socialInsurdCollecMonth.addMonths(monthCollected-2);

            GeneralDate incomeTaxDate1=generalDate;
            this.setDaySupportRepository.add(new SetDaySupport(
                    cid,
                    processCateNo,
                    currentYear * 100 + i,
                    closeDateTime,
                    empInsurdStanDate,
                    closureDateAccounting,
                    paymentDate,
                    empExtraRefeDate,
                    socialInsurdStanDate,
                    socialInsurdCollecMonth.v(),
                    incomeTaxDate1,
                    "20")
            );
        }

    }


    public GeneralDate convertDate(GeneralDate convertDate) {

        if (convertDate.dayOfWeek() == 6)
            return convertDate.addDays(-1);
        else if (convertDate.dayOfWeek() == 7)
            return convertDate.addDays(-2);
        else
            return convertDate;

    }


}
