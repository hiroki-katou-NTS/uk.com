package nts.uk.screen.at.app.kaf021.find;

import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.arc.error.BusinessException;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.YearMonthPeriod;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.function.dom.adapter.monthly.agreement.GetExcessTimesYearAdapter;
import nts.uk.ctx.at.record.dom.monthly.agreement.AgreMaxTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.agreement.AgreementTimeOfManagePeriod;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.export.AgreementTimeDetail;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.export.GetAgreementTime;
import nts.uk.ctx.at.record.dom.require.RecordDomRequireService;
import nts.uk.ctx.at.record.dom.standardtime.AgreementOperationSetting;
import nts.uk.ctx.at.record.dom.standardtime.export.GetAgreementTimeOfMngPeriod;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementOperationSettingRepository;
import nts.uk.ctx.at.shared.dom.common.Year;
import nts.uk.ctx.at.shared.dom.monthly.agreement.*;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService;
import nts.uk.screen.at.app.workrule.closure.ClosurePeriodForAllQuery;
import nts.uk.screen.at.app.workrule.closure.CurrentClosurePeriod;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Stateless
public class Kaf021Finder {

    @Inject
    private ClosurePeriodForAllQuery closurePeriodForAllQuery;
    @Inject
    private AgreementOperationSettingRepository agreementOperationSettingRepo;
    @Inject
    private GetAgreementTimeOfMngPeriod getAgreementTimeOfMngPeriod;
    @Inject
    private RecordDomRequireService requireService;
    @Inject
    private ClosureRepository closureRepo;
    @Inject
    private GetExcessTimesYearAdapter getExcessTimesYearAdapter;

    /**
     * 初期起動を行う
     */
    public StartupInfo initStarup() {
        String cid = AppContexts.user().companyId();

        // get(会社ID):36協定運用設定
        AgreementOperationSetting setting = getSetting(cid);

        // <call>(会社ID):List＜締めID, 現在の締め期間＞
        CurrentClosurePeriod closurePeriod = getClosure(cid);

        return new StartupInfo(setting.getStartingMonth().value, closurePeriod.getProcessingYm().v());
    }

    /**
     * 抽出した社員情報を一覧表示する（当月の申請種類）
     */
    public List<EmployeeAgreementTimeDto> getEmloyeeInfoForCurrentMonth(List<EmployeeBasicInfoDto> employees) {
        return getAgreementTime(employees, 0);
        // TODO: get(社員ID): 36協定特別条項の適用申請
    }

    /**
     * 抽出した社員情報を一覧表示する（翌月の申請種類）
     */
    public List<EmployeeAgreementTimeDto> getEmloyeeInfoForNextMonth(List<EmployeeBasicInfoDto> employees) {
        return getAgreementTime(employees, 1);
        // TODO: get(社員ID): 36協定特別条項の適用申請
    }

    /**
     * 抽出した社員情報を一覧表示する（年間の申請種類）
     */
    public List<EmployeeAgreementTimeDto> getEmloyeeInfoForYear(List<EmployeeBasicInfoDto> employees) {
        return getAgreementTime(employees, 0);
        // TODO: get(社員ID): 36協定特別条項の適用申請
    }

    private List<EmployeeAgreementTimeDto> getAgreementTime(List<EmployeeBasicInfoDto> employees, int monthAdd) {
        String cid = AppContexts.user().companyId();
        String sid = AppContexts.user().employeeId();
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
        YearMonth startYm = setting.getYearMonthOfAgreementPeriod(closureInfo.getClosureMonth().getProcessingYm().addMonths(monthAdd));
        YearMonth endYm = startYm.addMonths(11);

        // [NO.612]年月期間を指定して管理期間の36協定時間を取得する
        Map<String, List<AgreementTimeOfManagePeriod>> agreementTimeOfMngPeriodAll = getAgreementTimeOfMngPeriod
                .getAgreementTimeByMonths(employeeIds, new YearMonthPeriod(startYm, endYm))
                .stream().collect(Collectors.groupingBy(AgreementTimeOfManagePeriod::getEmployeeId));

        // Map<employeeId, <yearMonth, AgreementTimeDetail>>
        Map<String, Map<Integer, AgreementTimeDetail>> agreementTimeDetailAll = employeeIds.stream()
                .collect(Collectors.toMap(x -> x, x -> new HashMap<>()));
        YearMonthPeriod yearMonthPeriod = new YearMonthPeriod(startYm, endYm);
        for (YearMonth ymIndex : yearMonthPeriod.yearMonthsBetween()) {
            // 【NO.333】36協定時間の取得
            Map<String, AgreementTimeDetail> agreementTimeDetailByEmp = GetAgreementTime.get(
                    require, cacheCarrier, cid, employeeIds, ymIndex, closureInfo.getClosureId())
                    .stream().collect(Collectors.toMap(AgreementTimeDetail::getEmployeeId, Function.identity()));
            for (Map.Entry<String, AgreementTimeDetail> entry : agreementTimeDetailByEmp.entrySet()) {
                if (!agreementTimeDetailAll.containsKey(entry.getKey())) continue;
                Map<Integer, AgreementTimeDetail> monthDataMap = agreementTimeDetailAll.get(entry.getKey());
                if (!monthDataMap.containsKey(ymIndex.v())) {
                    monthDataMap.put(ymIndex.v(), entry.getValue());
                }
            }
        }

        Year fiscalYear = new Year(startYm.year());
        // [No.458]年間超過回数の取得
        Map<String, Integer> monthsExceededAll = getExcessTimesYearAdapter.algorithm(employeeIds, fiscalYear);

        Map<String, AgreementTimeOutput> agreementTimeYearAll = new HashMap<>();
        for (EmployeeBasicInfoDto employee : employees) {
            // 36協定上限複数月平均時間と年間時間の取得(年度指定)
            AgreementTimeOutput agreementTime = GetAgreementTime.getAverageAndYear(require, cacheCarrier, cid,
                    employee.getEmployeeId(), baseDate, fiscalYear, startYm, ScheRecAtr.RECORD);
            if (!agreementTimeYearAll.containsKey(employee.getEmployeeId())) {
                agreementTimeYearAll.put(employee.getEmployeeId(), agreementTime);
            }
        }

        return mappingEmployee(employees, startYm, endYm, agreementTimeOfMngPeriodAll, agreementTimeDetailAll,
                agreementTimeYearAll, monthsExceededAll);
    }

    private List<EmployeeAgreementTimeDto> mappingEmployee(List<EmployeeBasicInfoDto> employees,
                                                           YearMonth startYm, YearMonth endYm,
                                                           Map<String, List<AgreementTimeOfManagePeriod>> agreementTimeOfMngPeriodAll,
                                                           Map<String, Map<Integer, AgreementTimeDetail>> agreementTimeDetailAll,
                                                           Map<String, AgreementTimeOutput> agreementTimeYearAll,
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

            // fill data to each month: AgreementTimeOfManagePeriod, AgreementTimeDetail
            YearMonthPeriod yearMonthPeriod = new YearMonthPeriod(startYm, endYm);
            List<AgreementTimeOfManagePeriod> agrTimePeriods = new ArrayList<>();
            if (agreementTimeOfMngPeriodAll.containsKey(result.getEmployeeId())) {
                agrTimePeriods = agreementTimeOfMngPeriodAll.get(result.getEmployeeId());
            }
            Map<Integer, AgreementTimeDetail> agreementTimeDetailByMonth = new HashMap<>();
            if (agreementTimeDetailAll.containsKey(result.getEmployeeId())) {
                agreementTimeDetailByMonth = agreementTimeDetailAll.get(result.getEmployeeId());
            }
            mappingPeriodMonth(result, yearMonthPeriod, agrTimePeriods, agreementTimeDetailByMonth);

            // fill data AgreementTimeOutput
            mappingYearAndMonthAverage(result, agreementTimeYearAll);

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
                                List<AgreementTimeOfManagePeriod> agrTimePeriods,
                                Map<Integer, AgreementTimeDetail> agreementTimeDetailByMonth) {
        for (YearMonth ymIndex : yearMonthPeriod.yearMonthsBetween()) {
            AgreementTimeDto agreementTime = null;
            AgreementTimeDto agreementMaxTime = null;

            Optional<AgreementTimeOfManagePeriod> agrTimePeriodOpt = agrTimePeriods.stream()
                    .filter(x -> x.getYearMonth() == ymIndex).findFirst();
            if (agrTimePeriodOpt.isPresent()) {
                AgreementTimeOfManagePeriod agrTimePeriod = agrTimePeriodOpt.get();
                agreementTime = new AgreementTimeDto(agrTimePeriod);
            }

            if (agreementTimeDetailByMonth.containsKey(ymIndex.v())) {
                AgreementTimeDetail agreementTimeDetail = agreementTimeDetailByMonth.get(ymIndex.v());
                Optional<AgreMaxTimeOfMonthly> confirmedMaxOpt = agreementTimeDetail.getConfirmedMax();
                if (!confirmedMaxOpt.isPresent()) continue;
                agreementMaxTime = new AgreementTimeDto(confirmedMaxOpt.get());
            }

            AgreementTimeMonthDto agreementTimeMonth = new AgreementTimeMonthDto(ymIndex.v(), agreementTime, agreementMaxTime);
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

    private void mappingYearAndMonthAverage(EmployeeAgreementTimeDto result, Map<String, AgreementTimeOutput> agreementTimeYearAll) {
        if (agreementTimeYearAll.containsKey(result.getEmployeeId())) {
            // fill year
            AgreementTimeOutput agreementTimeOutput = agreementTimeYearAll.get(result.getEmployeeId());
            if (agreementTimeOutput.getAgreementTimeYear().isPresent()) {
                AgreementTimeYear agreementTimeYear = agreementTimeOutput.getAgreementTimeYear().get();
                result.setYear(new AgreementTimeYearDto(agreementTimeYear));
            }
            // fill average range month
            if (agreementTimeOutput.getAgreMaxAverageTimeMulti().isPresent()) {
                AgreMaxAverageTimeMulti agreMaxAverageTimeMulti = agreementTimeOutput.getAgreMaxAverageTimeMulti().get();
                for (AgreMaxAverageTime averageTime : agreMaxAverageTimeMulti.getAverageTimeList()) {
                    YearMonthPeriod period = averageTime.getPeriod();
                    int rangeMonth = period.end().compareTo(period.start());
                    AgreementMaxAverageTimeDto average = new AgreementMaxAverageTimeDto(averageTime);
                    switch (rangeMonth) {
                        case 1:
                            result.setMonthAverage2(average);
                            break;
                        case 2:
                            result.setMonthAverage3(average);
                            break;
                        case 3:
                            result.setMonthAverage4(average);
                            break;
                        case 4:
                            result.setMonthAverage5(average);
                            break;
                        case 5:
                            result.setMonthAverage6(average);
                            break;
                    }
                }
            }
        }
    }

    private AgreementOperationSetting getSetting(String cid) {
        Optional<AgreementOperationSetting> settingOpt = agreementOperationSettingRepo.find(cid);
        if (!settingOpt.isPresent()) throw new BusinessException("Msg_1843");
        return settingOpt.get();
    }

    private CurrentClosurePeriod getClosure(String cid) {
        List<CurrentClosurePeriod> closurePeriods = closurePeriodForAllQuery.get(cid);
        if (CollectionUtil.isEmpty(closurePeriods)) throw new BusinessException("Msg_1843");
        return closurePeriods.get(0);
    }
}
