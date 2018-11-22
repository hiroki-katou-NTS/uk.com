package nts.uk.ctx.pr.core.infra.repository.wageprovision.organizationinformation.salaryclassification.salaryclassificationhistory;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.organizationinformation.salaryclassification.salaryclasshistory.EmploySalaryCategory;
import nts.uk.ctx.pr.core.dom.wageprovision.organizationinformation.salaryclassification.salaryclasshistory.EmploySalaryCategoryRepository;

import javax.ejb.Stateless;
import java.util.List;
import java.util.Optional;

@Stateless
public class JpaEmploySalaryCategoryRepository extends JpaRepository implements EmploySalaryCategoryRepository
{

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QpbmtEmpSalaCategory f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.empSalaCategoryPk.hisId =:hisId ";


    @Override
    public List<EmploySalaryCategory> getAllEmploySalaryClassHistory() {
        return null;
    }

    @Override
    public Optional<EmploySalaryCategory> getEmploySalaryClassHistoryById(String hisId) {
        return Optional.empty();
    }

    @Override
    public void add(EmploySalaryCategory domain) {

    }

    @Override
    public void update(EmploySalaryCategory domain) {

    }

    @Override
    public void remove(String hisId) {

    }
}
