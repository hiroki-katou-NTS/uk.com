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

    Optional<SpecificationItemRangeSetting> getSpecificationItemRangeSettingById(String cid, String specCd, String histId);

    void add(SpecificationItemRangeSetting domain, YearMonthPeriod yearMonthPeriod, String cid, String specCd);

    void update(SpecificationItemRangeSetting domain, YearMonthPeriod yearMonthPeriod, String cid, String specCd);

    void remove(String cid, String specCd, String histId);

}
