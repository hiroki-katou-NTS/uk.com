package nts.uk.ctx.at.record.infra.repository.stampmanagement.timestampsetting.prefortimestaminput;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.ButtonSettings;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.CorrectionInterval;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.DisplaySettingsStampScreen;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.PortalStampSettings;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.PortalStampSettingsRepository;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.ResultDisplayTime;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.SettingDateTimeColorOfStampScreen;
import nts.uk.ctx.at.record.infra.entity.workrecord.stampmanagement.stamp.timestampsetting.prefortimestaminput.KrcmtSrampPortal;
import nts.uk.ctx.at.shared.dom.common.color.ColorCode;

/**
 * 
 * @author chungnt	
 *
 */

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class JpaPortalStampSettingsRepository extends JpaRepository implements PortalStampSettingsRepository {

	/**
	 *  [1]  insert(ポータルの打刻設定)
	 */
	@Override
	public void insert(PortalStampSettings domain) {
		this.commandProxy().insert(toEntity(domain));
	}
	
	/**
	 * 	[2]  update(ポータルの打刻設定)
	 */
	@Override
	public void update(PortalStampSettings domain) {
		Optional<KrcmtSrampPortal> entityOpt = this.queryProxy().find(domain.getCid(), KrcmtSrampPortal.class);
		
		if (!entityOpt.isPresent()) {
			return;
		}
		
		KrcmtSrampPortal entity = entityOpt.get();
		entity.update(domain);
		this.commandProxy().update(entity);
	}

	/**
	 * 	[3]  取得する
	 */
	@Override
	public Optional<PortalStampSettings> get(String comppanyID) {
		Optional<KrcmtSrampPortal> entityOpt = this.queryProxy().find(comppanyID, KrcmtSrampPortal.class);
		
		if (!entityOpt.isPresent()) {
			return Optional.empty();
		}
		
//		List<ButtonSettings> buttonSettings = 
		//TODO: Chungnt
		return null;
	}
	
	public KrcmtSrampPortal toEntity(PortalStampSettings domain) {
		
		KrcmtSrampPortal entity = new KrcmtSrampPortal();
		entity.cid = domain.getCid();
		entity.correctionInterval = domain.getDisplaySettingsStampScreen().getServerCorrectionInterval().v();
		entity.resultDispayTime = domain.getDisplaySettingsStampScreen().getResultDisplayTime().v();
		entity.textColor = domain.getDisplaySettingsStampScreen().getSettingDateTimeColor().getTextColor().v();
		entity.backGroundColor = domain.getDisplaySettingsStampScreen().getSettingDateTimeColor().getBackgroundColor().v();
		entity.buttonEmphasisArt = domain.getSuppressStampBtn() ? 1 : 0;
		entity.toppageLinkArt = domain.getUseTopMenuLink() ? 1 : 0;
												
		return entity;
	}
	
	public PortalStampSettings toDomain(KrcmtSrampPortal entity, List<ButtonSettings> buttonSettings) {
		
		DisplaySettingsStampScreen displaySettingsStampScreen = new DisplaySettingsStampScreen(new CorrectionInterval(entity.correctionInterval),
				new SettingDateTimeColorOfStampScreen(new ColorCode(entity.textColor), new ColorCode(entity.backGroundColor)),
				new ResultDisplayTime(entity.resultDispayTime));
		
		PortalStampSettings settings = new PortalStampSettings(entity.cid, displaySettingsStampScreen, buttonSettings, entity.buttonEmphasisArt == 1, entity.toppageLinkArt ==1);
		return settings;
	}
}
