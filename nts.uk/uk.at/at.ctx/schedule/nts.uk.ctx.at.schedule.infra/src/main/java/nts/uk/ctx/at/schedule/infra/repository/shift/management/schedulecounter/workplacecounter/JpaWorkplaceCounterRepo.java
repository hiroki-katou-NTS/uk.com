package nts.uk.ctx.at.schedule.infra.repository.shift.management.schedulecounter.workplacecounter;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter.WorkplaceCounter;
import nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter.WorkplaceCounterRepo;
import nts.uk.ctx.at.schedule.infra.entity.shift.management.schedulecounter.workplacecounter.KscmtWkpCounter;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import java.util.List;
import java.util.Optional;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class JpaWorkplaceCounterRepo extends JpaRepository implements WorkplaceCounterRepo {

    private static final String SELECT;

    private static final String FIND_BY_CID;

    static {
        StringBuilder builderString = new StringBuilder();
        builderString.append(" SELECT a.CID, a.WKP_CATEGORY ");
        builderString.append(" FROM KSCMT_WKP_COUNTER ");
        SELECT = builderString.toString();

        builderString = new StringBuilder();
        builderString.append(SELECT);
        builderString.append(" WHERE a.CID = 'companyId' ");
        FIND_BY_CID = builderString.toString();
    }

    @Override
    public void insert(String companyId, WorkplaceCounter domain) {
        commandProxy().insertAll(KscmtWkpCounter.toEntity(companyId,domain));
    }

    @Override
    public void update(String companyId, WorkplaceCounter domain) {
        List<KscmtWkpCounter> result = this.queryProxy().query(FIND_BY_CID, KscmtWkpCounter.class)
            .setParameter("companyId", companyId)
            .getList();
        commandProxy().removeAll(result);
        commandProxy().insertAll(KscmtWkpCounter.toEntity(companyId,domain));
    }

    @Override
    public Optional<WorkplaceCounter> get(String companyId) {
        List<KscmtWkpCounter> result = this.queryProxy().query(FIND_BY_CID, KscmtWkpCounter.class)
            .setParameter("companyId", companyId)
            .getList();
        return result.size() > 0 ? Optional.of(KscmtWkpCounter.toDomain(result)) : Optional.empty();
    }

    @Override
    public boolean exists(String companyId) {
        List<KscmtWkpCounter> result = this.queryProxy().query(FIND_BY_CID, KscmtWkpCounter.class)
            .setParameter("companyId", companyId)
            .getList();

        return result.size() > 0;
    }
}
