package nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.monthly;

import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.monthly.enums.FixedCheckMonthlyItemName;

import java.util.List;
import java.util.Optional;

public interface FixedExtractionMonthlyItemsRepository {

    List<FixedExtractionMonthlyItems> getBy(List<FixedCheckMonthlyItemName> nos);
}
