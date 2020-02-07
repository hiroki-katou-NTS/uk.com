package nts.uk.ctx.pr.transfer.dom.emppaymentinfo;

import java.util.List;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

/**
 * 
 * @author HungTT - 社員給与支払方法
 *
 */

@Getter
public class EmployeeSalaryPaymentMethod extends AggregateRoot {

	/**
	 * 履歴ID
	 */
	private String historyId;

	/***
	 * 支払方法詳細
	 */
	private List<PaymentMethodDetail> listPaymentMethod;

	@Override
	public void validate() {
		super.validate();
		PaymentMethodDetail.validate(this.listPaymentMethod);
	}

	public EmployeeSalaryPaymentMethod(String historyId, List<PaymentMethodDetail> listPaymentMethod) {
		super();
		this.historyId = historyId;
		this.listPaymentMethod = listPaymentMethod;
	}

}
