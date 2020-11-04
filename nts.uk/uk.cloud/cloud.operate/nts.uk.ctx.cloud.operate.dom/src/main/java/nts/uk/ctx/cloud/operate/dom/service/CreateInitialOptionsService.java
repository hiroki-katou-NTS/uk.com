package nts.uk.ctx.cloud.operate.dom.service;

import java.util.List;

import javax.ejb.Stateless;

import nts.arc.task.tran.AtomTask;
import nts.uk.shr.com.license.option.purchasestate.OptionCode;
import nts.uk.shr.com.license.option.purchasestate.OptionLicensePurchaseState;
import nts.uk.shr.com.license.option.purchasestate.OptionType;

/**
 * 初期オプションを設定する
 * @author ai_muto
 *
 */
@Stateless
public class CreateInitialOptionsService {

	public static AtomTask create(Require require, String tenantCode, String optionCodeString) {
		OptionCode optionCode = new OptionCode(optionCodeString);
		List<OptionType> optionTypes = optionCode.restore();

		OptionLicensePurchaseState optionLicensePurchaseState =
			OptionLicensePurchaseState.create(tenantCode, optionTypes);

		// TODO:
		return AtomTask.of(() -> {
			require.add(optionLicensePurchaseState);
		});
	}

	public static interface Require {
		void add(OptionLicensePurchaseState optionLicensePurchaseState);
	}
}
