package nts.uk.ctx.pr.core.dom.wageprovision.statementlayout;

import java.util.Optional;
import java.util.List;

/**
* 控除項目明細設定
*/
public interface DeductionItemDetailSetRepository
{

    List<DeductionItemDetailSet> getAllDeductionItemDetailSet();

    Optional<DeductionItemDetailSet> getDeductionItemDetailSetById(String histId);

    void add(DeductionItemDetailSet domain);

    void update(DeductionItemDetailSet domain);

    void remove(String histId);

}
