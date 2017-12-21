package nts.uk.ctx.at.shared.ac.employment;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.adapter.employment.EmpCdNameImport;
import nts.uk.ctx.at.shared.dom.adapter.employment.ShareEmploymentAdapter;
import nts.uk.ctx.bs.employee.pub.employment.EmpCdNameExport;
import nts.uk.ctx.bs.employee.pub.employment.SyEmploymentPub;
@Stateless
public class ShareEmploymentAdapterImpl implements ShareEmploymentAdapter{
	@Inject
	public SyEmploymentPub employment;
	@Override
	public List<EmpCdNameImport> findAll(String companyId) {
		List<EmpCdNameExport> data = employment.findAll(companyId);
		return data.stream().map(x -> {
			return new EmpCdNameImport(x.getCode(), x.getName());
		}).collect(Collectors.toList());
	}
}
