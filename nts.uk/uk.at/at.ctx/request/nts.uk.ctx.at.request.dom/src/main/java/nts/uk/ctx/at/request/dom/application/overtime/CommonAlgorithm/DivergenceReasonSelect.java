package nts.uk.ctx.at.request.dom.application.overtime.CommonAlgorithm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.dom.DomainObject;


/**
 * The Class DivergenceReasonSelect.
 */
//乖離理由の選択肢
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DivergenceReasonSelect extends DomainObject {

	/** The reason code. */
	// 乖離理由コード
	DivergenceReasonCode divergenceReasonCode;

	/** The reason. */
	// 乖離理由
	DivergenceReason reason;

	/** Reason required. */
	// 乖離理由の入力を必須とする
	DivergenceInputRequired reasonRequired;


}
