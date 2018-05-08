package nts.uk.ctx.at.function.dom.annualworkschedule.repository;

import java.util.Optional;

import nts.uk.ctx.at.function.dom.annualworkschedule.ItemOutTblBook;

import java.util.List;

/**
* 帳表に出力する項目
*/
public interface ItemOutTblBookRepository
{

    List<ItemOutTblBook> getAllItemOutTblBook();

    Optional<ItemOutTblBook> getItemOutTblBookById(String cid, String setOutCd, String cd);

    List<ItemOutTblBook> getItemOutTblBookBySetOutCd(String cid, String setOutCd);

    void add(ItemOutTblBook domain);

    void update(ItemOutTblBook domain);

    void remove(String cid, String setOutCd, String cd);

}
