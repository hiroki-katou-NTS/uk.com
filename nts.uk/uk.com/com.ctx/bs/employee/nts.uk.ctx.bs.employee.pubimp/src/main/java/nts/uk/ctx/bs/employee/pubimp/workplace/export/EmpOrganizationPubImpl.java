package nts.uk.ctx.bs.employee.pubimp.workplace.export;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.dom.workplace.EmployeeAffiliation;
import nts.uk.ctx.bs.employee.dom.workplace.group.AffWorkplaceGroup;
import nts.uk.ctx.bs.employee.dom.workplace.group.AffWorkplaceGroupRespository;
import nts.uk.ctx.bs.employee.dom.workplace.group.WorkplaceGroupGettingService;
import nts.uk.ctx.bs.employee.pub.workplace.export.EmpOrganizationPub;
import nts.uk.ctx.bs.employee.pub.workplace.workplacegroup.EmpOrganizationExport;
import nts.uk.shr.com.context.AppContexts;

/**
 * 社員の所属組織Publish Impl
 * @author HieuLt
 *
 */
public class EmpOrganizationPubImpl implements EmpOrganizationPub {

	@Override
	public List<EmpOrganizationExport> getEmpOrganiztion(GeneralDate baseDate, List<String> lstEmpId) {
		RequireWorkplaceGroupGettingService require = new RequireWorkplaceGroupGettingService();
		//$社員の所属組織リスト = 社員が所属する職場グループを取得する#取得する( require, 基準日, 社員IDリスト )
		List<EmployeeAffiliation> data = WorkplaceGroupGettingService.get(require, baseDate, lstEmpId);
		List<EmpOrganizationExport> result = data.stream().map(c -> new EmpOrganizationExport
				(c.getEmployeeID(),
				Optional.ofNullable(c.getEmployeeCode().get().v()) ,
				 c.getBusinessName(),
				 c.getWorkplaceID(),
				 c.getWorkplaceGroupID())).collect(Collectors.toList());
		return result;
	}
	//WorkplaceGroupGettingService
	
	private static class RequireWorkplaceGroupGettingService implements WorkplaceGroupGettingService.Require{
		@Inject
		private AffWorkplaceGroupRespository repo;
		@Override
		public String getAffWkpHistItemByEmpDate(String employeeID, GeneralDate date) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<AffWorkplaceGroup> getWGInfo(List<String> WKPID) {
			String cid = AppContexts.user().companyId();
			//List<AffWorkplaceGroup> getByListWKPID(String CID, List<String> WKPID)
			List<AffWorkplaceGroup> data = repo.getByListWKPID(cid, WKPID);		
			return data;			
		}	
	}
}
