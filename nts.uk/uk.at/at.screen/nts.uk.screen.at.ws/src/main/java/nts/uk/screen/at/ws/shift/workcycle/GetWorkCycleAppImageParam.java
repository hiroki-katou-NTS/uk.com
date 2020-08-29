package nts.uk.screen.at.ws.shift.workcycle;

import lombok.Data;

/**
 * @author khai.dh
 */
@Data
public class GetWorkCycleAppImageParam {
	private String creationPeriodStartDate; // 作成期間 start
	private String creationPeriodEndDate; // 作成期間 end
	private String workCycleCode; // 勤務サイクルコード
	private int[] refOrder; // 反映順序 : WORK_CYCLE(0), WEEKLY_WORK(1), PUB_HOLIDAY(2)
	private int numOfSlideDays; // スライド日数
	private String legalHolidayCd; // 祝日の勤務種類
	private String nonStatutoryHolidayCd; // 法定外休日の勤務種類
	private String holidayCd; // 法定休日の勤務種類
}


