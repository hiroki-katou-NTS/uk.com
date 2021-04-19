package nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.workplace;

import java.util.List;
import java.util.Optional;

public interface AlarmFixedExtractionItemRepository {

    List<AlarmFixedExtractionItem> getAll();

    Optional<AlarmFixedExtractionItem> getBy(FixedCheckItem no);

}
