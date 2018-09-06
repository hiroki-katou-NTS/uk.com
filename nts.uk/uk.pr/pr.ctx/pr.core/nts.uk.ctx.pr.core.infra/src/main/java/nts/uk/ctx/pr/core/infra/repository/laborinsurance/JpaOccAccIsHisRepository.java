package nts.uk.ctx.pr.core.infra.repository.laborinsurance;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.dom.laborinsurance.OccAccIsHis;
import nts.uk.ctx.pr.core.dom.laborinsurance.OccAccIsHisRepository;
import nts.uk.ctx.pr.core.infra.entity.laborinsurance.QpbmtOccAccIsHis;
import nts.uk.ctx.pr.core.infra.entity.laborinsurance.QpbmtOccAccIsHisPk;

import java.util.Optional;
import java.util.List;

import javax.ejb.Stateless;


@Stateless
public class JpaOccAccIsHisRepository extends JpaRepository implements OccAccIsHisRepository
{

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QpbmtOccAccIsHis f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.occAccIsHisPk.cid =:cid AND  f.occAccIsHisPk.hisId =:hisId ";
    private static final String SELECT_BY_CID = SELECT_ALL_QUERY_STRING + " WHERE  f.occAccIsHisPk.cid =:cid ";

    @Override
    public List<OccAccIsHis> getAllOccAccIsHis(){
        return this.queryProxy().query(SELECT_ALL_QUERY_STRING, QpbmtOccAccIsHis.class)
                .getList(item -> item.toDomain());
    }

    @Override
    public List<OccAccIsHis> getAllOccAccIsHisByCid(String cid) {
        return this.queryProxy().query(SELECT_BY_CID, QpbmtOccAccIsHis.class).setParameter("cid", cid).
                getList(item -> item.toDomain());
    }

    @Override
    public Optional<OccAccIsHis> getOccAccIsHisById(String cid, String hisId){
        return this.queryProxy().query(SELECT_BY_KEY_STRING, QpbmtOccAccIsHis.class)
        .setParameter("cid", cid)
        .setParameter("hisId", hisId)
        .getSingle(c->c.toDomain());
    }

    @Override
    public void add(OccAccIsHis domain){
        this.commandProxy().insert(QpbmtOccAccIsHis.toEntity(domain));
    }

    @Override
    public void update(OccAccIsHis domain){
        this.commandProxy().update(QpbmtOccAccIsHis.toEntity(domain));
    }

    @Override
    public void remove(String cid, String hisId){
        this.commandProxy().remove(QpbmtOccAccIsHis.class, new QpbmtOccAccIsHisPk(cid, hisId));
    }
}
