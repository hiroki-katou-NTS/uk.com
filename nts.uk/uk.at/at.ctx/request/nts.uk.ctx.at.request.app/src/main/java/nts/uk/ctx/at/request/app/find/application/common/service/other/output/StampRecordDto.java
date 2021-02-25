package nts.uk.ctx.at.request.app.find.application.common.service.other.output;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.gul.collection.CollectionUtil;
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
				CollectionUtil.isEmpty(stampRecordOutput.getNursingTime()) ? Collections.emptyList() : stampRecordOutput.getNursingTime().stream().map(x -> TimePlaceDto.fromDomain(x)).collect(Collectors.toList()), 
				CollectionUtil.isEmpty(stampRecordOutput.getBreakTime()) ? Collections.emptyList() : stampRecordOutput.getBreakTime().stream().map(x -> TimePlaceDto.fromDomain(x)).collect(Collectors.toList()), 
				CollectionUtil.isEmpty(stampRecordOutput.getWorkingTime()) ? Collections.emptyList() : stampRecordOutput.getWorkingTime().stream().map(x -> TimePlaceDto.fromDomain(x)).collect(Collectors.toList()), 
				CollectionUtil.isEmpty(stampRecordOutput.getOutingTime()) ? Collections.emptyList() : stampRecordOutput.getOutingTime().stream().map(x -> TimePlaceDto.fromDomain(x)).collect(Collectors.toList()), 
				CollectionUtil.isEmpty(stampRecordOutput.getSupportTime()) ? Collections.emptyList() : stampRecordOutput.getSupportTime().stream().map(x -> TimePlaceDto.fromDomain(x)).collect(Collectors.toList()), 
				CollectionUtil.isEmpty(stampRecordOutput.getParentingTime()) ? Collections.emptyList() : stampRecordOutput.getParentingTime().stream().map(x -> TimePlaceDto.fromDomain(x)).collect(Collectors.toList()), 
				CollectionUtil.isEmpty(stampRecordOutput.getExtraordinaryTime()) ? Collections.emptyList() : stampRecordOutput.getExtraordinaryTime().stream().map(x -> TimePlaceDto.fromDomain(x)).collect(Collectors.toList()));
	}
	
	public StampRecordOutput toDomain() {
		return new StampRecordOutput(
				nursingTime.stream().map(x -> x.toDomain()).collect(Collectors.toList()), 
				breakTime.stream().map(x -> x.toDomain()).collect(Collectors.toList()), 
				workingTime.stream().map(x -> x.toDomain()).collect(Collectors.toList()), 
				outingTime.stream().map(x -> x.toDomain()).collect(Collectors.toList()), 
				supportTime.stream().map(x -> x.toDomain()).collect(Collectors.toList()), 
				parentingTime.stream().map(x -> x.toDomain()).collect(Collectors.toList()), 
				extraordinaryTime.stream().map(x -> x.toDomain()).collect(Collectors.toList()));
	}
}
