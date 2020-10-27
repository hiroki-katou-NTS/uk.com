package nts.uk.screen.at.app.ksu001.sortsetting;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.employeeinfo.employeesort.SortSetting;
import nts.uk.ctx.at.schedule.dom.employeeinfo.medicalworkstyle.EmpLicenseClassification;
import nts.uk.ctx.at.schedule.dom.employeeinfo.medicalworkstyle.EmpMedicalWorkFormHisItem;
import nts.uk.ctx.at.schedule.dom.employeeinfo.medicalworkstyle.EmpMedicalWorkStyleHistoryRepository;
import nts.uk.ctx.at.schedule.dom.employeeinfo.medicalworkstyle.GetEmpLicenseClassificationService;
import nts.uk.ctx.at.schedule.dom.employeeinfo.medicalworkstyle.LicenseClassification;
import nts.uk.ctx.at.schedule.dom.employeeinfo.medicalworkstyle.NurseClassification;
import nts.uk.ctx.at.schedule.dom.employeeinfo.medicalworkstyle.NurseClassificationRepository;
import nts.uk.ctx.at.schedule.dom.employeeinfo.rank.EmpRankInfor;
import nts.uk.ctx.at.schedule.dom.employeeinfo.rank.EmployeeRank;
import nts.uk.ctx.at.schedule.dom.employeeinfo.rank.EmployeeRankRepository;
import nts.uk.ctx.at.schedule.dom.employeeinfo.rank.GetEmRankInforService;
import nts.uk.ctx.at.schedule.dom.employeeinfo.rank.Rank;
import nts.uk.ctx.at.schedule.dom.employeeinfo.rank.RankRepository;
import nts.uk.ctx.at.schedule.dom.employeeinfo.scheduleteam.BelongScheduleTeam;
import nts.uk.ctx.at.schedule.dom.employeeinfo.scheduleteam.BelongScheduleTeamRepository;
import nts.uk.ctx.at.schedule.dom.employeeinfo.scheduleteam.ScheduleTeam;
import nts.uk.ctx.at.schedule.dom.employeeinfo.scheduleteam.ScheduleTeamRepository;
import nts.uk.ctx.at.schedule.dom.employeeinfo.scheduleteam.domainservice.EmpTeamInfor;
import nts.uk.ctx.at.schedule.dom.employeeinfo.scheduleteam.domainservice.GetScheduleTeamInfoService;
import nts.uk.query.pub.employee.EmployeeInformationExport;
import nts.uk.query.pub.employee.EmployeeInformationPub;
import nts.uk.query.pub.employee.EmployeeInformationQueryDto;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author HieuLt
 *
 */
@Stateless
public class GetSortedListEmployeeQuery {

	//////////////////////////////////////////////////////////////////////
	@Inject
	private BelongScheduleTeamRepository belongScheduleTeamRepository;

	@Inject
	private ScheduleTeamRepository teamRepository;

	@Inject
	private EmployeeRankRepository employeeRankRepository;

	@Inject
	private RankRepository rankRepository;

	@Inject
	private NurseClassificationRepository nurseClassificationRepo;

	@Inject
	private EmpMedicalWorkStyleHistoryRepository empMedicalWorkStyleHisRepo;

	@Inject
	private EmployeeInformationPub employeeInformationPub;

	public SortedListEmpDto getSortListEmp(String companyId, GeneralDate date, List<String> lstEmpId ,List<EmployeeSwapDto> selectedEmployeeSwap) {
		List<EmpTeamInfor> empTeamInfors = new ArrayList<>();
		List<EmpRankInfor> empRankInfors = new ArrayList<>();
		List<EmpLicenseClassification> empLicenseClassifications  = new ArrayList<>();
		List<String> lstEmp = new ArrayList<>();
		lstEmp.add( "ae7fe82e-a7bd-4ce3-adeb-5cd403a9d570");
		lstEmp.add( "8f9edce4-e135-4a1e-8dca-ad96abe405d6");
		lstEmp.add( "787c06b-3c71-4508-8e06-c70ad41f042a");
		// 所属スケジュールチーム情報を取得する
		
		// 1 :取得する :get List<社員所属チーム情報>
		GetScheduleTeamInfoImpl getScheduleTeamInfoImpl = new GetScheduleTeamInfoImpl(belongScheduleTeamRepository,
				teamRepository);
		empTeamInfors = GetScheduleTeamInfoService.get(getScheduleTeamInfoImpl, lstEmpId);
		List<EmpTeamInforDto> lstEmpTeamInforDto = empTeamInfors.stream().map( x -> 
		new EmpTeamInforDto(x.getEmployeeID(), x.getOptScheduleTeamCd().isPresent() ? x.getOptScheduleTeamCd().get().v() : ""  ,
				x.getOptScheduleTeamName().isPresent() ? x.getOptScheduleTeamName().get().v() : "")).collect(Collectors.toList());
		// 2: 取得する(Require, List<社員ID>) : List<社員ランク情報>
		GetEmRankInforImpl getEmRankInforImpl = new GetEmRankInforImpl(employeeRankRepository, rankRepository);
		empRankInfors = GetEmRankInforService.get(getEmRankInforImpl, lstEmpId);
		List<EmpRankInforDto> lstEmpRankInforDto = empRankInfors.stream().map( x-> new EmpRankInforDto(
				x.getEmpId(),
				x.getRankCode().isPresent() ? x.getRankCode().get().v() : "",
						x.getRankSymbol().isPresent() ? x.getRankSymbol().get().v() : "")).collect(Collectors.toList());
		
		//3:取得する(Require, 年月日, List<社員ID>) : List<社員免許区分>
		GetEmpLicenseClassificationImpl getEmpLicenseClassificationImpl = new GetEmpLicenseClassificationImpl(nurseClassificationRepo, empMedicalWorkStyleHisRepo);
		empLicenseClassifications = GetEmpLicenseClassificationService.get(getEmpLicenseClassificationImpl, date, lstEmpId);
		List<EmpLicenseClassificationDto> lstEmpLicenseClassificationDto = empLicenseClassifications.stream().
				map( x-> new EmpLicenseClassificationDto(x.getEmpID(),
						x.getOptLicenseClassification().isPresent() ? x.getOptLicenseClassification().get().value : null )).collect(Collectors.toList());
		//4: call <get> <<Public>> 社員の情報を取得する 
		// <<Public>> 社員の情報を取得する
				List<EmployeeInformationExport> empInfoLst = employeeInformationPub.find(
						EmployeeInformationQueryDto
						.builder()
						.employeeIds(lstEmpId)
						.referenceDate(date)
						.toGetWorkplace(false)
						.toGetDepartment(false)
						.toGetPosition(true)
						.toGetEmployment(false)
						.toGetClassification(true)
						.toGetEmploymentCls(false)
						.build());
				List<EmplInforATR>  lstEmplInforATR =  empInfoLst.stream().map(x -> new EmplInforATR(x.getEmployeeId() ,x.getPosition().getPositionName(), x.getClassification().getClassificationName())).collect(Collectors.toList()); 	
		SortedListEmpDto data = new SortedListEmpDto(lstEmpTeamInforDto, lstEmpRankInforDto, lstEmpLicenseClassificationDto, lstEmplInforATR, lstEmp);
		return data;

	}

	@AllArgsConstructor
	private static class GetScheduleTeamInfoImpl implements GetScheduleTeamInfoService.Require {

		@Inject
		private BelongScheduleTeamRepository belongScheduleTeamRepository;

		@Inject
		private ScheduleTeamRepository teamRepository;

		@Override
		public List<BelongScheduleTeam> get(List<String> lstEmpId) {
			String companyId = AppContexts.user().companyId();
			List<BelongScheduleTeam> belongScheduleTeams = belongScheduleTeamRepository.get(companyId, lstEmpId);
			return belongScheduleTeams;
		}

		@Override
		public List<ScheduleTeam> getAllSchedule(List<String> listWKPGRPID) {
			String companyId = AppContexts.user().companyId();
			List<ScheduleTeam> scheduleTeams = teamRepository.getAllSchedule(companyId, listWKPGRPID);
			return scheduleTeams;
		}

	}

	@AllArgsConstructor
	private static class GetEmRankInforImpl implements GetEmRankInforService.Require {
		@Inject
		private EmployeeRankRepository employeeRankRepository;

		@Inject
		private RankRepository rankRepository;

		@Override
		public List<EmployeeRank> getAll(List<String> lstSID) {
			List<EmployeeRank> data = employeeRankRepository.getAll(lstSID);
			return data;
		}

		@Override
		public List<Rank> getListRank() {
			List<Rank> data = rankRepository.getListRank(AppContexts.user().companyId());
			return data;
		}

	}

	@AllArgsConstructor
	private static class GetEmpLicenseClassificationImpl implements GetEmpLicenseClassificationService.Require {

		@Inject
		private NurseClassificationRepository nurseClassificationRepo;

		@Inject
		private EmpMedicalWorkStyleHistoryRepository empMedicalWorkStyleHisRepo;

		@Override
		public List<EmpMedicalWorkFormHisItem> get(List<String> listEmp, GeneralDate referenceDate) {
			List<EmpMedicalWorkFormHisItem> data = empMedicalWorkStyleHisRepo.get(listEmp, referenceDate);
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
