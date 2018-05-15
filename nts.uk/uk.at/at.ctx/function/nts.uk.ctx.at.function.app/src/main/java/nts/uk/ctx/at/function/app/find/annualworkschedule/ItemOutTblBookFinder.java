package nts.uk.ctx.at.function.app.find.annualworkschedule;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.function.dom.annualworkschedule.repository.ItemOutTblBookRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
/**
* 帳表に出力する項目
*/
public class ItemOutTblBookFinder
{
	@Inject
	private ItemOutTblBookRepository finder;

	public List<ItemOutTblBookDto> getItemOutTblBookBySetOutCd(String setOutCd) {
		String cid = AppContexts.user().companyId();
		return finder.getItemOutTblBookBySetOutCd(cid, setOutCd).stream().map(item -> ItemOutTblBookDto.fromDomain(item))
				.collect(Collectors.toList());
	}
}
