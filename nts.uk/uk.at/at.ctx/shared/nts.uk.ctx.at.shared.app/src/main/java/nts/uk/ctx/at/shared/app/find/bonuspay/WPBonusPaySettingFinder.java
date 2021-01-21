package nts.uk.ctx.at.shared.app.find.bonuspay;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.uk.ctx.at.shared.dom.common.WorkplaceId;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.repository.WPBonusPaySettingRepository;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.setting.WorkplaceBonusPaySetting;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class WPBonusPaySettingFinder {
	@Inject
	private WPBonusPaySettingRepository repo;

	public List<WPBonusPaySettingDto> getListSetting(List<String> lstWorkplace) {
		if(lstWorkplace==null||lstWorkplace.isEmpty()){
			return new ArrayList<WPBonusPaySettingDto>();
		}
		String companyId = AppContexts.user().companyId();
		List<WorkplaceBonusPaySetting> domains = this.repo
				.getListSetting(companyId, lstWorkplace.stream().map(c -> new WorkplaceId(c)).collect(Collectors.toList()));
		return domains.stream().map(c -> toWPBonusPaySettingDto(c)).collect(Collectors.toList());
	}

	public WPBonusPaySettingDto getWPBPSetting(String WorkplaceId) {
		String companyId = AppContexts.user().companyId();
		Optional<WorkplaceBonusPaySetting> domain = this.repo.getWPBPSetting(companyId, new WorkplaceId(WorkplaceId));
		if (domain.isPresent()) {
			return this.toWPBonusPaySettingDto(domain.get());
		}
		return null;
	}

	private WPBonusPaySettingDto toWPBonusPaySettingDto(WorkplaceBonusPaySetting workplaceBonusPaySetting) {
		return new WPBonusPaySettingDto(workplaceBonusPaySetting.getWorkplaceId().toString(),
				workplaceBonusPaySetting.getBonusPaySettingCode().toString());
	}

}
