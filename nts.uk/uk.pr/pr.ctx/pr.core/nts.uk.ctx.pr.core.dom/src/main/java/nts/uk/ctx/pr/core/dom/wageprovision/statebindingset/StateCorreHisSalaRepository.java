package nts.uk.ctx.pr.core.dom.wageprovision.statebindingset;

import nts.uk.shr.com.history.YearMonthHistoryItem;

import java.util.List;
import java.util.Optional;

/**
* 明細書紐付け履歴（給与分類）
*/
public interface StateCorreHisSalaRepository {

    Optional<StateCorreHisSala> getStateCorrelationHisSalaryByCid(String cid);

    Optional<StateCorreHisSala> getStateCorrelationHisSalaryByKey(String cid, String hisId);

    List<StateLinkSetMaster> getStateLinkSettingMasterByHisId(String cId, String hisId);

    Optional<StateLinkSetMaster> getStateLinkSettingMasterById(String cid, String hisId, String masterCode);

    void update (String cid, YearMonthHistoryItem history);

    void updateAll(String cid, List<StateLinkSetMaster> stateLinkSetMasters, int startYearMonth, int endYearMonth);

    void addAll(String cid, List<StateLinkSetMaster> stateLinkSetMasters, int startYearMonth, int endYearMonth );

    void removeAll(String cid, String hisId);

}
