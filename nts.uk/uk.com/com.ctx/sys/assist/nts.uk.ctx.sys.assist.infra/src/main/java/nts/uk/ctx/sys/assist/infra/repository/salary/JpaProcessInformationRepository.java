package nts.uk.ctx.sys.assist.infra.repository.salary;

import java.util.Optional;
import java.util.List;

import javax.ejb.Stateless;

import nts.uk.ctx.sys.assist.infra.entity.salary.QpbmtProcessInformation;
import nts.uk.ctx.sys.assist.infra.entity.salary.QpbmtProcessInformationPk;
import nts.uk.ctx.sys.assist.dom.salary.ProcessInformationRepository;
import nts.uk.ctx.sys.assist.dom.salary.ProcessInformation;
import nts.arc.layer.infra.data.JpaRepository;

@Stateless
public class JpaProcessInformationRepository extends JpaRepository implements ProcessInformationRepository
{

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QpbmtProcessInformation f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.processInformationPk.cid =:cid AND  f.processInformationPk.processCateNo =:processCateNo ";

    @Override
    public List<ProcessInformation> getAllProcessInformation(){
        return this.queryProxy().query(SELECT_ALL_QUERY_STRING, QpbmtProcessInformation.class)
                .getList(item -> item.toDomain());
    }

    @Override
    public Optional<ProcessInformation> getProcessInformationById(String cid, int processCateNo){
        return this.queryProxy().query(SELECT_BY_KEY_STRING, QpbmtProcessInformation.class)
        .setParameter("cid", cid)
        .setParameter("processCateNo", processCateNo)
        .getSingle(c->c.toDomain());
    }

    @Override
    public void add(ProcessInformation domain){
        this.commandProxy().insert(QpbmtProcessInformation.toEntity(domain));
    }

    @Override
    public void update(ProcessInformation domain){
        this.commandProxy().update(QpbmtProcessInformation.toEntity(domain));
    }

    @Override
    public void remove(String cid, int processCateNo){
        this.commandProxy().remove(QpbmtProcessInformation.class, new QpbmtProcessInformationPk(cid, processCateNo)); 
    }
}
