package nts.uk.ctx.at.record.app.command.kdp.kdp010.a;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.app.command.kdp.kdp010.a.command.PortalStampSettingsCommand;
import nts.uk.ctx.at.record.app.command.kdp.kdp010.a.command.SettingsSmartphoneStampCommand;
import nts.uk.ctx.at.record.app.command.kdp.kdp010.a.command.SettingsUsingEmbossingCommand;
import nts.uk.ctx.at.record.app.command.kdp.kdp010.a.command.StampSetCommunalCommand;
import nts.uk.ctx.at.record.app.command.stamp.management.StampPageLayoutCommand;
import nts.uk.ctx.at.record.dom.stamp.application.CommonSettingsStampInput;
import nts.uk.ctx.at.record.dom.stamp.application.CommonSettingsStampInputRepository;
import nts.uk.ctx.at.record.dom.stamp.application.SettingsUsingEmbossingRepository;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.CorrectionInterval;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.DisplaySettingsStampScreen;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.NumberAuthenfailures;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.PortalStampSettingsRepository;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.ResultDisplayTime;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.SettingDateTimeColorOfStampScreen;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.SettingsSmartphoneStamp;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.SettingsSmartphoneStampRepository;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.StampPageLayout;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.StampSetCommunal;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.StampSetCommunalRepository;
import nts.uk.ctx.at.shared.dom.common.color.ColorCode;
import nts.uk.shr.com.context.AppContexts;

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
		Optional<SettingsSmartphoneStamp> oldDomain = settingsSmartphoneStampRepo.get(companyId);
		SettingsSmartphoneStamp saveDomain = command.toDomain();
		if(oldDomain.isPresent()) {
			saveDomain.setPageLayoutSettings(oldDomain.get().getPageLayoutSettings());
		}else {
			saveDomain.setPageLayoutSettings(new ArrayList<>());
		}
		settingsSmartphoneStampRepo.save(saveDomain);
		
		Optional<CommonSettingsStampInput> commonDomain = commonSettingsStampInputRepo.get(companyId);
		if (commonDomain.isPresent()) {
			commonDomain.get().setGooglemap(command.getGoogleMap() == 1);
			commonSettingsStampInputRepo.update(commonDomain.get());
		} else {
			commonSettingsStampInputRepo.insert(new CommonSettingsStampInput(companyId, new ArrayList<String>(), command.getGoogleMap() == 1, Optional.empty()));
		}
	}
	
	/**打刻の前準備(利用設定)を登録する*/
	public void saveSettingsUsingEmbossing(SettingsUsingEmbossingCommand command) {
		settingsUsingEmbossingRepo.save(command.toDomain());
	}
	
	/**打刻レイアウト(スマホ)の設定内容を更新する(Update)*/
	public void savePageLayoutSettingsSmartphone(StampPageLayoutCommand command) {
		String companyId = AppContexts.user().companyId();
		Optional<SettingsSmartphoneStamp> oldDomain = settingsSmartphoneStampRepo.get(companyId);
		if(oldDomain.isPresent()) {
			oldDomain.get().setPageLayoutSettings(Arrays.asList(command.toDomain()));
			settingsSmartphoneStampRepo.save(oldDomain.get());
		}else {
			DisplaySettingsStampScreen displaySettingsStampScreen = new DisplaySettingsStampScreen(
					new CorrectionInterval(10), 
					new SettingDateTimeColorOfStampScreen(new ColorCode("#ffffff"), new ColorCode("#0033cc")), 
					new ResultDisplayTime(3));
			SettingsSmartphoneStamp setting = new SettingsSmartphoneStamp(companyId, displaySettingsStampScreen, Arrays.asList(command.toDomain()), false);
			settingsSmartphoneStampRepo.save(setting);
			commonSettingsStampInputRepo.insert(new CommonSettingsStampInput(companyId, new ArrayList<String>(), false, Optional.empty()));
		}
	}
	
	/**打刻レイアウト(スマホ)の設定内容を削除する(Delete)*/
	public void delPageLayoutSettingsSmartphone() {
		String companyId = AppContexts.user().companyId();
		Optional<SettingsSmartphoneStamp> oldDomain = settingsSmartphoneStampRepo.get(companyId);
		if(oldDomain.isPresent()) {
			oldDomain.get().setPageLayoutSettings(new ArrayList<StampPageLayout>());
			settingsSmartphoneStampRepo.save(oldDomain.get());
		}
	}

	public void saveStampPageLayout(StampPageLayoutCommand command) {
		String companyId = AppContexts.user().companyId();
		Optional<StampSetCommunal> domainPre = stampSetCommunalRepo.gets(companyId);
		if (domainPre.isPresent()) {
			domainPre.get().getLstStampPageLayout().removeIf(c->c.getPageNo().v() == command.getPageNo());
			domainPre.get().getLstStampPageLayout().add(command.toDomain());
			stampSetCommunalRepo.save(domainPre.get());
		}else {
			DisplaySettingsStampScreen displaySettingsStampScreen = new DisplaySettingsStampScreen(
					new CorrectionInterval(10), 
					new SettingDateTimeColorOfStampScreen(new ColorCode("#ffffff"), new ColorCode("#0033cc")), 
					new ResultDisplayTime(3));
			StampSetCommunal domain = new StampSetCommunal(
					companyId, 
					displaySettingsStampScreen, 
					Arrays.asList(command.toDomain()), 
					false,
					true, 
					false, 
					Optional.of(new NumberAuthenfailures(1)));
			stampSetCommunalRepo.save(domain);
		}
	}
}
