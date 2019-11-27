package nts.uk.ctx.pr.yearend.dom.yearendadjustment.insurancecompany.earthquakeInsurance;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

/**
* 地震保険情報
*/
@Getter
public class EarthquakeInsurance extends AggregateRoot {
    
    /**
    * 会社ID
    */
    private String cId;
    
    /**
    * コード
    */
    private EarthquakeInsuranceCode earthquakeInsuranceCode;
    
    /**
    * 名称
    */
    private EarthquakeInsuranceName earthquakeInsuranceName;
    
    public EarthquakeInsurance(String cid, String earthquakeCode, String earthquakeName) {
        this.cId = cid;
        this.earthquakeInsuranceCode = new EarthquakeInsuranceCode(earthquakeCode);
        this.earthquakeInsuranceName = new EarthquakeInsuranceName(earthquakeName);
    }
    
}
