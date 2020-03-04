package nts.uk.ctx.pr.shared.dom.employaverwage;

import java.util.Optional;
import java.util.List;

/**
* 社員平均賃金
*/
public interface EmployAverWageRepository
{

    List<EmployAverWage> getAllEmployAverWage();

    Optional<EmployAverWage> getEmployAverWageById(String employeeId, int targetDate);

    List<EmployAverWage> getEmployByIds(List<String> employeeIds, int targetDate);

    void add(EmployAverWage domain);

    void update(EmployAverWage domain);

    void remove(String employeeId, int targetDate);

    void addAll(List<EmployAverWage> domains);

    void updateAll(List<EmployAverWage> domains);

}
