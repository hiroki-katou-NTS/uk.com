package nts.uk.screen.at.app.schedule.scheduleteam;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.bs.employee.dom.workplace.group.domainservice.EmployeeInfoData;
import nts.uk.ctx.bs.employee.dom.workplace.group.domainservice.GetAllEmpWhoBelongWorkplaceGroupService;
import nts.uk.ctx.bs.employee.pub.workplace.ResultRequest597Export;
import nts.uk.shr.com.context.AppContexts;


/**
 * 社員の所属チーム取得
 * UKDesign.UniversalK.就業.KSU_スケジュール.KSU001_個人スケジュール修正(職場別).L：チーム設定.La:チーム設定.メニュー別OCD.社員の所属チーム取得
 * @author quytb
 *
 */
@Stateless
public class Ksu001LaScreenQuery {
	
	
	public List<EmployeeOrganizationInfoDto> getEmployeesOrganizationInfo(){
		
		List<EmployeeOrganizationInfoDto> employeeOrganizationInfoDtos = new ArrayList<EmployeeOrganizationInfoDto>();
		for(int i = 1; i<10; i++){
			employeeOrganizationInfoDtos.add(new EmployeeOrganizationInfoDto("00"+ i,"0" + i , "NSVN", "0"+i, "A"));
		}
		
		//List<EmployeeAffiliation> affiliations = GetAllEmpWhoBelongWorkplaceGroupService.getAllEmp(require, baseDate, workplaceGroupId);
		
		return employeeOrganizationInfoDtos;
	}
	
	private static class RequireGetAllEmpWhoBelongWorkplaceGroupService implements GetAllEmpWhoBelongWorkplaceGroupService.Require{
//
//		@Inject 
//		private JpaAffWorkplaceGroupRespository repoAffWorkplaceGroup;
		
		@Override
		public List<String> getWorkplaceBelongsWorkplaceGroup(String workplaceGroupId) {
//			String companyId = AppContexts.user().companyId();
//			List<String> data = repoAffWorkplaceGroup.getWKPID(companyId, workplaceGroupId);
//			return data;
			return null;
		}

		@Override
		public List<EmployeeInfoData> getEmployeesWhoBelongWorkplace(String workplaceId, DatePeriod datePeriod) {
			// Request 597 職場の所属社員を取得する
//			List<ResultRequest597Export> data = syWorkplacePub.getLstEmpByWorkplaceIdsAndPeriod(Arrays.asList(workplaceId), datePeriod);
//			List<EmployeeInfoData> result = data.stream().map(c -> new EmployeeInfoData(c.getSid(), c.getEmployeeCode(), c.getEmployeeName())).collect(Collectors.toList());
//			return result;
			return null;
		}
		
	}
}
