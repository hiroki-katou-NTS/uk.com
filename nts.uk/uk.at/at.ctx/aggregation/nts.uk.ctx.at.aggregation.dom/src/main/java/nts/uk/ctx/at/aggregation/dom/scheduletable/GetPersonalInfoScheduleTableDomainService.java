package nts.uk.ctx.at.aggregation.dom.scheduletable;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.aggregation.dom.adapter.employee.EmployeeInformationAggAdapter;
import nts.uk.ctx.at.aggregation.dom.adapter.employee.EmployeeInformationImport;
import nts.uk.ctx.at.aggregation.dom.adapter.employee.EmployeeInformationQueryDtoImport;
import nts.uk.ctx.at.schedule.dom.employeeinfo.rank.*;
import nts.uk.ctx.at.schedule.dom.employeeinfo.scheduleteam.BelongScheduleTeam;
import nts.uk.ctx.at.schedule.dom.employeeinfo.scheduleteam.BelongScheduleTeamRepository;
import nts.uk.ctx.at.schedule.dom.employeeinfo.scheduleteam.ScheduleTeam;
import nts.uk.ctx.at.schedule.dom.employeeinfo.scheduleteam.ScheduleTeamRepository;
import nts.uk.ctx.at.schedule.dom.employeeinfo.scheduleteam.domainservice.EmpTeamInfor;
import nts.uk.ctx.at.schedule.dom.employeeinfo.scheduleteam.domainservice.GetScheduleTeamInfoService;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalworkstyle.*;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.*;
import java.util.stream.Collectors;

/**
 * スケジュール表の個人情報を取得する
 */
@Stateless
public class GetPersonalInfoScheduleTableDomainService {
    @Inject
    private EmployeeInformationAggAdapter employeeInformationAdapter;

    @Inject
    private BelongScheduleTeamRepository belongScheduleTeamRepository;
    @Inject
    private ScheduleTeamRepository teamRepository;

    @Inject
    private EmployeeRankRepository employeeRankRepo;
    @Inject
    private RankRepository rankRepo;

    @Inject
    private EmpMedicalWorkStyleHistoryRepository empMedicalWorkStyleHistoryRepo;
    @Inject
    private NurseClassificationRepository nurseClassificationRepo;

    /**
     * 取得する
     * @param employeeIds List<社員ID>
     * @param baseDate 年月日
     * @param personalInfoItems List<スケジュール表の個人情報項目>
     * @return List<スケジュール表の個人情報>
     */
    public List<PersonalInfoScheduleTable> create(List<String> employeeIds, GeneralDate baseDate, List<ScheduleTablePersonalInfoItem> personalInfoItems) {
        String companyId = AppContexts.user().companyId();
        // [prv-1] 社員情報を取得する
        EmployeeInformationQueryDtoImport param = new EmployeeInformationQueryDtoImport(
                employeeIds, baseDate, false, false,
                personalInfoItems.contains(ScheduleTablePersonalInfoItem.JOBTITLE),
                personalInfoItems.contains(ScheduleTablePersonalInfoItem.EMPLOYMENT),
                personalInfoItems.contains(ScheduleTablePersonalInfoItem.CLASSIFICATION),
                false
        );
        List<EmployeeInformationImport> listEmpInfo = employeeInformationAdapter.getEmployeeInfo(param);

        // [prv-2] 社員所属チーム情報
        List<EmpTeamInfor> empTeamInfors = GetScheduleTeamInfoService.get(new GetScheduleTeamInfoService.Require() {
            @Override
            public List<BelongScheduleTeam> get(List<String> lstEmpId) {
                List<BelongScheduleTeam> belongScheduleTeams = belongScheduleTeamRepository.get(companyId, lstEmpId);
                return belongScheduleTeams;
            }

            @Override
            public List<ScheduleTeam> getAllSchedule(List<String> listWKPGRPID) {
                List<ScheduleTeam> scheduleTeams = teamRepository.getAllSchedule(companyId, listWKPGRPID);
                return scheduleTeams;
            }
        }, employeeIds);

        // [prv-3] 社員ランク情報を取得する
        List<EmpRankInfor> lstRank = GetEmRankInforService.get(new GetEmRankInforService.Require() {
            @Override
            public List<EmployeeRank> getAll(List<String> lstSID) {
                List<EmployeeRank> data = employeeRankRepo.getAll(lstSID);
                return data;
            }

            @Override
            public List<Rank> getListRank() {
                List<Rank> data = rankRepo.getListRank(companyId);
                return data;
            }
        }, employeeIds);

        // [prv-4] 社員免許区分を取得する
        List<EmpLicenseClassification> lstEmpLicense = GetEmpLicenseClassificationService.get(new GetEmpLicenseClassificationService.Require() {
            @Override
            public List<EmpMedicalWorkFormHisItem> getEmpClassifications(List<String> listEmp, GeneralDate referenceDate) {
                List<EmpMedicalWorkFormHisItem> data = empMedicalWorkStyleHistoryRepo.get(listEmp, referenceDate);
                return data;
            }

            @Override
            public List<NurseClassification> getListCompanyNurseCategory() {
                List<NurseClassification> data = nurseClassificationRepo
                        .getListCompanyNurseCategory(AppContexts.user().companyId());
                return data;
            }
        }, baseDate, employeeIds);

        List<PersonalInfoScheduleTable> result = listEmpInfo.stream().map(info -> new PersonalInfoScheduleTable(
                info.getEmployeeId(),
                info,
                empTeamInfors.stream().filter(i -> i.getEmployeeID().equals(info.getEmployeeId())).findFirst(),
                lstRank.stream().filter(i -> i.getEmpId().equals(info.getEmployeeId())).findFirst(),
                lstEmpLicense.stream().filter(i -> i.getEmpID().equals(info.getEmployeeId())).findFirst()
        )).collect(Collectors.toList());
        return result;
    }
}
