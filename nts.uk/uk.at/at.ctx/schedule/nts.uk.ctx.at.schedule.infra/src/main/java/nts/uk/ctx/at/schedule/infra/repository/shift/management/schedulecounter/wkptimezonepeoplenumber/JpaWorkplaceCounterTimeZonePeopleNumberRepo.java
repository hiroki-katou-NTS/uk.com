package nts.uk.ctx.at.schedule.infra.repository.shift.management.schedulecounter.wkptimezonepeoplenumber;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter.timezonepeople.WorkplaceCounterTimeZonePeopleNumber;
import nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter.timezonepeople.WorkplaceCounterTimeZonePeopleNumberRepo;
import nts.uk.ctx.at.schedule.infra.entity.shift.management.schedulecounter.wkptimezonepeoplenumber.KscmtTallyByWkpEveryTimeZone;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import java.util.List;
import java.util.Optional;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class JpaWorkplaceCounterTimeZonePeopleNumberRepo extends JpaRepository implements WorkplaceCounterTimeZonePeopleNumberRepo {

    private static final String SELECT;

    private static final String FIND_BY_CID;

    static {
        StringBuilder builderString = new StringBuilder();
        builderString.append(" SELECT a ");
        builderString.append(" FROM KscmtTallyByWkpEveryTimeZone a ");
        SELECT = builderString.toString();

        builderString = new StringBuilder();
        builderString.append(SELECT);
        builderString.append(" WHERE a.pk.companyId = :companyId ");
        FIND_BY_CID = builderString.toString();
    }

    @Override
    public void insert(String companyId, WorkplaceCounterTimeZonePeopleNumber domain) {
        commandProxy().insertAll(KscmtTallyByWkpEveryTimeZone.toEntity(companyId,domain));
    }

    @Override
    public void update(String companyId, WorkplaceCounterTimeZonePeopleNumber domain) {
        List<KscmtTallyByWkpEveryTimeZone> result = this.queryProxy().query(FIND_BY_CID, KscmtTallyByWkpEveryTimeZone.class)
            .setParameter("companyId", companyId)
            .getList();
        commandProxy().removeAll(result);
        this.getEntityManager().flush();
        commandProxy().insertAll(KscmtTallyByWkpEveryTimeZone.toEntity(companyId,domain));
    }

    @Override
    public Optional<WorkplaceCounterTimeZonePeopleNumber> get(String companyId) {
        List<KscmtTallyByWkpEveryTimeZone> result = this.queryProxy().query(FIND_BY_CID, KscmtTallyByWkpEveryTimeZone.class)
            .setParameter("companyId", companyId)
            .getList();
        return result.size() > 0 ? Optional.of(KscmtTallyByWkpEveryTimeZone.toDomain(result)) : Optional.empty();
    }

    @Override
    public boolean exists(String companyId) {
        List<KscmtTallyByWkpEveryTimeZone> result = this.queryProxy().query(FIND_BY_CID, KscmtTallyByWkpEveryTimeZone.class)
            .setParameter("companyId", companyId)
            .getList();
        return result.size() > 0;
    }
}
