package nts.uk.ctx.at.record.infra.repository.stampmanagement.timestampsetting.prefortimestaminput;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.breakorgoout.enums.GoingOutReason;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.AudioType;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.ButtonDisSet;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.ButtonName;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.ButtonNameSet;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.ButtonPositionNo;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.ButtonSettings;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.ButtonType;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.ChangeCalArt;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.ChangeClockArt;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.CorrectionInterval;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.DisplaySettingsStampScreen;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.PortalStampSettings;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.PortalStampSettingsRepository;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.ReservationArt;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.ResultDisplayTime;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.SetPreClockArt;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.SettingDateTimeColorOfStampScreen;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.StampType;
import nts.uk.ctx.at.record.infra.entity.workrecord.stampmanagement.stamp.timestampsetting.prefortimestaminput.KrcmtSrampPortal;
import nts.uk.ctx.at.record.infra.entity.workrecord.stampmanagement.stamp.timestampsetting.prefortimestaminput.KrcmtStampLayoutDetail;
import nts.uk.ctx.at.shared.dom.common.color.ColorCode;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * 
 * @author chungnt	
 *
 */

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class JpaPortalStampSettingsRepository extends JpaRepository implements PortalStampSettingsRepository {

	private static final String SELECT_STAMP_LAYOUT_DETAIL = "SELECT r FROM KrcmtStampLayoutDetail r WHERE r.pk.companyId = :companyId";
	
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
		
		List<ButtonSettings> buttonSettings = this.queryProxy().query(SELECT_STAMP_LAYOUT_DETAIL,KrcmtStampLayoutDetail.class).setParameter("companyId", comppanyID).getList()
				
				.stream().map(m -> new ButtonSettings(new ButtonPositionNo(m.pk.buttonPositionNo),
				new ButtonDisSet(new ButtonNameSet(new ColorCode(m.textColor), new ButtonName(m.buttonName)), new ColorCode(m.backGroundColor)),
				new ButtonType(EnumAdaptor.valueOf(m.reservationArt, ReservationArt.class),
						Optional.of(new StampType( m.changeHalfDay ==  0 ? false : true , Optional.of(EnumAdaptor.valueOf(m.goOutArt, GoingOutReason.class)),
								EnumAdaptor.valueOf(m.setPreClockArt, SetPreClockArt.class),
								EnumAdaptor.valueOf(m.changeClockArt, ChangeClockArt.class),
								EnumAdaptor.valueOf(m.changeCalArt, ChangeCalArt.class)))),
				
				EnumAdaptor.valueOf(m.useArt, NotUseAtr.class),
				EnumAdaptor.valueOf(m.aidioType, AudioType.class)))
				
				.collect(Collectors.toList());
		
		PortalStampSettings domain = this.toDomain(entityOpt.get(), buttonSettings);
			
		return Optional.of(domain);
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
