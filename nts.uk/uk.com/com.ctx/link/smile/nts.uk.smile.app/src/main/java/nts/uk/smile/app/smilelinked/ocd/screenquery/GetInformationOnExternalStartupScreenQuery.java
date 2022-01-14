package nts.uk.smile.app.smilelinked.ocd.screenquery;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.uk.smile.dom.smilelinked.cooperationoutput.PaymentCategory;

@Stateless
public class GetInformationOnExternalStartupScreenQuery {

	@Inject
	private SelectAPaymentDateScreenQuery selectAPaymentDateScreenQuery;
	
	public void get(Integer paymentCode) {
		/**
		 * Function 雇用を取得する(契約コード, 会社ID, int) 
		 * Return List＜雇用選択DTO＞
		 */
		PaymentCategory paymentCategory = EnumAdaptor.valueOf(paymentCode, PaymentCategory.class);
		EmploymentChoiceDto employmentChoiceDto = selectAPaymentDateScreenQuery.get(paymentCode);
	}
}
