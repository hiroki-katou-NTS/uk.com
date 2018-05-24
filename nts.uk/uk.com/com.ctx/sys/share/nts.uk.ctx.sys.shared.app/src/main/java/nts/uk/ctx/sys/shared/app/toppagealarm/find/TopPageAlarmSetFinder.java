package nts.uk.ctx.sys.shared.app.toppagealarm.find;

import java.util.Optional;

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
	
	public Optional<TopPageAlarmSetDto> finder(){
		String companyId = AppContexts.user().companyId();
		return this.topSetRep.getByCom(companyId).map(x -> {
			return new TopPageAlarmSetDto(x.getCompanyId(), x.getAlarmCategory().value, x.getUseAtr().value);
		});
	}
}
