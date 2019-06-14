package nts.uk.ctx.pr.file.app.core.socialinsurance.salaryhealth;

import java.util.List;

public interface SalaryHealthRepository {

    List<Object[]> getSalaryHealth(String cid);

}
