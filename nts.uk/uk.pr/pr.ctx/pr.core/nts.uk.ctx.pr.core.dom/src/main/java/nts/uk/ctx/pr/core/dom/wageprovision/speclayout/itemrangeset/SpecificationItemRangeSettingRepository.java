package nts.uk.ctx.pr.core.dom.wageprovision.speclayout.itemrangeset;

import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;

import java.util.Optional;
import java.util.List;

/**
* itemrangeset
*/
public interface SpecificationItemRangeSettingRepository
{

    List<SpecificationItemRangeSetting> getAllSpecificationItemRangeSetting();

    Optional<SpecificationItemRangeSetting> getSpecificationItemRangeSettingById(String histId);

    void add(SpecificationItemRangeSetting domain);

    void update(SpecificationItemRangeSetting domain);

    void remove(String histId);

}
