package nts.uk.ctx.core.infra.repository.fromsetting.printdata.companystatuwrite;

import java.util.Optional;
import java.util.List;

import javax.ejb.Stateless;

import nts.uk.ctx.core.dom.printdata.CompanyStatutoryWrite;
import nts.uk.ctx.core.dom.printdata.CompanyStatutoryWriteRepository;
import nts.uk.ctx.core.infra.entity.fromsetting.printdata.companystatuwrite.QpbmtComStatutoryWrite;
import nts.uk.ctx.core.infra.entity.fromsetting.printdata.companystatuwrite.QpbmtComStatutoryWritePk;
import nts.arc.layer.infra.data.JpaRepository;

@Stateless
public class JpaCompanyStatutoryWriteRepository extends JpaRepository implements CompanyStatutoryWriteRepository
{

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QpbmtComStatutoryWrite f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.comStatutoryWritePk.cid =:cid AND  f.comStatutoryWritePk.code =:code ";

    @Override
    public List<CompanyStatutoryWrite> getAllCompanyStatutoryWrite(){
        return this.queryProxy().query(SELECT_ALL_QUERY_STRING, QpbmtComStatutoryWrite.class)
                .getList(item -> item.toDomain());
    }

    @Override
    public Optional<CompanyStatutoryWrite> getCompanyStatutoryWriteById(String cid, String code){
        return this.queryProxy().query(SELECT_BY_KEY_STRING, QpbmtComStatutoryWrite.class)
        .setParameter("cid", cid)
        .setParameter("code", code)
        .getSingle(c->c.toDomain());
    }

    @Override
    public void add(CompanyStatutoryWrite domain){
        this.commandProxy().insert(QpbmtComStatutoryWrite.toEntity(domain));
    }

    @Override
    public void update(CompanyStatutoryWrite domain){
        this.commandProxy().update(QpbmtComStatutoryWrite.toEntity(domain));
    }

    @Override
    public void remove(String cid, String code){
        this.commandProxy().remove(QpbmtComStatutoryWrite.class, new QpbmtComStatutoryWritePk(cid, code));
    }
}
