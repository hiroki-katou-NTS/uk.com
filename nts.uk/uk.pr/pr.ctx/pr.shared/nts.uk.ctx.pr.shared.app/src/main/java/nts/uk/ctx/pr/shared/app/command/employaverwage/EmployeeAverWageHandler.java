package nts.uk.ctx.pr.shared.app.command.employaverwage;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.pr.shared.dom.employaverwage.EmployAverWage;
import nts.uk.ctx.pr.shared.dom.employaverwage.EmployAverWageRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Stateless
public class EmployeeAverWageHandler extends CommandHandlerWithResult<EmployeeAverWageComand,List<String>> {

    @Inject
    private EmployAverWageRepository employAverWageRepository;

    @Override
    protected List<String> handle(CommandHandlerContext<EmployeeAverWageComand> context) {
        List<String> response = new ArrayList<>();
        EmployeeAverWageComand employeeComand = context.getCommand();
        if(employeeComand.getEmployeeDtoList().size() > 0) {
            List<EmployeeDto> employeeDtoList = employeeComand.getEmployeeDtoList();
            List<EmployAverWage> newEmployee = new ArrayList<>();
            List<EmployAverWage> oldEmployee = new ArrayList<>();
            for (EmployeeDto employee : employeeDtoList) {
                int targetDate = Integer.valueOf(employeeComand.getGiveCurrTreatYear().replaceAll("/",""));
                Optional<EmployAverWage> employAverWage = employAverWageRepository.getEmployAverWageById(employee.getEmployeeId(),targetDate);
                if (employAverWage.isPresent()) {
                    oldEmployee.add(new EmployAverWage(employee.getEmployeeId(),targetDate, BigDecimal.valueOf(employee.getAverageWage())));
                } else {
                    newEmployee.add(new EmployAverWage(employee.getEmployeeId(),targetDate, BigDecimal.valueOf(employee.getAverageWage())));
                }
            }
            try {
                employAverWageRepository.updateAll(oldEmployee);
                employAverWageRepository.addAll(newEmployee);
            } catch (Exception e) {
                response.add("Error");
                return response;
            }
            response.add("Msg_15");
        } else {
            response.add("Error");
        }
        return response;
    }
}
