package nts.uk.file.at.app.export.dailyschedule;

import lombok.Data;

/**
 * UKDesign.UniversalK.就業.KWR_帳表.KWR001_日別勤務表(daily work schedule).ユーザ固有情報(User specific information).勤務条件指定
 * @author phith
 *
 */
@Data
public class WorkConditionSpecification {
	// 複数回勤務
	private boolean workMultipleTimes;
	// 臨時勤務
	private boolean temporaryService;
	// 特定日
	private boolean specificDay;
}
