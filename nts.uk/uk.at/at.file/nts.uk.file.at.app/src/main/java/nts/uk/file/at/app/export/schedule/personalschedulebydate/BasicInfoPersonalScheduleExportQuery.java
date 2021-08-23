package nts.uk.file.at.app.export.schedule.personalschedulebydate;

import lombok.AllArgsConstructor;
import nts.arc.primitive.PrimitiveValueBase;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.function.dom.adapter.annualworkschedule.EmployeeInformationAdapter;
import nts.uk.ctx.at.function.dom.adapter.annualworkschedule.EmployeeInformationImport;
import nts.uk.ctx.at.function.dom.adapter.annualworkschedule.EmployeeInformationQueryDtoImport;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.event.CompanyEvent;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.event.CompanyEventRepository;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.event.WorkplaceEvent;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.event.WorkplaceEventRepository;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.holiday.PublicHoliday;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.holiday.PublicHolidayRepository;
import nts.uk.ctx.at.schedule.dom.shift.management.DateInformation;
import nts.uk.ctx.at.schedule.dom.shift.specificdayset.company.CompanySpecificDateItem;
import nts.uk.ctx.at.schedule.dom.shift.specificdayset.company.CompanySpecificDateRepository;
import nts.uk.ctx.at.schedule.dom.shift.specificdayset.item.SpecificDateItem;
import nts.uk.ctx.at.schedule.dom.shift.specificdayset.item.SpecificDateItemRepository;
import nts.uk.ctx.at.schedule.dom.shift.specificdayset.primitives.SpecificDateItemNo;
import nts.uk.ctx.at.schedule.dom.shift.specificdayset.workplace.WorkplaceSpecificDateItem;
import nts.uk.ctx.at.schedule.dom.shift.specificdayset.workplace.WorkplaceSpecificDateRepository;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.DisplayInfoOrganization;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrganizationUnit;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.WorkplaceInfo;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.adapter.WorkplaceGroupAdapter;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.adapter.WorkplaceGroupImport;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.AffWorkplaceAdapter;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.WorkplaceExportServiceAdapter;
import nts.uk.file.at.app.export.schedule.personalschedulebydate.dto.BasicInfoPersonalScheduleDto;
import nts.uk.shr.com.company.CompanyAdapter;
import nts.uk.shr.com.company.CompanyInfor;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 基本情報取得
 */
@Stateless
public class BasicInfoPersonalScheduleExportQuery {
    private final String companyId = AppContexts.user().companyId();

    @Inject
    private CompanyAdapter company;

    @Inject
    private EmployeeInformationAdapter employeeInfoAdapter;

    @Inject
    private WorkplaceGroupAdapter groupAdapter;

    @Inject
    private WorkplaceExportServiceAdapter serviceAdapter;

    @Inject
    private AffWorkplaceAdapter wplAdapter;

    @Inject
    private WorkplaceSpecificDateRepository workplaceSpecificDateRepo;

    @Inject
    private CompanySpecificDateRepository companySpecificDateRepo;

    @Inject
    private WorkplaceEventRepository workplaceEventRepo;

    @Inject
    private CompanyEventRepository companyEventRepo;

    @Inject
    private PublicHolidayRepository publicHolidayRepo;

    @Inject
    private SpecificDateItemRepository specificDateItemRepo;

    public BasicInfoPersonalScheduleDto getInfo(int orgUnit, String orgId, GeneralDate baseDate, List<String> sortedEmployeeIds) {
        // 1. [RQ622]会社IDから会社情報を取得する
        CompanyInfor companyInfo = company.getCurrentCompany().orElseGet(() -> {
            throw new RuntimeException("System Error: Company Info");
        });

        // 2. 職場グループを指定して識別情報を作成する(職場グループID): Input.対象組織.単位＝＝職場グループ
        // 3. 職場を指定して識別情報を作成する(職場ID) : Input.対象組織.単位＝＝職場
        TargetOrgIdenInfor targetOrgIdenInfor = orgUnit == TargetOrganizationUnit.WORKPLACE.value
                ? TargetOrgIdenInfor.creatIdentifiWorkplace(orgId)
                : TargetOrgIdenInfor.creatIdentifiWorkplaceGroup(orgId);

        // 4. 組織の表示情報を取得する(Require, 年月日): output 組織の表示情報
        DisplayInfoOrganization displayInfoOrganization = targetOrgIdenInfor.getDisplayInfor(new TargetOrgIdenInfor.Require() {
            @Override
            public List<WorkplaceGroupImport> getSpecifyingWorkplaceGroupId(List<String> workplacegroupId) {
                return groupAdapter.getbySpecWorkplaceGroupID(workplacegroupId);
            }

            @Override
            public List<WorkplaceInfo> getWorkplaceInforFromWkpIds(List<String> listWorkplaceId, GeneralDate baseDate) {
                List<WorkplaceInfo> workplaceInfos = serviceAdapter.getWorkplaceInforByWkpIds(companyId, listWorkplaceId, baseDate).stream()
                        .map(mapper -> new WorkplaceInfo(mapper.getWorkplaceId(), Optional.ofNullable(mapper.getWorkplaceCode()), Optional.ofNullable(mapper.getWorkplaceName()), Optional.ofNullable(mapper.getWorkplaceExternalCode()),
                                Optional.ofNullable(mapper.getWorkplaceGenericName()), Optional.ofNullable(mapper.getWorkplaceDisplayName()), Optional.ofNullable(mapper.getHierarchyCode()))).collect(Collectors.toList());
                return workplaceInfos;
            }

            @Override
            public List<String> getWKPID(String WKPGRPID) {
                return wplAdapter.getWKPID(companyId, WKPGRPID);
            }
        }, baseDate);

        // 5. 年月日情報 : 作成する(Require, 年月日, 対象組織識別情報) : param: require,Input.年月日、Input.対象組織
        DateInfoImpl dateInfoRequire = new DateInfoImpl(workplaceSpecificDateRepo, companySpecificDateRepo, workplaceEventRepo,
                companyEventRepo, publicHolidayRepo, specificDateItemRepo);
        DateInformation dateInfo = DateInformation.create(dateInfoRequire, baseDate, targetOrgIdenInfor);

        // 6. Create 取得したい社員情報: false, false, false, false, false, false
        // 7. call <<Public>> 社員の情報を取得する
        List<EmployeeInformationImport> employeeInfoList = this.employeeInfoAdapter
                .getEmployeeInfo(new EmployeeInformationQueryDtoImport(sortedEmployeeIds, baseDate, false, false, false,
                        false, false, false));

        return new BasicInfoPersonalScheduleDto(companyInfo, displayInfoOrganization, dateInfo, employeeInfoList);
    }

    @AllArgsConstructor
    public static class DateInfoImpl implements DateInformation.Require {

        @Inject
        private WorkplaceSpecificDateRepository workplaceSpecificDateRepo;
        @Inject
        private CompanySpecificDateRepository companySpecificDateRepo;
        @Inject
        private WorkplaceEventRepository workplaceEventRepo;
        @Inject
        private CompanyEventRepository companyEventRepo;
        @Inject
        private PublicHolidayRepository publicHolidayRepo;
        @Inject
        private SpecificDateItemRepository specificDateItemRepo;

        private final String companyId = AppContexts.user().companyId();

        @Override
        public List<WorkplaceSpecificDateItem> getWorkplaceSpecByDate(String workplaceId, GeneralDate specificDate) {
            List<WorkplaceSpecificDateItem> data = workplaceSpecificDateRepo.getWorkplaceSpecByDate(workplaceId,
                    specificDate);
            return data;
        }

        @Override
        public List<CompanySpecificDateItem> getComSpecByDate(GeneralDate specificDate) {
            List<CompanySpecificDateItem> data = companySpecificDateRepo
                    .getComSpecByDate(companyId, specificDate);
            return data;
        }

        @Override
        public Optional<WorkplaceEvent> findByPK(String workplaceId, GeneralDate date) {
            Optional<WorkplaceEvent> data = workplaceEventRepo.findByPK(workplaceId, date);
            return data;
        }

        @Override
        public Optional<CompanyEvent> findCompanyEventByPK(GeneralDate date) {
            Optional<CompanyEvent> data = companyEventRepo.findByPK(companyId, date);
            return data;
        }

        @Override
        public Optional<PublicHoliday> getHolidaysByDate(GeneralDate date) {
            return publicHolidayRepo.getHolidaysByDate(companyId, date);
        }

        @Override
        public List<SpecificDateItem> getSpecifiDateByListCode(List<SpecificDateItemNo> lstSpecificDateItemNo) {
            if (lstSpecificDateItemNo.isEmpty()) return new ArrayList<>();
            List<Integer> _lstSpecificDateItemNo = lstSpecificDateItemNo.stream().map(PrimitiveValueBase::v).collect(Collectors.toList());
            List<SpecificDateItem> data = specificDateItemRepo.getSpecifiDateByListCode(companyId, _lstSpecificDateItemNo);
            return data;
        }
    }
}
