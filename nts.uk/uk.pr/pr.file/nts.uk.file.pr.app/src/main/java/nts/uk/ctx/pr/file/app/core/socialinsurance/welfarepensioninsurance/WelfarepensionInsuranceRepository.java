package nts.uk.ctx.pr.file.app.core.socialinsurance.welfarepensioninsurance;

import java.util.List;

public interface WelfarepensionInsuranceRepository {

    List<Object[]> getWelfarepensionInsurance(String cid);

}
