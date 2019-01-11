package nts.uk.ctx.pr.core.dom.wageprovision.statebindingset;

import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.shr.com.history.YearMonthHistoryItem;

import java.util.List;
import java.util.Optional;

/**
* 明細書紐付け履歴（給与分類）
*/
public interface StateCorreHisSalaRepository {

    Optional<StateCorreHisSala> getStateCorrelationHisSalaryByCid(String cid);

    List<StateLinkSetMaster> getStateLinkSettingMasterByHisId(String cId, String hisId);

    List<StateLinkSetMaster> getStateLinkSetMaster(String cid, YearMonth yearMonth);

    void update (String cid, YearMonthHistoryItem history);

    void updateAll(String cid, List<StateLinkSetMaster> stateLinkSetMasters, int startYearMonth, int endYearMonth);

    void addAll(String cid, List<StateLinkSetMaster> stateLinkSetMasters, int startYearMonth, int endYearMonth );

    void removeAll(String cid, String hisId);

}
