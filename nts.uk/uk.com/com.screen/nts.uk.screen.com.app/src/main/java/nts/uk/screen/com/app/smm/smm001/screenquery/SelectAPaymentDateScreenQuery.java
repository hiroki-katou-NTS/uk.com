package nts.uk.screen.com.app.smm.smm001.screenquery;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.link.smile.dom.smilelinked.cooperationoutput.EmploymentAndLinkedMonthSetting;
import nts.uk.ctx.link.smile.dom.smilelinked.cooperationoutput.LinkedPaymentConversionRepository;
import nts.uk.ctx.link.smile.dom.smilelinked.cooperationoutput.PaymentCategory;
import nts.uk.shr.com.context.AppContexts;

/**
 * UKDesign.UniversalK.就業.SMM_Smile連携.SMM001_SMILE連携外部受入出力_詳細設定.B:SMILE連携外部出力詳細設定.支払日を選択する
 *
 */
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
@Stateless
public class SelectAPaymentDateScreenQuery {

	@Inject
	private LinkedPaymentConversionRepository linkedPaymentConversionRepository;

	/**
	 * 
	 * @param paymentCode
	 * @return EmploymentChoiceDto
	 */
	public EmploymentChoiceDto get(Integer paymentCode) {
		String contractCode = AppContexts.user().contractCode();
		String companyId = AppContexts.user().companyId();

		/**
		 * Function: 支払コードを指定して連動支払変換を取得する Input: 契約コード、会社ID、支払コード Return :
		 * 支払日指定したList＜雇用と連動月設定＞
		 */
		PaymentCategory paymentCategory = EnumAdaptor.valueOf(paymentCode, PaymentCategory.class);
		List<EmploymentAndLinkedMonthSetting> employmentListWithSpecifiedCompany = linkedPaymentConversionRepository
				.getByPaymentCode(contractCode, companyId, paymentCategory);

		/**
		 * Function: 会社を指定して連動支払変換を取得する Input: 契約コード、会社ID Return: 会社指定したList＜雇用と連動月設定＞
		 */
		List<EmploymentAndLinkedMonthSetting> employmentListWithSpecifiedPaymentDate = linkedPaymentConversionRepository
				.get(contractCode, companyId);

		return new EmploymentChoiceDto(employmentListWithSpecifiedCompany, employmentListWithSpecifiedPaymentDate);
	}
}
