package nts.uk.file.at.app.export.scheduledailytable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.function.dom.adapter.RegulationInfoEmployeeAdapter;
import nts.uk.ctx.at.function.dom.adapter.annualworkschedule.EmployeeInformationAdapter;
import nts.uk.ctx.at.function.dom.adapter.annualworkschedule.EmployeeInformationImport;
import nts.uk.ctx.at.function.dom.adapter.annualworkschedule.EmployeeInformationQueryDtoImport;
import nts.uk.ctx.at.schedule.dom.adapter.classification.SyClassificationAdapter;
import nts.uk.ctx.at.schedule.dom.adapter.jobtitle.PositionImport;
import nts.uk.ctx.at.schedule.dom.adapter.jobtitle.SyJobTitleAdapter;
import nts.uk.ctx.at.schedule.dom.employeeinfo.employeesort.EmpClassifiImport;
import nts.uk.ctx.at.schedule.dom.employeeinfo.employeesort.EmployeePosition;
import nts.uk.ctx.at.schedule.dom.employeeinfo.employeesort.SortSetting;
import nts.uk.ctx.at.schedule.dom.employeeinfo.employeesort.SortSettingRepository;
import nts.uk.ctx.at.schedule.dom.employeeinfo.rank.EmployeeRank;
import nts.uk.ctx.at.schedule.dom.employeeinfo.rank.EmployeeRankRepository;
import nts.uk.ctx.at.schedule.dom.employeeinfo.rank.RankPriority;
import nts.uk.ctx.at.schedule.dom.employeeinfo.rank.RankRepository;
import nts.uk.ctx.at.schedule.dom.employeeinfo.scheduleteam.BelongScheduleTeam;
import nts.uk.ctx.at.schedule.dom.employeeinfo.scheduleteam.BelongScheduleTeamRepository;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.medicalworkstyle.EmpMedicalWorkStyleHistoryItem;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.medicalworkstyle.EmpMedicalWorkStyleHistoryRepository;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.medicalworkstyle.NurseClassifiCode;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.medicalworkstyle.NurseClassification;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.medicalworkstyle.NurseClassificationRepository;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.EmployeeSearchCallSystemType;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.GetEmpCanReferService;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.RegulationInfoEmpQuery;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.adapter.WorkplaceGroupAdapter;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.adapter.WorkplaceGroupImport;
import nts.uk.ctx.sys.auth.dom.algorithm.AcquireUserIDFromEmpIDService;
import nts.uk.query.pub.employee.EmployeeSearchQueryDto;
import nts.uk.query.pub.employee.RegulationInfoEmployeeExport;
import nts.uk.query.pub.employee.RegulationInfoEmployeePub;
import nts.uk.shr.com.context.AppContexts;

/**
 * 職場グループの関係情報を取得する
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class GetWkpGroupRelatedInfoFileQuery {
    @Inject
    private WorkplaceGroupAdapter workplaceGroupAdapter;
    @Inject
    private RegulationInfoEmployeeAdapter regulInfoEmployeeAdap;
    @Inject
    private RegulationInfoEmployeePub regulInfoEmpPub;
    @Inject
    private AcquireUserIDFromEmpIDService acquireUserIDFromEmpIDService;

    @Inject
    private SortSettingRepository sortSettingRepo;

    @Inject
    private BelongScheduleTeamRepository belongScheduleTeamRepo;
    @Inject
    private EmployeeRankRepository employeeRankRepo;
    @Inject
    private RankRepository rankRepo;
    @Inject
    private SyJobTitleAdapter syJobTitleAdapter;
    @Inject
    private SyClassificationAdapter syClassificationAdapter;
    @Inject
    private EmpMedicalWorkStyleHistoryRepository empMedicalWorkStyleHisRepo;
    @Inject
    private NurseClassificationRepository nurseClassificationRepo;

    @Inject
    private EmployeeInformationAdapter employeeInformationAdapter;

    @Inject
    private GetDisplayAndAggregatedInfoFileQuery fileQuery;

    /**
     * 取得する
     * @param workplaceGroup 職場グループ
     * @param period 対処期間
     * @param printTarget 印刷対象
     * @param personalCounters 個人計
     * @param workplaceCounters 職場計
     * @return
     */
    public WkpGroupRelatedDisplayInfoDto get(WorkplaceGroupImport workplaceGroup, DatePeriod period, int printTarget, List<Integer> personalCounters, List<Integer> workplaceCounters) {
        String companyId = AppContexts.user().companyId();

        TargetOrgIdenInfor targetOrg = TargetOrgIdenInfor.creatIdentifiWorkplaceGroup(workplaceGroup.getWorkplaceGroupId());

        List<String> employeeIds = GetEmpCanReferService.getByOrg(
                new GetEmpCanReferService.Require() {
                    @Override
                    public List<String> getEmpCanReferByWorkplaceGroup(String empId, GeneralDate date, DatePeriod period, String workplaceGroupID) {
                        return workplaceGroupAdapter.getReferableEmp(empId, date, period, workplaceGroupID);
                    }
					@Override
					public List<String> getAllEmpCanReferByWorkplaceGroup(String empId, GeneralDate date, DatePeriod period) {
						// TODO 自動生成されたメソッド・スタブ
						return null;
					}
                    @Override
                    public List<String> sortEmployee(List<String> employeeIdList, EmployeeSearchCallSystemType systemType, Integer sortOrderNo, GeneralDate date, Integer nameType) {
                        return regulInfoEmployeeAdap.sortEmployee(companyId, employeeIdList, systemType.value, sortOrderNo, nameType, GeneralDateTime.fromString(date.toString() + " 00:00", "yyyy/MM/dd HH:mm"));
                    }
                    @Override
                    public String getRoleID() {
                        Optional<String> userID = acquireUserIDFromEmpIDService.getUserIDByEmpID(AppContexts.user().employeeId());
                        if (!userID.isPresent()) {
                            return null;
                        }
                        String roleId = AppContexts.user().roles().forAttendance();
                        return roleId;
                    }
                    @Override
                    public List<String> searchEmployee(RegulationInfoEmpQuery regulationInfoEmpQuery, String roleId) {
                        EmployeeSearchQueryDto query = EmployeeSearchQueryDto.builder()
                                .baseDate(GeneralDateTime.fromString(regulationInfoEmpQuery.getBaseDate().toString() + " 00:00", "yyyy/MM/dd HH:mm"))
                                .referenceRange(regulationInfoEmpQuery.getReferenceRange().value)
                                .systemType(regulationInfoEmpQuery.getSystemType().value)
                                .filterByWorkplace(regulationInfoEmpQuery.getFilterByWorkplace())
                                .workplaceCodes(regulationInfoEmpQuery.getWorkplaceIds())
                                .filterByEmployment(false)
                                .employmentCodes(new ArrayList<>())
                                .filterByDepartment(false)
                                .departmentCodes(new ArrayList<>())
                                .filterByClassification(false)
                                .classificationCodes(new ArrayList<>())
                                .filterByJobTitle(false)
                                .jobTitleCodes(new ArrayList<>())
                                .filterByWorktype(false)
                                .worktypeCodes(new ArrayList<>())
                                .filterByClosure(false)
                                .closureIds(new ArrayList<>())
                                .periodStart(GeneralDateTime.now())
                                .periodEnd(GeneralDateTime.now())
                                .includeIncumbents(true)
                                .includeWorkersOnLeave(true)
                                .includeOccupancy(true)
                                .includeRetirees(false)
                                .includeAreOnLoan(false)
                                .includeGoingOnLoan(false)
                                .retireStart(GeneralDateTime.now())
                                .retireEnd(GeneralDateTime.now())
                                .sortOrderNo(null)
                                .nameType(null)
                                .build();
                        List<RegulationInfoEmployeeExport> data = regulInfoEmpPub.find(query);
                        return data.stream().map(RegulationInfoEmployeeExport::getEmployeeId).collect(Collectors.toList());
                    }
                },
                AppContexts.user().employeeId(),
                period.end(),
                period,
                targetOrg
        );

        SortSetting sortSetting = sortSettingRepo.get(AppContexts.user().companyId()).orElseGet(() -> {
            throw new RuntimeException("SortSetting Not Found!");
        });
        employeeIds = sortSetting.sort(
                new SortSetting.Require() {
                    @Override
                    public List<BelongScheduleTeam> getScheduleTeam(List<String> empIDs) {
                        return belongScheduleTeamRepo.get(companyId, empIDs);
                    }
                    @Override
                    public List<EmployeeRank> getEmployeeRanks(List<String> lstSID) {
                        return employeeRankRepo.getAll(lstSID);
                    }
                    @Override
                    public Optional<RankPriority> getRankPriorities() {
                        return rankRepo.getRankPriority(companyId);
                    }
                    @Override
                    public List<EmployeePosition> getPositionEmps(GeneralDate ymd, List<String> lstEmp) {
                        return syJobTitleAdapter.findSJobHistByListSIdV2(lstEmp, ymd);
                    }
                    @Override
                    public List<PositionImport> getCompanyPosition(GeneralDate ymd) {
                        return syJobTitleAdapter.findAll(companyId, ymd);
                    }
                    @Override
                    public List<EmpClassifiImport> getEmpClassifications(GeneralDate ymd, List<String> lstEmpId) {
                        return syClassificationAdapter.getByListSIDAndBasedate(ymd, lstEmpId);
                    }
                    @Override
                    public List<EmpMedicalWorkStyleHistoryItem> getEmpMedicalWorkStyleHistoryItem(List<String> listEmp, GeneralDate referenceDate) {
                        return empMedicalWorkStyleHisRepo.get(listEmp, referenceDate);
                    }
                    @Override
                    public List<NurseClassification> getListCompanyNurseCategory() {
                        return nurseClassificationRepo.getListCompanyNurseCategory(companyId);
                    }
                },
                period.end(),
                employeeIds
        );

        Map<String, EmployeeInformationImport> mapEmpInfo = employeeInformationAdapter.getEmployeeInfo(new EmployeeInformationQueryDtoImport(
                employeeIds,
                period.end(),
                false,
                false,
                false,
                false,
                false,
                false
        )).stream().collect(Collectors.toMap(EmployeeInformationImport::getEmployeeId, Function.identity()));

        List<EmpMedicalWorkStyleHistoryItem> empMedicalWorkStyleHistoryItems = empMedicalWorkStyleHisRepo.get(employeeIds, period.end());

        Map<NurseClassifiCode, NurseClassification> nurseClassificationMap = nurseClassificationRepo.getListCompanyNurseCategory(companyId)
                .stream().collect(Collectors.toMap(NurseClassification::getNurseClassifiCode, Function.identity()));

        List<EmployeeInfoDto> employeeInfoDtos = empMedicalWorkStyleHistoryItems.stream().map(item -> {
            EmployeeInformationImport empInfo = mapEmpInfo.get(item.getEmpID());
            NurseClassification nurseClassification = nurseClassificationMap.get(item.getNurseClassifiCode());
            return new EmployeeInfoDto(
                    item.getEmpID(),
                    empInfo == null ? null : empInfo.getBusinessName(),
                    nurseClassification == null ? null : nurseClassification.getNurseClassifiName().v()
            );
        }).collect(Collectors.toList());

        EmployeeDisplayAndAggregatedInfoDto displayAndAggregatedInfo = fileQuery.get(employeeIds, period, printTarget, personalCounters, workplaceCounters);

        return new WkpGroupRelatedDisplayInfoDto(
                workplaceGroup.getWorkplaceGroupId(),
                workplaceGroup.getWorkplaceGroupCode(),
                workplaceGroup.getWorkplaceGroupName(),
                employeeInfoDtos,
                displayAndAggregatedInfo.getPersonalCounterResult(),
                displayAndAggregatedInfo.getWorkplaceCounterResult(),
                displayAndAggregatedInfo.getShiftDisplayInfos(),
                displayAndAggregatedInfo.getPersonalTotals(),
                displayAndAggregatedInfo.getWorkplaceTotals()
        );
    }
}
