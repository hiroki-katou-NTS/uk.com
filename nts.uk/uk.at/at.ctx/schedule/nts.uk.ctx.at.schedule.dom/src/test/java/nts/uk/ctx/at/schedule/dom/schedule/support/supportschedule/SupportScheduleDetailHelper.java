package nts.uk.ctx.at.schedule.dom.schedule.support.supportschedule;

import java.util.Optional;

import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.supportmanagement.SupportType;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.shr.com.time.TimeWithDayAttr;

public class SupportScheduleDetailHelper {

	/**
	 * 時間帯で応援予定詳細を作る
	 * @param supportDestination 応援先
	 * @return
	 */
	public static SupportScheduleDetail createSupportScheduleDetailByTimeZone(
			TargetOrgIdenInfor supportDestination) {
		
		return new SupportScheduleDetail(	
					supportDestination
				,	SupportType.TIMEZONE
				,	Optional.of(	
						new TimeSpanForCalc(	TimeWithDayAttr.hourMinute(9, 0)
											,	TimeWithDayAttr.hourMinute(12, 0))
						)
					);
	}
	
}
