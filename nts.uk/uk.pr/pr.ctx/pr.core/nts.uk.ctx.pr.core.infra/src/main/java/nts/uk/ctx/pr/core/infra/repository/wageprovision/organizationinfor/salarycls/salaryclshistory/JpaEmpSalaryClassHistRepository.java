package nts.uk.ctx.pr.core.infra.repository.wageprovision.organizationinfor.salarycls.salaryclshistory;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.organizationinfor.salarycls.salaryclshistory.EmploySalaryClassHistory;
import nts.uk.ctx.pr.core.dom.wageprovision.organizationinfor.salarycls.salaryclshistory.EmploySalaryClassHistoryRepository;

import javax.ejb.Stateless;

@Stateless
public class JpaEmpSalaryClassHistRepository extends JpaRepository implements EmploySalaryClassHistoryRepository {

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
