package nts.uk.ctx.pr.core.infra.repository.wageprovision.organizationinfor.salarycls.salaryclshistory;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.organizationinfor.salarycls.salaryclshistory.EmploySalaryClassHistory;
import nts.uk.ctx.pr.core.dom.wageprovision.organizationinfor.salarycls.salaryclshistory.EmploySalaryClassHistoryRepository;

import javax.ejb.Stateless;
import java.util.List;
import java.util.Optional;

@Stateless
public class JpaEmpSalaryClassHistRepository extends JpaRepository implements EmploySalaryClassHistoryRepository {
    @Override
    public List<EmploySalaryClassHistory> getAllEmploySalaryClassHistory() {
        return null;
    }

    @Override
    public Optional<EmploySalaryClassHistory> getEmploySalaryClassHistoryById(String employeeId, String hisId) {
        return Optional.empty();
    }

    @Override
    public void add(EmploySalaryClassHistory domain) {

    }

    @Override
    public void update(EmploySalaryClassHistory domain) {

    }

    @Override
    public void remove(String hisId) {

    }
}
