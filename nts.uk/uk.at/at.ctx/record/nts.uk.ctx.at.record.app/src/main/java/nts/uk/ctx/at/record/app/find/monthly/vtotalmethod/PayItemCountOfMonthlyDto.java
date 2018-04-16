package nts.uk.ctx.at.record.app.find.monthly.vtotalmethod;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author HoangNDH
 *
 */
@Getter
@Setter
public class PayItemCountOfMonthlyDto {
	/** 給与出勤日数 */
	private List<String> payAttendanceDays;
	/** 給与欠勤日数 */
	private List<String> payAbsenceDays;
}
