package nts.uk.ctx.at.record.app.command.kdp.kdp010.a;

import java.util.ArrayList;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.app.command.kdp.kdp010.a.command.PortalStampSettingsCommand;
import nts.uk.ctx.at.record.app.command.kdp.kdp010.a.command.SettingsSmartphoneStampCommand;
import nts.uk.ctx.at.record.app.command.kdp.kdp010.a.command.SettingsUsingEmbossingCommand;
import nts.uk.ctx.at.record.app.command.kdp.kdp010.a.command.StampSetCommunalCommand;
import nts.uk.ctx.at.record.app.command.kdp.kdp010.a.command.StampSettingOfRICOHCopierCommand;
import nts.uk.ctx.at.record.app.command.stamp.management.StampPageLayoutCommand;
import nts.uk.ctx.at.record.app.find.stamp.management.NoticeSetAndAupUseArtDto;
import nts.uk.ctx.at.record.dom.stamp.application.CommonSettingsStampInput;
import nts.uk.ctx.at.record.dom.stamp.application.CommonSettingsStampInputRepository;
import nts.uk.ctx.at.record.dom.stamp.application.MapAddress;
import nts.uk.ctx.at.record.dom.stamp.application.SettingsUsingEmbossingRepository;
import nts.uk.ctx.at.record.dom.stampmanagement.setting.preparation.smartphonestamping.employee.EmployeeStampingAreaRestrictionSetting;
import nts.uk.ctx.at.record.dom.stampmanagement.setting.preparation.smartphonestamping.employee.StampingAreaRepository;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.PortalStampSettingsRepository;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.StampSetCommunal;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.StampSetCommunalRepository;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.StampSetPerRepository;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.StampSettingPerson;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.settingforsmartphone.SettingsSmartphoneStamp;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.settingforsmartphone.SettingsSmartphoneStampRepository;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.stampsettingofRICOHcopier.StampSettingOfRICOHCopier;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.stampsettingofRICOHcopier.StampSettingOfRICOHCopierRepository;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.stampinputfunctionsettings.notificationmessagesettings.NoticeSetRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.enumcommon.NotUseAtr;

@Stateless
public class TimeStampInputSettingsCommandHandler {

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
	private NoticeSetRepository noticeSetRepo;
	
	@Inject
	private StampSettingOfRICOHCopierRepository stampSettingOfRICOHCopierRepo;
	
	@Inject
	private StampSetPerRepository stampSetPerRepo;
	
	@Inject
	private StampingAreaRepository stampingAreaRepository;
	
	/**打刻の前準備(ポータル)を登録する*/
	public void savePortalStampSettings(PortalStampSettingsCommand command) {
		portalStampSettingsRepo.save(command.toDomain());
	}
	
	/**打刻の前準備(共有)を登録する*/
	public void saveStampSetCommunal(StampSetCommunalCommand command) {
		String companyId = AppContexts.user().companyId();
		Optional<StampSetCommunal> domainPre = stampSetCommunalRepo.gets(companyId);
		StampSetCommunal domain = command.toDomain();
		if (domainPre.isPresent()) {
			domain.setLstStampPageLayout(domainPre.get().getLstStampPageLayout());
			stampSetCommunalRepo.save(domain);
		}else {
			domain.setLstStampPageLayout(new ArrayList<>());
			stampSetCommunalRepo.save(domain);
		}
	}
	
	/**打刻の前準備(スマホ)を登録する*/
	public void saveSettingsSmartphoneStamp(SettingsSmartphoneStampCommand command) {
		String companyId = AppContexts.user().companyId();
		Optional<SettingsSmartphoneStamp> oldDomain = settingsSmartphoneStampRepo.get(companyId, AppContexts.user().employeeId());
		SettingsSmartphoneStamp saveDomain = command.toDomain();
		if(oldDomain.isPresent()) {
			saveDomain.setPageLayoutSettings(oldDomain.get().getPageLayoutSettings());
		}else {
			saveDomain.setPageLayoutSettings(new ArrayList<>());
		}
		settingsSmartphoneStampRepo.save(saveDomain);
		
		stampingAreaRepository.saveStampingArea(new EmployeeStampingAreaRestrictionSetting(
				AppContexts.user().employeeId(), saveDomain.getStampingAreaRestriction()));
		
		Optional<CommonSettingsStampInput> commonDomain = commonSettingsStampInputRepo.get(companyId);
		if (commonDomain.isPresent()) {
			commonDomain.get().setGooglemap(command.getGoogleMap() == 1);
			if(!commonDomain.get().getMapAddres().isPresent()) {
				commonDomain.get().setMapAddres(Optional.of(new MapAddress("https://www.google.co.jp/maps/place/")));
			}
			commonSettingsStampInputRepo.update(commonDomain.get());
		} else {
			commonSettingsStampInputRepo.insert(new CommonSettingsStampInput(companyId, command.getGoogleMap() == 1, Optional.of(new MapAddress("https://www.google.co.jp/maps/place/")), NotUseAtr.NOT_USE));
		}
	}
	
	/**打刻の前準備(利用設定)を登録する*/
	public void saveSettingsUsingEmbossing(SettingsUsingEmbossingCommand command) {
		settingsUsingEmbossingRepo.save(command.toDomain());
	}
	
	/**打刻レイアウト(スマホ)の設定内容を削除する(Delete)*/
	public void delPageLayoutSettingsSmartphone() {
		String companyId = AppContexts.user().companyId();
		Optional<SettingsSmartphoneStamp> oldDomain = settingsSmartphoneStampRepo.get(companyId, AppContexts.user().employeeId());
		if(oldDomain.isPresent()) {
			oldDomain.get().deletePage();
			settingsSmartphoneStampRepo.save(oldDomain.get());
		}
	}

	public void saveStampPageLayout(StampPageLayoutCommand command) {
		String companyId = AppContexts.user().companyId();
		if(command.getStampMeans() == 0) {
			Optional<StampSetCommunal> domain = stampSetCommunalRepo.gets(companyId);
			if (domain.isPresent()) {
				domain.get().updatePage(command.toDomain());
				stampSetCommunalRepo.save(domain.get());
			}
		}else if(command.getStampMeans() == 1){
			Optional<StampSettingPerson> domain = stampSetPerRepo.getStampSet(companyId);
			if(domain.isPresent()) {
				domain.get().updatePage(command.toDomain());
				stampSetPerRepo.update(domain.get());
			}
		}else if(command.getStampMeans() == 3){
			Optional<SettingsSmartphoneStamp> oldDomain = settingsSmartphoneStampRepo.get(companyId, AppContexts.user().employeeId());
			if(oldDomain.isPresent()) {
				oldDomain.get().updatePage(command.toDomain());
				settingsSmartphoneStampRepo.save(oldDomain.get());
			}
		}else if(command.getStampMeans() == 5){
			Optional<StampSettingOfRICOHCopier> domain = stampSettingOfRICOHCopierRepo.get(companyId);
			if(domain.isPresent()) {
				domain.get().updatePage(command.toDomain());
				stampSettingOfRICOHCopierRepo.update(domain.get());
			}
		}
		//không quan tâm trường hợp không tồn tại data
		//http://192.168.50.4:3000/issues/115467
	}
	
	public void saveNoticeSetAndAupUseArt(NoticeSetAndAupUseArtDto command) {
		String cid = AppContexts.user().companyId();
		if(noticeSetRepo.get(cid).isPresent()) {
			noticeSetRepo.update(command.getNoticeSet().toDomain());
		}else {
			noticeSetRepo.insert(command.getNoticeSet().toDomain());
		}
		Optional<CommonSettingsStampInput> c= commonSettingsStampInputRepo.get(cid);
		if(c.isPresent()) {
			c.get().setSupportUseArt(NotUseAtr.valueOf(command.getSupportUseArt()));
			commonSettingsStampInputRepo.update(c.get());
		}else {
			commonSettingsStampInputRepo.insert(new CommonSettingsStampInput(cid, false, Optional.of(new MapAddress("https://www.google.co.jp/maps/place/")), NotUseAtr.valueOf(command.getSupportUseArt())));
		}
	}
	
	public void saveStampSettingOfRICOHCopier(StampSettingOfRICOHCopierCommand command) {
		String cid = AppContexts.user().companyId();
		Optional<StampSettingOfRICOHCopier> oldDomain = stampSettingOfRICOHCopierRepo.get(cid);
		StampSettingOfRICOHCopier saveDomain = command.toDomain();
		if(oldDomain.isPresent()) {
			saveDomain.setPageLayoutSettings(saveDomain.getPageLayoutSettings());
			stampSettingOfRICOHCopierRepo.update(command.toDomain());;
		}else {
			stampSettingOfRICOHCopierRepo.insert(command.toDomain());
		}
	}
}
