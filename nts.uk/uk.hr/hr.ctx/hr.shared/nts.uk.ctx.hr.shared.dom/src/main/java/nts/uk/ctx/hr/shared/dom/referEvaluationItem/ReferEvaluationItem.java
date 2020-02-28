package nts.uk.ctx.hr.shared.dom.referEvaluationItem;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.DomainObject;

/**
 * @author thanhpv
 * 評価参考情報
 */
@AllArgsConstructor
@Getter
public class ReferEvaluationItem extends DomainObject{

	/** 評価項目  */
	private EvaluationItem evaluationItem;
	
	/** 参照する */
	private boolean usageFlg;
	
	/** 評価表示数 */
	private Integer displayNum;
	
	/** 判断基準値 */
	private Optional<String> passValue;
	
	public static ReferEvaluationItem createFromJavaType(int evaluationItem, boolean usageFlg, Integer displayNum, String passValue) {
		return new ReferEvaluationItem(
				EnumAdaptor.valueOf(evaluationItem, EvaluationItem.class),
				usageFlg,
				displayNum,
				Optional.ofNullable(passValue)
				);
	}
}
