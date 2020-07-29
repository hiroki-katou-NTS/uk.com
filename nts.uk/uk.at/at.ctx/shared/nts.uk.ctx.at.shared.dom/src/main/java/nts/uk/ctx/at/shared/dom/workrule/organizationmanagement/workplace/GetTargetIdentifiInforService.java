package nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace;

import java.util.Arrays;
import java.util.List;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.adapter.EmployeeOrganizationImport;
/**
 * 社員の対象組織識別情報を取得する	
 * @author Hieult
 *
 */
public class GetTargetIdentifiInforService {
	/**
	 * [1] 取得する	
	 * @param require
	 * @param referenceDate
	 * @param listEmpId
	 * @return
	 */
	public static TargetOrgIdenInfor get(Require require, GeneralDate referenceDate, String empId) {
		/*
		 * $社員の所属組織リスト = require.社員の所属組織を取得する( 基準日, list: 社員ID ) $社員の所属組織 =
		 * $社員の所属組織リスト.first()
		 */
		List<EmployeeOrganizationImport> listEmployeeOrganization = require.get(referenceDate, Arrays.asList(empId));
		EmployeeOrganizationImport employeeOrganization = listEmployeeOrganization.get(0);
		if(employeeOrganization.getWorkplaceGroupId().isPresent()){
			return	TargetOrgIdenInfor.creatIdentifiWorkplaceGroup(employeeOrganization.getWorkplaceGroupId().get());
		}
		else{
			//	return 対象組織識別情報#職場を指定して識別情報を作成する( $.職場ID )
		return TargetOrgIdenInfor.creatIdentifiWorkplace(employeeOrganization.getWorkplaceId());
		}
	}

	public static interface Require {
		// WorkplaceGroupAdapter

		List<EmployeeOrganizationImport> get(GeneralDate referenceDate, List<String> listEmpId);
	}
}
