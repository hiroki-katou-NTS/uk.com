package nts.uk.ctx.bs.employee.pubimp.department;

import nts.arc.primitive.PrimitiveValueBase;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.dom.department.AffDepartmentRepository;
import nts.uk.ctx.bs.employee.pub.department.DepartmentExport;
import nts.uk.ctx.bs.employee.pub.department.SyDepartmentPub;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class DepartmentPubImpl implements SyDepartmentPub {

    @Inject
    private AffDepartmentRepository affDepartmentRepository;
    // for salary qmm016, 017
    @Override
    public List<DepartmentExport> getDepartmentByCompanyIdAndBaseDate(String companyId, GeneralDate baseDate){
        return affDepartmentRepository.getAllDepartmentByCompanyIdAndBaseDate(companyId, baseDate).stream().map(item -> {
            return DepartmentExport.builder().companyId(item.getCompanyId().v()).depHistoryId(item.getDepHistoryId()).departmentId(item.getDepartmentId()).departmentCode(item.getDepartmentCode().v()).departmentName(item.getDepartmentName().v()).depDisplayName(item.getDepDisplayName().v()).depGenericName(item.getDepGenericName().v()).outsideDepCode(item.getOutsideDepCode().map(PrimitiveValueBase::toString)).build();
        }).collect(Collectors.toList());
    }
}
