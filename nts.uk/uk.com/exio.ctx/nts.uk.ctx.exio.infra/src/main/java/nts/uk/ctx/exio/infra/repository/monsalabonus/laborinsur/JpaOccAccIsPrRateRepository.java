package nts.uk.ctx.exio.infra.repository.monsalabonus.laborinsur;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.exio.dom.monsalabonus.laborinsur.OccAccIsPrRate;
import nts.uk.ctx.exio.dom.monsalabonus.laborinsur.OccAccIsPrRateRepository;
import nts.uk.ctx.exio.infra.entity.monsalabonus.laborinsur.QpbmtOccAccIsPrRate;
import nts.uk.ctx.exio.infra.entity.monsalabonus.laborinsur.QpbmtOccAccIsPrRatePk;

@Stateless
public class JpaOccAccIsPrRateRepository extends JpaRepository implements OccAccIsPrRateRepository
{

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QpbmtOccAccIs PrRate f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.occAccIsPrRatePk.ocAcIsPrRtId =:ocAcIsPrRtId AND  f.occAccIsPrRatePk.hisId =:hisId ";

    @Override
    public List<OccAccIsPrRate> getAllOccAccIsPrRate(){
        return this.queryProxy().query(SELECT_ALL_QUERY_STRING, QpbmtOccAccIsPrRate.class)
                .getList(item -> item.toDomain());
    }

    @Override
    public Optional<OccAccIsPrRate> getOccAccIsPrRateById(String ocAcIsPrRtId, String hisId){
        return this.queryProxy().query(SELECT_BY_KEY_STRING, QpbmtOccAccIsPrRate.class)
        .setParameter("ocAcIsPrRtId", ocAcIsPrRtId)
        .setParameter("hisId", hisId)
        .getSingle(c->c.toDomain());
    }

    @Override
    public void add(OccAccIsPrRate domain){
        this.commandProxy().insert(QpbmtOccAccIsPrRate.toEntity(domain));
    }

    @Override
    public void update(OccAccIsPrRate domain){
        this.commandProxy().update(QpbmtOccAccIsPrRate.toEntity(domain));
    }

    @Override
    public void remove(String ocAcIsPrRtId, String hisId){
        this.commandProxy().remove(QpbmtOccAccIsPrRate.class, new QpbmtOccAccIsPrRatePk(ocAcIsPrRtId, hisId)); 
    }
}
