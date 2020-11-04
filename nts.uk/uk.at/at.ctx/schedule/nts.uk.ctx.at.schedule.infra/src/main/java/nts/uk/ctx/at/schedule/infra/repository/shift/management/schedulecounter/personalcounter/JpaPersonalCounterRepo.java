package nts.uk.ctx.at.schedule.infra.repository.shift.management.schedulecounter.personalcounter;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter.PersonalCounter;
import nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter.PersonalCounterRepo;
import nts.uk.ctx.at.schedule.infra.entity.shift.management.schedulecounter.personalcounter.KscmtPersonalCounter;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import java.util.List;
import java.util.Optional;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class JpaPersonalCounterRepo extends JpaRepository implements PersonalCounterRepo {

    private static final String SELECT;

    private static final String FIND_BY_CID;

    static {
        StringBuilder builderString = new StringBuilder();
        builderString.append(" SELECT a ");
        builderString.append(" FROM KscmtPersonalCounter a ");
        SELECT = builderString.toString();

        builderString = new StringBuilder();
        builderString.append(SELECT);
        builderString.append(" WHERE a.pk.companyId = :companyId ");
        FIND_BY_CID = builderString.toString();
    }

    @Override
    public void insert(String companyId, PersonalCounter domain) {
        commandProxy().insertAll(KscmtPersonalCounter.toEntity(companyId,domain));
    }

    @Override
    public void update(String companyId, PersonalCounter domain) {
        List<KscmtPersonalCounter> result = this.queryProxy().query(FIND_BY_CID, KscmtPersonalCounter.class)
            .setParameter("companyId", companyId)
            .getList();
        commandProxy().removeAll(result);
        commandProxy().insertAll(KscmtPersonalCounter.toEntity(companyId,domain));
    }

    @Override
    public Optional<PersonalCounter> get(String companyId) {
        List<KscmtPersonalCounter> result = this.queryProxy().query(FIND_BY_CID, KscmtPersonalCounter.class)
            .setParameter("companyId", companyId)
            .getList();
        return result.size() > 0 ? Optional.of(KscmtPersonalCounter.toDomain(result)) : Optional.empty();
    }

    @Override
    public boolean exists(String companyId) {
        List<KscmtPersonalCounter> result = this.queryProxy().query(FIND_BY_CID, KscmtPersonalCounter.class)
            .setParameter("companyId", companyId)
            .getList();
        return result.size() > 0;
    }
}
