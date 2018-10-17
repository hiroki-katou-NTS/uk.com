package nts.uk.ctx.pr.transfer.ac.wageprovision.processdatecls;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pr.core.pub.wageprovision.processdatecls.EmploymentTiedProcessYearMonth;
import nts.uk.ctx.pr.core.pub.wageprovision.processdatecls.EmploymentTiedProcessYmPub;
import nts.uk.ctx.pr.transfer.dom.adapter.wageprovision.processdatecls.EmploymentTiedProcYmAdapter;
import nts.uk.ctx.pr.transfer.dom.adapter.wageprovision.processdatecls.EmploymentTiedProcYmImport;

/**
 * 
 * @author HungTT
 *
 */

@Stateless
public class EmploymentTiedProcYmAdapterImpl implements EmploymentTiedProcYmAdapter {

	@Inject
	private EmploymentTiedProcessYmPub empTiedProcYmPub;

	@Override
	public List<EmploymentTiedProcYmImport> getByListProcCateNo(String companyId, List<Integer> procCateNos) {
		return empTiedProcYmPub.getByListProcCateNo(companyId, procCateNos).stream().map(i -> this.fromExport(i))
				.collect(Collectors.toList());
	}

	private EmploymentTiedProcYmImport fromExport(EmploymentTiedProcessYearMonth export) {
		return new EmploymentTiedProcYmImport(export.getCid(), export.getProcessCateNo(),
				export.getEmploymentCodes().stream().map(c -> c.v()).collect(Collectors.toList()));
	}

}
