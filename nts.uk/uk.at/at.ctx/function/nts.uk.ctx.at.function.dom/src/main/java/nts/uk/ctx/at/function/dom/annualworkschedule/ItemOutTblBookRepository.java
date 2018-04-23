package nts.uk.ctx.at.function.dom.annualworkschedule;

import java.util.Optional;
import java.util.List;

/**
* 帳表に出力する項目
*/
public interface ItemOutTblBookRepository
{

    List<ItemOutTblBook> getAllItemOutTblBook();

    Optional<ItemOutTblBook> getItemOutTblBookById(String cid, int cd);

    void add(ItemOutTblBook domain);

    void update(ItemOutTblBook domain);

    void remove(String cid, int cd);

}
