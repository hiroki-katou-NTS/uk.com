package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.createdailyoneday;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.workinfomation.WorkInfoOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.dailyperformanceprocessing.ErrMessageResource;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemRepository;
import nts.uk.ctx.at.shared.dom.workrecord.workperfor.dailymonthlyprocessing.ErrMessageContent;
import nts.uk.ctx.at.shared.dom.workrecord.workperfor.dailymonthlyprocessing.ErrorMessageInfo;
import nts.uk.ctx.at.shared.dom.workrecord.workperfor.dailymonthlyprocessing.enums.ExecutionContent;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
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
		if (!optWorkingConditionItem.isPresent()
				|| !optWorkingConditionItem.get().getWorkCategory().getHolidayWork().getWorkTypeCode().isPresent()) {
			listErrorMessageInfo.add(new ErrorMessageInfo(companyId, employeeId, ymd, ExecutionContent.DAILY_CREATION,
					new ErrMessageResource("012"), new ErrMessageContent(TextResource.localize("Msg_430"))));
			return listErrorMessageInfo;
		}
		WorkTypeCode workTypeCode = optWorkingConditionItem.get().getWorkCategory().getHolidayWork().getWorkTypeCode()
				.get();
		WorkTimeCode workTimeCode = optWorkingConditionItem.get().getWorkCategory().getHolidayWork().getWorkTimeCode()
				.isPresent() ? optWorkingConditionItem.get().getWorkCategory().getHolidayWork().getWorkTimeCode().get()
						: null;
		//休日の勤務種類を勤務予定に写す
		workInformation.getScheduleInfo().setWorkTypeCode(workTypeCode);
		workInformation.getScheduleInfo().setWorkTimeCode(workTimeCode);
		//休日の勤務種類を勤務実績に写す
		workInformation.getRecordInfo().setWorkTypeCode(workTypeCode);
		workInformation.getRecordInfo().setWorkTimeCode(workTimeCode);
		
		return listErrorMessageInfo;
	}
}
