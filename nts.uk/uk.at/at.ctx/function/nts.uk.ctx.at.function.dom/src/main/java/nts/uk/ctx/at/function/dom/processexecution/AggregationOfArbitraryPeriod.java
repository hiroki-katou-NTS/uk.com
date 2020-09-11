package nts.uk.ctx.at.function.dom.processexecution;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.shared.dom.ot.frame.NotUseAtr;

/**
 * 任意期間の集計
 * @author ngatt-nws
 *
 */
@Getter
@Setter
@AllArgsConstructor
public class AggregationOfArbitraryPeriod extends DomainObject {

	/* 使用区分 */
	private NotUseAtr classificationOfUse;
	
	/* コード */
	private Optional<ExternalAcceptanceConditionCode> code;
}
