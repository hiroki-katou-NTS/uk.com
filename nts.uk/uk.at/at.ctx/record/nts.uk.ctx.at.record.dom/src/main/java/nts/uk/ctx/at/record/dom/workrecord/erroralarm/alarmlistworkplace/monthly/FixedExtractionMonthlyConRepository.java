package nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.monthly;

import java.util.List;

public interface FixedExtractionMonthlyConRepository {

    List<FixedExtractionMonthlyCon> getBy(List<String> ids, boolean useAtr);

    List<FixedExtractionMonthlyCon> getByIds(List<String> ids);

    void register(List<FixedExtractionMonthlyCon> domain);

    void delete(List<String> ids);
}
