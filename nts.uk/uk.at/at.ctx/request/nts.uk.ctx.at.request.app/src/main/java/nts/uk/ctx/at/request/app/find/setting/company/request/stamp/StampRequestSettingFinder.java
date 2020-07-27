package nts.uk.ctx.at.request.app.find.setting.company.request.stamp;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.request.app.find.setting.company.request.stamp.dto.StampRequestSettingDto;
import nts.uk.ctx.at.request.dom.setting.company.request.stamp.StampRequestSetting_Old;
import nts.uk.ctx.at.request.dom.setting.company.request.stamp.StampRequestSettingRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class StampRequestSettingFinder {
	
	@Inject
	private StampRequestSettingRepository stampRequestSettingRepository;
	/**
	 * Doan Duy Hung
	 * @return
	 */
	public StampRequestSettingDto findByCompanyID() {
		String companyId = AppContexts.user().companyId();
		Optional<StampRequestSetting_Old> stampRequestSettingOp = stampRequestSettingRepository.findByCompanyID(companyId);
		if(stampRequestSettingOp.isPresent()){
			return StampRequestSettingDto.fromDomain(stampRequestSettingOp.get());
		}
		return null;
	}
	
}
