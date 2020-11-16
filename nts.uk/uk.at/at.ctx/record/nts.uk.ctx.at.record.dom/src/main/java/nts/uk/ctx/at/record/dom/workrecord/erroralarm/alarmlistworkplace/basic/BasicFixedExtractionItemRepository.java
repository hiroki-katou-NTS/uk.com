package nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.basic;

import java.util.List;
import java.util.Optional;

public interface BasicFixedExtractionItemRepository {

    Optional<BasicFixedExtractionItem> getByID(String id);

    List<BasicFixedExtractionItem> getAll();

    void register(BasicFixedExtractionCondition domain);

    void update(BasicFixedExtractionCondition domain);

    void delete(String id);

}
