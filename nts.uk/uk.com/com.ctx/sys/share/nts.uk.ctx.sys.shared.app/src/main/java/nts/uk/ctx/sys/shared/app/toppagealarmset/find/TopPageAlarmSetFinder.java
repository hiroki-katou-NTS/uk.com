package nts.uk.ctx.sys.shared.app.toppagealarmset.find;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.sys.shared.dom.toppagealarmset.TopPageAlarmSetRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author yennth
 *
 */
@Stateless
public class TopPageAlarmSetFinder {
	@Inject
	private TopPageAlarmSetRepository topSetRep;
	
	public List<TopPageAlarmSetDto> finder(){
		String companyId = AppContexts.user().companyId();
		return this.topSetRep.getAll(companyId).stream().map(x -> {
			return new TopPageAlarmSetDto(x.getCompanyId(), x.getAlarmCategory().value, x.getUseAtr().value);
		}).collect(Collectors.toList());
	}
}
