package nts.uk.ctx.pr.core.pubimp.wageprovision.processdatecls;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.EmpTiedProYearRepository;
import nts.uk.ctx.pr.core.pub.wageprovision.processdatecls.EmploymentTiedProcessYearMonth;
import nts.uk.ctx.pr.core.pub.wageprovision.processdatecls.EmploymentTiedProcessYmPub;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.EmpTiedProYear;

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
	public Optional<EmploymentTiedProcessYearMonth> getByListEmpCodes(String companyId, List<String> employmentCodes) {
		return repo.getEmpTiedProYearByEmployment(companyId, employmentCodes.get(0))
				.map(i -> fromDomain(i));
	}

	@Override
	public List<EmploymentTiedProcessYearMonth> getByListProcCateNo(String companyId, List<Integer> processCateNo) {
		return repo.getEmpTiedProYearById(companyId, processCateNo).stream()
				.map(i -> fromDomain(i)).collect(Collectors.toList());
	}
	
	private EmploymentTiedProcessYearMonth fromDomain(EmpTiedProYear domain) {
		return new EmploymentTiedProcessYearMonth(domain.getCid(), domain.getProcessCateNo(),
				domain.getEmploymentCodes().stream().map(c -> c.v()).collect(Collectors.toList()));
	}

}
