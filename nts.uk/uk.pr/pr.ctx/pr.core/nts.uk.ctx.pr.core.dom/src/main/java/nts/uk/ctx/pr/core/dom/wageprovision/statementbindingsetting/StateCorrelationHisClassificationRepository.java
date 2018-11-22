package nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting;

import java.util.List;
import java.util.Optional;

import nts.uk.shr.com.history.YearMonthHistoryItem;

/**
* 明細書紐付け履歴（分類）
*/
public interface StateCorrelationHisClassificationRepository {

    Optional<StateCorrelationHisClassification> getStateCorrelationHisClassificationById(String cid, String hisId);

    Optional<StateCorrelationHisClassification> getStateCorrelationHisClassificationByCid(String cid);

    List<StateLinkSettingMaster> getStateLinkSettingMasterByHisId(String cid, String hisId);

    Optional<StateLinkSettingMaster> getStateLinkSettingMasterById(String cid, String hisId, String masterCode);

    void update (String cid, YearMonthHistoryItem history);

    void updateAll(String cid, List<StateLinkSettingMaster> stateLinkSettingMasters, int startYearMonth, int endYearMonth);

    void addAll(String cid, List<StateLinkSettingMaster> stateLinkSettingMasters, int startYearMonth, int endYearMonth );

    void removeAll(String cid, String hisId);

}
