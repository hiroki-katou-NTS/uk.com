package nts.uk.ctx.at.request.app.find.application;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.request.dom.application.ReflectionStatus;

/**
 * refactor 4
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class ReflectionStatusDto {
	/**
	 * 対象日の反映状態
	 */
	private List<ReflectionStatusOfDayDto> listReflectionStatusOfDay;
	
	public static ReflectionStatusDto fromDomain(ReflectionStatus reflectionStatus) {
		return new ReflectionStatusDto(reflectionStatus.getListReflectionStatusOfDay().stream().map(x -> ReflectionStatusOfDayDto.fromDomain(x)).collect(Collectors.toList()));
	}
	
	public ReflectionStatus toDomain() {
		return new ReflectionStatus(listReflectionStatusOfDay.stream().map(x -> x.toDomain()).collect(Collectors.toList()));
	}
}
