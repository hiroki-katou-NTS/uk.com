package nts.uk.ctx.at.record.infra.repository.workrecord.erroralarm.alarmlist.monthly;

import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.monthly.FixedExtractionMonthlyCon;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.monthly.FixedExtractionMonthlyConRepository;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.alarmlistworkplace.monthly.KrcmtWkpMonFxexCon;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class JpaFixedExtractionMonthlyConRepository extends JpaRepository implements FixedExtractionMonthlyConRepository {

    private static final String SELECT;

    private static final String FIND_BY_IDS_AND_USEATR;

    private static final String GET_BY_IDS;

    static {
        StringBuilder builderString = new StringBuilder();
        builderString.append(" SELECT a FROM KrcmtWkpMonFxexCon a ");
        SELECT = builderString.toString();

        builderString = new StringBuilder();
        builderString.append(SELECT);
        builderString.append(" WHERE a.errorAlarmWorkplaceId in :ids AND a.useAtr = :useAtr ");
        FIND_BY_IDS_AND_USEATR = builderString.toString();

        builderString = new StringBuilder();
        builderString.append(SELECT);
        builderString.append(" WHERE a.errorAlarmWorkplaceId in :ids ");
        GET_BY_IDS = builderString.toString();

    }

    @Override
    public List<FixedExtractionMonthlyCon> getBy(List<String> ids, boolean useAtr) {
        if (CollectionUtil.isEmpty(ids)) return new ArrayList<>();
        return this.queryProxy().query(FIND_BY_IDS_AND_USEATR, KrcmtWkpMonFxexCon.class)
            .setParameter("ids", ids)
            .setParameter("useAtr", useAtr)
            .getList(KrcmtWkpMonFxexCon::toDomain);
    }

    @Override
    public List<FixedExtractionMonthlyCon> getByIds(List<String> ids) {
        if (ids.isEmpty()) {
            return Collections.emptyList();
        }
        return this.queryProxy().query(GET_BY_IDS, KrcmtWkpMonFxexCon.class)
                .setParameter("ids", ids)
                .getList(KrcmtWkpMonFxexCon::toDomain);
    }

    @Override
    public void register(List<FixedExtractionMonthlyCon> domain) {
        this.commandProxy().insertAll(domain.stream().map(KrcmtWkpMonFxexCon::fromDomain).collect(Collectors.toList()));
    }

    @Override
    public void delete(List<String> ids) {
        this.commandProxy().removeAll(KrcmtWkpMonFxexCon.class, ids);
    }
}
