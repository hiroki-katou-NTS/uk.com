package nts.uk.ctx.pr.transfer.dom.adapter.wageprovision.processdatecls;

import java.util.List;

/**
 * 
 * @author HungTT
 *
 */
public interface EmploymentTiedProcYmAdapter {

	public List<EmploymentTiedProcYmImport> getByListProcCateNo(String companyId, List<Integer> procCateNos);
}
