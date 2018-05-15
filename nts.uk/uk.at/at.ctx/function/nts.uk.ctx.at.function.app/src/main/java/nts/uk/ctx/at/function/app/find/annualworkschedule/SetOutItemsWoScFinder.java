package nts.uk.ctx.at.function.app.find.annualworkschedule;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.function.dom.annualworkschedule.repository.SetOutItemsWoScRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
/**
* 年間勤務表（36チェックリスト）の出力項目設定
*/
public class SetOutItemsWoScFinder
{
	@Inject
	private SetOutItemsWoScRepository finder;

	public List<SetOutItemsWoScDto> getAllSetOutItemsWoSc() {
		String cid = AppContexts.user().companyId();
		return finder.getAllSetOutItemsWoSc(cid).stream().map(item -> SetOutItemsWoScDto.fromDomain(item)).collect(Collectors.toList());
	}
}
