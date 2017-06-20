package nts.uk.ctx.sys.portal.app.find.toppagesetting;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.sys.portal.dom.toppagesetting.TopPageSettingRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author sonnh1
 *
 */
@Stateless
public class TopPageSettingFinder {

	@Inject
	private TopPageSettingRepository topPageSettingRepo;

	/**
	 * find topPageSetting Object base on companyId
	 * 
	 * @return topPageSettingDto
	 */
	public TopPageSettingDto findByCId() {
		String companyId = AppContexts.user().companyId();
		Optional<TopPageSettingDto> topPageSettingDto = topPageSettingRepo.findByCId(companyId)
				.map(x -> TopPageSettingDto.fromDomain(x));
		if (topPageSettingDto.isPresent()) {
			return topPageSettingDto.get();
		}
		return null;
	}
}
