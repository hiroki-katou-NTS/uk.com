package nts.uk.file.pr.infra.core.socinsurnoticreset;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.file.app.core.socialinsurnoticreset.GuaByTheInsurExportRepository;
import nts.uk.ctx.pr.report.dom.printdata.comlegalrecord.CompanyStatutoryWrite;
import nts.uk.ctx.pr.report.infra.entity.printdata.comlegalrecord.QrsmtComStatutoryWrite;

import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class JpaGuaByTheInsurExportRepository extends JpaRepository implements GuaByTheInsurExportRepository {

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QrsmtComStatutoryWrite f";
    private static final String SELECT_BY_CID = SELECT_ALL_QUERY_STRING + " WHERE  f.comStatutoryWritePk.cid =:cid order by f.comStatutoryWritePk.code ASC";




    @Override
    public List<CompanyStatutoryWrite> getByCid(String cid) {
        return this.queryProxy().query(SELECT_BY_CID, QrsmtComStatutoryWrite.class)
                .setParameter("cid", cid)
                .getList(QrsmtComStatutoryWrite::toDomain);
    }

}
