package nts.uk.ctx.pr.yearend.dom.yearendadjustment.insurancecompany.lifeInsurance;

import nts.uk.ctx.pr.yearend.dom.yearendadjustment.insurancecompany.earthquakeInsurance.EarthquakeInsurance;

import java.util.Optional;
import java.util.List;

/**
 * 生命保険情報
 */
public interface LifeInsuranceRepository {

    List<LifeInsurance> getAllLifeInsurance();

    List<LifeInsurance> getLifeInsuranceBycId(String cid);

    Optional<LifeInsurance> getLifeInsuranceById(String cid, String lifeInsuranceCode);

    void add(LifeInsurance domain);

    void update(LifeInsurance domain);

    void remove(String cid, String lifeInsuranceCode);

    void removeLifeInsurance(String cid, String lifeInsuranceCode);

    void copyAddEarthQuakeInsu(List<LifeInsurance> lstLifeInsurance);

    void updarteAddEarthQuakeInsu(List<LifeInsurance> lstLifeInsurance);

    List<EarthquakeInsurance> getEarthquakeByLstLifeInsuranceCode(String cid, List lifeInsuranceCode);

    List<LifeInsurance> getLifeInsurancedByLstEarthquakeInsuranceCode(String cid, List lifeInsuranceCode);
}
