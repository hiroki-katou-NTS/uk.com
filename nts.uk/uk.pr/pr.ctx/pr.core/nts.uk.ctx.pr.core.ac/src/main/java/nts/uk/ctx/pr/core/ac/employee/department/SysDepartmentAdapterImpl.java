package nts.uk.ctx.pr.core.ac.employee.department;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.pub.department.master.DepartmentPub;
import nts.uk.ctx.pr.core.dom.adapter.employee.department.DepartmentImport;
import nts.uk.ctx.pr.core.dom.adapter.employee.department.SysDepartmentAdapter;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class SysDepartmentAdapterImpl implements SysDepartmentAdapter {
    @Inject
    private DepartmentPub syDepartmentPub;
    @Override
    public List<DepartmentImport> getDepartmentByCompanyIdAndBaseDate(String companyId, GeneralDate baseDate) {
        return syDepartmentPub.getDepartmentByCompanyIdAndBaseDate(companyId, baseDate).stream().map(item -> new DepartmentImport(item.getDepartmentCode(), item.getDepartmentName(), item.getDepGenericName())).collect(Collectors.toList());
    }
}
