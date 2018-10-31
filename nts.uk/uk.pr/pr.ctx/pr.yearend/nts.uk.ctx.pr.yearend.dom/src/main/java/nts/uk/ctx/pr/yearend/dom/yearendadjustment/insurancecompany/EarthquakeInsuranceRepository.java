package nts.uk.ctx.pr.yearend.dom.yearendadjustment.insurancecompany;

import java.util.Optional;
import java.util.List;

/**
* 地震保険情報
*/
public interface EarthquakeInsuranceRepository
{

    List<EarthquakeInsurance> getAllEarthquakeInsurance();

    Optional<EarthquakeInsurance> getEarthquakeInsuranceById(String cid, String earthquakeCode);

    void add(EarthquakeInsurance domain);

    void update(EarthquakeInsurance domain);

    void remove(String cid, String earthquakeCode);

}
