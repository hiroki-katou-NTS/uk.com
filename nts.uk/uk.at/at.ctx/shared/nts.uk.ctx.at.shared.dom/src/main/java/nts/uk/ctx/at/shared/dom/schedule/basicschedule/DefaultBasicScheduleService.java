package nts.uk.ctx.at.shared.dom.schedule.basicschedule;

import javax.ejb.Stateless;

import nts.arc.error.BusinessException;
import nts.gul.text.StringUtil;

/**
 * 
 * @author sonnh1
 *
 */
@Stateless
public class DefaultBasicScheduleService implements BasicScheduleService {
//	
//	@Inject
//	public 

	@Override
	public SetupType checkNeededOfWorkTimeSetting(String workTypeCd) {
		// TODO Auto-generated method stub
		return SetupType.REQUIRED;
	}

	@Override
	public SetupType checkRequiredOfInputType(DayType dayType) {
		// TODO Auto-generated method stub
		return SetupType.REQUIRED;
	}

	@Override
	public WorkStyle checkWorkDay(String workTypeCd) {
		// TODO Auto-generated method stub
		return WorkStyle.ONE_DAY_WORK;
	}

	@Override
	public SetupType checkRequired(SetupType morningWorkStyle, SetupType afternoonWorkStyle) {

		if (morningWorkStyle == SetupType.REQUIRED) {
			return SetupType.REQUIRED;
		}
		
		if (morningWorkStyle == SetupType.NOT_REQUIRED) {
			return afternoonWorkStyle;
		}

		if (afternoonWorkStyle == SetupType.REQUIRED) {
			return SetupType.REQUIRED;
		}

		if (afternoonWorkStyle == SetupType.NOT_REQUIRED || afternoonWorkStyle == SetupType.OPTIONAL) {
			return SetupType.OPTIONAL;
		}

		throw new RuntimeException("ERROR!!");
	}

	@Override
	public void checkPairWorkTypeWorkTime(String workTypeCd, String workTimeCd) {
		SetupType setupType = this.checkNeededOfWorkTimeSetting(workTypeCd);
		if (setupType == SetupType.REQUIRED && StringUtil.isNullOrEmpty(workTimeCd, true)) {
			throw new BusinessException("Msg_435");
		}
		
		if (setupType == SetupType.NOT_REQUIRED && !StringUtil.isNullOrEmpty(workTimeCd, true)) {
			throw new BusinessException("Msg_434");
		}
	}

}
