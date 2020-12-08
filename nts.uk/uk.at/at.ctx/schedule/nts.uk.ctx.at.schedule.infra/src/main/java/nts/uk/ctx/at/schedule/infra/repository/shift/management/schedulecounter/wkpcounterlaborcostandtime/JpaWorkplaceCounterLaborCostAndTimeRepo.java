package nts.uk.ctx.at.schedule.infra.repository.shift.management.schedulecounter.wkpcounterlaborcostandtime;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter.laborcostandtime.WorkplaceCounterLaborCostAndTime;
import nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter.laborcostandtime.WorkplaceCounterLaborCostAndTimeRepo;
import nts.uk.ctx.at.schedule.infra.entity.shift.management.schedulecounter.wkpcounterlaborcostandtime.KscmtTallyByWkpLaborCost;

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
        builderString.append(" FROM KscmtTallyByWkpLaborCost a ");
        SELECT = builderString.toString();

        builderString = new StringBuilder();
        builderString.append(SELECT);
        builderString.append(" WHERE a.pk.companyId = :companyId ");
        FIND_BY_CID = builderString.toString();
    }

    @Override
    public void insert(String companyId, WorkplaceCounterLaborCostAndTime domain) {
        commandProxy().insertAll(KscmtTallyByWkpLaborCost.toEntity(companyId,domain));
    }

    @Override
    public void update(String companyId, WorkplaceCounterLaborCostAndTime domain) {
        commandProxy().updateAll(KscmtTallyByWkpLaborCost.toEntity(companyId,domain));
    }

    @Override
    public Optional<WorkplaceCounterLaborCostAndTime> get(String companyId) {
        List<KscmtTallyByWkpLaborCost> result = this.queryProxy().query(FIND_BY_CID, KscmtTallyByWkpLaborCost.class)
            .setParameter("companyId", companyId)
            .getList();
        return result.size() > 0 ? Optional.of(KscmtTallyByWkpLaborCost.toDomain(result)) : Optional.empty();
    }

    @Override
    public boolean exists(String companyId) {
        List<KscmtTallyByWkpLaborCost> result = this.queryProxy().query(FIND_BY_CID, KscmtTallyByWkpLaborCost.class)
            .setParameter("companyId", companyId)
            .getList();
        return result.size() > 0;
    }
}
