package nts.uk.ctx.pr.shared.app.find.employaverwage;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.shared.app.command.employaverwage.EmployeeComand;
import nts.uk.ctx.pr.shared.dom.adapter.query.employee.EmployeeInformationAdapter;
import nts.uk.ctx.pr.shared.dom.adapter.query.employee.EmployeeInformationQueryDtoImport;
import nts.uk.ctx.pr.shared.dom.adapter.wageprovision.processdatecls.ProcessDateClassificationAdapter;
import nts.uk.ctx.pr.shared.dom.adapter.wageprovision.processdatecls.ProcessDateClassificationImport;
import nts.uk.ctx.pr.shared.dom.employaverwage.EmployAverWage;
import nts.uk.ctx.pr.shared.dom.employaverwage.EmployAverWageRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.*;
import java.util.stream.Collectors;

@Stateless
public class EmployeeAverWageFinder {

    @Inject
    private EmployAverWageRepository employAverWageRepository;

    @Inject
    private EmployeeInformationAdapter employeeInformationAdapter;

    @Inject
    private ProcessDateClassificationAdapter processDateClassificationAdapter;


    public List<String> init() {
        List<String> response = new ArrayList<>();

        Optional<ProcessDateClassificationImport> processDateClassificationImport = processDateClassificationAdapter.getCurrentProcessYearMonthAndExtraRefDate();
        processDateClassificationImport.ifPresent(x -> {
            response.add(Objects.isNull(x.getGiveCurrTreatYear()) ? null : x.getGiveCurrTreatYear().toString());
            response.add(Objects.isNull(x.getEmpExtraRefeDate()) ? null : x.getEmpExtraRefeDate().toString());
        });
        return response;
    }

    public List<EmployeeInfoDto> getEmpInfoDept(EmployeeComand param) {
        long defaultAvgWage = 0L;
        // ドメインモデル「所属部門」をすべて取得する
        return employeeInformationAdapter
                .getEmployeeInfo(new EmployeeInformationQueryDtoImport(param.getEmployeeIds(), GeneralDate.fromString(param.getBaseDate(), "yyyy/MM/dd"),
                        false,
                        true,
                        false,
                        true,
                        false,
                        false)).stream().map(x -> {
                    EmployeeInfoDto dto = new EmployeeInfoDto();
                    dto.setEmployeeId(x.getEmployeeId());
                    dto.setEmployeeCode(x.getEmployeeCode());
                    dto.setBusinessName(x.getBusinessName());
                    if (x.getEmployment() != null) {
                        dto.setEmploymentName(x.getEmployment().getEmploymentName());
                    } else {
                        dto.setEmploymentName("");
                    }
                    if (x.getDepartment() != null) {
                        dto.setDepartmentName(x.getDepartment().getDepartmentName());
                    } else {
                        dto.setDepartmentName("");
                    }
                    Optional<EmployAverWage> employAverWage = employAverWageRepository.getEmployAverWageById(x.getEmployeeId(), Integer.valueOf(param.getGiveCurrTreatYear().replaceAll("/", "")));
                    dto.setAverageWage(employAverWage.map(e -> e.getAverageWage().v()).orElse(0L));
                    return dto;
                }).collect(Collectors.toList());
    }

    public EmploymentCodeDto getEmploymentCodeByEmpIdAndBaseDate(String employeeId) {
        return employeeInformationAdapter
                .getEmployeeInfo(new EmployeeInformationQueryDtoImport(Collections.singletonList(employeeId), GeneralDate.today(),
                        false,
                        false,
                        false,
                        true,
                        false,
                        false))
                .stream().map(x -> {
                    if (Objects.isNull(x.getEmployment())) return new EmploymentCodeDto(null);
                    return new EmploymentCodeDto(x.getEmployment().getEmploymentCode());
                }).findFirst().orElse(null);
    }
}
