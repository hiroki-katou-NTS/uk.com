package nts.uk.file.pr.infra.core.socialinsurance.welfarepensioninsurance;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.file.app.core.socialinsurance.welfarepensioninsurance.WelfarepensionInsuranceRepository;

import javax.ejb.Stateless;
import java.util.Collections;
import java.util.List;

@Stateless
public class JpaWelfarepensionInsuranceExRepository extends JpaRepository implements WelfarepensionInsuranceRepository {

    @Override
    public List<Object[]> getWelfarepensionInsurance(String cid) {
        return Collections.emptyList();
    }

}
