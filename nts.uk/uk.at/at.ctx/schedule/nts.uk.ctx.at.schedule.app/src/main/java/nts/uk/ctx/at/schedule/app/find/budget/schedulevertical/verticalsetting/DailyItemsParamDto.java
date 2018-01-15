package nts.uk.ctx.at.schedule.app.find.budget.schedulevertical.verticalsetting;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * TanLV
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class DailyItemsParamDto {
	private List<Integer> dailyAttendanceItemAtrs;
	private int scheduleAtr;
	private int budgetAtr;
	private int unitAtr;
}
