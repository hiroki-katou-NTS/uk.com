package nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlist.basic;

import java.util.List;
import java.util.Optional;

public interface BasicFixedExtractionConditionRepository {

    List<BasicFixedExtractionCondition> getByID(String id);

    void register(BasicFixedExtractionCondition domain);

    void update(BasicFixedExtractionCondition domain);

    void delete(String id);

}
