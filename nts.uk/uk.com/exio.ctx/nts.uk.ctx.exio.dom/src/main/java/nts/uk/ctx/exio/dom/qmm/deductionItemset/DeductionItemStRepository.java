package nts.uk.ctx.exio.dom.qmm.deductionItemset;

import java.util.Optional;
import java.util.List;

/**
* 控除項目設定
*/
public interface DeductionItemStRepository
{

    List<DeductionItemSt> getAllDeductionItemSt();

    Optional<DeductionItemSt> getDeductionItemStById(String cid, String salaryItemId);

    void add(DeductionItemSt domain);

    void update(DeductionItemSt domain);

    void remove(String cid, String salaryItemId);

}
