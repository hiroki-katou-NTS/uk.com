package nts.uk.ctx.sys.portal.app.find.toppagesetting;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.sys.portal.dom.toppagesetting.service.TopPageSettingService;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author sonnh1
 *
 */
@Stateless
public class TopPageSettingFinder {

//	@Inject
//	private TopPageSettingRepository topPageSettingRepo;
	
	@Inject
	private TopPageSettingService domainService;

	/**
	 * find topPageSetting Object base on companyId
	 * 
	 * @return topPageSettingDto
	 */
	public TopPageSettingDto findByCId() {
//		String companyId = AppContexts.user().companyId();
//		Optional<TopPageSettingDto> topPageSettingDto = topPageSettingRepo.findByCId(companyId)
//				.map(x -> TopPageSettingDto.fromDomain(x));
//		if (topPageSettingDto.isPresent()) {
//			return topPageSettingDto.get();
//		}
		Optional<TopPageSettingDto> topPageSettingDto = this.domainService.getTopPageSettings(
				AppContexts.user().companyId(), 
				AppContexts.user().employeeId()).map(TopPageSettingDto::fromDomain);
		return topPageSettingDto.orElse(null);
	}
}
