package nts.uk.query.app.ccg005.query.work.information.work.schedule.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AffiliationInforOfDailyAttdDto {
	// 雇用コード
	private String employmentCode;
	// 職位ID
	private String jobTitleID;
	// 職場ID
	private String wplID;
	// 分類コード
	private String clsCode;
	// 勤務種別コード
	private String businessTypeCode;
	// 加給コード
	private String bonusPaySettingCode;
}
