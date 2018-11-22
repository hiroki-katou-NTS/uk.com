package nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting;

import nts.arc.time.GeneralDate;
import nts.uk.shr.com.history.YearMonthHistoryItem;

import java.util.Optional;

/**
* 明細書紐付け履歴（会社）
*/
public interface StateCorrelationHisCompanyRepository {

    Optional<StateCorrelationHisCompany> getStateCorrelationHisCompanyById(String cid);

    Optional<StateLinkSettingCompany>  getStateLinkSettingCompanyById(String cid,String hisId);

    Optional<StateCorrelationHisCompany> getStateCorrelationHisCompanyByDate(String cid,GeneralDate baseDate);

    void add(String cid, YearMonthHistoryItem history, String salaryCode, String bonusCode);

    void update(String cid, YearMonthHistoryItem history);

    void update(String cid, YearMonthHistoryItem history, String salaryCode, String bonusCode);

    void remove(String cid, String hisId);

}
