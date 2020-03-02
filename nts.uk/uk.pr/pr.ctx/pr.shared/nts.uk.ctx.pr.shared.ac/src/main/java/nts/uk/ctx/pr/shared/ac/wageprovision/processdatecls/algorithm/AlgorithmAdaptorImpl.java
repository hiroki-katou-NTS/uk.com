package nts.uk.ctx.pr.shared.ac.wageprovision.processdatecls.algorithm;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pr.core.pub.wageprovision.processdatecls.algorithm.IAlgorithm;
import nts.uk.ctx.pr.shared.dom.wageprovision.processdatecls.algorithm.AlgorithmAdaptor;
import nts.uk.ctx.pr.shared.dom.wageprovision.processdatecls.algorithm.ClosureDateImport;

@Stateless
public class AlgorithmAdaptorImpl implements AlgorithmAdaptor {

	@Inject
	private IAlgorithm a;

	@Override
	public List<ClosureDateImport> GetClosingSalaryEmploymentList(String companyId) {

		return this.a
				.GetClosingSalaryEmploymentList(companyId).stream().map(x -> ClosureDateImport.builder()
						.employmentCodes(x.getEmploymentCodes()).referenceDates(x.getReferenceDates()).build())
				.collect(Collectors.toList());
	}

}
