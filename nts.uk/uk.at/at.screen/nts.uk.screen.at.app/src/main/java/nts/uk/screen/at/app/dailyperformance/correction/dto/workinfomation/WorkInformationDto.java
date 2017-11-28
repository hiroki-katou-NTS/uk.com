package nts.uk.screen.at.app.dailyperformance.correction.dto.workinfomation;

import lombok.Data;

@Data
public class WorkInformationDto {
	private String workTimeCode;

	private String workTypeCode;

	public WorkInformationDto(String workTimeCode, String workTypeCode) {
		this.workTimeCode = workTimeCode;
		this.workTypeCode = workTypeCode;
	}
}
