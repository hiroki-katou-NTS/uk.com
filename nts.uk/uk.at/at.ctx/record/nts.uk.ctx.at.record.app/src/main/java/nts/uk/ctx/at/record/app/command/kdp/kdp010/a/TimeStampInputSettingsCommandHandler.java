package nts.uk.ctx.at.record.app.command.kdp.kdp010.a;

import java.util.ArrayList;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.app.command.kdp.kdp010.a.command.PortalStampSettingsCommand;
import nts.uk.ctx.at.record.app.command.kdp.kdp010.a.command.SettingsSmartphoneStampCommand;
import nts.uk.ctx.at.record.app.command.kdp.kdp010.a.command.SettingsUsingEmbossingCommand;
import nts.uk.ctx.at.record.app.command.kdp.kdp010.a.command.StampSetCommunalCommand;
import nts.uk.ctx.at.record.dom.stamp.application.CommonSettingsStampInput;
import nts.uk.ctx.at.record.dom.stamp.application.CommonSettingsStampInputRepository;
import nts.uk.ctx.at.record.dom.stamp.application.SettingsUsingEmbossingRepository;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.PortalStampSettingsRepository;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.SettingsSmartphoneStamp;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.SettingsSmartphoneStampRepository;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.StampSetCommunalRepository;
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
	
	/*打刻の前準備(ポータル)を登録する*/
	public void savePortalStampSettings(PortalStampSettingsCommand command) {
		portalStampSettingsRepo.save(command.toDomain());
	}
	
	/*打刻の前準備(共有)を登録する*/
	public void saveStampSetCommunal(StampSetCommunalCommand command) {
		stampSetCommunalRepo.save(command.toDomain());
	}
	
	/*打刻の前準備(スマホ)を登録する*/
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
	
	/*打刻の前準備(利用設定)を登録する*/
	public void saveSettingsUsingEmbossing(SettingsUsingEmbossingCommand command) {
		settingsUsingEmbossingRepo.update(command.toDomain());
	}
}
