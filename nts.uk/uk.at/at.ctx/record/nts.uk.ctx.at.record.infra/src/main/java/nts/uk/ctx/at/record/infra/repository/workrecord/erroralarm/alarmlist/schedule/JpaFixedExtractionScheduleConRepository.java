package nts.uk.ctx.at.record.infra.repository.workrecord.erroralarm.alarmlist.schedule;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.schedule.FixedExtractionScheduleConRepository;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class JpaFixedExtractionScheduleConRepository extends JpaRepository implements FixedExtractionScheduleConRepository {
}
