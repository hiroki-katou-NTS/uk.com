package nts.uk.ctx.pr.core.ac.employee.department;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.pub.workplace.SyWorkplacePub;
import nts.uk.ctx.pr.core.dom.adapter.employee.department.Department;
import nts.uk.ctx.pr.core.dom.adapter.employee.department.DepartmentAdapter;
import nts.uk.ctx.pr.core.dom.adapter.employee.department.WkpConfigAtTimeExport;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
public class DepartmentAdapterImple implements DepartmentAdapter {
    @Inject
    private SyWorkplacePub mSyWorkplacePub;
    @Override
    public List<WkpConfigAtTimeExport> getDepartmentByBaseDate(String companyId, GeneralDate baseDate, List<String> wkpIds) {
        return mSyWorkplacePub.findByWkpIdsAtTime(companyId, baseDate, wkpIds)
                .stream().map(x -> new WkpConfigAtTimeExport(x.getWorkplaceId(),x.getHierarchyCd())).collect(Collectors.toList());
    }


    @Override
    public Optional<Department> getDepartmentByDepartmentId(String departmentId) {
        return Optional.empty();
    }
}
