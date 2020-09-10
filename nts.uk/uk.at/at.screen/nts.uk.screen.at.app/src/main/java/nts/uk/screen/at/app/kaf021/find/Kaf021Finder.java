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
import nts.uk.ctx.at.record.dom.monthly.agreement.AgreementTimeOfManagePeriod;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.export.AgreementTimeDetail;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.export.GetAgreementTime;
import nts.uk.ctx.at.record.dom.require.RecordDomRequireService;
import nts.uk.ctx.at.record.dom.standardtime.AgreementOperationSetting;
import nts.uk.ctx.at.record.dom.standardtime.export.GetAgreementTimeOfMngPeriod;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementOperationSettingRepository;
import nts.uk.ctx.at.shared.app.service.workrule.closure.ClosureEmploymentService;
import nts.uk.ctx.at.shared.dom.common.Year;
import nts.uk.ctx.at.shared.dom.monthly.agreement.AgreementTimeOutput;
import nts.uk.ctx.at.shared.dom.monthly.agreement.ScheRecAtr;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureRepository;
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
    @Inject
    private ClosureEmploymentService closureEmploymentService;

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
    public List<EmployeeAgreementTimeInfoDto> getEmloyeeInfoForCurrentMonth(List<EmployeeBasicInfoDto> employees) {
        return getAgreementTime(employees, 0);
        // TODO: get(社員ID): 36協定特別条項の適用申請
    }

    /**
     * 抽出した社員情報を一覧表示する（翌月の申請種類）
     */
    public List<EmployeeAgreementTimeInfoDto> getEmloyeeInfoForNextMonth(List<EmployeeBasicInfoDto> employees) {
        return getAgreementTime(employees, 1);
        // TODO: get(社員ID): 36協定特別条項の適用申請
    }

    /**
     * 抽出した社員情報を一覧表示する（年間の申請種類）
     */
    public List<EmployeeAgreementTimeInfoDto> getEmloyeeInfoForYear(List<EmployeeBasicInfoDto> employees) {
        return getAgreementTime(employees, 0);
        // TODO: get(社員ID): 36協定特別条項の適用申請
    }

    private List<EmployeeAgreementTimeInfoDto> getAgreementTime(List<EmployeeBasicInfoDto> employees, int monthAdd) {
        String cid = AppContexts.user().companyId();
        GeneralDate baseDate = GeneralDate.today();
        val require = requireService.createRequire();
        val cacheCarrier = new CacheCarrier();
        List<String> employeeIds = employees.stream().map(EmployeeBasicInfoDto::getEmployeeId)
                .collect(Collectors.toList());

        // get(会社ID):36協定運用設定
        AgreementOperationSetting setting = getSetting(cid);
        // <call>(会社ID):List＜締めID, 現在の締め期間＞
        CurrentClosurePeriod closurePeriod = getClosure(cid);

        // 社員に対応する処理締めを取得する
        Map<String, Closure> closureAll = closureEmploymentService.findClosureByEmployee(employeeIds, baseDate);

        // 年月を指定して、36協定期間の年月を取得する
        YearMonth startYm = setting.getYearMonthOfAgreementPeriod(closurePeriod.getProcessingYm().addMonths(monthAdd));
        YearMonth endYm = startYm.addMonths(11);

        // [NO.612]年月期間を指定して管理期間の36協定時間を取得する
        Map<String, List<AgreementTimeOfManagePeriod>> agreementTimeOfMngPeriodAll = getAgreementTimeOfMngPeriod
                .getAgreementTimeByMonths(employeeIds, new YearMonthPeriod(startYm, endYm))
                .stream().collect(Collectors.groupingBy(AgreementTimeOfManagePeriod::getEmployeeId));

        YearMonth ymIndex = YearMonth.of(startYm.v());
        // Map<employeeId, <yearMonth, AgreementTimeDetail>>
        Map<String, Map<Integer, AgreementTimeDetail>> agreementTimeDetailAll = employeeIds.stream()
                .collect(Collectors.toMap(x -> x, x -> new HashMap<>()));
        while (ymIndex.v() > endYm.v()) {
            // 【NO.333】36協定時間の取得
            Map<String, AgreementTimeDetail> agreementTimeDetailByEmp = GetAgreementTime.get(
                    require, cacheCarrier, cid, employeeIds, ymIndex,
                    EnumAdaptor.valueOf(closurePeriod.getClosureId(), ClosureId.class))
                    .stream().collect(Collectors.toMap(AgreementTimeDetail::getEmployeeId, Function.identity()));
            for (Map.Entry<String, AgreementTimeDetail> entry : agreementTimeDetailByEmp.entrySet()) {
                if (!agreementTimeDetailAll.containsKey(entry.getKey())) continue;
                Map<Integer, AgreementTimeDetail> monthDataMap = agreementTimeDetailAll.get(entry.getKey());
                if (!monthDataMap.containsKey(ymIndex.v())){
                    monthDataMap.put(ymIndex.v(), entry.getValue());
                }
            }
            ymIndex.addMonths(1);
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
                monthsExceededAll, agreementTimeYearAll);
    }

    private List<EmployeeAgreementTimeInfoDto> mappingEmployee(List<EmployeeBasicInfoDto> employees,
                                                               YearMonth startYm, YearMonth endYm,
                                                               Map<String, List<AgreementTimeOfManagePeriod>> agreementTimeOfMngPeriodAll,
                                                               Map<String, Map<Integer, AgreementTimeDetail>> agreementTimeDetailAll,
                                                               Map<String, Integer> monthsExceededAll,
                                                               Map<String, AgreementTimeOutput> agreementTimeYearAll) {
        List<EmployeeAgreementTimeInfoDto> results = new ArrayList<>();
        for (EmployeeBasicInfoDto employee : employees) {
            // mapping data
            EmployeeAgreementTimeInfoDto result = new EmployeeAgreementTimeInfoDto();
            result.setEmployeeId(employee.getEmployeeId());
            result.setEmployeeName(employee.getEmployeeName());
            result.setEmployeeCode(employee.getEmployeeCode());
            result.setAffId(employee.getAffId());
            result.setAffName(employee.getAffName());

            // init months
            YearMonth ymIndex = YearMonth.of(startYm.v());
            List<AgreementTimeOfManagePeriodDto> agreementTimes = new ArrayList<>();
            while (ymIndex.v() > endYm.v()) {
                AgreementTimeOfManagePeriodDto agreementTime = new AgreementTimeOfManagePeriodDto();
                agreementTime.setYearMonth(ymIndex.v());
                agreementTimes.add(agreementTime);
                ymIndex.addMonths(1);
            }

            // fill data to each month: AgreementTimeOfManagePeriod
            if (agreementTimeOfMngPeriodAll.containsKey(result.getEmployeeId())) {
                List<AgreementTimeOfManagePeriod> agrTimePeriods = agreementTimeOfMngPeriodAll.get(result.getEmployeeId());
                for (AgreementTimeOfManagePeriodDto agreementTime : agreementTimes) {
                    Optional<AgreementTimeOfManagePeriod> agrTimePeriodOpt = agrTimePeriods.stream()
                            .filter(x -> x.getYearMonth().v() == agreementTime.getYearMonth()).findFirst();
                    if (!agrTimePeriodOpt.isPresent()) continue;
                    AgreementTimeOfManagePeriod agrTimePeriod = agrTimePeriodOpt.get();

                    // set data
                    agreementTime.setAgreementTime(agrTimePeriod.getAgreementTime().getAgreementTime().getAgreementTime().v());
                    agreementTime.setAgreementMaxTime(agrTimePeriod.getAgreementMaxTime().getAgreementTime().getAgreementTime().v());
                    agreementTime.setStatus(agrTimePeriod.getAgreementTime().getAgreementTime().getStatus().value);
                }
            }

            // fill data to each month: AgreementTimeDetail
            if (agreementTimeDetailAll.containsKey(result.getEmployeeId())) {
                Map<Integer, AgreementTimeDetail> agreementTimeDetailByMonth = agreementTimeDetailAll.get(result.getEmployeeId());
                for (AgreementTimeOfManagePeriodDto agreementTime : agreementTimes) {
                    if (!agreementTimeDetailByMonth.containsKey(agreementTime.getYearMonth())) continue;
                    AgreementTimeDetail agreementTimeDetail = agreementTimeDetailByMonth.get(agreementTime.getYearMonth());

                    // set data

                }
            }


            result.setAgreementTimes(agreementTimes);
            results.add(result);
        }

        return results;
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
