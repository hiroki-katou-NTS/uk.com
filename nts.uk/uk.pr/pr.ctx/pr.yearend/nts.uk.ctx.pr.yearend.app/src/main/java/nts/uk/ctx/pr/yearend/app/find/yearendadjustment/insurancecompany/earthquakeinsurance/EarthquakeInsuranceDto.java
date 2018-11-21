package nts.uk.ctx.pr.yearend.app.find.yearendadjustment.insurancecompany.earthquakeinsurance;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.pr.yearend.dom.yearendadjustment.insurancecompany.earthquakeInsurance.EarthquakeInsurance;

/**
 * 地震保険情報: DTO
 */
@AllArgsConstructor
@Value
public class EarthquakeInsuranceDto {

    /**
     * コード
     */
    private String earthquakeInsuranceCode;

    /**
     * 名称
     */
    private String earthquakeInsuranceName;


    public static EarthquakeInsuranceDto fromDomain(EarthquakeInsurance domain) {
        return new EarthquakeInsuranceDto(domain.getEarthquakeInsuranceCode().v(), domain.getEarthquakeInsuranceName().v());
    }

}
