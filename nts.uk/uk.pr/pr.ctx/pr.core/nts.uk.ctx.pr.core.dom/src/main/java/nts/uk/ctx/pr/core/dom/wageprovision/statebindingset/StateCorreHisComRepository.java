package nts.uk.ctx.pr.core.dom.wageprovision.statebindingset;

import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.shr.com.history.YearMonthHistoryItem;

import java.util.Optional;

/**
* 明細書紐付け履歴（会社）
*/
public interface StateCorreHisComRepository {

    Optional<StateCorreHisCom> getStateCorrelationHisCompanyById(String cid);

    Optional<StateLinkSetCom>  getStateLinkSettingCompanyById(String cid, String hisId);

    Optional<StateCorreHisCom> getStateCorrelationHisCompanyByDate(String cid, YearMonth yearMonth);

    void add(String cid, YearMonthHistoryItem history, String salaryCode, String bonusCode);

    void update(String cid, YearMonthHistoryItem history);

    void update(String cid, YearMonthHistoryItem history, String salaryCode, String bonusCode);

    void remove(String cid, String hisId);

}
