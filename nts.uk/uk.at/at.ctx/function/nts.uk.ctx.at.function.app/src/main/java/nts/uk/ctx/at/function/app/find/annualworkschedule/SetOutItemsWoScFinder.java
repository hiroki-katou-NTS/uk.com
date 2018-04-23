package nts.uk.ctx.at.function.app.find.annualworkschedule;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import nts.uk.ctx.at.function.dom.annualworkschedule.SetOutItemsWoScRepository;

@Stateless
/**
* 年間勤務表（36チェックリスト）の出力項目設定
*/
public class SetOutItemsWoScFinder {
	@Inject
	private SetOutItemsWoScRepository finder;

	public List<SetOutItemsWoScDto> getAllSetOutItemsWoSc(){
		return finder.getAllSetOutItemsWoSc().stream().map(item -> SetOutItemsWoScDto.fromDomain(item))
				.collect(Collectors.toList());
	}
}
