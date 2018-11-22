package nts.uk.ctx.pr.core.infra.repository.wageprovision.wagetable;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.WageTable;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.WageTableRepository;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.wagetable.QpbmtWageTable;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.wagetable.QpbmtWageTablePk;

import javax.ejb.Stateless;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Stateless
public class JpaWageTableRepository extends JpaRepository implements WageTableRepository {

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QpbmtWageTable f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.wageTablePk.cid =:cid AND" +
            " f.wageTablePk.wageTableCode =:wageTableCode ";
    private static final String SELECT_BY_WAGE_TABLE_CDS = SELECT_ALL_QUERY_STRING + " WHERE  f.wageTablePk.cid =:cid AND" +
            " f.wageTablePk.wageTableCode IN :wageTableCodes" +
            " ORDER BY f.wageTablePk.wageTableCode ASC";

    @Override
    public List<WageTable> getAllWageTable() {
        return this.queryProxy().query(SELECT_ALL_QUERY_STRING, QpbmtWageTable.class)
                .getList(QpbmtWageTable::toDomain);
    }

    @Override
    public Optional<WageTable> getWageTableById(String cid, String wageTableCode) {
        return this.queryProxy().query(SELECT_BY_KEY_STRING, QpbmtWageTable.class)
                .setParameter("cid", cid)
                .setParameter("wageTableCode", wageTableCode)
                .getSingle(QpbmtWageTable::toDomain);
    }

    @Override
    public List<WageTable> getWageTableByCodes(String cid, List<String> wageTableCodes) {
        if (wageTableCodes == null || wageTableCodes.isEmpty()) return Collections.emptyList();
        return this.queryProxy().query(SELECT_BY_WAGE_TABLE_CDS, QpbmtWageTable.class)
                .setParameter("cid", cid)
                .setParameter("wageTableCodes", wageTableCodes)
                .getList(QpbmtWageTable::toDomain);
    }

    @Override
    public void add(WageTable domain) {
        this.commandProxy().insert(QpbmtWageTable.toEntity(domain));
    }

    @Override
    public void update(WageTable domain) {
        this.commandProxy().update(QpbmtWageTable.toEntity(domain));
    }

    @Override
    public void remove(String cid, String wageTableCode) {
        this.commandProxy().remove(QpbmtWageTable.class, new QpbmtWageTablePk(cid, wageTableCode));
    }
}
