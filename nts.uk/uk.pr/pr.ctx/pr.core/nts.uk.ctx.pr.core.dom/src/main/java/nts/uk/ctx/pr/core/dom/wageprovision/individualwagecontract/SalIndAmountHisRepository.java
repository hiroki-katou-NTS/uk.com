package nts.uk.ctx.pr.core.dom.wageprovision.individualwagecontract;

import java.util.Optional;
import java.util.List;

/**
 * 給与個人別金額履歴
 */
public interface SalIndAmountHisRepository {

    List<SalaryIndividualAmountHistory> getSalIndAmountHis(String perValCode, String empId, int salBonusCate, int cateIndicator);

    List<SalaryIndividualAmountHistory> getSalIndAmountHisDisplay(String perValCode, String empId, int salBonusCate, int cateIndicator, int currentProcessYearMonth);

    List<PersonalAmount> getSalIndAmountHisByPerVal(String perValCode, int cateIndicator, int salBonusCate, int standardYearMonth, List<String> empIds);

    void updateOldHistory(String historyId, int newEndMonthOfOldHistory);

    void updateHistory(SalIndAmountHis domain);

    void remove(String historyId);

    void add(SalIndAmountHis domain1, SalIndAmount domain2);

    void updateAmount(SalIndAmount domain);

}
