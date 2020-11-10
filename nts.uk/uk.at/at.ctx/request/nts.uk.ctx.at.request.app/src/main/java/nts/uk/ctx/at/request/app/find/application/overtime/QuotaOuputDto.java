package nts.uk.ctx.at.request.app.find.application.overtime;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.dom.application.overtime.CommonAlgorithm.QuotaOuput;

@AllArgsConstructor
@NoArgsConstructor
public class QuotaOuputDto {
	// フレックス時間表示区分
	public Boolean flexTimeClf;
	// 残業枠一覧
	public List<OvertimeWorkFrameDto> overTimeQuotaList;
	
	public static QuotaOuputDto fromDomain(QuotaOuput quotaOuput) {
		
		return new QuotaOuputDto(
				quotaOuput.getFlexTimeClf(),
				quotaOuput
					.getOverTimeQuotaList()
					.stream()
					.map(x -> OvertimeWorkFrameDto.fromDomain(x))
					.collect(Collectors.toList())
				);
	}
}
