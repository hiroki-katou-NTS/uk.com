package nts.uk.ctx.pr.core.dom.wageprovision.processdatecls;

import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class RegisterProcessing {
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

    private static final int NUMBER_OF_MONTH_IN_TWO_YEAR = 24;


    public void registerProcessInformation(ProcessInformation processInformation) {
        this.processInformationRepository.add(processInformation);
    }

    public void registerValPayDateSet(ValPayDateSet valPayDateSet) {
        this.valPayDateSetRepository.add(valPayDateSet);
    }

    public void registerSpecPrintYmSet(ValPayDateSet valPayDateSet) {
        String cid = AppContexts.user().companyId();
        int processCateNo = valPayDateSet.getProcessCateNo();
        int currentYear = GeneralDate.today().year();
        int montOption = valPayDateSet.getAdvancedSetting().getItemPrintingMonth().getPrintingMonth().value;
        List<SpecPrintYmSet> specPrintYmSets = new ArrayList<>();
        YearMonth lastMonthOfLastYaer = new YearMonth((currentYear - 1) * 100 + 12);

        int a = 0;
        if (montOption == PreviousMonthClassification.LAST_MONTH.value) {
            a = 1;
        }
        for (int i = 1; i <= NUMBER_OF_MONTH_IN_TWO_YEAR; i++) {
            specPrintYmSets.add(new SpecPrintYmSet(cid, processCateNo, lastMonthOfLastYaer.addMonths(i).v(),
                    lastMonthOfLastYaer.addMonths(i - a).v()));
        }
        this.specPrintYmSetRepository.addAll(specPrintYmSets);
    }


    public void registerSetDaySupport(ValPayDateSet valPayDateSet) {
        String cid = AppContexts.user().companyId();
        int currentYear = GeneralDate.today().year();
        int processCateNo = valPayDateSet.getProcessCateNo();
        // basic
        int payMentDate = valPayDateSet.getBasicSetting().getMonthlyPaymentDate().getDatePayMent().value;
        int refeDate = valPayDateSet.getBasicSetting().getEmployeeExtractionReferenceDate().getRefeDate().value;
        int refeMonth = valPayDateSet.getBasicSetting().getEmployeeExtractionReferenceDate().getRefeMonth().value;
        int disposalDay = valPayDateSet.getBasicSetting().getAccountingClosureDate().getDisposalDay().value;
        int processMonth = valPayDateSet.getBasicSetting().getAccountingClosureDate().getProcessMonth().value;

        // advanc
        int monthCollected = valPayDateSet.getAdvancedSetting().getSocialInsuColleMon().getMonthCollected().value;
        int baseYear = valPayDateSet.getAdvancedSetting().getSociInsuStanDate().getSociInsuBaseYear().value;
        int baseMonth = valPayDateSet.getAdvancedSetting().getSociInsuStanDate().getSociInsuBaseMonth().value;
        int baseDate = valPayDateSet.getAdvancedSetting().getSociInsuStanDate().getSociInsuRefeDate().value;
        int referDateEmploymentInsuranceStanDate = valPayDateSet.getAdvancedSetting().getEmpInsurStanDate().getEmpInsurRefeDate().value;
        int baseMonthEmploymentInsuranceStanDate = valPayDateSet.getAdvancedSetting().getEmpInsurStanDate().getEmpInsurBaseMonth().value;
        int timeCloseDate = valPayDateSet.getAdvancedSetting().getCloseDate().getTimeCloseDate();
        Integer refeDateClose = valPayDateSet.getAdvancedSetting().getCloseDate().getCloseDateRefeDate().map(i -> i.value).orElse(null);
        Integer baseMonthClose = valPayDateSet.getAdvancedSetting().getCloseDate().getCloseDateBaseMonth().map(i -> i.value).orElse(null);
        Integer baseYearClose = valPayDateSet.getAdvancedSetting().getCloseDate().getCloseDateBaseYear().map(i -> i.value).orElse(null);
        int inComRefeMonth = valPayDateSet.getAdvancedSetting().getIncomTaxBaseYear().getInComBaseMonth().value;
        int inComRefeYear = valPayDateSet.getAdvancedSetting().getIncomTaxBaseYear().getInComBaseYear().value;
        int inComRefeDate = valPayDateSet.getAdvancedSetting().getIncomTaxBaseYear().getInComRefeDate().value;

        //No2.9
        BigDecimal numberWorkDay = valPayDateSet.getBasicSetting().getWorkDay().v();

        YearMonth startMonth = new YearMonth((currentYear - 1) * 100 + 12);
        List<SetDaySupport> setDaySupports = new ArrayList<>();
        for (int i = 1; i <= NUMBER_OF_MONTH_IN_TWO_YEAR; i++) {
            YearMonth currentYearMonth = startMonth.addMonths(i);

            //No2.6 補足資料
            GeneralDate empInsurdStanDate = initDateTime(currentYearMonth.year(), currentYearMonth.month(), referDateEmploymentInsuranceStanDate);
            if (baseMonthEmploymentInsuranceStanDate > i) {
                empInsurdStanDate = initDateTime(empInsurdStanDate.year(), baseMonthEmploymentInsuranceStanDate,
                        empInsurdStanDate.day()).addYears(-1);
            }

            //No2.3 補足資料
            GeneralDate closureDateAccounting = initDateTime(currentYearMonth.year(), currentYearMonth.month(), disposalDay);
            if (processMonth == PreviousMonthClassification.LAST_MONTH.value) {
                closureDateAccounting = convertDate(closureDateAccounting).addMonths(-1);
            }

            //No2.1 補足資料
            GeneralDate paymentDate = convertDate(initDateTime(currentYearMonth.year(), currentYearMonth.month(), payMentDate));

            //No2.2 補足資料
            GeneralDate empExtraRefeDate = initDateTime(currentYearMonth.year(), currentYearMonth.month(), refeDate);
            if (refeMonth == PreviousMonthClassification.LAST_MONTH.value) {
                empExtraRefeDate = convertDate(empExtraRefeDate).addMonths(-1);
            }

            //No2.5 補足資料
            GeneralDate socialInsurdStanDate = initDateTime(currentYearMonth.year(), currentYearMonth.month(), baseDate);
            if (baseMonth == InsuranceStanMonthClassification.LAST_MONTH.value)
                socialInsurdStanDate = socialInsurdStanDate.addMonths(-1);
            else if (baseMonth == InsuranceStanMonthClassification.MONTH.value)
                socialInsurdStanDate = socialInsurdStanDate.addMonths(0);
            else
                socialInsurdStanDate = initDateTime(currentYearMonth.year() + baseYear - 1, baseMonth - 1, baseDate);

            //No2.4 補足資料
            YearMonth socialInsurdCollecMonth = currentYearMonth;
            socialInsurdCollecMonth = socialInsurdCollecMonth.addMonths(monthCollected - 2);

            //No2.7 補足資料
            GeneralDate closeDateTime = empExtraRefeDate;
            if (timeCloseDate == 1) {
                closeDateTime = closeDateTime.addYears(baseYearClose - 1);
                closeDateTime = closeDateTime.addMonths(baseMonthClose - 2);
                closeDateTime = initDateTime(closeDateTime.year(), closeDateTime.month(), refeDateClose);
            }

            //No2.8 補足資料
            GeneralDate incomeTaxDate = initDateTime(currentYearMonth.year(), inComRefeMonth, inComRefeDate);
            incomeTaxDate = incomeTaxDate.addYears(inComRefeYear - 1);

            setDaySupports.add(new SetDaySupport(cid, processCateNo, currentYearMonth.v(),
                            closeDateTime,
                            empInsurdStanDate,
                            closureDateAccounting,
                            paymentDate,
                            empExtraRefeDate,
                            socialInsurdStanDate,
                            socialInsurdCollecMonth.v(),
                            incomeTaxDate,
                            numberWorkDay
                    )
            );
        }
        this.setDaySupportRepository.addAll(setDaySupports);
    }


    public void addCurrProcessDate(ValPayDateSet valPayDateSet) {
        String cid = AppContexts.user().companyId();
        GeneralDate currentDay = GeneralDate.today();
        int currTreatYear = currentDay.yearMonth().v();
        int processCateNo = valPayDateSet.getProcessCateNo();
        this.currProcessDateRepository.add(new CurrProcessDate(cid, processCateNo, currTreatYear));
    }

    public void addEmpTiedProYear(ValPayDateSet valPayDateSet) {
        String cid = AppContexts.user().companyId();
        int processCateNo = valPayDateSet.getProcessCateNo();
        this.empTiedProYearRepository.add(new EmpTiedProYear(cid, processCateNo, new ArrayList<EmploymentCode>()));
    }


    public GeneralDate convertDate(GeneralDate convertDate) {
        if (convertDate.dayOfWeek() == 6)
            return convertDate.addDays(-1);
        else if (convertDate.dayOfWeek() == 7)
            return convertDate.addDays(-2);
        else
            return convertDate;
    }


    public GeneralDate initDateTime(int year, int month, int day) {
        if (month == 2 && day > 28 && year % 4 != 0)
            return GeneralDate.ymd(year, 2, 28);
        if (month == 2 && day > 29 && year % 4 == 0)
            return GeneralDate.ymd(year, 2, 29);
        else
            return GeneralDate.ymd(year, month, (day == DateSelectClassification.LAST_DAY_MONTH.value) ? GeneralDate.today().lastDateInMonth() : day);
    }
}
