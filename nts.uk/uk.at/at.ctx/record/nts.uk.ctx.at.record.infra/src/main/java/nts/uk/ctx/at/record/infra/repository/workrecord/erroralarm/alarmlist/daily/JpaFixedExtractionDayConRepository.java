package nts.uk.ctx.at.record.infra.repository.workrecord.erroralarm.alarmlist.daily;

import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.daily.FixedExtractionDayCon;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.daily.FixedExtractionDayConRepository;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.alarmlistworkplace.daily.KrcmtWkpFxexDayCon;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class JpaFixedExtractionDayConRepository extends JpaRepository implements FixedExtractionDayConRepository {

    private static final String GET_ALL = "SELECT a FROM KrcmtWkpFxexDayCon a ";

    private static final String SELECT_RANGE_FXEX_DAY_CON_BY_ID = GET_ALL
            + " WHERE a.errorAlarmWorkplaceId IN :ids ";

    private static final String FIND_BY_IDS_AND_USEATR = GET_ALL
            + " WHERE a.errorAlarmWorkplaceId IN :ids AND a.useAtr = :useAtr";

    @Override
    public List<FixedExtractionDayCon> getRange(List<String> ids) {
        if (ids.isEmpty()) {
            return new ArrayList<>();
        }

        return this.queryProxy().query(SELECT_RANGE_FXEX_DAY_CON_BY_ID, KrcmtWkpFxexDayCon.class)
                .setParameter("ids", ids)
                .getList(KrcmtWkpFxexDayCon::toDomain);
    }

    @Override
    public List<FixedExtractionDayCon> getBy(List<String> ids, boolean useAtr) {
        if (CollectionUtil.isEmpty(ids)) return new ArrayList<>();
        return this.queryProxy()
                .query(FIND_BY_IDS_AND_USEATR, KrcmtWkpFxexDayCon.class)
                .setParameter("ids", ids)
                .setParameter("useAtr", useAtr)
                .getList(KrcmtWkpFxexDayCon::toDomain);
    }

    @Override
    public void register(List<FixedExtractionDayCon> domain) {
        this.commandProxy().insertAll(domain.stream().map(KrcmtWkpFxexDayCon::fromDomain).collect(Collectors.toList()));
    }

    @Override
    public void delete(List<String> ids) {
        this.commandProxy().removeAll(KrcmtWkpFxexDayCon.class, ids);
    }

}
