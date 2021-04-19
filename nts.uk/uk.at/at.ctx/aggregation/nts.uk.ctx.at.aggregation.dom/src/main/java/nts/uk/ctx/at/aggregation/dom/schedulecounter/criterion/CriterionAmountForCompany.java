package nts.uk.ctx.at.aggregation.dom.schedulecounter.criterion;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainAggregate;

/**
 * 会社の目安金額
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.予実集計.スケジュール集計.目安金額.会社の目安金額
 * @author lan_lt
 *
 */
@Getter
@AllArgsConstructor
public class CriterionAmountForCompany implements DomainAggregate {

	/** 目安金額 */
	private CriterionAmount criterionAmount;

	/**
	 * 変更する
	 * @param criterion 目安金額
	 */
	public void update(CriterionAmount criterion) {
		this.criterionAmount = criterion;
	}

}
