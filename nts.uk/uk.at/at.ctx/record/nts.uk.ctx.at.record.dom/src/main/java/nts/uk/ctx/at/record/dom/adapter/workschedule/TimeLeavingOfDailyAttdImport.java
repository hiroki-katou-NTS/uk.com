package nts.uk.ctx.at.record.dom.adapter.workschedule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.dom.objecttype.DomainObject;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.temporarytime.WorkNo;
import nts.uk.ctx.at.shared.dom.worktime.TimeLeaveChangeEvent;
import nts.uk.ctx.at.shared.dom.worktime.common.JustCorrectionAtr;

/**
 * 
 * @author tutk
 *
 */
@Getter
@Setter
@NoArgsConstructor
public class TimeLeavingOfDailyAttdImport implements DomainObject{
	// 1 ~ 2
	// 出退勤
	private List<TimeLeavingWorkImport> timeLeavingWorks;
	// 勤務回数  WorkTimes (1-5)
	private int workTimes;
	public TimeLeavingOfDailyAttdImport(List<TimeLeavingWorkImport> timeLeavingWorks, int workTimes) {
		super();
		this.timeLeavingWorks = timeLeavingWorks;
		this.workTimes = workTimes;
	}
	
}
