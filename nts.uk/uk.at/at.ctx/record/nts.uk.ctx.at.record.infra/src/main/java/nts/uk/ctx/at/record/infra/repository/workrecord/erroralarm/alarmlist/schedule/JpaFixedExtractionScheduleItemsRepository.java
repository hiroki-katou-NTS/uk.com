package nts.uk.ctx.at.record.infra.repository.workrecord.erroralarm.alarmlist.schedule;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.schedule.FixedCheckDayItemName;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.schedule.FixedExtractionScheduleItems;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.schedule.FixedExtractionScheduleItemsRepository;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.alarmlistworkplace.schedule.KrcmtWkpSchedaiFxexItm;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import java.util.ArrayList;
import java.util.List;

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
        return this.queryProxy().query(FIND_BY_IDS_AND_USEATR, KrcmtWkpSchedaiFxexItm.class)
            .setParameter("nos", nos)
            .getList(KrcmtWkpSchedaiFxexItm::toDomain);
    }
}
