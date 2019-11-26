package nts.uk.ctx.pr.file.app.core.socialinsurance.welfarepensioninsurance;

import java.util.List;

public interface WelfarepensionInsuranceRepository {

    List<Object[]> getWelfarepensionInsuranceEmp(String cid, int startDate);
    List<Object[]> getWelfarepensionInsuranceBonus(String cid, int startDate);

}
