package nts.uk.ctx.at.aggregation.dom.schedulecounter.criterion;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;

/**
 * 雇用の目安金額
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.予実集計.スケジュール集計.目安金額.雇用の目安金額
 * @author lan_lt
 *
 */
@AllArgsConstructor
@Getter
public class CriterionAmountForEmployment implements  DomainAggregate {

	/** 雇用コード */
	private final  EmploymentCode employmentCode;

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
