package nts.uk.ctx.at.record.infra.repository.workrecord.erroralarm.alarmlist.schedule;

import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.schedule.FixedExtractionScheduleCon;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.schedule.FixedExtractionScheduleConRepository;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.alarmlistworkplace.schedule.KrcmtWkpSchedaiFxexCon;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class JpaFixedExtractionScheduleConRepository extends JpaRepository implements FixedExtractionScheduleConRepository {

    private static final String SELECT;

    private static final String FIND_BY_IDS_AND_USEATR;

    private static final String SELECT_BY_IDS;

    static {
        StringBuilder builderString = new StringBuilder();
        builderString.append(" SELECT a FROM KrcmtWkpSchedaiFxexCon a ");
        SELECT = builderString.toString();

        builderString = new StringBuilder();
        builderString.append(SELECT);
        builderString.append(" WHERE a.errorAlarmWorkplaceId in :ids AND a.useAtr = :useAtr ");
        FIND_BY_IDS_AND_USEATR = builderString.toString();

        builderString = new StringBuilder();
        builderString.append(SELECT);
        builderString.append(" WHERE a.errorAlarmWorkplaceId in :ids ");
        SELECT_BY_IDS = builderString.toString();

    }

    @Override
    public List<FixedExtractionScheduleCon> getBy(List<String> ids, boolean useAtr) {
        if (CollectionUtil.isEmpty(ids)) return new ArrayList<>();
        return this.queryProxy().query(FIND_BY_IDS_AND_USEATR, KrcmtWkpSchedaiFxexCon.class)
            .setParameter("ids", ids)
            .setParameter("useAtr", useAtr)
            .getList(KrcmtWkpSchedaiFxexCon::toDomain);
    }

    @Override
    public List<FixedExtractionScheduleCon> getByIds(List<String> ids) {
       if (ids.isEmpty()) {
           return Collections.emptyList();
       }
       return this.queryProxy().query(SELECT_BY_IDS, KrcmtWkpSchedaiFxexCon.class)
               .setParameter("ids", ids)
               .getList(KrcmtWkpSchedaiFxexCon::toDomain);
    }

    @Override
    public void register(List<FixedExtractionScheduleCon> domain) {
        this.commandProxy().insertAll(domain.stream().map(KrcmtWkpSchedaiFxexCon::fromDomain).collect(Collectors.toList()));
    }

    @Override
    public void delete(List<String> ids) {
        this.commandProxy().removeAll(KrcmtWkpSchedaiFxexCon.class, ids);
    }
}
