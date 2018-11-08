package nts.uk.ctx.pr.transfer.dom.emppaymentinfo;

import java.util.List;
import java.util.Optional;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

/**
 * 
 * @author HungTT - 社員賞与支払方法
 *
 */

@Getter
public class EmployeeBonusPaymentMethod extends AggregateRoot {

	/**
	 * 履歴ID
	 */
	private String historyId;

	/**
	 * 個別設定区分
	 */
	private IndividualSettingAtr settingAtr;

	/***
	 * 支払方法詳細
	 */
	private Optional<List<PaymentMethodDetail>> listPaymentMethod;

	@Override
	public void validate() {
		super.validate();
		if (this.settingAtr == IndividualSettingAtr.PAY_WITH_INDIVIDUAL_SETTING) {
			PaymentMethodDetail.validate(listPaymentMethod.get());
		}
	}

	public EmployeeBonusPaymentMethod(String historyId, boolean settingAtr, List<PaymentMethodDetail> listPaymentMethod) {
		super();
		this.historyId = historyId;
		this.settingAtr = IndividualSettingAtr.of(settingAtr);
		if (this.settingAtr == IndividualSettingAtr.PAY_WITH_INDIVIDUAL_SETTING)
			this.listPaymentMethod = Optional.ofNullable(listPaymentMethod);
		else
			this.listPaymentMethod = Optional.empty();
	}

}
