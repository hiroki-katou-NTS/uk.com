package nts.uk.ctx.at.record.app.find.stamp.application;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.stamp.application.StampResultDisplay;
import nts.uk.ctx.at.record.dom.stamp.application.StampResultDisplayRepository;
import nts.uk.shr.com.context.AppContexts;
/**
 * 打刻の前準備(オプション)を表示する
 * @author phongtq
 *
 */
@Stateless
public class StampFunctionFinder {
	@Inject
	private StampResultDisplayRepository repo;

	public StampFunctionDto getStampSet(){
		String companyId = AppContexts.user().companyId();
		Optional<StampResultDisplay> domain = repo.getStampSet(companyId);
		if(!domain.isPresent())
			return null;
		
	return StampFunctionDto.fromDomain(domain.get());
	}
}
