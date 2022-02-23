package nts.uk.screen.at.app.query.kdl.kdl016;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.function.dom.adapter.RegulationInfoEmployeeAdapter;
import nts.uk.ctx.at.function.dom.adapter.annualworkschedule.EmployeeInformationAdapter;
import nts.uk.ctx.at.function.dom.adapter.annualworkschedule.EmployeeInformationImport;
import nts.uk.ctx.at.function.dom.adapter.annualworkschedule.EmployeeInformationQueryDtoImport;
import nts.uk.ctx.at.record.app.find.dailyperform.dto.TimeSpanForCalcDto;
import nts.uk.ctx.at.schedule.dom.schedule.support.SupportFunctionControl;
import nts.uk.ctx.at.schedule.dom.schedule.support.SupportFunctionControlRepository;
import nts.uk.ctx.at.shared.app.find.supportmanagement.supportalloworg.SupportAllowOrganizationFinder;
import nts.uk.ctx.at.shared.dom.common.EmployeeId;
import nts.uk.ctx.at.shared.dom.supportmanagement.supportableemployee.SupportableEmployee;
import nts.uk.ctx.at.shared.dom.supportmanagement.supportableemployee.SupportableEmployeeRepository;
import nts.uk.ctx.at.shared.dom.supportmanagement.supportalloworg.SupportAllowOrganization;
import nts.uk.ctx.at.shared.dom.supportmanagement.supportoperationsetting.SupportOperationSetting;
import nts.uk.ctx.at.shared.dom.supportmanagement.supportoperationsetting.SupportOperationSettingRepository;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.*;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.adapter.EmpAffiliationInforAdapter;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.adapter.EmpOrganizationImport;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.adapter.WorkplaceGroupAdapter;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.adapter.WorkplaceGroupImport;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.AffWorkplaceAdapter;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.WorkplaceExportServiceAdapter;
import nts.uk.query.pub.employee.EmployeeSearchQueryDto;
import nts.uk.query.pub.employee.RegulationInfoEmployeeExport;
import nts.uk.query.pub.employee.RegulationInfoEmployeePub;
import nts.uk.screen.at.app.query.kdl.kdl016.dto.*;
import nts.uk.shr.com.context.AppContexts;
import org.apache.logging.log4j.util.Strings;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.*;
import java.util.stream.Collectors;

@Stateless
public class SupportInformationFinder {
    @Inject
    private SupportOperationSettingRepository supportOperationSettingRepo;

    @Inject
    private SupportFunctionControlRepository supportFuncCtrlRepo;

    @Inject
    private SupportableEmployeeRepository supportableEmployeeRepo;

    @Inject
    private WorkplaceGroupAdapter groupAdapter;

    @Inject
    private WorkplaceExportServiceAdapter serviceAdapter;

    @Inject
    private AffWorkplaceAdapter wplAdapter;

    @Inject
    private EmployeeInformationAdapter employeeInfoAdapter;

    @Inject
    private EmpAffiliationInforAdapter empAffiliationInforAdapter;

    @Inject
    private WorkplaceGroupAdapter workplaceGroupAdapter;

    @Inject
    private RegulationInfoEmployeeAdapter regulInfoEmployeeAdap;

    @Inject
    private RegulationInfoEmployeePub regulInfoEmpPub;

    @Inject
    private SupportAllowOrganizationFinder supportAllowOrganizationFinder;

    final static String SPACE = " ";
    final static String ZEZO_TIME = "00:00";
    final static String DATE_TIME_FORMAT = "yyyy/MM/dd HH:mm";

    /**
     * 初期起動の情報を取得する
     * UKDesign.UniversalK.就業.KDL_ダイアログ.KDL016_応援予定ダイアログ.A:応援予定一覧.メニュー別OCD.起動する
     *
     * @param employeeIds
     * @param period
     * @return List<応援情報DTO
                    */
    public List<SupportInfoDto> getDataInitScreenA(List<String> employeeIds, DatePeriod period) {
        String cid = AppContexts.user().companyId();
        // 応援の運用設定
        SupportOperationSetting supportOperationSetting = supportOperationSettingRepo.get(cid);

        // 応援予定の機能制御
        SupportFunctionControl supportFuncCtrl = null;
        // 応援の運用設定.利用するか==TRUE
        if (supportOperationSetting.isUsed()) {
            supportFuncCtrl = supportFuncCtrlRepo.get(cid);
        }

        //応援の運用設定.利用するか or 応援予定の機能制御.応援予定を利用するか==FALSE
        if (!supportOperationSetting.isUsed() || (supportFuncCtrl != null && !supportFuncCtrl.isUse())) {
            throw new BusinessException("Msg_3240");
        }

        // 応援の運用設定.利用するか and 応援予定の機能制御.応援予定を利用するか==TRUE
        List<SupportInfoDto> supportInfos = new ArrayList<>();
        if (supportOperationSetting.isUsed() && supportFuncCtrl != null && supportFuncCtrl.isUse()) {
            supportInfos = this.getInfoGoToSupport(employeeIds, period);
        }

        return supportInfos;
    }

    /**
     * 表示モードを切り替える
     * UKDesign.UniversalK.就業.KDL_ダイアログ.KDL016_応援予定ダイアログ.A:応援予定一覧.メニュー別OCD.表示モードを切り替える
     */
    public List<SupportInfoDto> getInfoByDisplayMode(SupportInfoInput input) {

        // 表示モード＝＝応援に行く情報
        if (input.getDisplayMode() == DisplayMode.GO_TO_SUPPORT.value) { //応援に行く情報
            return this.getInfoGoToSupport(input.getEmployeeIds(), input.getPeriod());
        } else { // 表示モード＝＝応援に来る情報: DisplayMode.GO_TO_SUPPORT
            return this.getInfoComeToSupport(input.getTargetOrg().getOrgId(), input.getTargetOrg().getOrgUnit(), input.getPeriod());
        }
    }

    /**
     * 応援に行く情報を取得する
     * UKDesign.UniversalK.就業.KDL_ダイアログ.KDL016_応援予定ダイアログ.A:応援予定一覧.メニュー別OCD.応援に行く情報を取得する.応援に行く情報を取得する
     *
     * @param employeeIds
     * @param period
     * @return
     */
    public List<SupportInfoDto> getInfoGoToSupport(List<String> employeeIds, DatePeriod period) {
        List<EmployeeId> sIds = employeeIds.stream().map(EmployeeId::new).collect(Collectors.toList());

        //1. List<応援可能な社員> : 社員と期間を指定して取得する()
        List<SupportableEmployee> supportableEmployees = supportableEmployeeRepo.findByEmployeeIdWithPeriod(sIds, period);

        //4. 社員の情報を取得する
        val supportEmpIds = supportableEmployees.stream().map(x -> x.getEmployeeId().v()).distinct().collect(Collectors.toList());
        List<EmployeeInformationImport> supportEmployeeInfos = this.employeeInfoAdapter
                .getEmployeeInfo(new EmployeeInformationQueryDtoImport(supportEmpIds, period.end(), false, false, false,
                        false, false, false));

        RequireOrgInfoImpl requireOrgInfo = new RequireOrgInfoImpl(groupAdapter, serviceAdapter, wplAdapter);
        List<SupportInfoDto> supportInfoResults = new ArrayList<>();
        for (SupportableEmployee supportableEmployee : supportableEmployees) {
            // 組織の表示情報を取得する(Require, 年月日): output 組織の表示情報
            DisplayInfoOrganization orgInfo = supportableEmployee.getRecipient().getDisplayInfor(requireOrgInfo, period.end());


            val employeeInfoOpt = supportEmployeeInfos.stream().filter(x -> x.getEmployeeId().equals(supportableEmployee.getEmployeeId().v())).findFirst();
            supportInfoResults.add(new SupportInfoDto(
                    supportableEmployee.getId(),
                    supportableEmployee.getPeriod(),
                    employeeInfoOpt.isPresent() ? employeeInfoOpt.get().getEmployeeCode() : Strings.EMPTY,
                    employeeInfoOpt.isPresent() ? employeeInfoOpt.get().getBusinessName() : Strings.EMPTY,
                    orgInfo.getDisplayName(),
                    supportableEmployee.getRecipient().getUnit() == TargetOrganizationUnit.WORKPLACE
                            ? supportableEmployee.getRecipient().getWorkplaceId().orElse(null)
                            : supportableEmployee.getRecipient().getWorkplaceGroupId().orElse(null),
                    supportableEmployee.getRecipient().getUnit().value,
                    supportableEmployee.getSupportType().getValue(),
                    supportableEmployee.getTimespan().isPresent() ? new TimeSpanForCalcDto(supportableEmployee.getTimespan().get().start(), supportableEmployee.getTimespan().get().end()) : null
            ));
        }

        return supportInfoResults;
    }

    /**
     * 応援に来る情報を取得する
     * UKDesign.UniversalK.就業.KDL_ダイアログ.KDL016_応援予定ダイアログ.A:応援予定一覧.メニュー別OCD.応援に来る情報を取得する.応援に来る情報取得
     *
     * @param orgId
     * @param orgUnit
     * @param period
     * @return
     */
    public List<SupportInfoDto> getInfoComeToSupport(String orgId, int orgUnit, DatePeriod period) {
        List<SupportInfoDto> supportInfoResults = new ArrayList<>();

        // 1. 対象組織識別情報
        TargetOrgIdenInfor targetOrgIdenInfor = orgUnit == TargetOrganizationUnit.WORKPLACE.value
                ? TargetOrgIdenInfor.creatIdentifiWorkplace(orgId)
                : TargetOrgIdenInfor.creatIdentifiWorkplaceGroup(orgId);

        // 2. List<応援可能な社員>
        List<SupportableEmployee> supportableEmployees = supportableEmployeeRepo.findByRecipientWithPeriod(targetOrgIdenInfor, period);

        // 3.
        // 4.
        val supportEmpIds = supportableEmployees.stream().map(x -> x.getEmployeeId().v()).collect(Collectors.toList());
        List<EmployeeInformationImport> supportEmployeeInfos = this.employeeInfoAdapter
                .getEmployeeInfo(new EmployeeInformationQueryDtoImport(supportEmpIds, period.end(), false, false, false,
                        false, false, false));

        RequireOrgImpl requireOrg = new RequireOrgImpl(empAffiliationInforAdapter);
        RequireOrgInfoImpl requireOrgInfo = new RequireOrgInfoImpl(groupAdapter, serviceAdapter, wplAdapter);
        for (SupportableEmployee supportableEmployee : supportableEmployees) {
            // 5.1. 取得する(Require, 年月日, 社員ID)
            TargetOrgIdenInfor orgInfor = GetTargetIdentifiInforService.get(
                    requireOrg,
                    supportableEmployee.getPeriod().end(),
                    supportableEmployee.getEmployeeId().v()
            );
            // 5.2 組織の表示情報 : 組織の表示情報を取得する(Require, 年月日)
            DisplayInfoOrganization orgInfo = orgInfor.getDisplayInfor(requireOrgInfo, supportableEmployee.getPeriod().end());

            val employeeInfoOpt = supportEmployeeInfos.stream().filter(x -> x.getEmployeeId().equals(supportableEmployee.getEmployeeId().v())).findFirst();
            supportInfoResults.add(new SupportInfoDto(
                    supportableEmployee.getId(),
                    supportableEmployee.getPeriod(),
                    employeeInfoOpt.isPresent() ? employeeInfoOpt.get().getEmployeeCode() : Strings.EMPTY,
                    employeeInfoOpt.isPresent() ? employeeInfoOpt.get().getBusinessName() : Strings.EMPTY,
                    orgInfo.getDisplayName(),
                    supportableEmployee.getRecipient().getUnit() == TargetOrganizationUnit.WORKPLACE
                            ? supportableEmployee.getRecipient().getWorkplaceId().orElse(null)
                            : supportableEmployee.getRecipient().getWorkplaceGroupId().orElse(null),
                    supportableEmployee.getRecipient().getUnit().value,
                    supportableEmployee.getSupportType().getValue(),
                    supportableEmployee.getTimespan().isPresent() ? new TimeSpanForCalcDto(supportableEmployee.getTimespan().get().start(), supportableEmployee.getTimespan().get().end()) : null
            ));

        }

        return supportInfoResults;
    }

    /**
     * 初期起動の情報を取得する
     * UKDesign.UniversalK.就業.KDL_ダイアログ.KDL016_応援予定ダイアログ.B:応援予定に行く情報の追加.メニュー別OCD.初期起動の情報を取得する.初期起動の情報取得
     *
     * @param orgId
     * @param orgUnit
     * @param employeeIds
     * @return
     */
    public Kdl016ScreenBOutput getDataInitScreenB(List<String> employeeIds, String orgId, int orgUnit) {
        GeneralDate baseDate = GeneralDate.today();
        // 1. 単位＝＝職場: 対象組織識別情報
        // 2. 単位＝＝職場グループ: 対象組織識別情報
        TargetOrgIdenInfor targetOrgIdenInfor = orgUnit == TargetOrganizationUnit.WORKPLACE.value
                ? TargetOrgIdenInfor.creatIdentifiWorkplace(orgId)
                : TargetOrgIdenInfor.creatIdentifiWorkplaceGroup(orgId);

        // 3. List<応援許可する組織>: 取得する(対象組織識別情報)
        List<SupportAllowOrganization> supportAllowOrgs = supportAllowOrganizationFinder.getListByTargetOrg(AppContexts.user().companyId(), targetOrgIdenInfor);

        // 4. List<応援許可する組織> empty
        if (supportAllowOrgs.isEmpty()) {
            throw new BusinessException("Msg_3279");
        }

        RequireOrgInfoImpl requireOrgInfo = new RequireOrgInfoImpl(groupAdapter, serviceAdapter, wplAdapter);
        List<OrganizationDisplayInfoDto> orgDisplayInfoList = new ArrayList<>();
        for (SupportAllowOrganization supportAllowOrg : supportAllowOrgs) {
            DisplayInfoOrganization displayInfoOrg = supportAllowOrg.getSupportableOrganization().getDisplayInfor(requireOrgInfo, baseDate);
            orgDisplayInfoList.add(new OrganizationDisplayInfoDto(
                    supportAllowOrg.getSupportableOrganization().getUnit() == TargetOrganizationUnit.WORKPLACE
                            ? supportAllowOrg.getSupportableOrganization().getWorkplaceId().orElse(null)
                            : supportAllowOrg.getSupportableOrganization().getWorkplaceId().orElse(null),
                    supportAllowOrg.getSupportableOrganization().getUnit().value,
                    displayInfoOrg.getCode(),
                    displayInfoOrg.getDisplayName()
            ));
        }

        List<EmployeeInformationImport> employeeInfos = this.employeeInfoAdapter
                .getEmployeeInfo(new EmployeeInformationQueryDtoImport(employeeIds, baseDate, false, false, false,
                        false, false, false));

        return new Kdl016ScreenBOutput(
                employeeInfos.stream().map(e -> new EmployeeInformationDto(e.getEmployeeId(), e.getEmployeeCode(), e.getBusinessName())).collect(Collectors.toList()),
                orgDisplayInfoList
        );
    }

    /**
     * UKDesign.UniversalK.就業.KDL_ダイアログ.KDL016_応援予定ダイアログ.C:応援予定に来る情報の追加.メニュー別OCD.起動する
     *
     * @param orgId
     * @param orgUnit
     * @param period
     * @return
     */
    public Kdl016ScreenBOutput getDataInitScreenC(String orgId, int orgUnit, DatePeriod period) {
        // 1.取得する(組織ID, 単位, 期間)
        List<OrganizationDisplayInfoDto> organizationDisplayInfos = this.getOrgInfoOfSupportSource(orgId, orgUnit, period);

        // 2. 取得する(組織ID, 単位, 期間)
        List<EmployeeInformationDto> employeeInfos = this.getEmployeeInfo(orgId, orgUnit, period);

        return new Kdl016ScreenBOutput(employeeInfos, organizationDisplayInfos);
    }

    /**
     * 応援元の組織情報を取得する
     * UKDesign.UniversalK.就業.KDL_ダイアログ.KDL016_応援予定ダイアログ.C:応援予定に来る情報の追加.メニュー別OCD.応援元の組織情報を取得する.応援元の組織情報を取得する
     *
     * @param orgId
     * @param orgUnit
     * @return
     */
    public List<OrganizationDisplayInfoDto> getOrgInfoOfSupportSource(String orgId, int orgUnit, DatePeriod period) {
        GeneralDate baseDate = GeneralDate.today();
        // 1. 単位＝＝職場: 対象組織識別情報
        // 2. 単位＝＝職場グループ: 対象組織識別情報
        TargetOrgIdenInfor targetOrgIdenInfor = orgUnit == TargetOrganizationUnit.WORKPLACE.value
                ? TargetOrgIdenInfor.creatIdentifiWorkplace(orgId)
                : TargetOrgIdenInfor.creatIdentifiWorkplaceGroup(orgId);

        // 3. List<応援許可する組織>: 取得する(対象組織識別情報)
        List<SupportAllowOrganization> supportAllowOrgs = supportAllowOrganizationFinder.getListBySupportableOrg(AppContexts.user().companyId(), targetOrgIdenInfor);

        // 4. List<応援許可する組織> empty
        if (supportAllowOrgs.isEmpty()) {
            throw new BusinessException("Msg_3279");
        }

        RequireOrgInfoImpl requireOrgInfo = new RequireOrgInfoImpl(groupAdapter, serviceAdapter, wplAdapter);
        List<OrganizationDisplayInfoDto> orgDisplayInfoList = new ArrayList<>();
        for (SupportAllowOrganization supportAllowOrg : supportAllowOrgs) {
            DisplayInfoOrganization displayInfoOrg = supportAllowOrg.getSupportableOrganization().getDisplayInfor(requireOrgInfo, baseDate);
            orgDisplayInfoList.add(new OrganizationDisplayInfoDto(
                    supportAllowOrg.getSupportableOrganization().getUnit() == TargetOrganizationUnit.WORKPLACE
                            ? supportAllowOrg.getSupportableOrganization().getWorkplaceId().orElse(null)
                            : supportAllowOrg.getSupportableOrganization().getWorkplaceId().orElse(null),
                    supportAllowOrg.getSupportableOrganization().getUnit().value,
                    displayInfoOrg.getCode(),
                    displayInfoOrg.getDisplayName()
            ));
        }

        return orgDisplayInfoList;
    }

    public Kdl016ScreenBOutput.Kdl016ScreenBOutputV2 getDataInitScreenCV2(String orgId, int orgUnit, DatePeriod period) {
        // 1.取得する(組織ID, 単位, 期間)
        val organizationDisplayInfos = this.getOrgInfoOfSupportSourceV2(orgId, orgUnit, period);

        // 2. 取得する(組織ID, 単位, 期間)
        List<EmployeeInformationDto> employeeInfos = this.getEmployeeInfo(orgId, orgUnit, period);

        return new Kdl016ScreenBOutput.Kdl016ScreenBOutputV2(employeeInfos, organizationDisplayInfos);
    }

    /**
     * 応援元の組織情報を取得する
     *
     * @param orgId
     * @param orgUnit
     * @param period
     * @return Map<組織ID   、   組織の表示情報>
     */
    public Map<String, DisplayInfoOrganization> getOrgInfoOfSupportSourceV2(String orgId, int orgUnit, DatePeriod period) {
        GeneralDate baseDate = GeneralDate.today();
        Map<String, DisplayInfoOrganization> resultMap = new HashMap<>();

        // 1. 単位＝＝職場: 対象組織識別情報
        // 2. 単位＝＝職場グループ: 対象組織識別情報
        TargetOrgIdenInfor targetOrgIdenInfor = orgUnit == TargetOrganizationUnit.WORKPLACE.value
                ? TargetOrgIdenInfor.creatIdentifiWorkplace(orgId)
                : TargetOrgIdenInfor.creatIdentifiWorkplaceGroup(orgId);

        // 3. List<応援許可する組織>: 取得する(対象組織識別情報)
        List<SupportAllowOrganization> supportAllowOrgs = supportAllowOrganizationFinder.getListBySupportableOrg(AppContexts.user().companyId(), targetOrgIdenInfor);

        // 4. List<応援許可する組織> empty
        if (supportAllowOrgs.isEmpty()) {
            throw new BusinessException("Msg_3279");
        }

        RequireOrgInfoImpl requireOrgInfo = new RequireOrgInfoImpl(groupAdapter, serviceAdapter, wplAdapter);
        for (SupportAllowOrganization supportAllowOrg : supportAllowOrgs) {
            String id = supportAllowOrg.getSupportableOrganization().getUnit() == TargetOrganizationUnit.WORKPLACE
                    ? supportAllowOrg.getSupportableOrganization().getWorkplaceId().orElse(null)
                    : supportAllowOrg.getSupportableOrganization().getWorkplaceId().orElse(null);
            DisplayInfoOrganization displayInfoOrg = supportAllowOrg.getSupportableOrganization().getDisplayInfor(requireOrgInfo, baseDate);
            resultMap.put(id, displayInfoOrg);
        }

        return resultMap;
    }

    /**
     * 所属してる社員情報を取得する
     * UKDesign.UniversalK.就業.KDL_ダイアログ.KDL016_応援予定ダイアログ.C:応援予定に来る情報の追加.メニュー別OCD.所属してる社員情報を取得する.所属してる社員情報を取得する
     *
     * @param orgId
     * @param orgUnit
     * @param period
     * @return
     */
    public List<EmployeeInformationDto> getEmployeeInfo(String orgId, int orgUnit, DatePeriod period) {
        GeneralDate baseDate = GeneralDate.today();

        // 1. 単位＝＝職場: 対象組織識別情報
        // 2. 単位＝＝職場グループ: 対象組織識別情報
        TargetOrgIdenInfor targetOrgIdenInfor = orgUnit == TargetOrganizationUnit.WORKPLACE.value
                ? TargetOrgIdenInfor.creatIdentifiWorkplace(orgId)
                : TargetOrgIdenInfor.creatIdentifiWorkplaceGroup(orgId);

        //3.
        RequireEmpImpl require = new RequireEmpImpl(
                workplaceGroupAdapter,
                regulInfoEmployeeAdap,
                regulInfoEmpPub);
        List<String> sids = GetEmpCanReferService.getByOrg(require, AppContexts.user().employeeId(), baseDate, period, targetOrgIdenInfor);

        List<EmployeeInformationImport> employeeInfos = this.employeeInfoAdapter
                .getEmployeeInfo(new EmployeeInformationQueryDtoImport(sids, baseDate, false, false, false,
                        false, false, false));

        return employeeInfos.stream().map(e -> new EmployeeInformationDto(e.getEmployeeId(), e.getEmployeeCode(), e.getBusinessName())).collect(Collectors.toList());
    }

    @AllArgsConstructor
    private class RequireOrgInfoImpl implements TargetOrgIdenInfor.Require {
        private WorkplaceGroupAdapter groupAdapter;
        private WorkplaceExportServiceAdapter serviceAdapter;
        private AffWorkplaceAdapter wplAdapter;

        @Override
        public List<WorkplaceGroupImport> getSpecifyingWorkplaceGroupId(List<String> workplacegroupId) {
            return groupAdapter.getbySpecWorkplaceGroupID(workplacegroupId);
        }

        @Override
        public List<WorkplaceInfo> getWorkplaceInforFromWkpIds(List<String> listWorkplaceId, GeneralDate baseDate) {
            return serviceAdapter.getWorkplaceInforByWkpIds(AppContexts.user().companyId(), listWorkplaceId, baseDate).stream()
                    .map(mapper -> new WorkplaceInfo(mapper.getWorkplaceId(), Optional.ofNullable(mapper.getWorkplaceCode()), Optional.ofNullable(mapper.getWorkplaceName()), Optional.ofNullable(mapper.getWorkplaceExternalCode()),
                            Optional.ofNullable(mapper.getWorkplaceGenericName()), Optional.ofNullable(mapper.getWorkplaceDisplayName()), Optional.ofNullable(mapper.getHierarchyCode()))).collect(Collectors.toList());
        }

        @Override
        public List<String> getWKPID(String WKPGRPID) {
            return wplAdapter.getWKPID(AppContexts.user().companyId(), WKPGRPID);
        }
    }

    @AllArgsConstructor
    private class RequireOrgImpl implements GetTargetIdentifiInforService.Require {
        private EmpAffiliationInforAdapter empAffiliationInforAdapter;

        @Override
        public List<EmpOrganizationImport> getEmpOrganization(GeneralDate referenceDate, List<String> listEmpId) {
            return empAffiliationInforAdapter.getEmpOrganization(referenceDate, listEmpId);
        }
    }

    @AllArgsConstructor
    private class RequireEmpImpl implements GetEmpCanReferService.Require {
        private WorkplaceGroupAdapter workplaceGroupAdapter;
        private RegulationInfoEmployeeAdapter regulInfoEmpAdap;
        private RegulationInfoEmployeePub regulInfoEmpPub;

        @Override
        public List<String> getEmpCanReferByWorkplaceGroup(String empId, GeneralDate date, DatePeriod period, String workplaceGroupID) {
            List<String> data = workplaceGroupAdapter.getReferableEmp(empId, date, period, workplaceGroupID);
            return data;
        }

        @Override
        public List<String> sortEmployee(List<String> lstmployeeId, EmployeeSearchCallSystemType sysAtr, Integer sortOrderNo,
                                         GeneralDate referenceDate, Integer nameType) {

            List<String> data = regulInfoEmpAdap.sortEmployee(
                    AppContexts.user().companyId(),
                    lstmployeeId,
                    sysAtr.value,
                    sortOrderNo,
                    nameType,
                    GeneralDateTime.fromString(referenceDate.toString() + SPACE + ZEZO_TIME, DATE_TIME_FORMAT));
            return data;
        }

        @Override
        public String getRoleID() {

            return AppContexts.user().roles().forAttendance();
        }

        @Override
        public List<String> searchEmployee(RegulationInfoEmpQuery q, String roleId) {
            EmployeeSearchQueryDto query = EmployeeSearchQueryDto.builder()
                    .baseDate(GeneralDateTime.fromString(q.getBaseDate().toString() + SPACE + ZEZO_TIME, DATE_TIME_FORMAT))
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
                    .periodStart(GeneralDateTime.fromString(q.getPeriodStart() + " 00:00", "yyyy/MM/dd HH:mm"))
                    .periodEnd(GeneralDateTime.fromString(q.getPeriodEnd() + " 00:00", "yyyy/MM/dd HH:mm"))
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
            List<String> resultList = data.stream().map(item -> item.getEmployeeId())
                    .collect(Collectors.toList());
            return resultList;
        }

        @Override
        public List<String> getAllEmpCanReferByWorkplaceGroup(String empId, GeneralDate date, DatePeriod period) {
            // don't have to implement it
            return null;
        }
    }

    @Getter
    @AllArgsConstructor
    public enum DisplayMode {

        /**
         * 応援に行く情報
         **/
        GO_TO_SUPPORT(1),
        /**  **/
        COME_TO_SUPPORT(2);

        public final int value;

    }
}
