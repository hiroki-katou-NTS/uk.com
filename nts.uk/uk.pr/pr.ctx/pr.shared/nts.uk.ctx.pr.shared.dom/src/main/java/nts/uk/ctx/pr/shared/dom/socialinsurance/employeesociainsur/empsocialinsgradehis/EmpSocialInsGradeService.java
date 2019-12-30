package nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empsocialinsgradehis;

import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.shared.dom.adapter.employment.SEmpHistImport;
import nts.uk.ctx.pr.shared.dom.adapter.employment.SystemEmploymentAdapter;
import nts.uk.ctx.pr.shared.dom.adapter.wageprovision.processdatecls.CurrentProcessDateAdapter;
import nts.uk.ctx.pr.shared.dom.adapter.wageprovision.processdatecls.CurrentProcessDateImport;
import nts.uk.ctx.pr.shared.dom.adapter.wageprovision.processdatecls.EmpTiedProYearAdapter;
import nts.uk.ctx.pr.shared.dom.adapter.wageprovision.processdatecls.EmpTiedProYearImport;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.*;
import java.util.stream.Collectors;

@Stateless
public class EmpSocialInsGradeService {
    @Inject
    private EmpTiedProYearAdapter empTiedProYearAdapter;

    @Inject
    private CurrentProcessDateAdapter currentProcessDateAdapter;

    @Inject
    private SystemEmploymentAdapter syEmploymentPub;

    private static final String PASS_HISTORY = "過去の履歴";
    private static final String PRESENT_HISTORY = "現在の給与処理に適用される履歴";
    private static final String FUTURE_HISTORY = "未来の履歴";

    /**
     * 選択履歴が次回給与適用等級か判定する
     *
     * @param history  EmpSocialInsGradeHis
     * @param baseDate the standard date
     * @return current grade
     */
    public String getCurrentGrade(EmpSocialInsGradeHis history, GeneralDate baseDate) {
        String cid = AppContexts.user().companyId();

        // get employment code
        String employmentCode = syEmploymentPub.findSEmpHistBySid(cid, history.getEmployeeId(), baseDate)
                .map(SEmpHistImport::getEmploymentCode).orElse("");

        // get current year month from employment code
        YearMonth currentYm = getProcessYear(cid, employmentCode);

        YearMonthPeriod period = history.items().get(0).span();
        if (period.start().greaterThan(currentYm)) {
            return PASS_HISTORY;
        } else if (period.end().lessThan(currentYm)) {
            return FUTURE_HISTORY;
        } else {
            return PRESENT_HISTORY;
        }
    }

    /**
     * 選択履歴が次回給与適用等級か判定する
     *
     * @param histories list EmpSocialInsGradeHis
     * @param baseDate  the standard date
     * @return map (key, value) = (sid, currentGrade)
     */
    public Map<String, String> getMapCurrentGrade(Map<String, EmpSocialInsGradeHis> histories, GeneralDate baseDate) {
        String cid = AppContexts.user().companyId();

        // Get map<sid, employment hist>
        Map<String, SEmpHistImport> mapEmpHists = syEmploymentPub.findSEmpHistByListSid(cid, new ArrayList<>(histories.keySet()), baseDate);

        // List<employment code> to find current year month
        List<String> employmentCodes = mapEmpHists.values().stream().distinct()
                .map(SEmpHistImport::getEmploymentCode).collect(Collectors.toList());

        // Map<employment code, current year month>
        Map<String, YearMonth> mapYearMonth = getMapProcessYear(cid, employmentCodes);

        // Map<sid, currentGrade>
        Map<String, String> result = new HashMap<>();
        histories.keySet().forEach(k -> {
            String currentGrade;
            YearMonthPeriod period = histories.get(k).items().get(0).span();
            String employmentCode = mapEmpHists.containsKey(k) ? mapEmpHists.get(k).getEmploymentCode() : "";
            YearMonth currentYm = mapYearMonth.get(employmentCode);
            if (period.start().greaterThan(currentYm)) {
                currentGrade = PASS_HISTORY;
            } else if (period.end().lessThan(currentYm)) {
                currentGrade = FUTURE_HISTORY;
            } else {
                currentGrade = PRESENT_HISTORY;
            }
            result.put(k, currentGrade);
        });
        return result;
    }

    /**
     * 雇用から現在処理年月を取得する
     *
     * @param employmentCode the employment code
     * @return current processing date
     */
    public YearMonth getProcessYear(String cid, String employmentCode) {
        // Get processing category no
        Optional<EmpTiedProYearImport> empTiedProYear = empTiedProYearAdapter.getEmpTiedProYearByEmployment(cid, employmentCode);
        if (empTiedProYear.isPresent()) {
            // Get current year month from processing category no
            Optional<CurrentProcessDateImport> currProcessDates = currentProcessDateAdapter.getCurrProcessDateByKey(cid, empTiedProYear.get().getProcessCateNo());
            if (currProcessDates.isPresent()) return currProcessDates.get().getGiveCurrTreatYear();
        }
        return GeneralDate.today().yearMonth();
    }

    /**
     * 雇用から現在処理年月を取得する
     *
     * @param employmentCodes list employment code
     * @return map (key, value) = (employment code, current processing date)
     */
    public Map<String, YearMonth> getMapProcessYear(String cid, List<String> employmentCodes) {
        // Get processing category no
        List<EmpTiedProYearImport> empTiedProYears = empTiedProYearAdapter.getEmpTiedProYearByEmployments(cid, employmentCodes);
        List<Integer> processCateNos = empTiedProYears.stream().map(EmpTiedProYearImport::getProcessCateNo).collect(Collectors.toList());

        // Get current year month from processing category no
        List<CurrentProcessDateImport> currProcessDates = currentProcessDateAdapter.getCurrProcessDateByProcessCateNos(cid, processCateNos);


        return null;
    }
}