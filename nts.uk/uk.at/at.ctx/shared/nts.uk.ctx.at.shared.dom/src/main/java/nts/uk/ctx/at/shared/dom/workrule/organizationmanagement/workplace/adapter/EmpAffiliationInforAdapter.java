package nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.adapter;

import java.util.List;

import nts.arc.time.GeneralDate;

/**
 * 社員の所属情報Adapter											

 * @author HieuLt
 *
 */
public interface EmpAffiliationInforAdapter {
	/**
	 * [1] 取得する							
	 * @param baseDate
	 * @param lstEmpId
	 * @return
	 */
	List<EmpOrganizationImport> getEmpOrganization(GeneralDate baseDate ,List<String> lstEmpId);
}
