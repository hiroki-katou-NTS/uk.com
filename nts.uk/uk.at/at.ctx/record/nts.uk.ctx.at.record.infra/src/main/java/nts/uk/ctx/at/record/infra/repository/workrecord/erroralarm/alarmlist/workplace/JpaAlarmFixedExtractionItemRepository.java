package nts.uk.ctx.at.record.infra.repository.workrecord.erroralarm.alarmlist.workplace;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlist.workplace.AlarmFixedExtractionItem;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlist.workplace.AlarmFixedExtractionItemRepository;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import java.util.List;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class JpaAlarmFixedExtractionItemRepository extends JpaRepository implements AlarmFixedExtractionItemRepository {
    @Override
    public List<AlarmFixedExtractionItem> getAll() {
        return null;
    }
}
