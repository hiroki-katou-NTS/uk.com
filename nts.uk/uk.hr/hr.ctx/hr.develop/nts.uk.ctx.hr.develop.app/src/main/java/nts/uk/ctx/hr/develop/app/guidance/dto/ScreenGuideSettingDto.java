package nts.uk.ctx.hr.develop.app.guidance.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.hr.develop.dom.guidance.IGuidanceExportDto;

@Data
@Getter
@Setter
@AllArgsConstructor
public class ScreenGuideSettingDto {

	private Boolean isUsed;
	
	private Integer lineCount;

	private String content;

	public static ScreenGuideSettingDto fromDomain(IGuidanceExportDto domain) {

		return new ScreenGuideSettingDto(domain.isDisplayMessage(), domain.getNumberLinesInMessage(), domain.getContentMessage());

	}
}
