package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.createdailyoneday.reflectcalcategory;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.ChangeCalArt;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.calcategory.CalAttrOfDailyAttd;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.editstate.EditStateOfDailyAttd;

/**
 * 計算区分に反映する
 * @author tutk
 *
 */
@Stateless
public class ReflectCalCategory {
	@Inject
	private ReflectStampCalCategory reflectStampCalCategory;
	
	public void reflect(ChangeCalArt changeCalArt,CalAttrOfDailyAttd calAttr,List<EditStateOfDailyAttd> editState) {
		//打刻.計算区分変更対象を確認する
		switch (changeCalArt) {
		case EARLY_APPEARANCE:// 早出
			reflectStampCalCategory.reflectStamp(calAttr.getOvertimeSetting().getEarlyOtTime(), editState, 627);
			reflectStampCalCategory.reflectStamp(calAttr.getOvertimeSetting().getEarlyMidOtTime(), editState, 628);
			break;
		case FIX:// ﾌﾚｯｸｽ
			reflectStampCalCategory.reflectStamp(calAttr.getFlexExcessTime().getFlexOtTime(), editState, 635);
			break;
		case BRARK: // 休出
			reflectStampCalCategory.reflectStamp(calAttr.getHolidayTimeSetting().getRestTime(), editState, 633);
			reflectStampCalCategory.reflectStamp(calAttr.getHolidayTimeSetting().getLateNightTime(), editState, 634);
			break;
		case OVER_TIME: // 残業
			reflectStampCalCategory.reflectStamp(calAttr.getOvertimeSetting().getNormalOtTime(), editState, 629);
			reflectStampCalCategory.reflectStamp(calAttr.getOvertimeSetting().getNormalMidOtTime(), editState, 630);
			break;
		default:
			break;
		
		}
	}

}
