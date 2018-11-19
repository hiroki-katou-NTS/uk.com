package nts.uk.ctx.pr.yearend.app.command.yearendadjustment.insurancecompany.earthquakeinsurance;

import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;

@Value
public class EarthquakeInsuranceCommand
{
    /**
    * コード
    */
    private String earthquakeInsuranceCode;
    
    /**
    * 名称
    */
    private String earthquakeInsuranceName;
    

}
