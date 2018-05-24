package nts.uk.file.at.app.export.dailyschedule;

import lombok.Data;

@Data
public class WorkScheduleSettingTotalOutputDto {
	// 明細
	private boolean details;
	// 個人計
	private boolean personalTotal;
	// 職場計
	private boolean workplaceTotal;
	// 日数計
	private boolean totalNumberDay;
	// 総合計
	private boolean grossTotal;
	// 職場累計
	private boolean cumulativeWorkplace;
	// 職場階層累計
	private TotalWorkplaceHierachyDto workplaceHierachyTotalDto;
}
