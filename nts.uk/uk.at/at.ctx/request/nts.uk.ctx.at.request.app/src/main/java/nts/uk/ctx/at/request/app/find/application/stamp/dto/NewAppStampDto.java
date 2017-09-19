package nts.uk.ctx.at.request.app.find.application.stamp.dto;

import java.util.List;

import lombok.Data;
import nts.uk.ctx.at.request.app.find.setting.applicationreason.ApplicationReasonDto;
import nts.uk.ctx.at.request.app.find.setting.stamp.dto.StampRequestSettingDto;

@Data
public class NewAppStampDto {
	private StampRequestSettingDto stampRequestSettingDto;
	private List<ApplicationReasonDto> applicationReasonDtos;
}
