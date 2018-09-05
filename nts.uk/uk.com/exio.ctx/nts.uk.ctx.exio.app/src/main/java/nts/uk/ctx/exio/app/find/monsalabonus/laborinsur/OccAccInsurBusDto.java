package nts.uk.ctx.exio.app.find.monsalabonus.laborinsur;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.exio.dom.monsalabonus.laborinsur.NameOfEachBusiness;
import nts.uk.ctx.exio.dom.monsalabonus.laborinsur.OccAccInsurBus;

import java.util.List;

/**
* 労災保険事業
*/
@AllArgsConstructor
@Value
public class OccAccInsurBusDto
{

    /**
     * 会社ID
     */
    private String cid;
    /**
     * 各事業
     */
    private List<NameOfEachBusiness> eachBusiness;
    
    public static OccAccInsurBusDto fromDomain(OccAccInsurBus domain)
    {
        return new OccAccInsurBusDto(domain.getCid(), domain.getEachBusiness());
    }
    
}
