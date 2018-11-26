package nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting;

import nts.arc.time.GeneralDate;
import nts.uk.shr.com.history.YearMonthHistoryItem;

import java.util.List;
import java.util.Optional;

/**
* 明細書紐付け履歴（部門）
*/
public interface StateCorreHisDeparRepository {

    Optional<StateCorreHisDepar> getStateCorrelationHisDeparmentById(String cid);

    Optional<StateCorreHisDepar> getStateCorrelationHisDeparmentByDate(String cid, GeneralDate baseDate);

    Optional<StateLinkSetDate> getStateLinkSettingDateById(String cId, String hisId);

    Optional<StateLinkSetMaster> getStateLinkSettingMasterById(String cid, String hisId, String masterCode);

    void update (String cid, YearMonthHistoryItem history);

    void updateAll(String cid, List<StateLinkSetMaster> stateLinkSetMasters, int startYearMonth, int endYearMonth, GeneralDate baseDate);

    void addAll(String cid, List<StateLinkSetMaster> stateLinkSetMasters, int startYearMonth, int endYearMonth, GeneralDate baseDate );

    void removeAll(String cid, String hisId);

}
