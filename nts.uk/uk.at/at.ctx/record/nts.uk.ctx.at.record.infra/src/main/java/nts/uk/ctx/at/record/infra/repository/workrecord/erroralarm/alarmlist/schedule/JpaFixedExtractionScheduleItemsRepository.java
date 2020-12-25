package nts.uk.ctx.at.record.infra.repository.workrecord.erroralarm.alarmlist.schedule;

import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.schedule.FixedCheckDayItemName;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.schedule.FixedExtractionScheduleItems;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.schedule.FixedExtractionScheduleItemsRepository;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.alarmlistworkplace.schedule.KrcmtWkpSchedaiFxexItm;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class JpaFixedExtractionScheduleItemsRepository extends JpaRepository implements FixedExtractionScheduleItemsRepository {

    private static final String SELECT;

    private static final String FIND_BY_IDS_AND_USEATR;

    static {
        StringBuilder builderString = new StringBuilder();
        builderString.append(" SELECT a FROM KrcmtWkpSchedaiFxexItm a ");
        SELECT = builderString.toString();

        builderString = new StringBuilder();
        builderString.append(SELECT);
        builderString.append(" WHERE a.fixedCheckDayItemName in :nos ");
        FIND_BY_IDS_AND_USEATR = builderString.toString();

    }

    @Override
    public List<FixedExtractionScheduleItems> getBy(List<FixedCheckDayItemName> nos) {
        if (CollectionUtil.isEmpty(nos)) return new ArrayList<>();
        List<Integer> noList = nos.stream().map(x -> x.value).collect(Collectors.toList());
        return this.queryProxy().query(FIND_BY_IDS_AND_USEATR, KrcmtWkpSchedaiFxexItm.class)
            .setParameter("nos", noList)
            .getList(KrcmtWkpSchedaiFxexItm::toDomain);
    }

    @Override
    public List<FixedExtractionScheduleItems> getAll() {
        return this.queryProxy().query(SELECT, KrcmtWkpSchedaiFxexItm.class).getList(KrcmtWkpSchedaiFxexItm::toDomain);
    }
}
