package nts.uk.ctx.at.request.app.find.application.overtime;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.deviationtime.deviationtimeframe.DivergenceTimeRoot;

@AllArgsConstructor
@NoArgsConstructor
public class DivergenceTimeRootDto {
	// 乖離時間NO
	public Integer divergenceTimeNo;

	// 会社ID
	public String companyId;

	// 使用区分
	public Integer divTimeUseSet;

	// 乖離時間名称
	public String divTimeName;

	// 乖離の種類
	public Integer divType;

	// 乖離時間のエラーの解除方法
	public DivergenceTimeErrorCancelMethodDto errorCancelMedthod;

	// 対象項目一覧
	public List<Integer> targetItems;
	
	public static DivergenceTimeRootDto fromDomain(DivergenceTimeRoot divergenceTimeRoot) {
		
		return new DivergenceTimeRootDto(
				divergenceTimeRoot.getDivergenceTimeNo(),
				divergenceTimeRoot.getCompanyId(),
				divergenceTimeRoot.getDivTimeUseSet().value,
				divergenceTimeRoot.getDivTimeName().v(),
				divergenceTimeRoot.getDivType().value,
				DivergenceTimeErrorCancelMethodDto.fromDomain(divergenceTimeRoot.getErrorCancelMedthod()),
				divergenceTimeRoot.getTargetItems());
	}
}
