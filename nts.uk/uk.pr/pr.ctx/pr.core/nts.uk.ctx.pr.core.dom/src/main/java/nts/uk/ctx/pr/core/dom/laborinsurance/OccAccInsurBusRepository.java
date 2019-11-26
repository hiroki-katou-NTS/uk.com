package nts.uk.ctx.pr.core.dom.laborinsurance;


import java.util.Optional;

/**
* 労災保険事業
*/
public interface OccAccInsurBusRepository
{
    Optional<OccAccInsurBus> getOccAccInsurBus(String cId);
    void update(OccAccInsurBus domain);
    void remove(String cid, int occAccInsurBusNo);
    
}
