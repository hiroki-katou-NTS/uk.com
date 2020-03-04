package nts.uk.ctx.pr.yearend.app.yearendadjustment.insurancecompany.lifeinsurance;

import java.util.List;

public interface LifeInsuranceExRepository {

    List<Object[]> getLifeInsurances(String cid);

}
