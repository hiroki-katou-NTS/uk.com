package nts.uk.ctx.pr.shared.app.find.wageprovision.processdatecls.algorithm;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pr.shared.dom.wageprovision.processdatecls.algorithm.AlgorithmAdaptor;
import nts.uk.ctx.pr.shared.dom.wageprovision.processdatecls.algorithm.ClosureDateImport;

@Stateless
public class AlgorithmFinder {
	@Inject
	private AlgorithmAdaptor a;

	public List<ClosureDateImport> GetClosingSalaryEmploymentList(String companyId) {
		return this.a.GetClosingSalaryEmploymentList(companyId);
	}

}
