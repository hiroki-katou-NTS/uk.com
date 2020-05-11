package nts.uk.ctx.at.record.infra.repository.stampmanagement.timestampsetting.prefortimestaminput;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.CorrectionInterval;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.DisplaySettingsStampScreen;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.ResultDisplayTime;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.SettingDateTimeColorOfStampScreen;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.TimeStampSetShareTStamp;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.TimeStampSetShareTStampRepository;
import nts.uk.ctx.at.record.infra.entity.workrecord.stampmanagement.stamp.timestampsetting.prefortimestaminput.KrcmtStampCommunal;
import nts.uk.ctx.at.shared.dom.common.color.ColorCode;

/**
 * 共有打刻の打刻設定Repository
 * @author chungnt
 *
 */

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class JpaTimeStampSetShareTStampRepository extends JpaRepository implements TimeStampSetShareTStampRepository  {

	@Override
	public void insert(TimeStampSetShareTStamp domain) {
		this.commandProxy().insert(toEntity(domain));
	}

	@Override
	public void update(TimeStampSetShareTStamp domain) {
		
		Optional<KrcmtStampCommunal> entityOpt = this.queryProxy().find(domain.getCid(),
				KrcmtStampCommunal.class);
		if (!entityOpt.isPresent()) {
			return;
		}
		KrcmtStampCommunal entity =  entityOpt.get();
		entity.update(domain);
		this.commandProxy().update(entity);
	}

	@Override
	public Optional<TimeStampSetShareTStamp> get(String comppanyID) {
		return null;
	}
	
	public KrcmtStampCommunal toEntity(TimeStampSetShareTStamp domain) {
		KrcmtStampCommunal entity = new KrcmtStampCommunal();
		entity.cid = domain.getCid();
		entity.correctionInterval = domain.getDisplaySetStampScreen().getServerCorrectionInterval().v();
		entity.resultDispayTime = domain.getDisplaySetStampScreen().getResultDisplayTime().v();
		entity.textColor = domain.getDisplaySetStampScreen().getSettingDateTimeColor().getTextColor().v();
		entity.backGroundColor = domain.getDisplaySetStampScreen().getSettingDateTimeColor().getBackgroundColor().v();
		entity.nameSelectArt = domain.getUseSelectName();
		entity.passwordRequiredArt = domain.getPasswordInputReq();
//		entity.employeeAuthcUseArt = domain.getUseEmpCodeToAuthen() == 1;
		entity.authcFailCnt = domain.getNumberAuthenfailures().get().v();
		return entity;
	}
	
	public TimeStampSetShareTStamp toDomain(KrcmtStampCommunal entity) {
		
		DisplaySettingsStampScreen displaySetStampScreen = new DisplaySettingsStampScreen(new CorrectionInterval(entity.correctionInterval),
				new SettingDateTimeColorOfStampScreen(new ColorCode(entity.textColor), new ColorCode(entity.backGroundColor)),
				new ResultDisplayTime(entity.resultDispayTime));
				
//		return new TimeStampSetShareTStamp(entity.cid,
//				displaySetStampScreen,
//				lstStampPageLayout,
//				entity.nameSelectArt,
//				entity.passwordRequiredArt,
//				entity.employeeAuthcUseArt,
//				entity.authcFailCnt);
		return null;
	}

}
