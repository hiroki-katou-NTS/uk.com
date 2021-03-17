package nts.uk.ctx.exio.app.find.exi.execlog;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.bs.company.dom.company.Company;
import nts.uk.ctx.bs.company.dom.company.CompanyRepository;
import nts.uk.ctx.bs.employee.dom.employee.mgndata.EmployeeDataMngInfoRepository;
import nts.uk.ctx.bs.employee.dom.employee.mgndata.EmployeeInfo;
import nts.uk.ctx.exio.dom.exi.condset.StdAcceptCondSet;
import nts.uk.ctx.exio.dom.exi.condset.StdAcceptCondSetRepository;
import nts.uk.ctx.exio.dom.exi.execlog.ExacExeResultLog;
import nts.uk.ctx.exio.dom.exi.execlog.ExacExeResultLogRepository;
import nts.uk.ctx.sys.auth.dom.role.Role;
import nts.uk.ctx.sys.auth.dom.role.RoleAtr;
import nts.uk.ctx.sys.auth.dom.role.RoleRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
/**
 * 外部受入実行結果ログ
 */
public class ExacExeResultLogFinder {

	@Inject
	private ExacExeResultLogRepository finder;
	
	@Inject
	private RoleRepository roleRepo;
	
	@Inject
	private StdAcceptCondSetRepository stdConSetRep;
	
	@Inject
	private EmployeeDataMngInfoRepository employeeRep;
	
    @Inject
    private CompanyRepository companyRepository;

	// 受入実行履歴の初期処理
	public List<ExacExeResultLogNameDto> getAllExacExeResultLog(GeneralDateTime start, GeneralDateTime end) {
		String cid = AppContexts.user().companyId();
		String attendanceRoleID = AppContexts.user().roles().forAttendance();
		String salaryRoleID = AppContexts.user().roles().forPayroll();
		String humanResourceRoleID = AppContexts.user().roles().forPersonnel();
		String officeHelperRoleID = AppContexts.user().roles().forOfficeHelper();
		
		List<Integer> lstSystem = new ArrayList<>();
		
		// Imported(共通)　「システムコード」を取得する
		Optional<Role> roleAttendance = roleRepo.findByRoleId(attendanceRoleID);
		if (roleAttendance.isPresent()) {
			if (roleAttendance.get().getAssignAtr().equals(RoleAtr.INCHARGE)) {
				lstSystem.add(1);
			}
		}
		Optional<Role> roleSalaryRole = roleRepo.findByRoleId(salaryRoleID);
		if (roleSalaryRole.isPresent()) {
			if (roleSalaryRole.get().getAssignAtr().equals(RoleAtr.INCHARGE)) {
				lstSystem.add(2);
			}
		}
		Optional<Role> roleHumanResource = roleRepo.findByRoleId(humanResourceRoleID);
		if (roleHumanResource.isPresent()) {
			if (roleHumanResource.get().getAssignAtr().equals(RoleAtr.INCHARGE)) {
				lstSystem.add(0);
			}
		}
		Optional<Role> roleOfficeHelper = roleRepo.findByRoleId(officeHelperRoleID);
		if (roleOfficeHelper.isPresent()) {
			if (roleOfficeHelper.get().getAssignAtr().equals(RoleAtr.INCHARGE)) {
				lstSystem.add(3);
			}
		}
		List<StdAcceptCondSet> lstCondSet = stdConSetRep.getStdAcceptCondSetByListSys(cid, lstSystem);
		List<ExacExeResultLog> listResultLog = finder.getAllExacExeResultLog(cid, lstSystem, start, end);
		List<ExacExeResultLogNameDto> lstDto = new ArrayList<>();
		
		
		for(ExacExeResultLog log : listResultLog) {
			
			// get employee name
			Optional<EmployeeInfo> employeeName = employeeRep.findById(log.getUserId());
			
			// get condition name
			Optional<StdAcceptCondSet> acceptConSet = lstCondSet.stream().filter(x -> x.getCompanyId().equals(log.getCid()) 
					&& x.getConditionSetCode().equals(log.getConditionSetCd())).findFirst();
			
			Optional<Company> companyInfo = companyRepository.find(log.getCid());
			
			if(acceptConSet.isPresent() && employeeName.isPresent() && companyInfo.isPresent()) {
				lstDto.add(ExacExeResultLogNameDto.fromDomain(log, acceptConSet.get().getConditionSetName().v(), employeeName.get().getEmployeeCode(), 
						employeeName.get().getEmployeeName(), companyInfo.get().getCompanyCode().v()));
			}
			
		}
		return lstDto;
	}

	/**
	 * @param externalProcessId
	 * @return
	 */
	public List<ExacExeResultLogDto> getExacExeResultLogByProcessId(String externalProcessId) {
		return finder.getExacExeResultLogByProcessId(externalProcessId).stream()
				.map(item -> ExacExeResultLogDto.fromDomain(item)).collect(Collectors.toList());
	}

}
