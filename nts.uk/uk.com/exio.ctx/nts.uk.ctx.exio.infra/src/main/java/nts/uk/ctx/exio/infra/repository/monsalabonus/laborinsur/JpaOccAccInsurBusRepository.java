package nts.uk.ctx.exio.infra.repository.monsalabonus.laborinsur;

import java.util.Optional;
import java.util.List;

import javax.ejb.Stateless;

import nts.uk.ctx.exio.dom.OccAccInsurBusRepository;
import nts.uk.ctx.exio.dom.OccAccInsurBus;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.exio.infra.entity.monsalabonus.laborinsur.QpbmtOccAccInsurBus;
import nts.uk.ctx.exio.infra.entity.monsalabonus.laborinsur.QpbmtOccAccInsurBusPk;

@Stateless
public class JpaOccAccInsurBusRepository extends JpaRepository implements OccAccInsurBusRepository
{

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QpbmtOccAccInsurBus f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.occAccInsurBusPk.cid =:cid AND  f.occAccInsurBusPk.occAccInsurBusNo =:occAccInsurBusNo ";

    @Override
    public List<OccAccInsurBus> getAllOccAccInsurBus(){
        return this.queryProxy().query(SELECT_ALL_QUERY_STRING, QpbmtOccAccInsurBus.class)
                .getList(item -> item.toDomain());
    }

    @Override
    public Optional<OccAccInsurBus> getOccAccInsurBusById(String cid, int occAccInsurBusNo){
        return this.queryProxy().query(SELECT_BY_KEY_STRING, QpbmtOccAccInsurBus.class)
        .setParameter("cid", cid)
        .setParameter("occAccInsurBusNo", occAccInsurBusNo)
        .getSingle(c->c.toDomain());
    }

    @Override
    public void add(OccAccInsurBus domain){
        this.commandProxy().insert(QpbmtOccAccInsurBus.toEntity(domain));
    }

    @Override
    public void update(OccAccInsurBus domain){
        this.commandProxy().update(QpbmtOccAccInsurBus.toEntity(domain));
    }

    @Override
    public void remove(String cid, int occAccInsurBusNo){
        this.commandProxy().remove(QpbmtOccAccInsurBus.class, new QpbmtOccAccInsurBusPk(cid, occAccInsurBusNo));
    }
}
