package nts.uk.ctx.at.request.app.find.application.stamp.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.app.find.setting.applicationreason.ApplicationReasonDto;
import nts.uk.ctx.at.request.app.find.setting.company.request.stamp.dto.StampRequestSettingDto;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@NoArgsConstructor
public class AppStampSetDto {
	public StampRequestSettingDto stampRequestSettingDto;
	public List<ApplicationReasonDto> applicationReasonDtos;
}
