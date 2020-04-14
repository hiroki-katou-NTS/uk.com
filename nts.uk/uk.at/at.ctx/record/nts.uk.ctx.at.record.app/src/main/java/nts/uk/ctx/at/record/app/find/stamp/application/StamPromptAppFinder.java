package nts.uk.ctx.at.record.app.find.stamp.application;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.stamp.application.StampPromptAppRepository;
import nts.uk.shr.com.context.AppContexts;
/**
 * 打刻の前準備(オプション)を表示する of StampFunctionFinder
 * @author phongtq
 *
 */

@Stateless
public class StamPromptAppFinder {
	
	@Inject
	private StampPromptAppRepository repository;

	public List<StampPronptAppDto> getStampSet(){
		String companyId = AppContexts.user().companyId();
		List<StampPronptAppDto> stampSet = repository.getAllStampSetPage(companyId).stream().map(x->StampPronptAppDto.fromDomain(x)).collect(Collectors.toList());
	return stampSet;
	}
}
