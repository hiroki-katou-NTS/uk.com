package nts.uk.ctx.pr.core.infra.repository.emprsdttaxinfo;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.core.dom.emprsdttaxinfo.EmployeeResidentTaxPayeeInfo;
import nts.uk.ctx.pr.core.dom.emprsdttaxinfo.EmployeeResidentTaxPayeeInfoRepository;
import nts.uk.ctx.pr.core.infra.entity.emprsdttaxinfo.QpbmtEmpRsdtTaxPayee;
import nts.uk.ctx.pr.core.infra.entity.emprsdttaxinfo.QpbmtEmpRsdtTaxPayeePk;

import javax.ejb.Stateless;
import java.util.Collections;
import java.util.List;

@Stateless
public class JpaEmployeeResidentTaxPayeeInfoRepository extends JpaRepository
        implements EmployeeResidentTaxPayeeInfoRepository {

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QpbmtEmpRsdtTaxPayee f";
    private static final String SELECT_BY_SID = SELECT_ALL_QUERY_STRING +
            " WHERE  f.empRsdtTaxPayeePk.sid IN :listSId " +
            " AND  f.startYM <= :periodYM AND f.endYM >= :periodYM ";

    @Override
    public List<EmployeeResidentTaxPayeeInfo> getEmpRsdtTaxPayeeInfo(List<String> listSId, YearMonth periodYM) {
        if (listSId == null || listSId.isEmpty()) return Collections.emptyList();
        return QpbmtEmpRsdtTaxPayee.toDomain(this.queryProxy().query(SELECT_BY_SID, QpbmtEmpRsdtTaxPayee.class)
                .setParameter("listSId", listSId).setParameter("periodYM", periodYM.v())
                .getList());
    }

    @Override
    public void add(List<EmployeeResidentTaxPayeeInfo> domains) {
        this.commandProxy().insertAll(QpbmtEmpRsdtTaxPayee.toEntity(domains));
    }

    @Override
    public void update(List<EmployeeResidentTaxPayeeInfo> domains) {
        this.commandProxy().updateAll(QpbmtEmpRsdtTaxPayee.toEntity(domains));
    }

    @Override
    public void remove(String sid, String histId) {
        this.commandProxy().remove(QpbmtEmpRsdtTaxPayee.class, new QpbmtEmpRsdtTaxPayeePk(sid, histId));
    }
}
