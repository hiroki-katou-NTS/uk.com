package nts.uk.ctx.pr.core.dom.wageprovision.individualwagecontract;

import java.util.Optional;
import java.util.List;

/**
* 給与個人別金額履歴
*/
public interface SalIndAmountHisRepository
{

    List<SalIndAmountHis> getAllSalIndAmountHis();

    Optional<SalIndAmountHis> getSalIndAmountHisById(String historyId, String perValCode, String empId);

    Optional<SalIndAmountHis> getSalIndAmountHisByPerVal( String perValCode);

    void add(SalIndAmountHis domain);

    void update(SalIndAmountHis domain);

    void remove(String historyId, String perValCode, String empId);

}
