package nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.monthly;

import java.util.List;

public interface ExtractionMonthlyConRepository {

    List<ExtractionMonthlyCon> getBy(List<String> ids, boolean useAtr);
}
