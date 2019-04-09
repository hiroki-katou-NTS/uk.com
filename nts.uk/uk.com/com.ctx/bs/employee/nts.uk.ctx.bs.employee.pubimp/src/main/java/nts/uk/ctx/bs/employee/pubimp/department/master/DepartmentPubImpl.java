package nts.uk.ctx.bs.employee.pubimp.department.master;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.dom.department.master.service.DepartmentExportSerivce;
import nts.uk.ctx.bs.employee.pub.deparment.master.DepartmentInforExport;
import nts.uk.ctx.bs.employee.pub.deparment.master.DepartmentPub;

@Stateless
public class DepartmentPubImpl implements DepartmentPub {

	@Inject
	private DepartmentExportSerivce depExpService;

	@Override
	public List<DepartmentInforExport> getDepartmentInforByDepIds(String companyId, List<String> listDepartmentId,
			GeneralDate baseDate) {
		return depExpService.getDepartmentInforFromDepIds(companyId, listDepartmentId, baseDate).stream()
				.map(i -> new DepartmentInforExport(i.getDepartmentId(), i.getHierarchyCode().v(),
						i.getDepartmentCode().v(), i.getDepartmentName().v(), i.getDepartmentDisplayName().v(),
						i.getDepartmentGeneric().v(),
						i.getDepartmentExternalCode().isPresent() ? i.getDepartmentExternalCode().get().v() : null))
				.collect(Collectors.toList());
	}

}
