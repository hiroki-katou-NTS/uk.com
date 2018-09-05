package nts.uk.ctx.exio.dom.monsalabonus.laborinsur;


import java.util.List;
import java.util.Optional;

/**
* 労災保険事業
*/
public interface OccAccInsurBusRepository
{
    Optional<OccAccInsurBus> getOccAccInsurBus(String cId);
    void remove(String cid, int occAccInsurBusNo);

}
