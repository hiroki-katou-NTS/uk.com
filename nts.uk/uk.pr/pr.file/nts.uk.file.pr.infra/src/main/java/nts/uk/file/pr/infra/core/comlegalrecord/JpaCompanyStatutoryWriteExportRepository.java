package nts.uk.file.pr.infra.core.comlegalrecord;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.file.app.core.comlegalrecord.CompanyStatutoryWriteExportRepository;
import nts.uk.ctx.pr.report.dom.printdata.comlegalrecord.CompanyStatutoryWrite;
import nts.uk.ctx.pr.report.infra.entity.printdata.comlegalrecord.QrsmtComStatutoryWrite;

import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class JpaCompanyStatutoryWriteExportRepository extends JpaRepository implements CompanyStatutoryWriteExportRepository {

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QrsmtComStatutoryWrite f";
    private static final String SELECT_BY_CID = SELECT_ALL_QUERY_STRING + " WHERE  f.comStatutoryWritePk.cid =:cid order by f.comStatutoryWritePk.code ASC";

    @Override
    public List<CompanyStatutoryWrite> getByCid(String cid) {
        return this.queryProxy().query(SELECT_BY_CID, QrsmtComStatutoryWrite.class)
                .setParameter("cid", cid)
                .getList(QrsmtComStatutoryWrite::toDomain);
    }
}
