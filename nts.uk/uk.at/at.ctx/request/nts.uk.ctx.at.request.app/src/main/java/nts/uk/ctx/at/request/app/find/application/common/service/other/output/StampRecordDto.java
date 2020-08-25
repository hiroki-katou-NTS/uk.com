package nts.uk.ctx.at.request.app.find.application.common.service.other.output;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.StampRecordOutput;

/**
 * refactor 4
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class StampRecordDto {
	/**
	 * 介護時間帯
	 */
	private List<TimePlaceDto> nursingTime;
	
	/**
	 * 休憩時間帯
	 */
	private List<TimePlaceDto> breakTime;
	
	/**
	 * 勤務時間帯
	 */
	private List<TimePlaceDto> workingTime;
	
	/**
	 * 外出時間帯
	 */
	private List<TimePlaceDto> outingTime;
	
	/**
	 * 応援時間帯
	 */
	private List<TimePlaceDto> supportTime;
	
	/**
	 * 育児時間帯
	 */
	private List<TimePlaceDto> parentingTime;
	
	/**
	 * 臨時時間帯
	 */
	private List<TimePlaceDto> extraordinaryTime;
	
	public static StampRecordDto fromDomain(StampRecordOutput stampRecordOutput) {
		return new StampRecordDto(
				stampRecordOutput.getNursingTime().stream().map(x -> TimePlaceDto.fromDomain(x)).collect(Collectors.toList()), 
				stampRecordOutput.getBreakTime().stream().map(x -> TimePlaceDto.fromDomain(x)).collect(Collectors.toList()), 
				stampRecordOutput.getWorkingTime().stream().map(x -> TimePlaceDto.fromDomain(x)).collect(Collectors.toList()), 
				stampRecordOutput.getOutingTime().stream().map(x -> TimePlaceDto.fromDomain(x)).collect(Collectors.toList()), 
				stampRecordOutput.getSupportTime().stream().map(x -> TimePlaceDto.fromDomain(x)).collect(Collectors.toList()), 
				stampRecordOutput.getParentingTime().stream().map(x -> TimePlaceDto.fromDomain(x)).collect(Collectors.toList()), 
				stampRecordOutput.getExtraordinaryTime().stream().map(x -> TimePlaceDto.fromDomain(x)).collect(Collectors.toList()));
	}
}
