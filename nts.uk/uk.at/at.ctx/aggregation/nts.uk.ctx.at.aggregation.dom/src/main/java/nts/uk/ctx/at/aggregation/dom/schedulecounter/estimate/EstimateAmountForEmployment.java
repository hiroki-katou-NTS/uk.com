package nts.uk.ctx.at.aggregation.dom.schedulecounter.estimate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;
/**
 * 雇用の目安金額
 * @author lan_lt
 *
 */
@AllArgsConstructor
@Getter
public class EstimateAmountForEmployment implements  DomainAggregate{
	
	/** 雇用コード*/
	private final  EmploymentCode employmentCode;
	
	/**　目安金額詳細　*/
	private EstimateAmountDetail detail;
	
	/**
	 * 変更する
	 * @param detail 目安金額詳細
	 */
	public void update(EstimateAmountDetail detail) {
		this.detail = detail;
	}
}
