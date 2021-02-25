package nts.uk.screen.at.app.ksu003.start.dto;

import java.util.List;

import lombok.Setter;
import lombok.Value;
import nts.uk.ctx.at.record.app.find.dailyperform.dto.TimeSpanForCalcDto;

/**
 * 
 * @author phongtq
 *
 */

@Value
@Setter
public class FixedWorkInformationDto {
	
	// List<勤務固定情報　dto>
	public List<FixedWorkInforDto> fixedWorkInforDto;
	
	// List<休憩時間帯>
	public List<TimeSpanForCalcDto> listBreakTimeZoneDto;

	public FixedWorkInformationDto(List<FixedWorkInforDto> fixedWorkInforDto,
			List<TimeSpanForCalcDto> listBreakTimeZoneDto) {
		super();
		this.fixedWorkInforDto = fixedWorkInforDto;
		this.listBreakTimeZoneDto = listBreakTimeZoneDto;
	}
}
