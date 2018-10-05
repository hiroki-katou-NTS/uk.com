package nts.uk.ctx.pr.core.infra.repository.wageprovision.processdatecls;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.SetDaySupport;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.SetDaySupportRepository;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.processdatecls.QpbmtSetDaySupport;

import javax.ejb.Stateless;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class JpaSetDaySupportRepository extends JpaRepository implements SetDaySupportRepository {

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QpbmtSetDaySupport f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.setDaySupportPk.cid =:cid AND  f.setDaySupportPk.processCateNo =:processCateNo ";
    private static final String SELECT_BY_KEY_AND_YEAR_STRING = SELECT_BY_KEY_STRING + " AND  f.setDaySupportPk.processDate LIKE CONCAT(:year, '%') ORDER BY f.setDaySupportPk.processDate ASC";

    @Override
    public List<SetDaySupport> getAllSetDaySupport() {
        return this.queryProxy().query(SELECT_ALL_QUERY_STRING, QpbmtSetDaySupport.class)
                .getList(item -> item.toDomain());
    }

    @Override
    public List<SetDaySupport> getSetDaySupportById(String cid, int processCateNo) {
        return this.queryProxy().query(SELECT_BY_KEY_STRING, QpbmtSetDaySupport.class)
                .setParameter("cid", cid)
                .setParameter("processCateNo", processCateNo)
                .getList(c -> c.toDomain());
    }

    @Override
    public List<SetDaySupport> getSetDaySupportByIdAndYear(String cid, int processCateNo, int year) {
        return this.queryProxy().query(SELECT_BY_KEY_AND_YEAR_STRING, QpbmtSetDaySupport.class)
                .setParameter("cid", cid)
                .setParameter("processCateNo", processCateNo)
                .setParameter("year", year + "")
                .getList(c -> c.toDomain());
    }

    @Override
    public void add(SetDaySupport domain) {
        this.commandProxy().insert(QpbmtSetDaySupport.toEntity(domain));
    }

    @Override
    public void update(SetDaySupport domain) {
        this.commandProxy().update(QpbmtSetDaySupport.toEntity(domain));
    }

    @Override
    public void addAll(List<SetDaySupport> domains) {
        List<QpbmtSetDaySupport> entities = domains.stream().map(QpbmtSetDaySupport::toEntity).collect(Collectors.toList());
        this.commandProxy().insertAll(entities);
    }
}
