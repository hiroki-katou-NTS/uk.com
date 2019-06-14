package nts.uk.ctx.pr.core.infra.repository.wageprovision.processdatecls;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.ValPayDateSet;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.ValPayDateSetRepository;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.processdatecls.QpbmtValPayDateSet;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.processdatecls.QpbmtValPayDateSetPk;

@Stateless
public class JpaValPayDateSetRepository extends JpaRepository implements ValPayDateSetRepository
{


    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QpbmtValPayDateSet f ";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.valPayDateSetPk.cid =:cid AND  f.valPayDateSetPk.processCateNo =:processCateNo ";

    @Override
    public List<ValPayDateSet> getAllValPayDateSet(){

        return this.queryProxy().query(SELECT_ALL_QUERY_STRING, QpbmtValPayDateSet.class)
                .getList(item -> item.toDomain());
    }

    @Override
    public Optional<ValPayDateSet> getValPayDateSetById(String cid, int processCateNo){
        return this.queryProxy().query(SELECT_BY_KEY_STRING, QpbmtValPayDateSet.class)
        .setParameter("cid", cid)
        .setParameter("processCateNo", processCateNo)
        .getSingle(c->c.toDomain());
    }

    @Override
    public void add(ValPayDateSet domain){
        this.commandProxy().insert(QpbmtValPayDateSet.toEntity(domain));
    }

    @Override
    public void update(ValPayDateSet domain){
        this.commandProxy().update(QpbmtValPayDateSet.toEntity(domain));
    }

    @Override
    public void remove(String cid, int processCateNo){
        this.commandProxy().remove(QpbmtValPayDateSet.class, new QpbmtValPayDateSetPk(cid, processCateNo)); 
    }
}
