package nts.uk.ctx.at.schedule.pubimp.schedule.workschedule.employeeinfo;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.employeeinfo.rank.EmployeeRank;
import nts.uk.ctx.at.schedule.dom.employeeinfo.rank.EmployeeRankRepository;
import nts.uk.ctx.at.schedule.dom.employeeinfo.rank.Rank;
import nts.uk.ctx.at.schedule.dom.employeeinfo.rank.RankRepository;
import nts.uk.ctx.at.schedule.dom.employeeinfo.scheduleteam.BelongScheduleTeam;
import nts.uk.ctx.at.schedule.dom.employeeinfo.scheduleteam.BelongScheduleTeamRepository;
import nts.uk.ctx.at.schedule.dom.employeeinfo.scheduleteam.ScheduleTeam;
import nts.uk.ctx.at.schedule.dom.employeeinfo.scheduleteam.ScheduleTeamRepository;
import nts.uk.ctx.at.schedule.dom.employeeinfo.scheduleteam.domainservice.GetScheduleTeamInfoService;
import nts.uk.ctx.at.schedule.dom.workschedule.displaysetting.DisplayControlPersonalCondition;
import nts.uk.ctx.at.schedule.pub.schedule.workschedule.employeeinfo.EmpAffTeamInfoPub;
import nts.uk.ctx.at.schedule.pub.schedule.workschedule.employeeinfo.EmpTeamInfoExport;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.medicalworkstyle.EmpMedicalWorkStyleHistoryItem;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.medicalworkstyle.EmpMedicalWorkStyleHistoryRepository;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.medicalworkstyle.NurseClassification;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.medicalworkstyle.NurseClassificationRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class EmpAffTeamInfoPubImp implements EmpAffTeamInfoPub {
    @Inject
    private BelongScheduleTeamRepository belongScheduleTeamRepo;
    @Inject
    private ScheduleTeamRepository scheduleTeamRepo;
    @Inject
    private EmployeeRankRepository employeeRankRepo;
    @Inject
    private RankRepository rankRepo;
    @Inject
    private EmpMedicalWorkStyleHistoryRepository empMedicalWorkStyleHistoryRepo;
    @Inject
    private NurseClassificationRepository nurseClassificationRepo;

    /**
     * @param listEmpId
     * @return List<???????????????????????????Exported>
     */
    @Override
    public List<EmpTeamInfoExport> get(List<String> listEmpId) {
        //return DS_??????????????????????????????????????????????????????.????????????( require, ??????????????? ):
        //map ???????????????????????????Export( $.??????ID, $.??????????????????, $.???????????????)
        RequireImplDispControlPerCond requireImplDispControlPerCond = new RequireImplDispControlPerCond();
        return GetScheduleTeamInfoService.get(requireImplDispControlPerCond, listEmpId).stream().map(x -> new EmpTeamInfoExport(
                x.getEmployeeID(),
                x.getOptScheduleTeamCd().isPresent() ? x.getOptScheduleTeamCd().get().v() : "",
                x.getOptScheduleTeamName().isPresent() ? x.getOptScheduleTeamName().get().v() : ""
        )).collect(Collectors.toList());
    }

    private class RequireImplDispControlPerCond implements DisplayControlPersonalCondition.Require {
    	
        @Override
        public List<BelongScheduleTeam> get(List<String> lstEmpId) {
            List<BelongScheduleTeam> data = belongScheduleTeamRepo.get(AppContexts.user().companyId(), lstEmpId);
            return data;
        }

        @Override
        public List<ScheduleTeam> getAllSchedule(List<String> listWKPGRPID) {
            List<ScheduleTeam> data = scheduleTeamRepo.getAllSchedule(AppContexts.user().companyId(), listWKPGRPID);
            return data;
        }

        @Override
        public List<EmployeeRank> getAll(List<String> lstSID) {
            List<EmployeeRank> data = employeeRankRepo.getAll(lstSID);
            return data;
        }

        @Override
        public List<Rank> getListRank() {
            List<Rank> data = rankRepo.getListRank(AppContexts.user().companyId());
            return data;
        }

        @Override
        public List<EmpMedicalWorkStyleHistoryItem> getEmpMedicalWorkStyleHistoryItem(List<String> listEmp, GeneralDate referenceDate) {
            List<EmpMedicalWorkStyleHistoryItem> data = empMedicalWorkStyleHistoryRepo.get(listEmp, referenceDate);
            return data;
        }

        @Override
        public List<NurseClassification> getListCompanyNurseCategory() {
            List<NurseClassification> data = nurseClassificationRepo
                    .getListCompanyNurseCategory(AppContexts.user().companyId());
            return data;
        }
    }
}