package nts.uk.ctx.pr.transfer.ac.employee;

import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.pr.transfer.dom.adapter.employee.*;
import nts.uk.query.pub.employee.EmployeeInformationExport;
import nts.uk.query.pub.employee.EmployeeInformationPub;
import nts.uk.query.pub.employee.EmployeeInformationQueryDto;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class EmployeeInfoAdapterImpl2 implements EmployeeInformationAdapter {

    @Inject
    EmployeeInformationPub employeeInformationPub;

    @Override
    public List<EmployeeInformationImport> getEmployeeInfo(EmployeeInformationQueryDtoImport param) {
        EmployeeInformationQueryDto employeeInformationQueryDto =
                EmployeeInformationQueryDto.builder()
                        .employeeIds(param.getEmployeeIds())
                        .referenceDate(param.getReferenceDate())
                        .toGetWorkplace(param.isToGetWorkplace())
                        .toGetDepartment(param.isToGetDepartment())
                        .toGetPosition(param.isToGetPosition())
                        .toGetEmployment(param.isToGetEmployment())
                        .toGetClassification(param.isToGetClassification())
                        .toGetEmploymentCls(param.isToGetEmploymentCls())
                        .build();
        List<EmployeeInformationExport> employeeInformationExport = employeeInformationPub.find(employeeInformationQueryDto);
        if (CollectionUtil.isEmpty(employeeInformationExport)) {
            return Collections.emptyList();
        }

        return employeeInformationExport.stream()
                .map(f -> new EmployeeInformationImport(
                        f.getEmployeeId(),
                        f.getEmployeeCode(),
                        f.getBusinessName(),
                        f.getWorkplace() == null ? null : new WorkplaceImport(f.getWorkplace().getWorkplaceId(), f.getWorkplace().getWorkplaceCode(), f.getWorkplace().getWorkplaceGenericName(), f.getWorkplace().getWorkplaceName()),
                        f.getClassification() == null ? null : new ClassificationImport(f.getClassification().getClassificationCode(), f.getClassification().getClassificationName()),
                        f.getDepartment() == null ? null : new DepartmentImport(f.getDepartment().getDepartmentCode(), f.getDepartment().getDepartmentName(), f.getDepartment().getDepartmentGenericName()),
                        f.getPosition() == null ? null : new PositionImport(f.getPosition().getPositionId(), f.getPosition().getPositionCode(), f.getPosition().getPositionName()),
                        f.getEmployment() == null ? null : new EmploymentImport(f.getEmployment().getEmploymentCode(), f.getEmployment().getEmploymentName()),
                        f.getEmploymentCls()
                ))
                .collect(Collectors.toList());
    }
}
