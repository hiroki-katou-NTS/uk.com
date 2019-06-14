package nts.uk.ctx.pr.core.dom.wageprovision.statementitem.deductionitemset;

import java.util.List;
import java.util.Optional;

/**
 * @author thanh.tq 控除項目設定
 */
public interface DeductionItemSetRepository {

    List<DeductionItemSet> getAllDeductionItemSt();

    Optional<DeductionItemSet> getDeductionItemStById(String cid, int categoryAtr, String itemNameCd);

    void add(DeductionItemSet domain);

    void update(DeductionItemSet domain);

    void remove(String cid, int categoryAtr, String itemNameCd);

}
