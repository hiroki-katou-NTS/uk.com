package nts.uk.ctx.pr.core.infra.repository.emprsdttaxinfo.amountinfo;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.dom.emprsdttaxinfo.amountinfo.EmployeeResidentTaxPayAmountInfo;
import nts.uk.ctx.pr.core.dom.emprsdttaxinfo.amountinfo.EmployeeResidentTaxPayAmountInfoRepository;
import nts.uk.ctx.pr.core.infra.entity.emprsdttaxinfo.amountinfo.QpbmtEmpRsdtTaxPayAm;
import nts.uk.ctx.pr.core.infra.entity.emprsdttaxinfo.amountinfo.QpbmtEmpRsdtTaxPayAmPk;

import javax.ejb.Stateless;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Stateless
public class JpaEmployeeResidentTaxPayAmountInfoRepository extends JpaRepository
        implements EmployeeResidentTaxPayAmountInfoRepository {

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QpbmtEmpRsdtTaxPayAm f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING +
            " WHERE  f.empRsdtTaxPayAmPk.sid =:sid AND  f.empRsdtTaxPayAmPk.year =:year ";
    private static final String SELECT_BY_LIST_KEY_STRING = SELECT_ALL_QUERY_STRING +
            " WHERE  f.empRsdtTaxPayAmPk.sid IN :listSId AND  f.empRsdtTaxPayAmPk.year =:year ";

    @Override
    public Optional<EmployeeResidentTaxPayAmountInfo> getEmpRsdtTaxPayAmountInfoById(String sid, int year) {
        return this.queryProxy().query(SELECT_BY_KEY_STRING, QpbmtEmpRsdtTaxPayAm.class)
                .setParameter("sid", sid)
                .setParameter("year", year)
                .getSingle(c -> c.toDomain());
    }

    @Override
    public List<EmployeeResidentTaxPayAmountInfo> getListEmpRsdtTaxPayAmountInfo(List<String> listSId, int year) {
        if (listSId == null || listSId.isEmpty()) return Collections.emptyList();
        return this.queryProxy().query(SELECT_BY_LIST_KEY_STRING, QpbmtEmpRsdtTaxPayAm.class)
                .setParameter("listSId", listSId)
                .setParameter("year", year)
                .getList(item -> item.toDomain());
    }

    @Override
    public void add(EmployeeResidentTaxPayAmountInfo domain) {
        this.commandProxy().insert(QpbmtEmpRsdtTaxPayAm.toEntity(domain));
    }

    @Override
    public void addAll(List<EmployeeResidentTaxPayAmountInfo> domains) {
        this.commandProxy().insertAll(QpbmtEmpRsdtTaxPayAm.toEntitys(domains));
    }

    @Override
    public void update(EmployeeResidentTaxPayAmountInfo domain) {
        this.commandProxy().update(QpbmtEmpRsdtTaxPayAm.toEntity(domain));
    }

    @Override
    public void updateAll(List<EmployeeResidentTaxPayAmountInfo> domains) {
        this.commandProxy().updateAll(QpbmtEmpRsdtTaxPayAm.toEntitys(domains));
    }

    @Override
    public void remove(String sid, int year) {
        this.commandProxy().remove(QpbmtEmpRsdtTaxPayAm.class, new QpbmtEmpRsdtTaxPayAmPk(sid, year));
    }

    @Override
    public void removeAll(List<String> sids, int year) {
        this.commandProxy().removeAll(QpbmtEmpRsdtTaxPayAm.class, QpbmtEmpRsdtTaxPayAmPk.toKeys(sids, year));
    }
}
