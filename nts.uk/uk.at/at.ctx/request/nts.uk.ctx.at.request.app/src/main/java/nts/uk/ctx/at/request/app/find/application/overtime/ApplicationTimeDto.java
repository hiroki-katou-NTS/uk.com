package nts.uk.ctx.at.request.app.find.application.overtime;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.dom.application.overtime.ApplicationTime;

@AllArgsConstructor
@NoArgsConstructor
public class ApplicationTimeDto {
	// 申請時間
	public List<OvertimeApplicationSettingDto> applicationTime = Collections.emptyList();
	// フレックス超過時間
	public Integer flexOverTime;
	// 就業時間外深夜時間
	public OverTimeShiftNightDto overTimeShiftNight;
	// 任意項目
	public List<AnyItemValueDto> anyItem;
	// 乖離理由
	public List<ReasonDivergenceDto> reasonDissociation;
	
	public static ApplicationTimeDto fromDomain(ApplicationTime applicationTime) {
		if (applicationTime == null) return null;
		return new ApplicationTimeDto(
				applicationTime.getApplicationTime()
					.stream()
					.map(x -> OvertimeApplicationSettingDto.fromDomain(x))
					.collect(Collectors.toList()),
				applicationTime.getFlexOverTime().map(x -> x.v()).orElse(null),
				OverTimeShiftNightDto.fromDomain(applicationTime.getOverTimeShiftNight().orElse(null)),
				applicationTime.getAnyItem()
					.orElse(Collections.emptyList())
					.stream()
					.map(x -> AnyItemValueDto.fromDomain(x))
					.collect(Collectors.toList()),
				applicationTime.getReasonDissociation()
					.orElse(Collections.emptyList())
					.stream()
					.map(x -> ReasonDivergenceDto.fromDomain(x))
					.collect(Collectors.toList()));
	}
}
