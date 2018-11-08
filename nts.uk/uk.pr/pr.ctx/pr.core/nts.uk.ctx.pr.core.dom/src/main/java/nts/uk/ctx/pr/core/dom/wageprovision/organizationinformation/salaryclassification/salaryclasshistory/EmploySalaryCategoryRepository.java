package nts.uk.ctx.pr.core.dom.wageprovision.organizationinformation.salaryclassification.salaryclasshistory;

import java.util.Optional;
import java.util.List;

/**
* 社員給与分類履歴
*/
public interface EmploySalaryCategoryRepository
{

    List<EmploySalaryCategory> getAllEmploySalaryClassHistory();

    Optional<EmploySalaryCategory> getEmploySalaryClassHistoryById(String hisId);

    void add(EmploySalaryCategory domain);

    void update(EmploySalaryCategory domain);

    void remove(String hisId);

}
