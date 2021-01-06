package nts.uk.ctx.at.record.pub.application.divergence;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.divergence.time.reason.DivergenceReasonSelect;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DivergenceReasonInputMethodExport {
	
	// 乖離時間NO
	private Integer divergenceTimeNo;

	// 会社ID
	private String companyId;

	// 乖離理由を入力する
	private Boolean divergenceReasonInputed;

	// 乖離理由を選択肢から選ぶ
	private Boolean divergenceReasonSelected;

	// 乖離理由の選択肢
	private List<DivergenceReasonSelect> reasons;
}
