package nts.uk.ctx.pr.report.app.find.printconfig.empinsreportsetting;

import nts.uk.ctx.pr.core.dom.adapter.employee.employee.EmployeeInfoAdapter;
import nts.uk.ctx.pr.core.dom.adapter.employee.employee.EmployeeInfoEx;
import nts.uk.ctx.pr.core.dom.adapter.person.PersonExport;
import nts.uk.ctx.pr.core.dom.adapter.person.PersonExportAdapter;
import nts.uk.ctx.pr.report.dom.printconfig.empinsreportsetting.EmpInsReportSetting;
import nts.uk.ctx.pr.report.dom.printconfig.empinsreportsetting.EmpInsReportSettingRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
public class EmpInsReportSettingFinder {

    @Inject
    EmpInsReportSettingRepository mEmpInsReportSettingRepository;

    @Inject
    private EmployeeInfoAdapter employeeInfoAdapter;

    @Inject
    private PersonExportAdapter personExportAdapter;

    public EmpInsReportSettingDto getEmpInsReportSetting(){
        String cid = AppContexts.user().companyId();
        String userId = AppContexts.user().userId();
        Optional<EmpInsReportSetting> resulf =  mEmpInsReportSettingRepository.getEmpInsReportSettingById(cid,userId);
        if(resulf.isPresent()){
            return resulf.map(empInsReportSetting -> {
                return EmpInsReportSettingDto.builder()
                        .cid(empInsReportSetting.getCid())
                        .userId(empInsReportSetting.getUserId())
                        .submitNameAtr(empInsReportSetting.getSubmitNameAtr().value)
                        .outputOrderAtr(empInsReportSetting.getOutputOrderAtr().value)
                        .officeClsAtr(empInsReportSetting.getOfficeClsAtr().value)
                        .myNumberClsAtr(empInsReportSetting.getMyNumberClsAtr().value)
                        .nameChangeClsAtr(empInsReportSetting.getNameChangeClsAtr().value)
                        .build();
            }).get();
        }
        return EmpInsReportSettingDto.builder()
                .cid(cid)
                .userId(userId)
                .submitNameAtr(0)
                .outputOrderAtr(0)
                .officeClsAtr(1)
                .myNumberClsAtr(0)
                .nameChangeClsAtr(0)
                .build();

    }

    public List<PersonDto> getPerson(List<String> employeeId){
        List<EmployeeInfoEx> employeeInfo= employeeInfoAdapter.findBySIds(employeeId);
        List<PersonExport> person = personExportAdapter.findByPids(employeeInfo.stream().map(EmployeeInfoEx::getPId).collect(Collectors.toList()));
        return employeeInfo.stream().map(item -> toPersonDto(item, person)).collect(Collectors.toList());
    }

    private PersonDto toPersonDto(EmployeeInfoEx employee, List<PersonExport> persons){
        Optional<PersonExport> person = persons.stream().filter(item -> item.getPersonId().equals(employee.getPId())).findFirst();
        String oldName = person.isPresent() ? person.get().getPersonNameGroup().getOldName().getFullName() : "";
        return new PersonDto(employee.getPId(), oldName, employee.getEmployeeId());
    }


}
