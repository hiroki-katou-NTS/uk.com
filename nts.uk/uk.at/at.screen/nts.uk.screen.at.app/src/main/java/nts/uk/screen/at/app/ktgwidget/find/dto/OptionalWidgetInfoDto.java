package nts.uk.screen.at.app.ktgwidget.find.dto;

import lombok.Value;
import nts.arc.time.GeneralDate;

@Value
public class OptionalWidgetInfoDto {
	
	/** 1 OverTime Work Number 残業指示件数 */
	private int overTime = 0;
	
	/** 2 Instructions Holiday Number 休出指示件数 */
	private int holidayInstruction = 0;
	
	/** 3 Approved Number 承認された件数 */
	private int approved = 0;
	
	/** 4 Approved Number 承認された件数 */
	private int unApproved = 0;
	
	/** 5 Denied Number 否認された件数 */
	private int denied = 0;

	/** 6 Remand Number 差し戻し件数 */
	private int remand = 0;
	
	/** 7 App Deadline Month 今月の申請締め切り日 */
	private DeadlineOfRequestDto deadlineOfRequest = new DeadlineOfRequestDto(0, GeneralDate.today());
	
	/** 8 Presence Daily Per 日別実績のエラー有無 */
	private boolean presenceDailyPer = false;
	
	/** 10 Overtime Hours 残業時間 */
	private TimeOT timeOT = new TimeOT(0, 0);
	
	/** 11 Overtime Hours 残業時間 */
	private TimeOT flexTime = new TimeOT(0, 0);
	
	/** 12 Overtime Hours 残業時間 */
	private TimeOT restTime = new TimeOT(0, 0);
	
	/** 13 Overtime Hours 残業時間 */
	private TimeOT nightWorktime = new TimeOT(0, 0);
	
	/** 14 Overtime Hours 残業時間 */
	private int lateOrEarlyRetreat = 0;
	
	/** 15 Yearly Holiday 年休残数 */
	private int yearlyHoliday = 0;
	
	/** 16 Reserved Years Remain Number 積立年休残数 */
	private int reservedYearsRemainNo = 0;
	
	/** 18 Remain Alternation Number 代休残数 */
	private int remainAlternationNo = 0;
	
	/** 19 RemainsLeft 振休残数 */
	private int remainsLeft = 0;
	
	/** 21 Holiday Remain Number 子の看護休暇残数 */
	private int hDRemainNo = 0;
	
	/** 22 Care Leave Number 介護休暇残数 */
	private int careLeaveNo = 0;
	
	/** 23 Special Holiday Remain Number 特休残数 */
	private int sPHDRamainNo = 0;

}
