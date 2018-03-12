package nts.uk.ctx.at.schedule.dom.schedule.commonalgorithm;

import lombok.Data;

@Data
public class ScheduleMasterInformationDto {
	//・雇用コード
	private String employeeCode;
	//・分類コード
	private String classificationCode;
	//・職場ID
	private String workplaceId;
	//・職位ID
	private String jobId;
	//・勤務種別コード
	private String businessTypeCode;
}
