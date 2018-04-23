package nts.uk.ctx.at.function.app.find.annualworkschedule;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import nts.uk.ctx.at.function.dom.annualworkschedule.ItemOutTblBookRepository;

@Stateless
/**
* 帳表に出力する項目
*/
public class ItemOutTblBookFinder {
	@Inject
	private ItemOutTblBookRepository finder;

	public List<ItemOutTblBookDto> getAllItemOutTblBook(){
		return finder.getAllItemOutTblBook().stream().map(item -> ItemOutTblBookDto.fromDomain(item))
				.collect(Collectors.toList());
	}
}
