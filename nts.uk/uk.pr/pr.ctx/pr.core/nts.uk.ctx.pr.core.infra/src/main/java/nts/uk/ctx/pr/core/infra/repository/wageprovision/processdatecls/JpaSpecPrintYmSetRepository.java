package nts.uk.ctx.pr.core.infra.repository.wageprovision.processdatecls;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.SpecPrintYmSet;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.SpecPrintYmSetRepository;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.processdatecls.QpbmtSpecPrintYmSet;

import javax.ejb.Stateless;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class JpaSpecPrintYmSetRepository extends JpaRepository implements SpecPrintYmSetRepository {

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QpbmtSpecPrintYmSet f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.specPrintYmSetPk.cid =:cid AND  f.specPrintYmSetPk.processCateNo =:processCateNo ";
    private static final String SELECT_BY_KEY_AND_YEAR_STRING = SELECT_BY_KEY_STRING + " AND  f.specPrintYmSetPk.processDate LIKE CONCAT(:year, '%') ORDER BY f.specPrintYmSetPk.processDate ASC";

    @Override
    public List<SpecPrintYmSet> getAllSpecPrintYmSet() {
        return this.queryProxy().query(SELECT_ALL_QUERY_STRING, QpbmtSpecPrintYmSet.class)
                .getList(QpbmtSpecPrintYmSet::toDomain);
    }

    @Override
    public List<SpecPrintYmSet> getSpecPrintYmSetById(String cid, int processCateNo) {
        return this.queryProxy().query(SELECT_BY_KEY_STRING, QpbmtSpecPrintYmSet.class)
                .setParameter("cid", cid)
                .setParameter("processCateNo", processCateNo)
                .getList(QpbmtSpecPrintYmSet::toDomain);
    }

    @Override
    public List<SpecPrintYmSet> getSpecPrintYmSetByIdAndYear(String cid, int processCateNo, int year) {
        return this.queryProxy().query(SELECT_BY_KEY_AND_YEAR_STRING, QpbmtSpecPrintYmSet.class)
                .setParameter("cid", cid)
                .setParameter("processCateNo", processCateNo)
                .setParameter("year", year + "")
                .getList(QpbmtSpecPrintYmSet::toDomain);
    }

    @Override
    public void add(SpecPrintYmSet domain) {
        this.commandProxy().insert(QpbmtSpecPrintYmSet.toEntity(domain));
    }

    @Override
    public void addAll(List<SpecPrintYmSet> domains) {
        List<QpbmtSpecPrintYmSet> entities=domains.stream().map(item->QpbmtSpecPrintYmSet.toEntity(item)).collect(Collectors.toList());
        this.commandProxy().insertAll(entities);
    }

    @Override
    public void update(SpecPrintYmSet domain) {
        this.commandProxy().update(QpbmtSpecPrintYmSet.toEntity(domain));
    }
}
