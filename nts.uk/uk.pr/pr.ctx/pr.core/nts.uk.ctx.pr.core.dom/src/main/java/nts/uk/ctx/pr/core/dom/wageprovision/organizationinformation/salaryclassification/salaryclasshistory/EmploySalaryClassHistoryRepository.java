package nts.uk.ctx.pr.core.dom.wageprovision.organizationinformation.salaryclassification.salaryclasshistory;

import java.util.Optional;
import java.util.List;

/**
* 社員給与分類項目
*/
public interface EmploySalaryClassHistoryRepository
{

    List<EmploySalaryClassHistory> getAllEmploySalaryClassHistory();

    Optional<EmploySalaryClassHistory> getEmploySalaryClassHistoryById(String employeeId,String hisId);

    void add(EmploySalaryClassHistory domain);

    void update(EmploySalaryClassHistory domain);

    void remove(String hisId);

}
