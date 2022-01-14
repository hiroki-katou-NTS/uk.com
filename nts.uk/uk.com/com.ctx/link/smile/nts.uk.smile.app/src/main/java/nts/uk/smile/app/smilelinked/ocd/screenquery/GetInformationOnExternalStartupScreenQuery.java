package nts.uk.smile.app.smilelinked.ocd.screenquery;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.smile.dom.smilelinked.cooperationoutput.EmploymentAndLinkedMonthSetting;
import nts.uk.smile.dom.smilelinked.cooperationoutput.LinkedPaymentConversionRepository;
import nts.uk.smile.dom.smilelinked.cooperationoutput.PaymentCategory;
import nts.uk.smile.dom.smilelinked.cooperationoutput.SmileLinkageOutputSetting;
import nts.uk.smile.dom.smilelinked.cooperationoutput.SmileLinkageOutputSettingRepository;

@Stateless
public class GetInformationOnExternalStartupScreenQuery {

	@Inject
	private SelectAPaymentDateScreenQuery selectAPaymentDateScreenQuery;
	
	@Inject
	private SmileLinkageOutputSettingRepository smileLinkageOutputSettingRepository;
	
	@Inject
	private LinkedPaymentConversionRepository linkedPaymentConversionRepository;
	
	public void get(Integer paymentCode) {
		/**
		 * Function: 会社IDを指定してSM連携出力設定を取得する
		 * Input: 契約コード、会社ID
		 * Return: Object＜Smile連携出力設定＞
		 */
		String contractCode = AppContexts.user().contractCode();
		String companyId = AppContexts.user().companyId();
		SmileLinkageOutputSetting smileLinkageOutputSetting = smileLinkageOutputSettingRepository.get(contractCode, companyId);
		
		/**
		 * Function: 会社IDを指定して連動支払変換を取得する
		 * Input: 契約コード、会社ID
		 * Return: List＜出力条件設定（定型）＞
		 */
		List<EmploymentAndLinkedMonthSetting> listStandardOutputConditionSetting = linkedPaymentConversionRepository.get(contractCode, companyId);
		
		/**
		 * Function 雇用を取得する(契約コード, 会社ID, int) 
		 * Return List＜雇用選択DTO＞
		 */
		PaymentCategory paymentCategory = EnumAdaptor.valueOf(paymentCode, PaymentCategory.class);
		EmploymentChoiceDto employmentChoiceDto = selectAPaymentDateScreenQuery.get(paymentCode);
	}
}
