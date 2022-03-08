package nts.uk.screen.com.app.smm.smm001.screenquery;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.bs.employee.dom.employment.EmploymentRepository;
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

	@Inject
	private EmploymentRepository employmentRepository;

	/**
	 * get
	 * 
	 * @param paymentCode
	 * @return EmploymentChoiceDto
	 */
	public EmploymentChoiceDto get(Integer paymentCode) {
		String contractCode = AppContexts.user().contractCode();
		String companyId = AppContexts.user().companyId();

		// 支払コードを指定して連動支払変換を取得する
		PaymentCategory paymentCategory = EnumAdaptor.valueOf(paymentCode, PaymentCategory.class);
		List<EmploymentAndLinkedMonthSettingDto> employmentListWithSpecifiedCompany = linkedPaymentConversionRepository
				.getByPaymentCode(contractCode, companyId, paymentCategory).stream()
				.map(data -> new EmploymentAndLinkedMonthSettingDto(data.getInterlockingMonthAdjustment().value,
						data.getScd()))
				.collect(Collectors.toList());

		// 会社を指定して連動支払変換を取得する
		List<EmploymentAndLinkedMonthSettingDto> employmentListWithSpecifiedPaymentDate = linkedPaymentConversionRepository
				.get(contractCode, companyId).stream()
				.map(data -> new EmploymentAndLinkedMonthSettingDto(data.getInterlockingMonthAdjustment().value,
						data.getScd()))
				.collect(Collectors.toList());

		// 会社IDから雇用を取得する
		List<EmploymentDto> employments = this.employmentRepository.findAll(companyId).stream()
				.map(data -> new EmploymentDto(data.getEmploymentCode().v(), data.getEmploymentName().v()))
				.sorted(Comparator.comparing(EmploymentDto::getEmploymentCode)).collect(Collectors.toList());

		return new EmploymentChoiceDto(employmentListWithSpecifiedCompany, employmentListWithSpecifiedPaymentDate,
				employments);
	}
}
