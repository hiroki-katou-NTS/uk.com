package nts.uk.ctx.pr.core.dom.employaverwage;

import java.util.Optional;
import java.util.List;

/**
* 社員平均賃金
*/
public interface EmployAverWageRepository
{

    List<EmployAverWage> getAllEmployAverWage();

    Optional<EmployAverWage> getEmployAverWageById(String employeeId);

    void add(EmployAverWage domain);

    void update(EmployAverWage domain);

    void remove(String employeeId);

}
