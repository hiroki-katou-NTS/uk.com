package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.createdailyoneday;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.dailyperformanceprocessing.ErrMessageResource;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.CalculationState;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.NotUseAttribute;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.WorkInfoOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.workingcondition.SingleDaySchedule;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemRepository;
import nts.uk.ctx.at.shared.dom.workrecord.workperfor.dailymonthlyprocessing.ErrMessageContent;
import nts.uk.ctx.at.shared.dom.workrecord.workperfor.dailymonthlyprocessing.ErrorMessageInfo;
import nts.uk.ctx.at.shared.dom.workrecord.workperfor.dailymonthlyprocessing.enums.ExecutionContent;
import nts.uk.shr.com.i18n.TextResource;

/**
 * 個人情報から勤務種類と就業時間帯を写す
 * @author tutk
 *
 */
@Stateless
public class CopyWorkTypeWorkTime {
	
	@Inject
	private WorkingConditionItemRepository workingConditionItemRepository;

	public List<ErrorMessageInfo> copyWorkTypeWorkTime(String companyId, String employeeId, GeneralDate ymd,
			WorkInfoOfDailyAttendance workInformation) {
		List<ErrorMessageInfo> listErrorMessageInfo = new ArrayList<>();
		//個人情報の休日の勤務種類を取得する
		// ドメインモデル「労働条件項目」を取得する
		Optional<WorkingConditionItem> optWorkingConditionItem = this.workingConditionItemRepository
				.getBySidAndStandardDate(employeeId, ymd);
		if (!optWorkingConditionItem.isPresent() || optWorkingConditionItem.get().getWorkCategory().getHolidayTime() == null
				|| !optWorkingConditionItem.get().getWorkCategory().getHolidayTime().getWorkTypeCode().isPresent()) {
			listErrorMessageInfo.add(new ErrorMessageInfo(companyId, employeeId, ymd, ExecutionContent.DAILY_CREATION,
					new ErrMessageResource("012"), new ErrMessageContent(TextResource.localize("Msg_430"))));
			return listErrorMessageInfo;
		}
		
		WorkInformation recordInfo = optWorkingConditionItem.map(opt -> {
			SingleDaySchedule sched = opt.getWorkCategory().getHolidayTime();
			
			return new WorkInformation(sched.getWorkTypeCode().orElse(null), sched.getWorkTimeCode().orElse(null));
		}).orElse(null);
		
		//休日の勤務種類を勤務予定に写す
		workInformation.setScheduleInfo(recordInfo);
		//休日の勤務種類を勤務実績に写す
		workInformation.setRecordInfo(recordInfo);
		//set default 
		//set 計算状態
		workInformation.setCalculationState(CalculationState.No_Calculated);
		//set 直行区分
		workInformation.setGoStraightAtr(NotUseAttribute.Not_use);
		//set 直帰区分
		workInformation.setBackStraightAtr(NotUseAttribute.Not_use);
		
		return listErrorMessageInfo;
	}
}
