package nts.uk.screen.com.app.smm.smm001.screenquery;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

//import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.bs.employee.dom.employment.Employment;
import nts.uk.ctx.bs.employee.dom.employment.EmploymentRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.smile.dom.smilelinked.cooperationoutput.EmploymentAndLinkedMonthSetting;
import nts.uk.smile.dom.smilelinked.cooperationoutput.LinkedPaymentConversionRepository;
//import nts.uk.smile.dom.smilelinked.cooperationoutput.PaymentCategory;
import nts.uk.smile.dom.smilelinked.cooperationoutput.SmileLinkageOutputSetting;
import nts.uk.smile.dom.smilelinked.cooperationoutput.SmileLinkageOutputSettingRepository;

@Stateless
public class GetInformationOnExternalStartupScreenQuery {

//	@Inject
//	private SelectAPaymentDateScreenQuery selectAPaymentDateScreenQuery;

	@Inject
	private SmileLinkageOutputSettingRepository smileLinkageOutputSettingRepository;

	@Inject
	private LinkedPaymentConversionRepository linkedPaymentConversionRepository;

	@Inject
	private EmploymentRepository employmentRepository;

	public InitialStartupOutputDto get(Integer paymentCode) {
		/**
		 * Function: 会社IDを指定してSM連携出力設定を取得する Input: 契約コード、会社ID Return:
		 * Object＜Smile連携出力設定＞
		 */
		String contractCode = AppContexts.user().contractCode();
		String companyId = AppContexts.user().companyId();
		SmileLinkageOutputSetting smileLinkageOutputSetting = smileLinkageOutputSettingRepository.get(contractCode,
				companyId);
		SmileLinkageOutputSettingDto smileLinkageOutputSettingDto = new SmileLinkageOutputSettingDto(
				smileLinkageOutputSetting.getSalaryCooperationClassification().value,
				smileLinkageOutputSetting.getMonthlyLockClassification().value,
				smileLinkageOutputSetting.getMonthlyApprovalCategory().value,
				smileLinkageOutputSetting.getSalaryCooperationConditions().isPresent()
						? smileLinkageOutputSetting.getSalaryCooperationConditions().get().v()
						: null);

		/**
		 * Function: 会社IDを指定して連動支払変換を取得する Input: 契約コード、会社ID Return: List＜出力条件設定（定型）＞
		 */
		List<EmploymentAndLinkedMonthSetting> listStandardOutputConditionSetting = linkedPaymentConversionRepository
				.get(contractCode, companyId);
		List<EmploymentAndLinkedMonthSettingDto> listStandardOutputConditionSettingDtos = listStandardOutputConditionSetting
				.stream()
				.map(e -> new EmploymentAndLinkedMonthSettingDto(e.getInterlockingMonthAdjustment().value, e.getScd()))
				.collect(Collectors.toList());

		/**
		 * List＜雇用コード、雇用名称＞
		 */
		List<Employment> employments = employmentRepository.findAll(companyId);
		List<EmploymentDto> employmentDtos = employments.stream()
				.map(e -> new EmploymentDto(e.getEmploymentCode().v(), e.getEmploymentName().v()))
				.collect(Collectors.toList());

//		/**
//		 * Function 雇用を取得する(契約コード, 会社ID, int) Return List＜雇用選択DTO＞
//		 */
//		PaymentCategory paymentCategory = EnumAdaptor.valueOf(paymentCode, PaymentCategory.class);
//		EmploymentChoiceDto employmentChoiceDto = selectAPaymentDateScreenQuery.get(paymentCode);

		return new InitialStartupOutputDto(smileLinkageOutputSettingDto, listStandardOutputConditionSettingDtos,
				employmentDtos);
	}
}
