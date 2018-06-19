package nts.uk.file.at.app.export.dailyschedule;

import java.util.List;

import lombok.Data;

/**
 * 個人別日別勤務表
 * @author HoangNDH
 *
 */
@Data
public class IndividualDailyWorkSchedule {
	// 社員ID
	private String employeeId;
	// 年月日
	private int date;
	// 備考内容
	private List<Integer> remarkContent;
	// 実績値
	private List<ActualValue> actualValue;
}
