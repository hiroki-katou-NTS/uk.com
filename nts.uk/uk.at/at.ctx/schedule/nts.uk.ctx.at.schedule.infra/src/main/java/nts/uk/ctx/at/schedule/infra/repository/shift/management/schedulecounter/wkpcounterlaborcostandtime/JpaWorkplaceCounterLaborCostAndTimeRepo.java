package nts.uk.ctx.at.schedule.infra.repository.shift.management.schedulecounter.wkpcounterlaborcostandtime;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter.laborcostandtime.WorkplaceCounterLaborCostAndTime;
import nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter.laborcostandtime.WorkplaceCounterLaborCostAndTimeRepo;
import nts.uk.ctx.at.schedule.infra.entity.shift.management.schedulecounter.wkpcounterlaborcostandtime.KscmtWkpLaborCostAndTime;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import java.util.List;
import java.util.Optional;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class JpaWorkplaceCounterLaborCostAndTimeRepo extends JpaRepository implements WorkplaceCounterLaborCostAndTimeRepo {

    private static final String SELECT;

    private static final String FIND_BY_CID;

    static {
        StringBuilder builderString = new StringBuilder();
        builderString.append(" SELECT a ");
        builderString.append(" FROM KscmtWkpLaborCostAndTime a ");
        SELECT = builderString.toString();

        builderString = new StringBuilder();
        builderString.append(SELECT);
        builderString.append(" WHERE a.pk.companyId = :companyId ");
        FIND_BY_CID = builderString.toString();
    }

    @Override
    public void insert(String companyId, WorkplaceCounterLaborCostAndTime domain) {
        commandProxy().insertAll(KscmtWkpLaborCostAndTime.toEntity(companyId,domain));
    }

    @Override
    public void update(String companyId, WorkplaceCounterLaborCostAndTime domain) {
        List<KscmtWkpLaborCostAndTime> result = this.queryProxy().query(FIND_BY_CID, KscmtWkpLaborCostAndTime.class)
            .setParameter("companyId", companyId)
            .getList();
        commandProxy().removeAll(result);
        commandProxy().insertAll(KscmtWkpLaborCostAndTime.toEntity(companyId,domain));
    }

    @Override
    public Optional<WorkplaceCounterLaborCostAndTime> get(String companyId) {
        List<KscmtWkpLaborCostAndTime> result = this.queryProxy().query(FIND_BY_CID, KscmtWkpLaborCostAndTime.class)
            .setParameter("companyId", companyId)
            .getList();
        return result.size() > 0 ? Optional.of(KscmtWkpLaborCostAndTime.toDomain(result)) : Optional.empty();
    }

    @Override
    public boolean exists(String companyId) {
        List<KscmtWkpLaborCostAndTime> result = this.queryProxy().query(FIND_BY_CID, KscmtWkpLaborCostAndTime.class)
            .setParameter("companyId", companyId)
            .getList();
        return result.size() > 0;
    }
}
