package nts.uk.screen.at.app.ksu003.start.dto;

import java.util.List;

import lombok.Value;

/**
 * 
 * @author phongtq
 *
 */

@Value
public class FixedWorkInformationDto {
	
	// List<勤務固定情報　dto>
	private List<FixedWorkInforDto> fixedWorkInforDto;
	
	// List<休憩時間帯>
	private List<BreakTimeOfDailyAttdDto> listBreakTimeZoneDto;
}
