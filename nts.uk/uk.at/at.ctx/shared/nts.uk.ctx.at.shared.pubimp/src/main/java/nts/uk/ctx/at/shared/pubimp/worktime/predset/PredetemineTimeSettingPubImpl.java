package nts.uk.ctx.at.shared.pubimp.worktime.predset;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.predset.TimezoneUse;
import nts.uk.ctx.at.shared.dom.worktime.predset.UseSetting;
import nts.uk.ctx.at.shared.pub.worktime.predset.PredetemineTimeSettingPub;

/**
 * The Class PredetemineTimeSettingPubImpl.
 */
@Stateless
public class PredetemineTimeSettingPubImpl implements PredetemineTimeSettingPub{
	
	/** The work two. */
	public static Integer WORK_TWO = 2;
	
	/** The predetemine time setting repository. */
	@Inject
	private PredetemineTimeSettingRepository predetemineTimeSettingRepository;

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.pub.worktime.predset.PredetemineTimeSettingPub#IsWorkingTwice(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean IsWorkingTwice(String companyId, String workTimeCode) {
		// ドメインモデル「所定時間設定」を取得
		Optional<PredetemineTimeSetting> optPredetemineTimeSetting = this.predetemineTimeSettingRepository.findByWorkTimeCode(companyId, workTimeCode);
		
		// 取得できなかった場合
		if (!optPredetemineTimeSetting.isPresent()) {
			return false;
		}
		
		// 所定時間帯．時間帯を取得
		TimezoneUse abc =  optPredetemineTimeSetting.get().getPrescribedTimezoneSetting().getMatchWorkNoTimeSheet(WORK_TWO);
		
		// 使用区分を判断
		if (abc.getUseAtr().value == UseSetting.USE.value) {
			return true;
		}else {
			return false;
		}
	}

}
