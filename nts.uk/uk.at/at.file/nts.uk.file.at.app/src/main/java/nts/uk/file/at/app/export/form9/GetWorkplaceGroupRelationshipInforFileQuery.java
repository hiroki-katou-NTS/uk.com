package nts.uk.file.at.app.export.form9;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import lombok.val;
import nts.arc.primitive.PrimitiveValueBase;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.aggregation.dom.adapter.dailyrecord.DailyRecordAdapter;
import nts.uk.ctx.at.aggregation.dom.adapter.workschedule.WorkScheduleAdapter;
import nts.uk.ctx.at.aggregation.dom.common.ScheRecGettingAtr;
import nts.uk.ctx.at.aggregation.dom.form9.EmployeeIdAndYmd;
import nts.uk.ctx.at.aggregation.dom.form9.Form9OutputEmployeeInfoList;
import nts.uk.ctx.at.aggregation.dom.form9.GetForm9OutputEmployeeInfoService;
import nts.uk.ctx.at.aggregation.dom.form9.GetMedicalTimeOfEmployeeService;
import nts.uk.ctx.at.aggregation.dom.form9.MedicalTimeOfEmployee;
import nts.uk.ctx.at.function.dom.adapter.RegulationInfoEmployeeAdapter;
import nts.uk.ctx.at.shared.dom.adapter.jobtitle.SharedAffJobTitleHisImport;
import nts.uk.ctx.at.shared.dom.adapter.jobtitle.SharedAffJobtitleHisAdapter;
import nts.uk.ctx.at.shared.dom.common.EmployeeId;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.medicalworkstyle.EmpMedicalWorkStyleHistoryItem;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.medicalworkstyle.EmpMedicalWorkStyleHistoryRepository;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.medicalworkstyle.NurseClassifiCode;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.medicalworkstyle.NurseClassification;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.medicalworkstyle.NurseClassificationRepository;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.shortworktime.SWorkTimeHistItemRepository;
import nts.uk.ctx.at.shared.dom.shortworktime.ShortWorkTimeHistoryItem;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.employeeinfor.EmployeeCodeAndDisplayNameImport;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.employeeinfor.employee.EmployeeAdapter;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.EmployeeSearchCallSystemType;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.RegulationInfoEmpQuery;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.adapter.WorkplaceGroupAdapter;
import nts.uk.ctx.bs.employee.dom.workplace.group.hospitalofficeinfo.HospitalBusinessOfficeInfoHistoryRepository;
import nts.uk.ctx.sys.auth.dom.algorithm.AcquireUserIDFromEmpIDService;
import nts.uk.file.at.app.export.form9.dto.DisplayInfoRelatedToWorkplaceGroupDto;
import nts.uk.file.at.app.export.form9.dto.WorkplaceGroupInfoDto;
import nts.uk.query.pub.employee.EmployeeSearchQueryDto;
import nts.uk.query.pub.employee.RegulationInfoEmployeeExport;
import nts.uk.query.pub.employee.RegulationInfoEmployeePub;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class GetWorkplaceGroupRelationshipInforFileQuery {
    private final static String SPACE = " ";
    private final static String ZERO_TIME = "00:00";
    private final static String DATE_TIME_FORMAT = "yyyy/MM/dd HH:mm";

    @Inject
    private HospitalBusinessOfficeInfoHistoryRepository hospitalBusinessOfficeRepo;

    @Inject
    private WorkplaceGroupAdapter workplaceGroupAdapter;

    @Inject
    private RegulationInfoEmployeeAdapter regulInfoEmpAdap;

    @Inject
    private RegulationInfoEmployeePub regulInfoEmpPub;

    @Inject
    private AcquireUserIDFromEmpIDService acquireUserIDFromEmpIDService;

    @Inject
    private NurseClassificationRepository nurseClassificationRepo;

    @Inject
    private EmpMedicalWorkStyleHistoryRepository empMedicalWorkStyleHisRepo;

    @Inject
    private EmployeeAdapter employeeShareAdap;

    @Inject
    private SharedAffJobtitleHisAdapter affJobTitleAdapter;

    @Inject
    private SWorkTimeHistItemRepository sWorkTimeHistItemRepo;

    @Inject
    private WorkScheduleAdapter workScheduleAdapter;

    @Inject
    private DailyRecordAdapter dailyRecordAdapter;

    public DisplayInfoRelatedToWorkplaceGroupDto get(WorkplaceGroupInfoDto workplaceGroup, DatePeriod period, int acquireTarget) {

        // 1. get 病棟・事業所情報履歴
        val hospitalBusinessOfficeOpt = hospitalBusinessOfficeRepo.get(workplaceGroup.getId(), period.end());

        // 2. 取得する(Require, 職場グループID, 年月日)
        val requireOutputEmployeeImpl = new RequireOutputEmployeeImpl(workplaceGroupAdapter,
                regulInfoEmpAdap, regulInfoEmpPub, acquireUserIDFromEmpIDService, nurseClassificationRepo, empMedicalWorkStyleHisRepo,
                employeeShareAdap, affJobTitleAdapter, sWorkTimeHistItemRepo);
        Form9OutputEmployeeInfoList outputEmpInfoList = GetForm9OutputEmployeeInfoService.get(
                requireOutputEmployeeImpl,
                workplaceGroup.getId(),
                period.end());

        // 3. 取得する(Require, List<社員ID>, 期間, 予実取得区分)
        // result: Map<社員IDと年月日,社員の出力医療時間>
        // 社員IDリスト＝２の結果の様式９の出力社員情報リスト.社員IDリストを取得する() で取得する
        val employeeIds = outputEmpInfoList.getEmployeeIdList().stream().map(EmployeeId::new).collect(Collectors.toList());
        Map<EmployeeIdAndYmd, MedicalTimeOfEmployee> medicalTimeOfEmployeeMap = GetMedicalTimeOfEmployeeService.get(
                new RequireMedicalTimeImpl(workScheduleAdapter, dailyRecordAdapter),
                employeeIds,
                period,
                acquireTarget == 0 ? ScheRecGettingAtr.ONLY_SCHEDULE : acquireTarget == 1 ? ScheRecGettingAtr.ONLY_RECORD :
                        acquireTarget == 2 ? ScheRecGettingAtr.SCHEDULE_WITH_RECORD : null);

        // 4. Create dto
        return new DisplayInfoRelatedToWorkplaceGroupDto(
                medicalTimeOfEmployeeMap,
                hospitalBusinessOfficeOpt,
                workplaceGroup.getId(),
                workplaceGroup.getCode(),
                workplaceGroup.getName(),
                outputEmpInfoList.getEmployeeInfoList()
        );
    }

    @AllArgsConstructor
    public static class RequireOutputEmployeeImpl implements GetForm9OutputEmployeeInfoService.Require {

        private WorkplaceGroupAdapter workplaceGroupAdapter;

        private RegulationInfoEmployeeAdapter regulInfoEmpAdap;

        private RegulationInfoEmployeePub regulInfoEmpPub;

        private AcquireUserIDFromEmpIDService acquireUserIDFromEmpIDService;

        private NurseClassificationRepository nurseClassificationRepo;

        private EmpMedicalWorkStyleHistoryRepository empMedicalWorkStyleHisRepo;

        private EmployeeAdapter employeeShareAdap;

        private SharedAffJobtitleHisAdapter affJobTitleAdapter;

        private SWorkTimeHistItemRepository sWorkTimeHistItemRepo;

        @Override
        public String getLoginEmployeeId() {
            return AppContexts.user().employeeId();
        }

        @Override
        public Optional<EmployeeCodeAndDisplayNameImport> getPersonEmployeeBasicInfo(String employeeId) {
            return employeeShareAdap.getEmployeeCodeAndNameByEmployeeId(employeeId);
        }

        @Override
        public Optional<EmpMedicalWorkStyleHistoryItem> getEmpMedicalWorkStyleHistoryItem(String employeeId, GeneralDate baseDate) {
            return empMedicalWorkStyleHisRepo.get(employeeId, baseDate);
        }

        @Override
        public Optional<ShortWorkTimeHistoryItem> getShortWorkTimeHistoryItem(String employeeId, GeneralDate baseDate) {
            return sWorkTimeHistItemRepo.findByEmployeeIdAndDate(employeeId, baseDate);
        }

        @Override
        public Optional<NurseClassification> getNurseClassification(NurseClassifiCode nurseClassifiCode) {
            return nurseClassificationRepo.getSpecifiNurseCategory(AppContexts.user().companyId(), nurseClassifiCode.v());
        }

        @Override
        public List<SharedAffJobTitleHisImport> getEmployeeJobTitle(GeneralDate baseDate, List<String> employeeIds) {
            return affJobTitleAdapter.findAffJobTitleHisByListSid(employeeIds, baseDate);
        }

        @Override
        public List<EmployeeCodeAndDisplayNameImport> getEmployeeCodeAndDisplayNameImportByEmployeeIds(List<String> employeeIds) {
            return employeeShareAdap.getEmployeeCodeAndDisplayNameImportByEmployeeIds(employeeIds);
        }

        @Override
        public List<EmpMedicalWorkStyleHistoryItem> getEmpMedicalWorkStyleHistoryItem(List<String> listEmp, GeneralDate referenceDate) {
            return empMedicalWorkStyleHisRepo.get(listEmp, referenceDate);
        }

        @Override
        public List<NurseClassification> getListCompanyNurseCategory() {
            return nurseClassificationRepo.getListCompanyNurseCategory(AppContexts.user().companyId());
        }

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
            return regulInfoEmpAdap.sortEmployee(AppContexts.user().companyId(), employeeIdList, systemType.value, sortOrderNo, nameType,
                    GeneralDateTime.fromString(date.toString() + SPACE + ZERO_TIME, DATE_TIME_FORMAT));
        }

        @Override
        public String getRoleID() {
            Optional<String> userID = acquireUserIDFromEmpIDService.getUserIDByEmpID(AppContexts.user().employeeId());
            if (!userID.isPresent()) {
                return null;
            }
            return AppContexts.user().roles().forAttendance();
        }

        @Override
        public List<String> searchEmployee(RegulationInfoEmpQuery q, String roleId) {
            EmployeeSearchQueryDto query = EmployeeSearchQueryDto.builder()
                    .baseDate(GeneralDateTime.fromString(q.getBaseDate().toString() + SPACE + ZERO_TIME, DATE_TIME_FORMAT))
                    .referenceRange(q.getReferenceRange().value)
                    .systemType(q.getSystemType().value)
                    .filterByWorkplace(q.getFilterByWorkplace())
                    .workplaceCodes(q.getWorkplaceIds())
                    .filterByEmployment(false)
                    .employmentCodes(new ArrayList<String>())
                    .filterByDepartment(false)
                    .departmentCodes(new ArrayList<String>())
                    .filterByClassification(false)
                    .classificationCodes(new ArrayList<String>())
                    .filterByJobTitle(false)
                    .jobTitleCodes(new ArrayList<String>())
                    .filterByWorktype(false)
                    .worktypeCodes(new ArrayList<String>())
                    .filterByClosure(false)
                    .closureIds(new ArrayList<Integer>())
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
            return data.stream().map(RegulationInfoEmployeeExport::getEmployeeId)
                    .collect(Collectors.toList());
        }
    }

    @AllArgsConstructor
    public static class RequireMedicalTimeImpl implements GetMedicalTimeOfEmployeeService.Require {

        private WorkScheduleAdapter workScheduleAdapter;

        private DailyRecordAdapter dailyRecordAdapter;

        @Override
        public List<IntegrationOfDaily> getSchduleList(List<EmployeeId> empIds, DatePeriod period) {
            return workScheduleAdapter.getList(empIds.stream().map(PrimitiveValueBase::v).collect(Collectors.toList()), period);
        }

        @Override
        public List<IntegrationOfDaily> getRecordList(List<EmployeeId> empIds, DatePeriod period) {
            return dailyRecordAdapter.getDailyRecordByScheduleManagement(empIds.stream().map(PrimitiveValueBase::v).collect(Collectors.toList()), period);
        }
    }
}
