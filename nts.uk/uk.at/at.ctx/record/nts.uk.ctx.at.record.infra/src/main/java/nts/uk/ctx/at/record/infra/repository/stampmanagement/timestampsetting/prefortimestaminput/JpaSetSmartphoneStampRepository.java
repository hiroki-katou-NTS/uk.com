/**
 * 
 */
package nts.uk.ctx.at.record.infra.repository.stampmanagement.timestampsetting.prefortimestaminput;

import java.util.ArrayList;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.CorrectionInterval;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.DisplaySettingsStampScreen;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.ResultDisplayTime;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.SettingDateTimeColorOfStampScreen;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.SettingsSmartphoneStamp;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.SettingsSmartphoneStampRepository;
import nts.uk.ctx.at.record.infra.entity.workrecord.stampmanagement.stamp.timestampsetting.prefortimestaminput.KrcmtStampSmartPhone;
import nts.uk.ctx.at.shared.dom.common.color.ColorCode;
import nts.uk.shr.com.context.AppContexts;

/**
 * スマホ打刻の打刻設定Repository
 * @author laitv
 *
 */
@Stateless
public class JpaSetSmartphoneStampRepository  extends JpaRepository implements SettingsSmartphoneStampRepository  {

	@Override
	public void insert(SettingsSmartphoneStamp settingsSmartphoneStamp) {
		KrcmtStampSmartPhone  entity =  new KrcmtStampSmartPhone(); 
		entity = toEntity(settingsSmartphoneStamp, entity);
		this.commandProxy().insert(entity);
	}

	@Override
	public void update(SettingsSmartphoneStamp settingsSmartphoneStamp) {
		
		Optional<KrcmtStampSmartPhone> entityOpt = this.queryProxy().find(settingsSmartphoneStamp.getCid(), KrcmtStampSmartPhone.class);
		
		if (!entityOpt.isPresent()) {
			return;
		}
		KrcmtStampSmartPhone entity = toEntity(settingsSmartphoneStamp , entityOpt.get());
		
		this.commandProxy().update(entity);
	}

	@Override
	public Optional<SettingsSmartphoneStamp> get(String cid) {
		Optional<KrcmtStampSmartPhone> entityOpt = this.queryProxy().find(cid, KrcmtStampSmartPhone.class);

		if (!entityOpt.isPresent()) {
			return Optional.empty();
		}
		
		SettingsSmartphoneStamp domain = toDomain(entityOpt.get());
		
		return Optional.of(domain);
	}
	
	public KrcmtStampSmartPhone toEntity(SettingsSmartphoneStamp domain, KrcmtStampSmartPhone  entity) {
		entity.cid = domain.getCid();
		entity.contractCode = AppContexts.user().contractCode();
		entity.correctionInteval = domain.getDisplaySettingsStamScreen() == null ? 0
				: (domain.getDisplaySettingsStamScreen().getServerCorrectionInterval() == null ? 0
						: domain.getDisplaySettingsStamScreen().getServerCorrectionInterval().v());
		
		entity.resultDisplayTime =  domain.getDisplaySettingsStamScreen() == null ? 0
				: (domain.getDisplaySettingsStamScreen().getResultDisplayTime() == null ? 0
						: domain.getDisplaySettingsStamScreen().getResultDisplayTime().v());
		
		entity.textColor = domain.getDisplaySettingsStamScreen() == null ? ""
				: (domain.getDisplaySettingsStamScreen().getSettingDateTimeColor() == null ? ""
						: (domain.getDisplaySettingsStamScreen().getSettingDateTimeColor().getTextColor() == null ? ""
								: domain.getDisplaySettingsStamScreen().getSettingDateTimeColor().getTextColor().v()));
		
		entity.backGroundColor = domain.getDisplaySettingsStamScreen() == null ? ""
				: (domain.getDisplaySettingsStamScreen().getSettingDateTimeColor() == null ? ""
						: (domain.getDisplaySettingsStamScreen().getSettingDateTimeColor().getBackgroundColor() == null ? ""
								: domain.getDisplaySettingsStamScreen().getSettingDateTimeColor().getBackgroundColor().v()));
		entity.btnEmphasisArt = domain.getSuppressStampBtn();
		
		return entity;
	}
	
	public SettingsSmartphoneStamp toDomain(KrcmtStampSmartPhone entity) {
		
		DisplaySettingsStampScreen displaySettingsStampScreen = new DisplaySettingsStampScreen
				(new CorrectionInterval(entity.correctionInteval), 
				 new SettingDateTimeColorOfStampScreen(new ColorCode(entity.textColor), new ColorCode(entity.backGroundColor)),
				 new ResultDisplayTime(entity.resultDisplayTime));
		
		SettingsSmartphoneStamp domain = new SettingsSmartphoneStamp(entity.cid, displaySettingsStampScreen, new ArrayList<>(), entity.btnEmphasisArt);
		
		return domain;
	}
	
	

}
