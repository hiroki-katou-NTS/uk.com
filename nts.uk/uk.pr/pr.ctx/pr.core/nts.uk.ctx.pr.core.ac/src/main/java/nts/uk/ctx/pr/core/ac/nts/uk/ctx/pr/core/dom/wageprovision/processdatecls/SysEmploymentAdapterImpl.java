package nts.uk.ctx.pr.core.ac.nts.uk.ctx.pr.core.dom.wageprovision.processdatecls;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.bs.employee.pub.employment.SyEmploymentPub;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.EmpCdNameImport;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.SysEmploymentAdapter;

@Stateless
public class SysEmploymentAdapterImpl implements SysEmploymentAdapter {

	@Inject
	private SyEmploymentPub sysEmploymentPub;

	@Override
	public List<EmpCdNameImport> findAll(String companyId) {
		return this.sysEmploymentPub.findAll(companyId).stream().map(x -> new EmpCdNameImport(x.getCode(), x.getName()))
				.collect(Collectors.toList());
	}
}
