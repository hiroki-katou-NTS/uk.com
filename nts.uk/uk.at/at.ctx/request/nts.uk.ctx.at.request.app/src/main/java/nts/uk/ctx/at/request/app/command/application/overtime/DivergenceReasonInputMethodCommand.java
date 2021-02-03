package nts.uk.ctx.at.request.app.command.application.overtime;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.request.dom.application.overtime.CommonAlgorithm.DivergenceReasonInputMethod;

public class DivergenceReasonInputMethodCommand {
	// 乖離時間NO
	public Integer divergenceTimeNo;

	// 会社ID
	public String companyId;

	// 乖離理由を入力する
	public Boolean divergenceReasonInputed;

	// 乖離理由を選択肢から選ぶ
	public Boolean divergenceReasonSelected;

	// 乖離理由の選択肢
	public List<DivergenceReasonSelectCommand> reasons;
	
	
	public DivergenceReasonInputMethod toDomain() {

		return new DivergenceReasonInputMethod(
				divergenceTimeNo,
				companyId,
				divergenceReasonInputed,
				divergenceReasonSelected,
				CollectionUtil.isEmpty(reasons) 
				? Collections.emptyList() : 
					reasons.stream()
						   .map(x -> x.toDomain())
						   .collect(Collectors.toList()));
	}
}
