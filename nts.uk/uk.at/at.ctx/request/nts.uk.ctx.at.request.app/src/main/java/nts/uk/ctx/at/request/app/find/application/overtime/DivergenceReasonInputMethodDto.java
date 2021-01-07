package nts.uk.ctx.at.request.app.find.application.overtime;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.dom.application.overtime.CommonAlgorithm.DivergenceReasonInputMethod;

@AllArgsConstructor
@NoArgsConstructor
public class DivergenceReasonInputMethodDto {
	// 乖離時間NO
	public Integer divergenceTimeNo;

	// 会社ID
	public String companyId;

	// 乖離理由を入力する
	public Boolean divergenceReasonInputed;

	// 乖離理由を選択肢から選ぶ
	public Boolean divergenceReasonSelected;

	// 乖離理由の選択肢
	public List<DivergenceReasonSelectDto> reasons;
	
	public static DivergenceReasonInputMethodDto fromDomain(DivergenceReasonInputMethod divergenceReasonInputMethod) {
		return new DivergenceReasonInputMethodDto(
				divergenceReasonInputMethod.getDivergenceTimeNo(),
				divergenceReasonInputMethod.getCompanyId(),
				divergenceReasonInputMethod.isDivergenceReasonInputed(),
				divergenceReasonInputMethod.isDivergenceReasonSelected(),
				divergenceReasonInputMethod.getReasons()
					.stream()
					.map(x -> DivergenceReasonSelectDto.fromDomain(x))
					.collect(Collectors.toList())
					);
	}
	
}
