package nts.uk.ctx.at.request.app.find.application.overtime.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.WorkInformation;

@AllArgsConstructor
@NoArgsConstructor
public class WorkInformationDto {
	// 勤務種類コード
	public String workType;
	// 就業時間帯コード
	public String workTime;
	
	public static WorkInformationDto fromDomain(WorkInformation value) {
		if (value == null) return null;
		return new WorkInformationDto(
				value.getWorkTypeCode().v(),
				value.getWorkTimeCode() != null ? value.getWorkTimeCode().v() : null);
	}
}
