package nts.uk.screen.com.app.find.ccg005.attendance.information.dto;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Builder;
import lombok.Data;
import nts.uk.ctx.at.request.dom.application.ReflectionStatus;

@Builder
@Data
public class ReflectionStatusDto {
	/**
	 * 対象日の反映状態
	 */
	private List<ReflectionStatusOfDayDto> listReflectionStatusOfDay;
	
	public static ReflectionStatusDto toDto (ReflectionStatus domain) {
		return ReflectionStatusDto.builder()
				.listReflectionStatusOfDay(domain.getListReflectionStatusOfDay().stream()
						.map(v -> ReflectionStatusOfDayDto.toDto(v))
						.collect(Collectors.toList()))
				.build();
	}
}
