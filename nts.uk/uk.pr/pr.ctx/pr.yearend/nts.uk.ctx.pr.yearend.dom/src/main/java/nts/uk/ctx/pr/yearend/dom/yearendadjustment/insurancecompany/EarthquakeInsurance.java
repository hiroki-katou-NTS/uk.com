package nts.uk.ctx.pr.yearend.dom.yearendadjustment.insurancecompany;

import java.util.Optional;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.arc.enums.EnumAdaptor;

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
