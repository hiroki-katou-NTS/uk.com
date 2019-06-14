package nts.uk.ctx.pr.core.dom.wageprovision.organizationinfor.salarycls.salaryclshistory;

/**
* 社員給与分類項目
*/
public interface EmploySalaryCategoryRepository {

    void add(EmploySalaryCategory domain);

    void update(EmploySalaryCategory domain);

    void remove(String hisId);

}
