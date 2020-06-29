package nts.uk.screen.at.app.query.kdp.kdp010.a;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.stamp.application.CommonSettingsStampInputRepository;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.PortalStampSettings;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.PortalStampSettingsRepository;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.SettingsSmartphoneStamp;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.SettingsSmartphoneStampRepository;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.StampSetCommunal;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.StampSetCommunalRepository;
import nts.uk.screen.at.app.query.kdp.kdp001.a.PortalStampSettingsDto;
import nts.uk.screen.at.app.query.kdp.kdp010.a.dto.SettingsSmartphoneStampDto;
import nts.uk.screen.at.app.query.kdp.kdp010.a.dto.StampSetCommunalDto;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class TimeStampInputSettingFinder {

	@Inject
	private PortalStampSettingsRepository portalStampSettingsRepo;
	
	@Inject
	private StampSetCommunalRepository stampSetCommunalRepo;
	
	@Inject
	private SettingsSmartphoneStampRepository SettingsSmartphoneStampRepo;
	
	@Inject
	private CommonSettingsStampInputRepository commonSettingsStampInputRepo;
	
	/** 打刻の前準備(ポータル)の設定内容を取得する*/
	public Optional<PortalStampSettingsDto> getPortalStampSettings() {
		Optional<PortalStampSettings> domain = portalStampSettingsRepo.get(AppContexts.user().companyId());
		if(domain.isPresent()) {
			return Optional.of(PortalStampSettingsDto.fromDomain(domain.get()));
		}else {
			return Optional.empty();
		}
	}
	
	/** 打刻の前準備(共有)の設定を取得する*/
	public Optional<StampSetCommunalDto> getStampSetCommunal() {
		Optional<StampSetCommunal> domain = stampSetCommunalRepo.gets(AppContexts.user().companyId());
		if(domain.isPresent()) {
			return Optional.of(StampSetCommunalDto.fromDomain(domain.get()));
		}else {
			return Optional.empty();
		}
	}
	
	/** 打刻の前準備(スマホ)の設定を取得する*/
	public SettingsSmartphoneStampDto getSettingsSmartphoneStamp() {
		SettingsSmartphoneStampDto result = new SettingsSmartphoneStampDto();
		String cId = AppContexts.user().companyId();
		Optional<SettingsSmartphoneStamp> domain = SettingsSmartphoneStampRepo.get(cId);
		commonSettingsStampInputRepo.get(cId).ifPresent(c->result.setGoogleMap(c.isGooglemap()?1:0));
		if(domain.isPresent()) {
			result.settingsSmartphoneStamp(domain.get());
		}
		return result;
	}
}
