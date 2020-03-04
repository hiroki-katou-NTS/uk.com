package nts.uk.ctx.pr.yearend.app.yearendadjustment.insurancecompany.earthquakeinsurance;

import java.util.List;

public interface EarthQuakeInsuranceExRepository {

    List<Object[]> getEarthQuakeInsurances(String cid);


}
