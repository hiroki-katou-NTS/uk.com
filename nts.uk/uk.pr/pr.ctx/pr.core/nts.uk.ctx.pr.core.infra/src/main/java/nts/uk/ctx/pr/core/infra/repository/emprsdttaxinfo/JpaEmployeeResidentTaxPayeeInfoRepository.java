package nts.uk.ctx.pr.core.infra.repository.emprsdttaxinfo;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.dom.emprsdttaxinfo.EmployeeResidentTaxPayeeInfo;
import nts.uk.ctx.pr.core.dom.emprsdttaxinfo.EmployeeResidentTaxPayeeInfoRepository;
import nts.uk.ctx.pr.core.infra.entity.emprsdttaxinfo.QpbmtEmpRsdtTaxPayee;
import nts.uk.ctx.pr.core.infra.entity.emprsdttaxinfo.QpbmtEmpRsdtTaxPayeePk;

import javax.ejb.Stateless;
import java.util.List;
import java.util.Optional;

@Stateless
public class JpaEmployeeResidentTaxPayeeInfoRepository extends JpaRepository
        implements EmployeeResidentTaxPayeeInfoRepository {

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QpbmtEmpRsdtTaxPayee f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING +
            " WHERE  f.empRsdtTaxPayeePk.sid =:sid AND  f.empRsdtTaxPayeePk.histId =:histId ";

    @Override
    public List<EmployeeResidentTaxPayeeInfo> getAllEmployeeResidentTaxPayeeInfo() {
        return this.queryProxy().query(SELECT_ALL_QUERY_STRING, QpbmtEmpRsdtTaxPayee.class)
                .getList(item -> item.toDomain());
    }

    @Override
    public Optional<EmployeeResidentTaxPayeeInfo> getEmployeeResidentTaxPayeeInfoById(String sid, String histId) {
        return this.queryProxy().query(SELECT_BY_KEY_STRING, QpbmtEmpRsdtTaxPayee.class)
                .setParameter("sid", sid)
                .setParameter("histId", histId)
                .getSingle(c -> c.toDomain());
    }

    @Override
    public void add(EmployeeResidentTaxPayeeInfo domain) {
        this.commandProxy().insert(QpbmtEmpRsdtTaxPayee.toEntity(domain));
    }

    @Override
    public void update(EmployeeResidentTaxPayeeInfo domain) {
        this.commandProxy().update(QpbmtEmpRsdtTaxPayee.toEntity(domain));
    }

    @Override
    public void remove(String sid, String histId) {
        this.commandProxy().remove(QpbmtEmpRsdtTaxPayee.class, new QpbmtEmpRsdtTaxPayeePk(sid, histId));
    }
}
