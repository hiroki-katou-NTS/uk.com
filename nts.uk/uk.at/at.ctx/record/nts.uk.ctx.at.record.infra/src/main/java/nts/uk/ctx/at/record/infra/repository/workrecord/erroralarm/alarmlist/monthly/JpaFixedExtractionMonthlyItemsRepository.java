package nts.uk.ctx.at.record.infra.repository.workrecord.erroralarm.alarmlist.monthly;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.monthly.FixedExtractionMonthlyItems;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.monthly.FixedExtractionMonthlyItemsRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.monthly.enums.FixedCheckMonthlyItemName;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import java.util.List;
import java.util.Optional;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class JpaFixedExtractionMonthlyItemsRepository extends JpaRepository implements FixedExtractionMonthlyItemsRepository {

    @Override
    public Optional<FixedExtractionMonthlyItems> getBy(List<FixedCheckMonthlyItemName> nos) {
        return Optional.empty();
    }
}
