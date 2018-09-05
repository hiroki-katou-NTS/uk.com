package nts.uk.ctx.sys.assist.infra.repository.salary;

import java.util.Optional;
import java.util.List;

import javax.ejb.Stateless;

import nts.uk.ctx.sys.assist.infra.entity.salary.QpbmtSpecPrintYmSet;
import nts.uk.ctx.sys.assist.infra.entity.salary.QpbmtSpecPrintYmSetPk;
import nts.uk.ctx.sys.assist.dom.salary.SpecPrintYmSetRepository;
import nts.uk.ctx.sys.assist.dom.salary.SpecPrintYmSet;
import nts.arc.layer.infra.data.JpaRepository;

@Stateless
public class JpaSpecPrintYmSetRepository extends JpaRepository implements SpecPrintYmSetRepository
{

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QpbmtSpecPrintYmSet f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.specPrintYmSetPk.cid =:cid AND  f.specPrintYmSetPk.processCateNo =:processCateNo ";

    @Override
    public List<SpecPrintYmSet> getAllSpecPrintYmSet(){
        return this.queryProxy().query(SELECT_ALL_QUERY_STRING, QpbmtSpecPrintYmSet.class)
                .getList(item -> item.toDomain());
    }

    @Override
    public Optional<SpecPrintYmSet> getSpecPrintYmSetById(String cid, int processCateNo){
        return this.queryProxy().query(SELECT_BY_KEY_STRING, QpbmtSpecPrintYmSet.class)
        .setParameter("cid", cid)
        .setParameter("processCateNo", processCateNo)
        .getSingle(c->c.toDomain());
    }

    @Override
    public void add(SpecPrintYmSet domain){
        this.commandProxy().insert(QpbmtSpecPrintYmSet.toEntity(domain));
    }

    @Override
    public void update(SpecPrintYmSet domain){
        this.commandProxy().update(QpbmtSpecPrintYmSet.toEntity(domain));
    }

    @Override
    public void remove(String cid, int processCateNo){
        this.commandProxy().remove(QpbmtSpecPrintYmSet.class, new QpbmtSpecPrintYmSetPk(cid, processCateNo)); 
    }
}
