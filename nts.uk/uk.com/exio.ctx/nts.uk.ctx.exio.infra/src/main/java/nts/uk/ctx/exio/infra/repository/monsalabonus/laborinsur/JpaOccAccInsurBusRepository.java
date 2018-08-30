package nts.uk.ctx.exio.infra.repository.monsalabonus.laborinsur;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.exio.dom.monsalabonus.laborinsur.OccAccInsurBusRepository;
import nts.uk.ctx.exio.infra.entity.monsalabonus.laborinsur.QpbmtOccAccInsurBus;
import nts.uk.ctx.exio.infra.entity.monsalabonus.laborinsur.QpbmtOccAccInsurBusPk;

import javax.ejb.Stateless;


@Stateless
public class JpaOccAccInsurBusRepository extends JpaRepository implements OccAccInsurBusRepository
{

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QpbmtOccAccInsurBus f";
    



    @Override
    public void remove(String cid, int occAccInsurBusNo){
        this.commandProxy().remove(QpbmtOccAccInsurBus.class, new QpbmtOccAccInsurBusPk(cid, occAccInsurBusNo));
    }
}
