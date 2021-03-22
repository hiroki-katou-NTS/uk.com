package nts.uk.ctx.at.request.app.find.application.overtime.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.app.find.application.overtime.DisplayInfoOverTimeDto;
import nts.uk.ctx.at.request.dom.application.overtime.service.DetailOutput;

@AllArgsConstructor
@NoArgsConstructor
public class DetailOutputDto {
	// 残業申請の表示情報
	public DisplayInfoOverTimeDto displayInfoOverTime;
	// 残業申請
	public AppOverTimeDto appOverTime;
	
	public static DetailOutputDto fromDomain(DetailOutput detail) {
		return new DetailOutputDto(
				DisplayInfoOverTimeDto.fromDomain(detail.getDisplayInfoOverTime()),
				AppOverTimeDto.fromDomain(detail.getAppOverTime()));
	}
}
