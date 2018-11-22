package nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting;

import nts.uk.shr.com.history.YearMonthHistoryItem;

import java.util.List;
import java.util.Optional;

/**
* 明細書紐付け履歴（給与分類）
*/
public interface StateCorrelationHisSalaryRepository {

    Optional<StateCorrelationHisSalary> getStateCorrelationHisSalaryByCid(String cid);

    Optional<StateCorrelationHisSalary> getStateCorrelationHisSalaryByKey(String cid,String hisId);

    List<StateLinkSettingMaster> getStateLinkSettingMasterByHisId(String cId, String hisId);

    Optional<StateLinkSettingMaster> getStateLinkSettingMasterById(String cid, String hisId, String masterCode);

    void update (String cid, YearMonthHistoryItem history);

    void updateAll(String cid, List<StateLinkSettingMaster> stateLinkSettingMasters, int startYearMonth, int endYearMonth);

    void addAll(String cid, List<StateLinkSettingMaster> stateLinkSettingMasters, int startYearMonth, int endYearMonth );

    void removeAll(String cid, String hisId);

}
