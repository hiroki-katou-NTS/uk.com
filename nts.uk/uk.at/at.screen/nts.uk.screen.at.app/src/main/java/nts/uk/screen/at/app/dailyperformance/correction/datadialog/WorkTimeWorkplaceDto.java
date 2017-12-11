package nts.uk.screen.at.app.dailyperformance.correction.datadialog;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WorkTimeWorkplaceDto {
	/**
	 * 会社ID
	 */
	private String companyID;
	/**
	 * 職場ID
	 */
	private String workplaceID;
	/**
	 * 利用就業時間帯
	 */
	private String workTimeID;

	public static WorkTimeWorkplaceDto createFromJavaType(String companyID, String workplaceID, String workTimeID) {
		return new WorkTimeWorkplaceDto(companyID, workplaceID, workTimeID);
	}
}
