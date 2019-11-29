package nts.uk.ctx.pr.file.app.core.socialinsurance.healthinsurance;

import java.util.List;

public interface HealthInsuranceRepository {

    List<Object[]> getHeathyInsuranceMonth(String cid, int startDate);

    List<Object[]> getBonusHeathyInsurance(String cid, int startDate);


}
