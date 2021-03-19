package nts.uk.ctx.at.aggregation.dom.schedulecounter.criterion;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainAggregate;

/**
 * 目安金額の扱い
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.予実集計.スケジュール集計.目安金額.目安金額の扱い
 * @author lan_lt
 *
 */
@AllArgsConstructor
@Getter
public class HandlingOfCriterionAmount implements DomainAggregate {

	/** 枠別の扱いリスト */
	private List<HandlingOfCriterionAmountByNo> list;

}
