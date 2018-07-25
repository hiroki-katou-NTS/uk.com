package nts.uk.file.at.app.export.dailyschedule.totalsum;

import java.util.List;

import lombok.Data;

/**
 * 合計行の合算値
 * @author HoangNDH
 *
 */
@Data
public class TotalSumRowOfDailySchedule {
	// 日数計
	private List<PersonalTotal> personalTotal;
	// 総合計
	private List<TotalCountDay> dayCount;
	// 個人計
	private List<WorkplaceTotal> workplaceTotal;
	// 職場計
	private List<TotalValue> grossTotal;
}
