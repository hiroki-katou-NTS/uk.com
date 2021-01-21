package nts.uk.ctx.at.request.app.find.application.lateorleaveearly;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.app.find.application.ApplicationDto;
import nts.uk.ctx.at.request.dom.application.lateleaveearly.ArrivedLateLeaveEarly;
import nts.uk.ctx.at.request.dom.application.lateorleaveearly.ArrivedLateLeaveEarly_Old;

@AllArgsConstructor
@NoArgsConstructor
@Data
// 遅刻早退取消申請
public class ArrivedLateLeaveEarlyDto {
	// 取消
	private List<LateCancelationDto> lateCancelation;
	// 時刻報告
	private List<TimeReportDto> lateOrLeaveEarlies;

	public static ArrivedLateLeaveEarlyDto convertDto(ArrivedLateLeaveEarly app) {
		ArrivedLateLeaveEarlyDto apArrivedLateLeaveEarlyDto = new ArrivedLateLeaveEarlyDto();
		apArrivedLateLeaveEarlyDto.setLateCancelation(app.getLateCancelation().stream()
				.map(item -> LateCancelationDto.fromDomain(item)).collect(Collectors.toList()));
		apArrivedLateLeaveEarlyDto.setLateOrLeaveEarlies(app.getLateOrLeaveEarlies().stream()
				.map(x -> TimeReportDto.fromDomain(x)).collect(Collectors.toList()));
		return apArrivedLateLeaveEarlyDto;
	}
	
	public ArrivedLateLeaveEarly convertDomain() {
		ArrivedLateLeaveEarly dto = new ArrivedLateLeaveEarly();
		dto.setLateCancelation(this.lateCancelation.stream().map(x -> x.toDomain()).collect(Collectors.toList()));
		dto.setLateOrLeaveEarlies(this.lateOrLeaveEarlies.stream().map(x -> x.toDomain()).collect(Collectors.toList()));
		
		return dto;
	}
	
	public static ArrivedLateLeaveEarlyDto convertDto(ArrivedLateLeaveEarly_Old app) {
		return null;
	}

}
