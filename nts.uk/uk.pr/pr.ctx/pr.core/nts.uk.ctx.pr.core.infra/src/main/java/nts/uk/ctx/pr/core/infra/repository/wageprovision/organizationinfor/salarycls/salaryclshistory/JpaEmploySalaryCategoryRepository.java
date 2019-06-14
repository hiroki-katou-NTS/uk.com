package nts.uk.ctx.pr.core.infra.repository.wageprovision.organizationinfor.salarycls.salaryclshistory;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.organizationinfor.salarycls.salaryclshistory.EmploySalaryCategory;
import nts.uk.ctx.pr.core.dom.wageprovision.organizationinfor.salarycls.salaryclshistory.EmploySalaryCategoryRepository;

import javax.ejb.Stateless;

@Stateless
public class JpaEmploySalaryCategoryRepository extends JpaRepository implements EmploySalaryCategoryRepository {

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
