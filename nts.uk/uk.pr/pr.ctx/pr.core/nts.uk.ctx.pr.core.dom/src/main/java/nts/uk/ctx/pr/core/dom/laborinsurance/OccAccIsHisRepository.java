package nts.uk.ctx.pr.core.dom.laborinsurance;


import nts.uk.shr.com.history.YearMonthHistoryItem;

import java.util.Optional;
import java.util.List;

/**
* 労災保険履歴
*/
public interface OccAccIsHisRepository
{

    Optional<OccAccIsHis> getAllOccAccIsHisByCid(String cid);


    void add(YearMonthHistoryItem domain, String cId);

    void update(YearMonthHistoryItem domain,String cId);

    void remove(String cid, String hisId);

}
