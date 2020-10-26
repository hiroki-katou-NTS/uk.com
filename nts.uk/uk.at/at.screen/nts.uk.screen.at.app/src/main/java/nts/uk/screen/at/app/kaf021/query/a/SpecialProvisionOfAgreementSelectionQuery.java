package nts.uk.screen.at.app.kaf021.query.a;

import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.Year;
import nts.arc.time.calendar.period.YearMonthPeriod;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.function.dom.adapter.monthly.agreement.GetExcessTimesYearAdapter;
import nts.uk.ctx.at.record.dom.monthly.agreement.export.GetAgreementTime;
import nts.uk.ctx.at.record.dom.monthly.agreement.export.GetAgreementTimeOfMngPeriod;
import nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.specialprovision.ApprovalStatus;
import nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.specialprovision.SpecialProvisionsOfAgreement;
import nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.specialprovision.SpecialProvisionsOfAgreementRepo;
import nts.uk.ctx.at.record.dom.require.RecordDomRequireService;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementOperationSettingRepository;

import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.*;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.setting.AgreementOperationSetting;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService;
import nts.uk.screen.at.app.workrule.closure.ClosurePeriodForAllQuery;
import nts.uk.screen.at.app.workrule.closure.CurrentClosurePeriod;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.*;
import java.util.stream.Collectors;

@Stateless
public class SpecialProvisionOfAgreementSelectionQuery {

    @Inject
    private ClosurePeriodForAllQuery closurePeriodForAllQuery;
    @Inject
    private AgreementOperationSettingRepository agreementOperationSettingRepo;
    @Inject
    private RecordDomRequireService requireService;
    @Inject
    private GetExcessTimesYearAdapter getExcessTimesYearAdapter;
    @Inject
    private AgreementTimeOfManagePeriodRepository agreementTimeOfManagePeriodRepo;
    @Inject
    private SpecialProvisionsOfAgreementRepo specialProvisionsOfAgreementRepo;

    /**
     * 初期起動を行う
     */
    public StartupInfo initStarup() {
        String cid = AppContexts.user().companyId();

        // get(会社ID):36協定運用設定
        AgreementOperationSetting setting = getSetting(cid);

        // <call>(会社ID):List＜締めID, 現在の締め期間＞
        CurrentClosurePeriod closurePeriod = getClosure(cid);

        return new StartupInfo(new AgreementOperationSettingDto(setting), closurePeriod.getProcessingYm().v());
    }

    /**
     * 抽出した社員情報を一覧表示する（当月の申請種類）
     */
    public List<EmployeeAgreementTimeDto> getEmloyeeInfoForCurrentMonth(List<EmployeeBasicInfoDto> employees) {
        return getAgreementTime(employees, 0, false);
    }

    /**
     * 抽出した社員情報を一覧表示する（翌月の申請種類）
     */
    public List<EmployeeAgreementTimeDto> getEmloyeeInfoForNextMonth(List<EmployeeBasicInfoDto> employees) {
        return getAgreementTime(employees, 1, false);
    }

    /**
     * 抽出した社員情報を一覧表示する（年間の申請種類）
     */
    public List<EmployeeAgreementTimeDto> getEmloyeeInfoForYear(List<EmployeeBasicInfoDto> employees) {
        return getAgreementTime(employees, 0, true);
    }

    private List<EmployeeAgreementTimeDto> getAgreementTime(List<EmployeeBasicInfoDto> employees, int monthAdd, boolean isYearMode) {
        if (CollectionUtil.isEmpty(employees)) return new ArrayList<>();
        String cid = AppContexts.user().companyId();
        String sid = employees.get(0).getEmployeeId();
        GeneralDate baseDate = GeneralDate.today();
        val require = requireService.createRequire();
        val cacheCarrier = new CacheCarrier();
        List<String> employeeIds = employees.stream().map(EmployeeBasicInfoDto::getEmployeeId).distinct()
                .collect(Collectors.toList());

        // get(会社ID):36協定運用設定
        AgreementOperationSetting setting = getSetting(cid);

        // 社員に対応する処理締めを取得する
        Closure closureInfo = ClosureService.getClosureDataByEmployee(require, cacheCarrier, sid, baseDate);

        // 年月を指定して、36協定期間の年月を取得する
        YearMonth processingYm = closureInfo.getClosureMonth().getProcessingYm();
        YearMonth startYm = setting.getYearMonthOfAgreementPeriod(YearMonth.of(processingYm.year(), setting.getStartingMonth().getMonth()));
        YearMonth currentYm = setting.getYearMonthOfAgreementPeriod(closureInfo.getClosureMonth().getProcessingYm().addMonths(monthAdd));
        YearMonth endYm = startYm.addMonths(11);
        YearMonthPeriod yearMonthPeriod = new YearMonthPeriod(currentYm, endYm);
        Map<String, List<AgreementTimeOfManagePeriod>> agreementTimeAll = new HashMap<>();
        for (String employeeId : employeeIds) {
            agreementTimeAll.put(employeeId, new ArrayList<>());
        }

        // [NO.612]年月期間を指定して管理期間の36協定時間を取得する
        Map<String, List<AgreementTimeOfManagePeriod>> agreementTimeOfManagePeriods = GetAgreementTimeOfMngPeriod
                .get(this.createRequire(), employeeIds, new YearMonthPeriod(startYm, currentYm.addMonths(-1)))
                .stream().collect(Collectors.groupingBy(AgreementTimeOfManagePeriod::getSid));

        for (Map.Entry<String, List<AgreementTimeOfManagePeriod>> agreementTime : agreementTimeOfManagePeriods.entrySet()) {
            if (agreementTimeAll.containsKey(agreementTime.getKey())) {
                agreementTimeAll.get(agreementTime.getKey()).addAll(agreementTime.getValue());
            }
        }

        for (String employeeId : employeeIds) {
            List<AgreementTimeOfManagePeriod> timeAll = agreementTimeAll.get(employeeId);
            for (YearMonth ymIndex : yearMonthPeriod.yearMonthsBetween()) {
                // 【NO.333】36協定時間の取得
                AgreementTimeOfManagePeriod time = GetAgreementTime.get(require, employeeId, ymIndex, new ArrayList<>(), baseDate, ScheRecAtr.RECORD);
                if (time != null) {
                    timeAll.add(time);
                }
            }
        }

        Year fiscalYear = new Year(startYm.year());
        // [No.458]年間超過回数の取得
        Map<String, Integer> monthsExceededAll = getExcessTimesYearAdapter.algorithm(employeeIds, fiscalYear);

        Map<String, AgreementTimeYear> agreementTimeYearAll = new HashMap<>();
        Map<String, AgreMaxAverageTimeMulti> agreMaxAverageTimeMultiAll = new HashMap<>();
        for (EmployeeBasicInfoDto employee : employees) {
            // 36協定上限複数月平均時間と年間時間の取得(年度指定)
            Optional<AgreementTimeYear> agreementTimeYearOpt = GetAgreementTime.getYear(require,
                    employee.getEmployeeId(), yearMonthPeriod, baseDate, ScheRecAtr.RECORD);
            agreementTimeYearOpt.ifPresent(agreementTimeYear -> agreementTimeYearAll.put(employee.getEmployeeId(), agreementTimeYear));

            Optional<AgreMaxAverageTimeMulti> agreMaxAverageTimeMultiOpt = GetAgreementTime.getMaxAverageMulti(require,
                    new ArrayList<>(), employee.getEmployeeId(), currentYm, baseDate, ScheRecAtr.RECORD);
            agreMaxAverageTimeMultiOpt.ifPresent(agreMaxAverageTimeMulti -> agreMaxAverageTimeMultiAll.put(employee.getEmployeeId(), agreMaxAverageTimeMulti));
        }

        List<EmployeeAgreementTimeDto> empAgreementTimes = mappingEmployee(employees, startYm, endYm, agreementTimeAll,
                agreementTimeYearAll, agreMaxAverageTimeMultiAll, monthsExceededAll);

        for (EmployeeAgreementTimeDto emp : empAgreementTimes) {
            Optional<SpecialProvisionsOfAgreement> specialAgreementOpt;
            if (isYearMode) {
                specialAgreementOpt = specialProvisionsOfAgreementRepo.getByYear(emp.getEmployeeId(), fiscalYear);
            } else {
                specialAgreementOpt = specialProvisionsOfAgreementRepo.getByYearMonth(emp.getEmployeeId(), currentYm);
            }

            if (specialAgreementOpt.isPresent()) {
                SpecialProvisionsOfAgreement specialAgreement = specialAgreementOpt.get();
                ApprovalStatus status = specialAgreement.getApprovalStatusDetails().getApprovalStatus();
                emp.setStatus(status.value);
            }
        }

        return empAgreementTimes;
    }

    private List<EmployeeAgreementTimeDto> mappingEmployee(List<EmployeeBasicInfoDto> employees,
                                                           YearMonth startYm, YearMonth endYm,
                                                           Map<String, List<AgreementTimeOfManagePeriod>> agreementTimeAll,
                                                           Map<String, AgreementTimeYear> agreementTimeYearAll,
                                                           Map<String, AgreMaxAverageTimeMulti> agreMaxAverageTimeMultiAll,
                                                           Map<String, Integer> monthsExceededAll) {
        List<EmployeeAgreementTimeDto> results = new ArrayList<>();
        for (EmployeeBasicInfoDto employee : employees) {
            // mapping data
            EmployeeAgreementTimeDto result = new EmployeeAgreementTimeDto();
            result.setEmployeeId(employee.getEmployeeId());
            result.setEmployeeName(employee.getEmployeeName());
            result.setEmployeeCode(employee.getEmployeeCode());
            result.setAffiliationCode(employee.getAffiliationCode());
            result.setAffiliationId(employee.getAffiliationId());
            result.setAffiliationName(employee.getAffiliationName());

            // fill data to each month: AgreementTimeOfManagePeriod
            YearMonthPeriod yearMonthPeriod = new YearMonthPeriod(startYm, endYm);
            List<AgreementTimeOfManagePeriod> agrTimePeriods = new ArrayList<>();
            if (agreementTimeAll.containsKey(result.getEmployeeId())) {
                agrTimePeriods = agreementTimeAll.get(result.getEmployeeId());
            }
            mappingPeriodMonth(result, yearMonthPeriod, agrTimePeriods);

            // fill data AgreementTimeOutput
            mappingYearAndMonthAverage(result, startYm.year(), agreementTimeYearAll, agreMaxAverageTimeMultiAll);

            // fill data monthsExceeded
            if (monthsExceededAll.containsKey(result.getEmployeeId())) {
                result.setExceededNumber(monthsExceededAll.get(result.getEmployeeId()));
            }

            results.add(result);
        }

        return results.stream().sorted(Comparator.comparing(EmployeeAgreementTimeDto::getEmployeeCode))
                .collect(Collectors.toList());
    }

    private void mappingPeriodMonth(EmployeeAgreementTimeDto result,
                                    YearMonthPeriod yearMonthPeriod,
                                    List<AgreementTimeOfManagePeriod> agrTimePeriods) {
        for (YearMonth ymIndex : yearMonthPeriod.yearMonthsBetween()) {

            Optional<AgreementTimeOfManagePeriod> agrTimePeriodOpt = agrTimePeriods.stream()
                    .filter(x -> x.getYm().equals(ymIndex)).findFirst();
            if (!agrTimePeriodOpt.isPresent()) {
                continue;
            }
            AgreementTimeOfManagePeriod agrTimePeriod = agrTimePeriodOpt.get();
            AgreementTimeMonthDto agreementTimeMonth = new AgreementTimeMonthDto(agrTimePeriod);

            switch (ymIndex.month()) {
                case 1:
                    result.setMonth1(agreementTimeMonth);
                    break;
                case 2:
                    result.setMonth2(agreementTimeMonth);
                    break;
                case 3:
                    result.setMonth3(agreementTimeMonth);
                    break;
                case 4:
                    result.setMonth4(agreementTimeMonth);
                    break;
                case 5:
                    result.setMonth5(agreementTimeMonth);
                    break;
                case 6:
                    result.setMonth6(agreementTimeMonth);
                    break;
                case 7:
                    result.setMonth7(agreementTimeMonth);
                    break;
                case 8:
                    result.setMonth8(agreementTimeMonth);
                    break;
                case 9:
                    result.setMonth9(agreementTimeMonth);
                    break;
                case 10:
                    result.setMonth10(agreementTimeMonth);
                    break;
                case 11:
                    result.setMonth11(agreementTimeMonth);
                    break;
                case 12:
                    result.setMonth12(agreementTimeMonth);
                    break;
            }
        }
    }

    private void mappingYearAndMonthAverage(EmployeeAgreementTimeDto result,
                                            int year,
                                            Map<String, AgreementTimeYear> agreementTimeYearAll,
                                            Map<String, AgreMaxAverageTimeMulti> agreMaxAverageTimeMultiAll) {
        if (agreementTimeYearAll.containsKey(result.getEmployeeId())) {
            // fill year

            if (agreementTimeYearAll.containsKey(result.getEmployeeId())) {
                AgreementTimeYear agreementTimeYear = agreementTimeYearAll.get(result.getEmployeeId());
                result.setYear(new AgreementTimeYearDto(year, agreementTimeYear));
            }
            // fill average range month
            if (agreMaxAverageTimeMultiAll.containsKey(result.getEmployeeId())) {
                AgreMaxAverageTimeMulti agreMaxAverageTimeMulti = agreMaxAverageTimeMultiAll.get(result.getEmployeeId());
                for (AgreMaxAverageTime averageTime : agreMaxAverageTimeMulti.getAverageTimes()) {
                    YearMonthPeriod period = averageTime.getPeriod();
                    int rangeMonth = period.yearMonthsBetween().size();
                    AgreementMaxAverageTimeDto average = new AgreementMaxAverageTimeDto(averageTime);
                    switch (rangeMonth) {
                        case 2:
                            result.setMonthAverage2(average);
                            break;
                        case 3:
                            result.setMonthAverage3(average);
                            break;
                        case 4:
                            result.setMonthAverage4(average);
                            break;
                        case 5:
                            result.setMonthAverage5(average);
                            break;
                        case 6:
                            result.setMonthAverage6(average);
                            break;
                    }
                }
            }
        }
    }

    private AgreementOperationSetting getSetting(String cid) {
        Optional<AgreementOperationSetting> settingOpt = agreementOperationSettingRepo.find(cid);
        if (!settingOpt.isPresent()) throw new RuntimeException("AgreementOperationSetting is null!");
        AgreementOperationSetting setting = settingOpt.get();
        if (!setting.isSpecicalConditionApplicationUse()) throw new BusinessException("Msg_1843");
        return setting;
    }

    private CurrentClosurePeriod getClosure(String cid) {
        List<CurrentClosurePeriod> closurePeriods = closurePeriodForAllQuery.get(cid);
        if (CollectionUtil.isEmpty(closurePeriods)) throw new RuntimeException("CurrentClosurePeriod is null!");
        return closurePeriods.get(0);
    }

    private GetAgreementTimeOfMngPeriod.RequireM1 createRequire() {

        return new GetAgreementTimeOfMngPeriod.RequireM1() {

            @Override
            public List<AgreementTimeOfManagePeriod> agreementTimeOfManagePeriod(List<String> sids,
                                                                                 List<YearMonth> yearMonths) {

                return agreementTimeOfManagePeriodRepo.findBySidsAndYearMonths(sids, yearMonths);
            }

            @Override
            public Optional<AgreementOperationSetting> agreementOperationSetting(String cid) {

                return agreementOperationSettingRepo.find(cid);
            }
        };
    }
}
