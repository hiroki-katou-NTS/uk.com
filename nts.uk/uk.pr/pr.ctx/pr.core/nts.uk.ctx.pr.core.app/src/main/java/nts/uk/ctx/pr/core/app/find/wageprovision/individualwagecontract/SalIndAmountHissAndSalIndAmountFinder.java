package nts.uk.ctx.pr.core.app.find.wageprovision.individualwagecontract;


import nts.uk.ctx.pr.core.app.command.wageprovision.individualwagecontract.SalIndAmountByPerValCodeCommand;
import nts.uk.ctx.pr.core.dom.adapter.employee.employee.EmployeeInfoAdapter;
import nts.uk.ctx.pr.core.dom.wageprovision.individualwagecontract.*;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.*;

@Stateless
public class SalIndAmountHissAndSalIndAmountFinder {

    @Inject
    private SalIndAmountHisRepository salIndAmountHisRepository;

    @Inject
    private EmployeeInfoAdapter employeeInfoAdapter;

    public EmployeeInfoImportDto getPersonalAmounts(SalIndAmountByPerValCodeCommand command) {
        List<EmployeeInfoImport> employeeInfoImports = employeeInfoAdapter.getByListSid(command.getEmployeeIds());
        List<PersonalAmount> personalAmount = this.salIndAmountHisRepository.getSalIndAmountHisByPerVal(command.getPerValCode(),
                command.getCateIndicator(), command.getSalBonusCate(), command.getStandardYearMonth(), command.getEmployeeIds());
        return new EmployeeInfoImportDto(employeeInfoImports, personalAmount);
    }
}