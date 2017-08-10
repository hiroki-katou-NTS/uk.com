package nts.uk.ctx.at.shared.dom.schedule.basicschedule;

import javax.ejb.Stateless;

/**
 * 
 * @author sonnh1
 *
 */
@Stateless
public class DefaultBasicScheduleService implements BasicScheduleService {

	@Override
	public SetupType checkNeededOfWorkTimeSetting(String workTypeCd) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SetupType checkRequiredOfInputType(DayType dayType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WorkStyle checkWorkDay(String workTypeCd) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SetupType checkRequired(SetupType morningWorkStyle, SetupType afternoonWorkStyle) {
		// TODO Auto-generated method stub
		return null;
	}

}
