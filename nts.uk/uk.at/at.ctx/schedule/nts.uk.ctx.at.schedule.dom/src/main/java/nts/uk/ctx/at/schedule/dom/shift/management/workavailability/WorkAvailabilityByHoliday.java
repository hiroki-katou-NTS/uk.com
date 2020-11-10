package nts.uk.ctx.at.schedule.dom.shift.management.workavailability;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import nts.arc.layer.dom.objecttype.DomainValue;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.WorkStyle;

/**
 * 休日の勤務希望
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定.シフト管理.シフト勤務.勤務希望
 * @author dan_pv
 *
 */
public class WorkAvailabilityByHoliday implements WorkAvailability, DomainValue {

	@Override
	public AssignmentMethod getAssignmentMethod() {
		return AssignmentMethod.HOLIDAY;
	}

	@Override
	public boolean isMatchingWorkAvailability(WorkAvailability.Require require, WorkInformation workInformation,
			List<TimeSpanForCalc> timeZoneList) {

		Optional<WorkStyle> workStyle = workInformation.getWorkStyle(require);
		
		if (workStyle.isPresent() && workStyle.get() == WorkStyle.ONE_DAY_REST){
			return true;
		}
		
		return false;
	}

	@Override
	public WorkAvailabilityDisplayInfo getDisplayInformation(WorkAvailability.Require require) {
		AssignmentMethod asignmentMethod = this.getAssignmentMethod();
		return new WorkAvailabilityDisplayInfo(asignmentMethod, Collections.emptyList(), Collections.emptyList());
	}
	
	public static interface Require extends WorkInformation.Require {
		
	}

}
