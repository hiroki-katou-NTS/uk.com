package nts.uk.ctx.pr.report.infra.repository.printdata.comlegalrecord;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.report.dom.printdata.comlegalrecord.Code;
import nts.uk.ctx.pr.report.dom.printdata.comlegalrecord.CompanyStatutoryWrite;
import nts.uk.ctx.pr.report.dom.printdata.comlegalrecord.CompanyStatutoryWriteRepository;
import nts.uk.ctx.pr.report.infra.entity.printdata.comlegalrecord.QrsmtComStatutoryWrite;
import nts.uk.ctx.pr.report.infra.entity.printdata.comlegalrecord.QrsmtComStatutoryWritePk;

import javax.ejb.Stateless;
import java.util.List;
import java.util.Optional;

@Stateless
public class JpaCompanyStatutoryWriteRepository extends JpaRepository implements CompanyStatutoryWriteRepository {

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QrsmtComStatutoryWrite f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.comStatutoryWritePk.cid =:cid AND  f.comStatutoryWritePk.code =:code ";
    private static final String SELECT_BY_CID = SELECT_ALL_QUERY_STRING + " WHERE  f.comStatutoryWritePk.cid =:cid order by f.comStatutoryWritePk.code ASC";


    @Override
    public List<CompanyStatutoryWrite> getAllCompanyStatutoryWrite() {
        return this.queryProxy().query(SELECT_ALL_QUERY_STRING, QrsmtComStatutoryWrite.class)
                .getList(QrsmtComStatutoryWrite::toDomain);
    }

    @Override
    public Optional<CompanyStatutoryWrite> getCompanyStatutoryWriteById(String cid, String code) {
        return this.queryProxy().query(SELECT_BY_KEY_STRING, QrsmtComStatutoryWrite.class)
                .setParameter("cid", cid)
                .setParameter("code", new Code(code).v())
                .getSingle(QrsmtComStatutoryWrite::toDomain);
    }

    @Override
    public void add(CompanyStatutoryWrite domain) {
        this.commandProxy().insert(QrsmtComStatutoryWrite.toEntity(domain));
    }

    @Override
    public void update(CompanyStatutoryWrite domain) {
        this.commandProxy().update(QrsmtComStatutoryWrite.toEntity(domain));
    }

    @Override
    public void remove(String cid, String code) {
        this.commandProxy().remove(QrsmtComStatutoryWrite.class, new QrsmtComStatutoryWritePk(cid, code));
    }

    @Override
    public List<CompanyStatutoryWrite> getByCid(String cid) {
        return this.queryProxy().query(SELECT_BY_CID, QrsmtComStatutoryWrite.class)
                .setParameter("cid", cid)
                .getList(QrsmtComStatutoryWrite::toDomain);
    }
}