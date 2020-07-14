package nts.uk.ctx.at.request.app.find.application.lateorleaveearly;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import nts.uk.ctx.at.request.app.find.application.common.ApplicationDto_New;
import nts.uk.ctx.at.request.dom.application.Application_New;
import nts.uk.ctx.at.request.dom.application.lateorleaveearly.ArrivedLateLeaveEarly_Old;

@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
@Data
//遅刻早退取消申請
public class ArrivedLateLeaveEarlyDto extends ApplicationDto_New{
//	取消
	private List<LateCancelationDto> lateCancelation;
//	時刻報告
	private List<TimeReportDto> lateOrLeaveEarlies;
	
	public static ArrivedLateLeaveEarlyDto convertDto(Application_New app) {
		ApplicationDto_New x = ApplicationDto_New.fromDomain(app);
		ArrivedLateLeaveEarlyDto y = (ArrivedLateLeaveEarlyDto)x;
		y.setLateCancelation(
				((ArrivedLateLeaveEarly_Old)app).getLateCancelation().stream().map(item -> LateCancelationDto.convertDto(item)).collect(Collectors.toList())
				);
		y.setLateOrLeaveEarlies(
				((ArrivedLateLeaveEarly_Old)app).getLateOrLeaveEarlies().stream().map(item -> TimeReportDto.convertDto(item)).collect(Collectors.toList())

				);
		return y;		
	}
	
}
