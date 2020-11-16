package nts.uk.ctx.at.record.infra.repository.workrecord.erroralarm.alarmlist.workplace;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlist.workplace.AlarmFixedExtractionCondition;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlist.workplace.AlarmFixedExtractionConditionRepository;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import java.util.List;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class JpaAlarmFixedExtractionConditionRepository extends JpaRepository implements AlarmFixedExtractionConditionRepository {

    @Override
    public List<AlarmFixedExtractionCondition> getByID(String id) {
        return null;
    }

    @Override
    public List<AlarmFixedExtractionCondition> getByIDs(List<String> ids) {
        return null;
    }

}
