package nts.uk.file.pr.infra.core.socialinsurance.salaryhealth;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.file.app.core.socialinsurance.salaryhealth.SalaryHealthRepository;

import javax.ejb.Stateless;
import java.util.Collections;
import java.util.List;

@Stateless
public class JpaSalaryHealthExRepository extends JpaRepository implements SalaryHealthRepository {


    @Override
    public List<Object[]> getSalaryHealth(String cid) {
        return Collections.emptyList();
    }
}
