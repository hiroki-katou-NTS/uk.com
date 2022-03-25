package nts.uk.screen.com.app.find.smm001;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

//import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.bs.employee.dom.employment.Employment;
import nts.uk.ctx.bs.employee.dom.employment.EmploymentRepository;
import nts.uk.ctx.exio.dom.exo.condset.StdOutputCondSet;
import nts.uk.ctx.exio.dom.exo.condset.StdOutputCondSetRepository;
import nts.uk.ctx.link.smile.dom.smilelinked.cooperationoutput.EmploymentAndLinkedMonthSetting;
import nts.uk.ctx.link.smile.dom.smilelinked.cooperationoutput.LinkedPaymentConversionRepository;
import nts.uk.ctx.link.smile.dom.smilelinked.cooperationoutput.SmileLinkageOutputSetting;
import nts.uk.ctx.link.smile.dom.smilelinked.cooperationoutput.SmileLinkageOutputSettingRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * UKDesign.UniversalK.就業.SMM_Smile連携.SMM001_SMILE連携外部受入出力_詳細設定.B:SMILE連携外部出力詳細設定.外部出起動の情報取得する
 *
 */
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
@Stateless
public class GetExternalStartupInfoScreenQuery {

	@Inject
	private SmileLinkageOutputSettingRepository smileLinkageOutputSettingRepository;

	@Inject
	private LinkedPaymentConversionRepository linkedPaymentConversionRepository;

	@Inject
	private EmploymentRepository employmentRepository;

	@Inject
	StdOutputCondSetRepository condSetRepository;

	public InitialStartupOutputDto get(Integer paymentCode) {
		// Function: 会社IDを指定してSM連携出力設定を取得する 
		String contractCode = AppContexts.user().contractCode();
		String companyId = AppContexts.user().companyId();
		Optional<SmileLinkageOutputSetting> smileLinkageOutputSetting = smileLinkageOutputSettingRepository.get(contractCode,
				companyId);
		SmileLinkageOutputSettingDto smileLinkageOutputSettingDto = new SmileLinkageOutputSettingDto();
		if (smileLinkageOutputSetting.isPresent()) {
			smileLinkageOutputSettingDto = new SmileLinkageOutputSettingDto(
					smileLinkageOutputSetting.get().getSalaryCooperationClassification().value,
					smileLinkageOutputSetting.get().getMonthlyLockClassification().value,
					smileLinkageOutputSetting.get().getMonthlyApprovalCategory().value,
					smileLinkageOutputSetting.get().getSalaryCooperationConditions().isPresent()
							? smileLinkageOutputSetting.get().getSalaryCooperationConditions().get().v()
							: null);
		}

		// 会社IDを指定して連動支払変換を取得する
		List<StdOutputCondSet> outputConditionSettings = condSetRepository.getStdOutCondSetByCid(companyId);
		List<StdOutputCondSetDto> stdOutputCondSetDtos = outputConditionSettings.stream()
				.map(e -> new StdOutputCondSetDto(e.getConditionSetCode().v(), e.getConditionSetName().v()))
				.collect(Collectors.toList());

		List<EmploymentAndLinkedMonthSetting> listStandardOutputConditionSetting = linkedPaymentConversionRepository
				.get(contractCode, companyId);
		List<EmploymentAndLinkedMonthSettingDto> listStandardOutputConditionSettingDtos = listStandardOutputConditionSetting
				.stream()
				.map(e -> new EmploymentAndLinkedMonthSettingDto(e.getInterlockingMonthAdjustment().value, e.getScd()))
				.collect(Collectors.toList());

		// List＜雇用コード、雇用名称＞
		List<Employment> employments = employmentRepository.findAll(companyId);
		List<EmploymentDto> employmentDtos = employments.stream()
				.map(e -> new EmploymentDto(e.getEmploymentCode().v(), e.getEmploymentName().v()))
				.collect(Collectors.toList());
		return new InitialStartupOutputDto(smileLinkageOutputSettingDto, listStandardOutputConditionSettingDtos,
				employmentDtos, stdOutputCondSetDtos);
	}
}
