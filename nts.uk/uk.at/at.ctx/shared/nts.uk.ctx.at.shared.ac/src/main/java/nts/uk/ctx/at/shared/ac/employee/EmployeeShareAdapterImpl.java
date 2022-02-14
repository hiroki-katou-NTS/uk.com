/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.ac.employee;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.employeeinfor.EmployeeCodeAndDisplayNameImport;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.employeeinfor.employee.EmployeeAdapter;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.employeeinfor.employee.EmployeeInfoWantToBeGet;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.employeeinfor.employee.EmployeeInformationQueryDto;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.employeeinfor.employee.importeddto.EmployeeInfoImport;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.employeeinfor.employee.importeddto.EmployeeInfoImported;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.employeeinfor.employmenthistory.imported.DepartmentImport;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.employeeinfor.employmenthistory.imported.EmployeeInformationImport;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.employeeinfor.employmenthistory.imported.EmploymentImport;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.employeeinfor.employmenthistory.imported.PositionImport;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.employeeinfor.employmenthistory.imported.WorkplaceImport;
import nts.uk.ctx.bs.employee.pub.employee.EmployeeBasicExport;
import nts.uk.ctx.bs.employee.pub.employee.EmployeeDataMngInfoExport;
import nts.uk.ctx.bs.employee.pub.employee.SyEmployeePub;
import nts.uk.query.pub.employee.EmployeeInformationExport;
import nts.uk.query.pub.employee.EmployeeInformationPub;

/**
 * The Class EmpEmployeeAdapterImpl.
 */
@Stateless
public class EmployeeShareAdapterImpl implements EmployeeAdapter {

    /**
     * The employee pub.
     */
    @Inject
    private SyEmployeePub employeePub;

    @Inject
    private EmployeeInformationPub employeeInformationPub;


    /**
     * 社員コードから社員IDを取得する
     *
     * @param companyId     会社ID
     * @param employeeCodes List<社員コード>
     * @return
     */
    @Override
    public Map<String, String> getEmployeeIdFromCode(String companyId, List<String> employeeCodes) {
        Map<String, String> empInfoMap = new HashMap<>();
        for (String emCode : employeeCodes) {
            Optional<EmployeeDataMngInfoExport> emp = employeePub.getSdataMngInfoByEmployeeCode(companyId, emCode);
            if (emp.isPresent()) {
                empInfoMap.put(emp.get().getEmployeeCode(), emp.get().getEmployeeId());
            }
        }
        return empInfoMap;
    }

    /**
     * 社員IDリストから社員コードと表示名を取得する
     *
     * @param employeeIds 社員IDリスト
     * @return
     */
    @Override
    public List<EmployeeCodeAndDisplayNameImport> getEmployeeCodeAndDisplayNameImportByEmployeeIds(List<String> employeeIds) {
        return employeePub.getByListSid(employeeIds)
                .stream()
                .map(x -> new EmployeeCodeAndDisplayNameImport(x.getSid(), x.getScd(), x.getBussinessName()))
                .collect(Collectors.toList());
    }

    /**
     * Find.
     *
     * @param param the param
     * @return the list
     */
    @Override
    public List<EmployeeInformationImport> find(EmployeeInformationQueryDto param) {
        nts.uk.query.pub.employee.EmployeeInformationQueryDto query = nts.uk.query.pub.employee.EmployeeInformationQueryDto.builder()
                .employeeIds(param.getEmployeeIds())
                .referenceDate(param.getReferenceDate())
                .toGetClassification(param.isToGetClassification())
                .toGetDepartment(param.isToGetDepartment())
                .toGetEmployment(param.isToGetEmployment())
                .toGetEmploymentCls(param.isToGetEmploymentCls())
                .toGetPosition(param.isToGetPosition())
                .toGetWorkplace(param.isToGetWorkplace())
                .build();
        return employeeInformationPub.find(query).stream().map(item -> toImport(item)).collect(Collectors.toList());
    }

    @Override
    public Optional<EmployeeCodeAndDisplayNameImport> getEmployeeCodeAndNameByEmployeeId(String employeeId) {
        EmployeeBasicExport empInfo = employeePub.getEmpBasicBySId(employeeId);
        return Optional.of(new EmployeeCodeAndDisplayNameImport(empInfo.getEmployeeId(), empInfo.getEmployeeCode(), empInfo.getBusinessName()));
    }

    private EmployeeInformationImport toImport(EmployeeInformationExport item) {

        nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.employeeinfor.employmenthistory.imported.ClassificationImport cls = nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.employeeinfor.employmenthistory.imported.ClassificationImport.builder()
                .classificationCode(item.getClassification().getClassificationCode())
                .classificationName(item.getClassification().getClassificationName())
                .build();

        WorkplaceImport wkp = WorkplaceImport.builder()
                .workplaceId(item.getWorkplace().getWorkplaceId())
                .workplaceCode(item.getWorkplace().getWorkplaceCode())
                .workplaceName(item.getWorkplace().getWorkplaceName())
                .workplaceGenericName(item.getWorkplace().getWorkplaceGenericName())
                .build();

        DepartmentImport dep = DepartmentImport.builder()
                .companyId(item.getDepartment().getCompanyId())
                .deleteFlag(item.getDepartment().isDeleteFlag())
                .departmentHistoryId(item.getDepartment().getDepartmentHistoryId())
                .departmentId(item.getDepartment().getDepartmentId())
                .departmentCode(item.getDepartment().getDepartmentCode())
                .departmentName(item.getDepartment().getDepartmentName())
                .departmentGeneric(item.getDepartment().getDepartmentGeneric())
                .departmentDisplayName(item.getDepartment().getDepartmentDisplayName())
                .hierarchyCode(item.getDepartment().getHierarchyCode())
                .departmentExternalCode(item.getDepartment().getDepartmentExternalCode())
                .build();

        PositionImport pos = PositionImport.builder()
                .positionId(item.getPosition().getPositionId())
                .positionCode(item.getPosition().getPositionCode())
                .positionName(item.getPosition().getPositionName())
                .build();

        EmploymentImport emp = EmploymentImport.builder()
                .employmentCode(item.getEmployment().getEmploymentCode())
                .employmentName(item.getEmployment().getEmploymentName())
                .build();

        return EmployeeInformationImport.builder()
                .businessName(item.getBusinessName())
                .businessNameKana(item.getBusinessNameKana())
                .employeeCode(item.getEmployeeCode())
                .employeeId(item.getEmployeeId())
                .gender(item.getGender())
                .classification(cls)
                .workplace(wkp)
                .department(dep)
                .position(pos)
                .employment(emp)
                .employmentCls(item.getEmploymentCls())
                .build();
    }

	@Override
	public Map<String, String> getEmploymentMapCodeName(String companyId, List<String> empCodes) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public List<EmployeeInfoImport> getByListSid(List<String> sIds) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public List<EmployeeInfoImported> getEmployeeInfo(List<String> employeeIds, GeneralDate baseDate,
			EmployeeInfoWantToBeGet param) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}
}