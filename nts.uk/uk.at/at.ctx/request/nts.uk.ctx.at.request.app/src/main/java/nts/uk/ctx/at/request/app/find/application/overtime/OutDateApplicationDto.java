package nts.uk.ctx.at.request.app.find.application.overtime;

import java.util.List;
import java.util.stream.Collectors;

import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.dom.application.overtime.OutDateApplication;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class OutDateApplicationDto {
	// ﾌﾚｯｸｽの超過状態
	public Integer flex;
	// 休出深夜時間
	public List<ExcessStateMidnightDto> excessStateMidnight;
	// 残業深夜の超過状態
	public Integer overTimeLate;
	// 申請時間
	public List<ExcessStateDetailDto> excessStateDetail;
	
	public static OutDateApplicationDto fromDomain(OutDateApplication outDateApplication) {
		
		return new OutDateApplicationDto(
				outDateApplication.getFlex().value,
				outDateApplication.getExcessStateMidnight()
					.stream()
					.map(x -> ExcessStateMidnightDto.fromDomain(x))
					.collect(Collectors.toList()),
					outDateApplication.getOverTimeLate().value,
				outDateApplication.getExcessStateDetail()
					.stream()
					.map(x -> ExcessStateDetailDto.fromDomain(x))
					.collect(Collectors.toList()));
	}
}
