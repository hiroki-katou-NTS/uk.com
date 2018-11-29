package nts.uk.ctx.pr.core.dom.wageprovision.organizationinfor.salarycls.salaryclshistory;

import java.util.Optional;
import java.util.List;

/**
* 社員給与分類項目
*/
public interface EmploySalaryCategoryRepository {

    Optional<EmploySalaryClassHistory> getEmploySalaryClassHistoryById(String employeeId, String hisId);

    List<EmploySalaryCategory> getAllEmploySalaryClassHistory();

    Optional<EmploySalaryCategory> getEmploySalaryClassHistoryById(String hisId);

    void add(EmploySalaryCategory domain);

    void update(EmploySalaryCategory domain);

    void remove(String hisId);

}
