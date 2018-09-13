package nts.uk.ctx.pr.core.dom.laborinsurance;


import java.util.Optional;

import nts.uk.shr.com.history.YearMonthHistoryItem;


/**
* 労災保険履歴
*/
public interface OccAccIsHisRepository
{

    Optional<OccAccIsHis> getAllOccAccIsHisByCid(String cid);

    void update(YearMonthHistoryItem domain, String cId);

    void remove(String cid, String hisId);

}
