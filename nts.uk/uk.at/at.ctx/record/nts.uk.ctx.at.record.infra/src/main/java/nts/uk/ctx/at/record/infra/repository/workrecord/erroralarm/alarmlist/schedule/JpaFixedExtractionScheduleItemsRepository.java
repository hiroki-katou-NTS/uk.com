package nts.uk.ctx.at.record.infra.repository.workrecord.erroralarm.alarmlist.schedule;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.schedule.FixedCheckDayItemName;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.schedule.FixedExtractionScheduleItems;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.schedule.FixedExtractionScheduleItemsRepository;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import java.util.ArrayList;
import java.util.List;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class JpaFixedExtractionScheduleItemsRepository extends JpaRepository implements FixedExtractionScheduleItemsRepository {

    @Override
    public List<FixedExtractionScheduleItems> getBy(List<FixedCheckDayItemName> nos) {
        return new ArrayList<>();
    }
}
