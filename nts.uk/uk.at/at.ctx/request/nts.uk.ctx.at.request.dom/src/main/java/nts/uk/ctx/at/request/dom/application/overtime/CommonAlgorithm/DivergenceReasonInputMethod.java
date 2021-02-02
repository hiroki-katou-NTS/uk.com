package nts.uk.ctx.at.request.dom.application.overtime.CommonAlgorithm;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;

/**
 * The Class DivergenceReasonInputMethod.
 */
//乖離理由の入力方法
@Getter
@Setter
@AllArgsConstructor
public class DivergenceReasonInputMethod extends AggregateRoot {

	/** The divergence time no. */
	// 乖離時間NO
	private int divergenceTimeNo;

	/** The companyId. */
	// 会社ID
	private String companyId;

	/** The divergence reason inputed. */
	// 乖離理由を入力する
	private boolean divergenceReasonInputed;

	/** The divergence reason selected. */
	// 乖離理由を選択肢から選ぶ
	private boolean divergenceReasonSelected;

	/** The reason list. */
	// 乖離理由の選択肢
	private List<DivergenceReasonSelect> reasons;

	
}
