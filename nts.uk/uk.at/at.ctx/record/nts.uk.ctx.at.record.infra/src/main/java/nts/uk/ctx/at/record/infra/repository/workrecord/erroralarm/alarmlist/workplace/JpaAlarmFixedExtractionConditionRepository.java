package nts.uk.ctx.at.record.infra.repository.workrecord.erroralarm.alarmlist.workplace;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.workplace.AlarmFixedExtractionCondition;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.workplace.AlarmFixedExtractionConditionRepository;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.alarmlistworkplace.workplace.KrcmtWkpFxexCon;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import java.util.List;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class JpaAlarmFixedExtractionConditionRepository extends JpaRepository implements AlarmFixedExtractionConditionRepository {

    private static final String GET_ALL = "select f from KrcmtWkpFxexCon f";
    private static final String GET_BY_IDS = GET_ALL + " where f.pk.id in :ids";

    @Override
    public List<AlarmFixedExtractionCondition> getByID(String id) {
        return null;
    }

    @Override
    public List<AlarmFixedExtractionCondition> getByIDs(List<String> ids) {
        return this.queryProxy()
                .query(GET_BY_IDS, KrcmtWkpFxexCon.class)
                .setParameter("ids", ids)
                .getList(KrcmtWkpFxexCon::toDomain);
    }

}
