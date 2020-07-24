package nts.uk.ctx.bs.employee.pubimp.workplace.workplacegroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.bs.employee.dom.workplace.EmployeeAffiliation;
import nts.uk.ctx.bs.employee.dom.workplace.group.WorkplaceGroup;
import nts.uk.ctx.bs.employee.dom.workplace.group.WorkplaceGroupRespository;
import nts.uk.ctx.bs.employee.dom.workplace.group.domainservice.EmployeeInfoData;
import nts.uk.ctx.bs.employee.dom.workplace.group.domainservice.GetAllEmpWhoBelongWorkplaceGroupService;
import nts.uk.ctx.bs.employee.pub.employee.workplace.export.WorkplaceGroupExport;
import nts.uk.ctx.bs.employee.pub.workplace.workplacegroup.EmpOrganizationExport;
import nts.uk.ctx.bs.employee.pub.workplace.workplacegroup.WorkplaceGroupPublish;
import nts.uk.shr.com.context.AppContexts;
import repository.workplacegroup.JpaAffWorkplaceGroupRespository;

/**
 * 職場グループPublish
 * @author HieuLt
 *
 */
@Stateless
public class WorkplaceGroupPubIpml implements WorkplaceGroupPublish {

	@Override
	public List<WorkplaceGroupExport> getByWorkplaceGroupID(List<String> lstWorkplaceGroupId) {
		Require require = new Require();
		String companyId = AppContexts.user().companyId();
		//	$職場グループリスト = require.職場グループIDを指定して職場グループを取得する( 職場グループIDリスト )	
		List<WorkplaceGroup> data = require.getAllById(companyId, lstWorkplaceGroupId);
		List<WorkplaceGroupExport> result = data.stream().map( c -> new WorkplaceGroupExport(
				c.getWKPGRPID(),
				c.getWKPGRPCode().v(),
				c.getWKPGRPName().v(),
				c.getWKPGRPType().value)).collect(Collectors.toList());
		//return $職場グループリスト: map 職場グループExported( $.職場グループID, $.職場グループコード, $.職場グループ名称, $.職場グループ種別 )																														
		return result;
	}

	@Override
	public List<WorkplaceGroupExport> getAllWorkplaces(GeneralDate date, String workplaceGroupId) {
		Require require = new Require();
		String companyId = AppContexts.user().companyId();
		//	$職場グループリスト = require.職場グループIDを指定して職場グループを取得する( 職場グループIDリスト )	
		List<WorkplaceGroup> data = require.getAllById(companyId, Arrays.asList(workplaceGroupId));
		List<WorkplaceGroupExport> result = data.stream().map( c -> new WorkplaceGroupExport(
				c.getWKPGRPID(),
				c.getWKPGRPCode().v(),
				c.getWKPGRPName().v(),
				c.getWKPGRPType().value)).collect(Collectors.toList());
		//return $職場グループリスト: map 職場グループExported( $.職場グループID, $.職場グループコード, $.職場グループ名称, $.職場グループ種別 )																														
		return result;
	}

	@Override
	public List<EmpOrganizationExport> getAllEmployees(GeneralDate date, String workplaceGroupId) {
		RequireAllEmpWhoBelongWklGroupSv requireGetAllEmp = new RequireAllEmpWhoBelongWklGroupSv();
		//$社員の所属組織リスト = 職場グループに所属する社員をすべて取得する#取得する( require, 基準日, 職場グループID )	
		List<EmployeeAffiliation> data = GetAllEmpWhoBelongWorkplaceGroupService.getAllEmp(requireGetAllEmp, date, workplaceGroupId);
		List<EmpOrganizationExport> result = data.stream().map(c -> new EmpOrganizationExport(
				c.getEmployeeID(),
			Optional.ofNullable(c.getEmployeeCode().get().v()),
				c.getBusinessName(),
				c.getWorkplaceID(),
				c.getWorkplaceGroupID())).collect(Collectors.toList());
		/*return $社員の所属組織リスト:																							
			map 社員の所属組織Exported( $.社員ID, $.社員コード, $.ビジネスネーム, $職場ID, $.職場グループID )*/	
		return result;
	}

	@Override
	public List<String> getReferableEmployees(GeneralDate date, String empID, String workplaceGroupId) {
		// TODO Auto-generated method stub
		return null;
	}
	
	class Require {
		@Inject
		private WorkplaceGroupRespository repo;
		/**
		 * [R-1] 職場グループIDを指定して職場グループを取得する		
		 * @param companyId
		 * @param lstWKPGRPID
		 * @return
		 */
		public List<WorkplaceGroup> getAllById( String companyId , List<String> lstWKPGRPID){
			return  repo.getAllById(companyId, lstWKPGRPID);
		}		
	}
	private static class RequireAllEmpWhoBelongWklGroupSv implements GetAllEmpWhoBelongWorkplaceGroupService.Require{
		@Inject
		private JpaAffWorkplaceGroupRespository repo;
		@Override
		public List<String> getWorkplaceBelongsWorkplaceGroup(String workplaceGroupId) {
			// JpaAffWorkplaceGroupRespository List<String> getWKPID(String CID, String WKPGRPID);
			List<String> data = repo.getWKPID(AppContexts.user().companyId(), workplaceGroupId);
			return data;
		}

		@Override
		public List<EmployeeInfoData> getEmployeesWhoBelongWorkplace(String workplaceId, DatePeriod datePeriod) {
			// TODO Auto-generated method stub
			return null;
		}
		
		
	}

}
