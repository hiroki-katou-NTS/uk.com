package nts.uk.ctx.at.record.dom.remainingnumber.specialleave.empinfo.grantremainingdata;

import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.export.NextAnnualLeaveGrant;

/**
 * 消滅情報WORK
 * @author masaaki_jinno
 *
 */
@Getter
@Setter
public class SpecialLeaveLapsedWork {

	/** 期間の開始日に消滅するかどうか */
	private boolean lapsedAtr;
	
}
