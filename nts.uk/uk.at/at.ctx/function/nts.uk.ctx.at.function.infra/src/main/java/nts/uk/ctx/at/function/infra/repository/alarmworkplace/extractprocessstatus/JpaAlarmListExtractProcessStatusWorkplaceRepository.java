package nts.uk.ctx.at.function.infra.repository.alarmworkplace.extractprocessstatus;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.function.dom.alarmworkplace.extractprocessstatus.AlarmListExtractProcessStatusWorkplace;
import nts.uk.ctx.at.function.dom.alarmworkplace.extractprocessstatus.AlarmListExtractProcessStatusWorkplaceRepository;
import nts.uk.ctx.at.function.infra.entity.alarmworkplace.extractprocessstatus.KfnmtWkpAlexProStatus;

import javax.ejb.Stateless;
import java.util.Optional;

@Stateless
public class JpaAlarmListExtractProcessStatusWorkplaceRepository extends JpaRepository implements AlarmListExtractProcessStatusWorkplaceRepository {

    private static final String SELECT;

    private static final String SELECT_BY_ID_AND_CID;

    static {
        StringBuilder builderString = new StringBuilder();
        builderString.append("SELECT a FROM KfnmtWkpAlexProStatus a ");
        SELECT = builderString.toString();

        builderString = new StringBuilder();
        builderString.append(SELECT);
        builderString.append("WHERE a.pk.extraStatusId = :id AND a.companyID = :companyId ");
        SELECT_BY_ID_AND_CID = builderString.toString();

    }

    @Override
    public Optional<AlarmListExtractProcessStatusWorkplace> getBy(String companyId, String id) {
        return this.queryProxy().query(SELECT_BY_ID_AND_CID, KfnmtWkpAlexProStatus.class)
            .setParameter("companyId", companyId)
            .setParameter("id", id)
            .getSingle(KfnmtWkpAlexProStatus::toDomain);

    }

    @Override
    public void add(AlarmListExtractProcessStatusWorkplace processStatus) {
        this.commandProxy().insert(KfnmtWkpAlexProStatus.toEntity(processStatus));

    }

    @Override
    public void update(AlarmListExtractProcessStatusWorkplace processStatus) {
        this.commandProxy().update(KfnmtWkpAlexProStatus.toEntity(processStatus));
    }
}
