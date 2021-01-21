package nts.uk.screen.at.ws.shift.workcycle;

import lombok.Data;

/**
 * @author khai.dh
 */
@Data
public class GetStartupInfoParam {
	private int bootMode; //REF_MODE(0), EXEC_MODE(1)
	private String creationPeriodStartDate; // 作成期間 start
	private String creationPeriodEndDate; // 作成期間 end
	private String workCycleCode; // 勤務サイクルコード
	private int[] refOrder; // 反映順序 : WORK_CYCLE(0), WEEKLY_WORK(1), PUB_HOLIDAY(2)
	private int numOfSlideDays; // スライド日数
}


