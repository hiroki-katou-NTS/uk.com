package nts.uk.ctx.at.shared.ac.workplace.export;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.common.EmployeeId;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.adapter.EmpAffiliationInforAdapter;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.adapter.EmpOrganizationImport;
import nts.uk.ctx.bs.employee.pub.workplace.export.EmpOrganizationPub;
import nts.uk.ctx.bs.employee.pub.workplace.workplacegroup.EmpOrganizationExport;

/**
 * 社員の所属情報Adapter Impl										
 * @author HieuLt
 *
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class EmpAffiliationInforAdapterImpl implements EmpAffiliationInforAdapter{

	@Inject
		private EmpOrganizationPub pub;
	@Override
	public List<EmpOrganizationImport> getEmpOrganization(GeneralDate baseDate, List<String> lstEmpId) {
		
		List<EmpOrganizationExport> data = pub.getEmpOrganiztion(baseDate, lstEmpId);
		List<EmpOrganizationImport> result = data.stream().map(c -> new 
				 EmpOrganizationImport(
						new EmployeeId(c.getEmpId()),
						c.getEmpCd(),
						c.getBusinessName(),
						c.getWorkplaceId(),
						c.getWorkplaceGroupId())).collect(Collectors.toList());
		//	return 社員の所属組織Publish.取得する( 基準日, 社員IDリスト )																		
		return result;
	}

}
