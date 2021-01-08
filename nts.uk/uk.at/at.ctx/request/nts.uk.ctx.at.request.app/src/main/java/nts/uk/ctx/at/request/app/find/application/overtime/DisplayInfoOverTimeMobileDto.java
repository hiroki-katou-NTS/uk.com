package nts.uk.ctx.at.request.app.find.application.overtime;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.app.find.application.overtime.dto.AppOverTimeDto;
import nts.uk.ctx.at.request.dom.application.overtime.service.DisplayInfoOverTimeMobile;

@AllArgsConstructor
@NoArgsConstructor
public class DisplayInfoOverTimeMobileDto {
	
	public DisplayInfoOverTimeDto displayInfoOverTime;
	
	// Optional
	public AppOverTimeDto appOverTime;
	
	
	public static DisplayInfoOverTimeMobileDto fromDomain(DisplayInfoOverTimeMobile disMobile) {
		return new DisplayInfoOverTimeMobileDto(
				DisplayInfoOverTimeDto.fromDomain(disMobile.getDisplayInfoOverTime()),
				disMobile.getAppOverTimeOp().isPresent() ? AppOverTimeDto.fromDomain(disMobile.getAppOverTimeOp().get()) : null
				);
	}
}
