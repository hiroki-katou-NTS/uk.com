package nts.uk.screen.at.app.query.kdp.kdp010.a;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.PortalStampSettings;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.PortalStampSettingsRepository;
import nts.uk.screen.at.app.query.kdp.kdp001.a.PortalStampSettingsDto;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class TimeStampInputSettingFinder {

	@Inject
	private PortalStampSettingsRepository portalStampSettingsRepo;
	
	/** 打刻の前準備(ポータル)の設定内容を取得する*/
	public Optional<PortalStampSettingsDto> getPortalStampSettings() {
		Optional<PortalStampSettings> domain = portalStampSettingsRepo.get(AppContexts.user().companyId());
		if(domain.isPresent()) {
			return Optional.of(PortalStampSettingsDto.fromDomain(domain.get()));
		}else {
			return Optional.empty();
		}
	}
}
