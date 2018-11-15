package nts.uk.ctx.pr.core.dom.wageprovision.salaryindividualamountname;

import java.util.Optional;
import java.util.List;

/**
* 給与個人別金額名称
*/
public interface SalIndAmountNameRepository
{

    List<SalIndAmountName> getAllSalIndAmountName();

    Optional<SalIndAmountName> getSalIndAmountNameById(String cid, String individualPriceCode, int cateIndicator);

    List<SalIndAmountName> getAllSalIndAmountNameByCateIndi(String cid, int cateIndicator);

    void add(SalIndAmountName domain);

    void update(SalIndAmountName domain);

    void remove(String cid, String individualPriceCode,int cateIndicator);



}
