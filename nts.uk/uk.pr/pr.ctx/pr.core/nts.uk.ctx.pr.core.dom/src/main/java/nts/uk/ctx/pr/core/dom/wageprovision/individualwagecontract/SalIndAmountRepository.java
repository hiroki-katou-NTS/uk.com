package nts.uk.ctx.pr.core.dom.wageprovision.individualwagecontract;

import java.util.Optional;
import java.util.List;

/**
* 給与個人別金額
*/
public interface SalIndAmountRepository
{

    List<SalIndAmount> getAllSalIndAmount();

    Optional<SalIndAmount> getSalIndAmountById(String historyId);

    void add(SalIndAmount domain);

    void update(SalIndAmount domain);

    void remove(String historyId);

    void updateAll(List<SalIndAmount> domains);


}
