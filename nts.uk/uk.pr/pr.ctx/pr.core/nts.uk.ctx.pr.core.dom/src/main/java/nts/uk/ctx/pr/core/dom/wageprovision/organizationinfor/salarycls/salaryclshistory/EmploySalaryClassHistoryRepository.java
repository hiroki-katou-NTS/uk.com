package nts.uk.ctx.pr.core.dom.wageprovision.organizationinfor.salarycls.salaryclshistory;

/**
* 社員給与分類項目
*/
public interface EmploySalaryClassHistoryRepository
{

    void add(EmploySalaryClassHistory domain);

    void update(EmploySalaryClassHistory domain);

    void remove(String hisId);

}
