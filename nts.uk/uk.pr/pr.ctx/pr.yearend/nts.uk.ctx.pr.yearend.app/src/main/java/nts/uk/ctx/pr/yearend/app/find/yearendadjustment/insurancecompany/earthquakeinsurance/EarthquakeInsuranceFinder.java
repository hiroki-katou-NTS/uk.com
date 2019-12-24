package nts.uk.ctx.pr.yearend.app.find.yearendadjustment.insurancecompany.earthquakeinsurance;

import nts.uk.ctx.pr.yearend.dom.yearendadjustment.insurancecompany.earthquakeInsurance.EarthquakeInsuranceRepository;
import nts.uk.shr.com.context.AppContexts;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
/**
 * 地震保険情報: Finder
 */
public class EarthquakeInsuranceFinder {

    @Inject
    private EarthquakeInsuranceRepository finder;

    public List<EarthquakeInsuranceDto> getAllEarthquakeInsurance() {
        return finder.getAllEarthquakeInsurance().stream().map(item -> EarthquakeInsuranceDto.fromDomain(item))
                .collect(Collectors.toList());
    }

    public List<EarthquakeInsuranceDto> getEarthquakeInsuranceByCid() {
        String cid = AppContexts.user().companyId();
        return finder.getEarthquakeInsuranceByCid(cid).stream().map(item -> EarthquakeInsuranceDto.fromDomain(item))
                .collect(Collectors.toList());
    }

}
