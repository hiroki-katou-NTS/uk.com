package nts.uk.ctx.at.record.infra.repository.workrecord.erroralarm.alarmlist.workplace;

import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.workplace.AlarmFixedExtractionCondition;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.workplace.AlarmFixedExtractionConditionRepository;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.alarmlistworkplace.workplace.KrcmtWkpFxexCon;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import java.util.ArrayList;
import java.util.List;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class JpaAlarmFixedExtractionConditionRepository extends JpaRepository implements AlarmFixedExtractionConditionRepository {

    private static final String GET_ALL = "select f from KrcmtWkpFxexCon f";
    private static final String GET_BY_IDS = GET_ALL + " where f.pk.id in :ids";
    private static final String GET_BY_IDS_USE_ATR = GET_ALL + " where f.pk.id in :ids AND f.useAtr = :useAtr ";

    @Override
    public List<AlarmFixedExtractionCondition> getByID(String id) {
        return null;
    }

    @Override
    public List<AlarmFixedExtractionCondition> getByIDs(List<String> ids) {
        if (CollectionUtil.isEmpty(ids)) return new ArrayList<>();
        return this.queryProxy()
                .query(GET_BY_IDS, KrcmtWkpFxexCon.class)
                .setParameter("ids", ids)
                .getList(KrcmtWkpFxexCon::toDomain);
    }

    @Override
    public List<AlarmFixedExtractionCondition> getBy(List<String> ids, boolean useAtr) {
        if (CollectionUtil.isEmpty(ids)) return new ArrayList<>();
        return this.queryProxy()
                .query(GET_BY_IDS_USE_ATR, KrcmtWkpFxexCon.class)
                .setParameter("ids", ids)
                .setParameter("useAtr", useAtr)
                .getList(KrcmtWkpFxexCon::toDomain);
    }

}
