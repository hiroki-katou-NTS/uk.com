package nts.uk.ctx.pr.core.dom.wageprovision.breakdownitemamount;

import java.util.Optional;
import java.util.List;

/**
* 給与内訳個人金額履歴
*/
public interface BreakdownAmountHisRepository
{

    List<BreakdownAmountHis> getAllBreakdownAmountHis();

    Optional<BreakdownAmountHis> getBreakdownAmountHisBySalaryBonusAtr(String cid,int categoryAtr, String itemNameCd, String employeeId, int salaryBonusAtr);

    Optional<BreakdownAmountHis> getBreakdownAmountHisById();

    void add(BreakdownAmountHis domain);

    void update(BreakdownAmountHis domain);

    void updateByHistoryId(String cid,int categoryAtr, String itemNameCd, String employeeId, int salaryBonusAtr, String lastHistoryId);

    void updateByLastHistoryId(String cid,int categoryAtr, String itemNameCd, String employeeId, int salaryBonusAtr, String lastHistoryId, int startYearMonth);

    void remove(String cid,int categoryAtr, String itemNameCd, String employeeId, int salaryBonusAtr, String lstHistoryId);

}
