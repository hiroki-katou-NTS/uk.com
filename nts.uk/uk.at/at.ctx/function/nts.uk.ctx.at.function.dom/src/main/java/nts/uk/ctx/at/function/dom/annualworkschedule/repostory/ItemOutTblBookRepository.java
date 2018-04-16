package nts.uk.ctx.at.function.dom.annualworkschedule.repostory;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.function.dom.annualworkschedule.ItemOutTblBook;

/**
* 帳表に出力する項目
*/
public interface ItemOutTblBookRepository {

	List<ItemOutTblBook> getAllItemOutTblBook();

	Optional<ItemOutTblBook> getItemOutTblBookById(String cid, int code, int sortBy);

	void add(ItemOutTblBook domain);

	void update(ItemOutTblBook domain);

	void remove(String cid, int code, int sortBy);
}
