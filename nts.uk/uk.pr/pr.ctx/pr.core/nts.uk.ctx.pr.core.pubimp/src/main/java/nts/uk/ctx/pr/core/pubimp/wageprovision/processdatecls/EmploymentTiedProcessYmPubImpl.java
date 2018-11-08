package nts.uk.ctx.pr.core.pubimp.wageprovision.processdatecls;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.EmpTiedProYearRepository;
import nts.uk.ctx.pr.core.pub.wageprovision.processdatecls.EmploymentTiedProcessYearMonth;
import nts.uk.ctx.pr.core.pub.wageprovision.processdatecls.EmploymentTiedProcessYmPub;

/**
 * 
 * @author HungTT
 *
 */

@Stateless
public class EmploymentTiedProcessYmPubImpl implements EmploymentTiedProcessYmPub {

	@Inject
	private EmpTiedProYearRepository repo;

	@Override
	public List<EmploymentTiedProcessYearMonth> getByListProcCateNo(String companyId, List<Integer> processCateNo) {
		return repo.getEmpTiedProYearById(companyId, processCateNo).stream()
				.map(i -> EmploymentTiedProcessYearMonth.fromDomain(i)).collect(Collectors.toList());
	}

}
