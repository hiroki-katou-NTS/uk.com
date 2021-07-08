package nts.uk.screen.at.app.query.kdp.kdp010.a;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.app.find.stamp.management.StampPageLayoutDto;
import nts.uk.ctx.at.record.dom.stamp.application.CommonSettingsStampInputRepository;
import nts.uk.ctx.at.record.dom.stamp.application.SettingsUsingEmbossing;
import nts.uk.ctx.at.record.dom.stamp.application.SettingsUsingEmbossingRepository;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.PortalStampSettings;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.PortalStampSettingsRepository;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.StampPageLayout;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.StampSetCommunal;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.StampSetCommunalRepository;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.settingforsmartphone.SettingsSmartphoneStamp;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.settingforsmartphone.SettingsSmartphoneStampRepository;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.stampsettingofRICOHcopier.StampSettingOfRICOHCopier;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.stampsettingofRICOHcopier.StampSettingOfRICOHCopierRepository;
import nts.uk.screen.at.app.query.kdp.kdp001.a.PortalStampSettingsDto;
import nts.uk.screen.at.app.query.kdp.kdp010.a.dto.SettingsSmartphoneStampDto;
import nts.uk.screen.at.app.query.kdp.kdp010.a.dto.SettingsUsingEmbossingDto;
import nts.uk.screen.at.app.query.kdp.kdp010.a.dto.StampSetCommunalDto;
import nts.uk.screen.at.app.query.kdp.kdp010.a.dto.StampSettingOfRICOHCopierDto;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class TimeStampInputSettingFinder {

	@Inject
	private PortalStampSettingsRepository portalStampSettingsRepo;
	
	@Inject
	private StampSetCommunalRepository stampSetCommunalRepo;
	
	@Inject
	private SettingsSmartphoneStampRepository settingsSmartphoneStampRepo;
	
	@Inject
	private CommonSettingsStampInputRepository commonSettingsStampInputRepo;
	
	@Inject
	private SettingsUsingEmbossingRepository settingsUsingEmbossingRepo;
	
	@Inject
	private StampSettingOfRICOHCopierRepository stampSettingOfRICOHCopierRepo;
	
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
		Optional<SettingsSmartphoneStamp> domain = settingsSmartphoneStampRepo.get(cId);
		commonSettingsStampInputRepo.get(cId).ifPresent(c->result.setGoogleMap(c.isGooglemap()?1:0));
		if(domain.isPresent()) {
			result.settingsSmartphoneStamp(domain.get());
			return result;
		}else {
			return null;
		}
		
	}
	
	/**打刻の前準備(利用設定)を表示する*/
	public Optional<SettingsUsingEmbossingDto> getSettingsUsingEmbossing() {
		String cId = AppContexts.user().companyId();
		Optional<SettingsUsingEmbossing> domain = settingsUsingEmbossingRepo.get(cId);
		if(domain.isPresent()) {
			return Optional.of(new SettingsUsingEmbossingDto(domain.get()));
		}
		return Optional.empty();
	}
	
	
	/**打刻レイアウト(スマホ)の設定内容を取得する*/
	public StampPageLayoutDto getLayoutSettingsSmartphone(Integer pageNo){
		String cId = AppContexts.user().companyId();
		Optional<SettingsSmartphoneStamp> domain = settingsSmartphoneStampRepo.get(cId);
		if(domain.isPresent()) {
			 Optional<StampPageLayout> result = domain.get().getPageLayoutSettings().stream().filter(c->c.getPageNo().v() == pageNo).findFirst();
			 if(result.isPresent()) {
				 return StampPageLayoutDto.fromDomain(result.get());
			 }
		}
		return null;
	}
	
	public StampSettingOfRICOHCopierDto getStampSettingOfRICOHCopier(){
		String cId = AppContexts.user().companyId();
		Optional<StampSettingOfRICOHCopier> domain = stampSettingOfRICOHCopierRepo.get(cId);
		if(domain.isPresent()) {
			return new StampSettingOfRICOHCopierDto(domain.get());
		}
		return null;
	}
	
}
