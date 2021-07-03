package nts.uk.ctx.at.shared.dom.adapter.workschedule;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.dom.objecttype.DomainObject;

/**
 * 
 * @author tutk
 *
 */
@Getter
@Setter
@NoArgsConstructor
public class TimeLeavingOfDailyAttdSharedImport implements DomainObject{
	// 1 ~ 2
	// 出退勤
	private List<TimeLeavingWorkSharedImport> timeLeavingWorks;
	// 勤務回数  WorkTimes (1-5)
	private int workTimes;
	public TimeLeavingOfDailyAttdSharedImport(List<TimeLeavingWorkSharedImport> timeLeavingWorks, int workTimes) {
		super();
		this.timeLeavingWorks = timeLeavingWorks;
		this.workTimes = workTimes;
	}
	
}
