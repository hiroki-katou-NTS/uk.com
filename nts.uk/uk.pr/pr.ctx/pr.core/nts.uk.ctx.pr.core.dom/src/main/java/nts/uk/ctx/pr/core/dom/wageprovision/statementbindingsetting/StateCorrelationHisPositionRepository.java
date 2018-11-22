package nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.shr.com.history.YearMonthHistoryItem;

/**
* 明細書紐付け履歴（職位）
*/
public interface StateCorrelationHisPositionRepository {

    Optional<StateCorrelationHisPosition> getStateCorrelationHisPositionById(String cid, String hisId);

    Optional<StateCorrelationHisPosition> getStateCorrelationHisPositionByCid(String cId);

    List<StateLinkSettingMaster> getStateLinkSettingMasterByHisId(String cId, String hisId);

    Optional<StateLinkSettingMaster> getStateLinkSettingMasterById(String cid, String hisId, String masterCode);

    Optional<StateLinkSettingDate> getStateLinkSettingDateById(String cId, String hisId);

    void update (String cid, YearMonthHistoryItem history);

    void updateAll(String cid, List<StateLinkSettingMaster> stateLinkSettingMasters, int startYearMonth, int endYearMonth, GeneralDate baseDate);

    void addAll(String cid, List<StateLinkSettingMaster> stateLinkSettingMasters, int startYearMonth, int endYearMonth, GeneralDate baseDate );

    void removeAll(String cid, String hisId);

}
